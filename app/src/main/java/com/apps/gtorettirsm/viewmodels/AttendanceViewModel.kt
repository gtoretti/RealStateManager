/*
 */

package com.apps.gtorettirsm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.gtorettirsm.data.MonthlyBilling
import com.apps.gtorettirsm.data.MonthlyBillingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyBillingViewModel @Inject internal constructor(
    private val repository: MonthlyBillingRepository
) : ViewModel() {

    fun getMonthlyBillings(idProperty: Long) = repository.getMonthlyBillings(idProperty)

    fun getNonReceiptMonthlyBillings(idProperty: Long) =
        repository.getNonReceiptMonthlyBillings(idProperty)

    fun saveMonthlyBilling(monthlyBilling: MonthlyBilling) {
        viewModelScope.launch {
            repository.saveMonthlyBilling(monthlyBilling)
        }
    }

    fun deleteMonthlyBilling(monthlyBilling: MonthlyBilling) {
        viewModelScope.launch {
            repository.deleteMonthlyBilling(monthlyBilling)
        }
    }
}