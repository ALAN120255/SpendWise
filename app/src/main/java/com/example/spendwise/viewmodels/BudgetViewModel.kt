package com.example.spendwise.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.spendwise.repositories.AppRepository
import kotlinx.coroutines.launch

class BudgetViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = AppRepository(app.applicationContext)

    private val _budget = MutableLiveData<Double>()
    val budget: LiveData<Double> = _budget

    private val _spent = MutableLiveData<Double>()
    val spent: LiveData<Double> = _spent

    fun loadBudget() {
        viewModelScope.launch {
            _budget.value = repo.getBudget()
            _spent.value  = repo.getExpenses().sumOf { it.amount }
        }
    }

    fun setBudget(amount: Double) {
        viewModelScope.launch {
            repo.setBudget(amount)
            _budget.value = amount
        }
    }

    fun getProgressPercent(): Int {
        val b = _budget.value ?: return 0
        val s = _spent.value  ?: return 0
        if (b <= 0) return 0
        return ((s / b) * 100).toInt().coerceIn(0, 100)
    }
}
