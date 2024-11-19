/*
 */

package com.apps.gtorettirsm.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.gtorettirsm.compose.utils.generatePDF
import com.apps.gtorettirsm.data.ReceiptPDFRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptPDFViewModel @Inject internal constructor(
    private val receiptPDFRepository: ReceiptPDFRepository
) : ViewModel() {

    fun openPDF(receiptId: Long, context: Context){
        Thread {
            val pdf = receiptPDFRepository.getReceiptPDF(receiptId)
            generatePDF(
                pdf.header,
                pdf.body,
                pdf.signingName,
                pdf.signingCPF,
                pdf.footer,
                context,
                pdf.pdfFileName
            )
        }.start()
    }

    suspend fun deleteReceipt(receiptId: Long) {
        viewModelScope.launch {
            receiptPDFRepository.deleteReceipt(receiptId)
        }
    }

}