/*
 */

package com.apps.gtorettirsm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.gtorettirsm.data.Expense
import com.apps.gtorettirsm.data.Receiving
import com.apps.gtorettirsm.data.ReceivingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ReceivingViewModel @Inject internal constructor(
    private val receivingRepository: ReceivingRepository,
) : ViewModel() {

    fun getReceivingsByProperty(id:Long, startDate: Date, endDate: Date) = receivingRepository.getReceivings(id,startDate,endDate)

    fun getReceivingsByProperty(id:Long) = receivingRepository.getReceivings(id)

    fun getRentReceivings(id:Long, contractStartDate: Date) = receivingRepository.getRentReceivings(id,contractStartDate)

    fun getRentReceivingsByDateFilter(id:Long, contractStartDate: Date, startDateFilter: Date, endDateFilter: Date) = receivingRepository.getRentReceivingsByDateFilter(id,contractStartDate,startDateFilter,endDateFilter)



    fun saveReceiving(receiving: Receiving) {
        viewModelScope.launch {
            receivingRepository.saveReceiving(receiving)
        }
    }

    fun deleteReceiving(receiving: Receiving) {
        viewModelScope.launch {
            receivingRepository.deleteReceiving(receiving)
        }
    }
}