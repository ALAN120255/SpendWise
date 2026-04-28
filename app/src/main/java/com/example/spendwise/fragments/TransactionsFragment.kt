package com.example.spendwise.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spendwise.*
import com.example.spendwise.repositories.AppRepository
import com.example.spendwise.repositories.CurrencyRepository
import com.example.spendwise.utils.CameraHelper
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.util.*

class TransactionsFragment : Fragment() {

    private lateinit var repo: AppRepository
    private lateinit var adapter: TransactionAdapter
    private var allItems = listOf<TransactionItem>()
    private var selectedPhotoUri: Uri? = null
    private var selectedDateMillis = System.currentTimeMillis()
    private var cameraUri: Uri? = null

    // Pick from gallery
    private val pickPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectedPhotoUri = it
            updatePhotoUI()
        }
    }

    // Take with camera
    private val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && cameraUri != null) {
            selectedPhotoUri = cameraUri
            updatePhotoUI()
        }
    }

    private var currentPhotoLabel: TextView? = null
    private var currentPhotoPreview: ImageView? = null
    private var currentClearBtn: View? = null

    private fun updatePhotoUI() {
        currentPhotoLabel?.text = if (selectedPhotoUri != null) "📷 Photo attached" else ""
        currentPhotoPreview?.let { iv ->
            if (selectedPhotoUri != null) {
                iv.visibility = View.VISIBLE
                iv.setImageURI(selectedPhotoUri)
            } else {
                iv.visibility = View.GONE
            }
        }
        currentClearBtn?.visibility = if (selectedPhotoUri != null) View.VISIBLE else View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_transactions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repo = AppRepository(requireContext())
        val sym = CurrencyRepository(requireContext()).getCurrencySymbol()

        val rv        = view.findViewById<RecyclerView>(R.id.recycler_transactions)
        val etSearch  = view.findViewById<EditText>(R.id.et_search)
        val chipGroup = view.findViewById<ChipGroup>(R.id.chip_group_filter)
        val fab       = view.findViewById<FloatingActionButton>(R.id.fab_add)

        adapter = TransactionAdapter(emptyList(), sym) { item ->
            AlertDialog.Builder(requireContext())
                .setTitle("Delete Transaction")
                .setMessage("Delete ${item.category} - $sym%.2f?".format(item.amount))
                .setPositiveButton("Delete") { _, _ ->
                    lifecycleScope.launch {
                        if (item.isExpense) repo.deleteExpense(item.id)
                        else repo.deleteIncome(item.id)
                        loadTransactions(chipGroup, etSearch.text.toString())
                    }
                }
                .setNegativeButton("Cancel", null).show()
        }
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) { filterList(chipGroup, s.toString()) }
            override fun afterTextChanged(s: Editable?) {}
        })

        chipGroup.setOnCheckedStateChangeListener { _, _ -> filterList(chipGroup, etSearch.text.toString()) }
        fab.setOnClickListener { showAddDialog(sym) }
        loadTransactions(chipGroup, "")
    }

    private fun loadTransactions(chipGroup: ChipGroup, query: String) {
        lifecycleScope.launch {
            val expenses = repo.getExpenses().map {
                TransactionItem(it.id, it.amount, it.category, it.description, it.date, true, it.getCategoryIcon(), it.photoUrl)
            }
            val incomes = repo.getIncomes().map {
                TransactionItem(it.id, it.amount, it.category, it.description, it.date, false, it.getCategoryIcon(), "")
            }
            allItems = (expenses + incomes).sortedByDescending { it.date }
            filterList(chipGroup, query)
        }
    }

    private fun filterList(chipGroup: ChipGroup, query: String) {
        val checkedId = chipGroup.checkedChipId
        val filtered = allItems.filter { item ->
            val matchesFilter = when (checkedId) {
                R.id.chip_expenses -> item.isExpense
                R.id.chip_income   -> !item.isExpense
                else               -> true
            }
            val matchesQuery = query.isEmpty() ||
                item.category.contains(query, true) ||
                item.description.contains(query, true)
            matchesFilter && matchesQuery
        }
        adapter.updateItems(filtered)
    }

    private fun showAddDialog(sym: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_transaction, null)
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()

        val chipGroup   = dialogView.findViewById<ChipGroup>(R.id.chip_group_type)
        val etAmount    = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.et_amount)
        val etDesc      = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.et_description)
        val spinner     = dialogView.findViewById<Spinner>(R.id.spinner_category)
        val btnDate     = dialogView.findViewById<Button>(R.id.btn_select_date)
        val btnTakePhoto = dialogView.findViewById<Button>(R.id.btn_take_photo)
        val btnPickPhoto = dialogView.findViewById<Button>(R.id.btn_pick_photo)
        val btnClear    = dialogView.findViewById<Button>(R.id.btn_clear_photo)
        val layoutPhoto = dialogView.findViewById<View>(R.id.layout_photo)
        val tvPhotoLabel= dialogView.findViewById<TextView>(R.id.tv_has_photo)
        val ivPreview   = dialogView.findViewById<ImageView>(R.id.iv_expense_photo)
        val btnCancel   = dialogView.findViewById<Button>(R.id.btn_cancel)
        val btnSave     = dialogView.findViewById<Button>(R.id.btn_save)

        selectedPhotoUri = null
        selectedDateMillis = System.currentTimeMillis()

        // Sync helper variables for updatePhotoUI
        currentPhotoLabel = tvPhotoLabel
        currentPhotoPreview = ivPreview
        currentClearBtn = btnClear
        updatePhotoUI()

        val expCats = listOf("Food","Transport","Shopping","Health","Entertainment","Utilities","Rent","Education","Other")
        val incCats = listOf("Salary","Freelance","Investment","Gift","Rental","Other")

        fun updateSpinner(isExpense: Boolean) {
            val cats = if (isExpense) expCats else incCats
            spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, cats)
        }

        updateSpinner(true)

        val cal = Calendar.getInstance()
        btnDate.text = "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"

        btnDate.setOnClickListener {
            DatePickerDialog(requireContext(), { _, y, m, d ->
                selectedDateMillis = Calendar.getInstance().apply { set(y, m, d) }.timeInMillis
                btnDate.text = "$d/${m+1}/$y"
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        chipGroup.setOnCheckedStateChangeListener { _, ids ->
            val isExp = ids.contains(R.id.chip_expense)
            layoutPhoto.visibility = if (isExp) View.VISIBLE else View.GONE
            updateSpinner(isExp)
        }

        // Camera button
        btnTakePhoto.setOnClickListener {
            cameraUri = CameraHelper.createImageUri(requireContext())
            takePhoto.launch(cameraUri!!)
        }

        // Gallery button
        btnPickPhoto.setOnClickListener {
            pickPhoto.launch("image/*")
        }

        btnClear.setOnClickListener {
            selectedPhotoUri = null
            updatePhotoUI()
        }

        btnCancel.setOnClickListener { dialog.dismiss() }

        btnSave.setOnClickListener {
            val amountStr = etAmount.text.toString().trim()
            if (amountStr.isEmpty()) { Toast.makeText(context, "Enter amount", Toast.LENGTH_SHORT).show(); return@setOnClickListener }
            val amount = amountStr.toDoubleOrNull() ?: run {
                Toast.makeText(context, "Invalid amount", Toast.LENGTH_SHORT).show(); return@setOnClickListener
            }
            val desc = etDesc.text.toString().trim()
            val category = spinner.selectedItem.toString()
            val isExpense = chipGroup.checkedChipId == R.id.chip_expense

            lifecycleScope.launch {
                try {
                    if (isExpense) {
                        repo.addExpense(Expense(
                            amount = amount, category = category,
                            description = desc, date = selectedDateMillis,
                            photoUrl = selectedPhotoUri?.toString() ?: ""
                        ))
                    } else {
                        repo.addIncome(Income(
                            amount = amount, category = category,
                            description = desc, date = selectedDateMillis
                        ))
                    }
                    dialog.dismiss()
                    view?.let { loadTransactions(it.findViewById(R.id.chip_group_filter), "") }
                } catch (e: Exception) {
                    Toast.makeText(context, "Save failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        view?.let { loadTransactions(it.findViewById(R.id.chip_group_filter), "") }
    }
}
