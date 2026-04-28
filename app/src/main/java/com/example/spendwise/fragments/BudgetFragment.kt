package com.example.spendwise.fragments

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.spendwise.R
import com.example.spendwise.repositories.CurrencyRepository
import com.example.spendwise.viewmodels.BudgetViewModel
import com.google.android.material.textfield.TextInputEditText

class BudgetFragment : Fragment() {

    private val viewModel: BudgetViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_budget, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sym = CurrencyRepository(requireContext()).getCurrencySymbol()

        val tvBudget    = view.findViewById<TextView>(R.id.tv_budget_amount)
        val tvSpent     = view.findViewById<TextView>(R.id.tv_spent)
        val tvRemaining = view.findViewById<TextView>(R.id.tv_remaining)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_budget)
        val etBudget    = view.findViewById<TextInputEditText>(R.id.et_budget)
        val btnSet      = view.findViewById<Button>(R.id.btn_set_budget)

        viewModel.budget.observe(viewLifecycleOwner) { budget ->
            tvBudget.text = "$sym%.2f".format(budget)
            val spent = viewModel.spent.value ?: 0.0
            tvRemaining.text = "$sym%.2f".format(budget - spent)
            val pct = viewModel.getProgressPercent()
            progressBar.progress = pct
            progressBar.progressTintList = android.content.res.ColorStateList.valueOf(
                when {
                    pct >= 100 -> 0xFFE57373.toInt()
                    pct >= 90  -> 0xFFFFB74D.toInt()
                    pct >= 75  -> 0xFFFFD54F.toInt()
                    else       -> 0xFF81C784.toInt()
                }
            )
        }

        viewModel.spent.observe(viewLifecycleOwner) { spent ->
            tvSpent.text = "$sym%.2f".format(spent)
            val budget = viewModel.budget.value ?: 0.0
            tvRemaining.text = "$sym%.2f".format(budget - spent)
        }

        btnSet.setOnClickListener {
            val amount = etBudget.text.toString().trim().toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(context, "Enter a valid budget amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.setBudget(amount)
            etBudget.text?.clear()
            Toast.makeText(context, "Budget set to $sym%.2f".format(amount), Toast.LENGTH_SHORT).show()
        }

        viewModel.loadBudget()
    }

    override fun onResume() { super.onResume(); viewModel.loadBudget() }
}
