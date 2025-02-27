/*
 */

package com.apps.gtorettirsm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.gtorettirsm.data.Expense
import com.apps.gtorettirsm.data.ExpenseRepository
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.Provider
import com.apps.gtorettirsm.data.ProviderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject internal constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    fun getExpense(id:Long) = repository.getExpense(id)

    fun getExpensesByProperty(id:Long, startDate: Date, endDate: Date) = repository.getExpensesByProperty(id,startDate,endDate)

    fun getExpensesByProperty(id:Long) = repository.getExpensesByProperty(id)

    fun saveExpense(expense: Expense) {
        viewModelScope.launch {
            repository.saveExpense(expense)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }

}