/*
 */

package com.apps.gtorettirsm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.gtorettirsm.data.Expense
import com.apps.gtorettirsm.data.Receiving
import com.apps.gtorettirsm.data.ReceivingRepository
import com.apps.gtorettirsm.data.ReceiptPDF
import com.apps.gtorettirsm.data.ReceiptPDFRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ReceivingViewModel @Inject internal constructor(
    private val receivingRepository: ReceivingRepository,
) : ViewModel() {

    fun getReceivingsByProperty(id:Long) = receivingRepository.getReceivings(id)

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