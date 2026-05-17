package com.example.spendwise.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.spendwise.R
import com.example.spendwise.XPSystem
import com.example.spendwise.repositories.AppRepository
import com.example.spendwise.repositories.CurrencyRepository
import com.example.spendwise.viewmodels.DashboardViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_dashboard, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sym  = CurrencyRepository(requireContext()).getCurrencySymbol()
        val repo = AppRepository(requireContext())

        val tvIncome      = view.findViewById<TextView>(R.id.tv_total_income)
        val tvExpenses    = view.findViewById<TextView>(R.id.tv_total_expenses)
        val tvNet         = view.findViewById<TextView>(R.id.tv_net_balance)
        val pieChart      = view.findViewById<PieChart>(R.id.pie_chart_expenses)
        val lineChart     = view.findViewById<LineChart>(R.id.line_chart)
        val tvXpTotal     = view.findViewById<TextView>(R.id.tv_xp_total)
        val tvBadge       = view.findViewById<TextView>(R.id.tv_level_badge)
        val tvLevelNum    = view.findViewById<TextView>(R.id.tv_level_number)
        val tvLevelTitle  = view.findViewById<TextView>(R.id.tv_level_title)
        val tvXpLabel     = view.findViewById<TextView>(R.id.tv_xp_progress_label)
        val pbXp          = view.findViewById<ProgressBar>(R.id.pb_xp)
        val llLevels      = view.findViewById<LinearLayout>(R.id.ll_levels_container)
        val tvEssential   = view.findViewById<TextView>(R.id.tv_essential_ratio)

        viewModel.expenses.observe(viewLifecycleOwner) { expenses ->
            val totalSpent = expenses.sumOf { it.amount }
            tvExpenses.text = "$sym%.2f".format(totalSpent)
            tvNet.text = "$sym%.2f".format(viewModel.getNetBalance())

            // Essential ratio label
            val essentialSpent = expenses.filter { it.isEssential() }.sumOf { it.amount }
            val essentialPct = if (totalSpent > 0) ((essentialSpent / totalSpent) * 100).toInt() else 0
            tvEssential.text = "🛒 $essentialPct% of spending on essential needs"

            val byCategory = expenses.groupBy { it.category }
                .map { (cat, list) -> PieEntry(list.sumOf { it.amount }.toFloat(), cat) }
            if (byCategory.isNotEmpty()) {
                val ds = PieDataSet(byCategory, "").apply {
                    colors = ColorTemplate.MATERIAL_COLORS.toList()
                    valueTextSize = 11f; valueTextColor = Color.WHITE
                }
                pieChart.data = PieData(ds)
                pieChart.description.isEnabled = false
                pieChart.setHoleColor(Color.TRANSPARENT)
                pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
                pieChart.invalidate()
            }

            // XP — now uses income as budget reference, driven by essential spend
            lifecycleScope.launch {
                val totalIncome = viewModel.getTotalIncome()
                val xp = XPSystem.calculateXP(expenses, totalIncome)
                val level = XPSystem.getLevelForXP(xp)
                val progress = XPSystem.getProgressPercent(xp)
                val nextLevel = XPSystem.levels.getOrNull(level.level)

                tvXpTotal.text   = "$xp XP"
                tvBadge.text     = level.badge
                tvLevelNum.text  = "Level ${level.level}"
                tvLevelTitle.text = level.title
                pbXp.progress    = progress
                tvXpLabel.text   = if (level.level < 5 && nextLevel != null)
                    "$xp / ${nextLevel.xp} XP to next level" else "Max level! 🎉"

                llLevels.removeAllViews()
                XPSystem.levels.forEach { lvl ->
                    val unlocked  = xp >= lvl.xp
                    val isCurrent = lvl.level == level.level
                    val row = LinearLayout(requireContext()).apply {
                        orientation = LinearLayout.HORIZONTAL
                        setPadding(if (isCurrent) 12 else 0, 8, if (isCurrent) 12 else 0, 8)
                        gravity = android.view.Gravity.CENTER_VERTICAL
                        if (isCurrent) setBackgroundColor(0x22E57373.toInt())
                    }
                    val emoji = TextView(requireContext()).apply {
                        text = if (unlocked) lvl.badge else "🔒"; textSize = 22f
                        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { marginEnd = 12 }
                    }
                    val col = LinearLayout(requireContext()).apply {
                        orientation = LinearLayout.VERTICAL
                        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                    }
                    col.addView(TextView(requireContext()).apply {
                        text = "Level ${lvl.level} — ${lvl.title}"; textSize = 13f
                        setTextColor(if (unlocked) 0xFFFFFFFF.toInt() else 0xFF888888.toInt())
                        if (isCurrent) setTypeface(null, android.graphics.Typeface.BOLD)
                    })
                    col.addView(TextView(requireContext()).apply {
                        text = lvl.unlockCondition; textSize = 11f; setTextColor(0xFF888888.toInt())
                    })
                    row.addView(emoji); row.addView(col)
                    row.addView(TextView(requireContext()).apply {
                        text = "${lvl.xp} XP"; textSize = 11f
                        setTextColor(if (unlocked) 0xFFE57373.toInt() else 0xFF555555.toInt())
                    })
                    llLevels.addView(row)
                    llLevels.addView(View(requireContext()).apply {
                        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1)
                        setBackgroundColor(0x22888888)
                    })
                }
            }
        }

        viewModel.incomes.observe(viewLifecycleOwner) { incomes ->
            tvIncome.text = "$sym%.2f".format(incomes.sumOf { it.amount })
            tvNet.text    = "$sym%.2f".format(viewModel.getNetBalance())

            val entries = incomes.takeLast(6).mapIndexed { i, inc -> Entry(i.toFloat(), inc.amount.toFloat()) }
            if (entries.isNotEmpty()) {
                val ds = LineDataSet(entries, "Income Trend").apply {
                    color = requireContext().getColor(R.color.green)
                    setCircleColor(requireContext().getColor(R.color.green))
                    lineWidth = 2f; circleRadius = 4f; valueTextSize = 10f
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                }
                lineChart.data = LineData(ds)
                lineChart.description.isEnabled = false
                lineChart.invalidate()
            }
        }

        viewModel.loadData()
    }

    override fun onResume() { super.onResume(); viewModel.loadData() }
}
