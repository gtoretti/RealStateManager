/*
 */

package com.apps.gtorettirsm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.gtorettirsm.data.Attendance
import com.apps.gtorettirsm.data.AttendanceRepository
import com.apps.gtorettirsm.data.Receipt
import com.apps.gtorettirsm.data.ReceiptPDF
import com.apps.gtorettirsm.data.ReceiptPDFRepository
import com.apps.gtorettirsm.data.ReceiptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject internal constructor(
    private val receiptRepository: ReceiptRepository,
    private val attendanceRepository: AttendanceRepository,
    private val receiptPDFRepository: ReceiptPDFRepository
) : ViewModel() {

    fun getReceipts(idPatient: Long) = receiptRepository.getReceipts(idPatient)

    fun getReceivedReceiptsByDate(start: Date, end: Date) = receiptRepository.getReceivedReceiptsByDate(start,end)

    fun getUnpaidReceipts(idPatient: Long) = receiptRepository.getUnpaidReceipts(idPatient)

    fun saveReceipt(receipt: Receipt, attendances: List<Attendance>, selected: List<Long>, receiptPDF: ReceiptPDF) {
        viewModelScope.launch {
            var receiptId = Date().time
            receiptRepository.saveReceipt(Receipt(receiptId,date=receipt.date,patientId=receipt.patientId,total=receipt.total,paymentDate=receipt.paymentDate,received=receipt.received))

            attendances.forEach { attendance ->
                if (selected.contains(attendance.attendanceId)) {
                    attendance.receiptId = receiptId
                    attendanceRepository.saveAttendance(attendance)
                }
            }
            val saveReceiptPDF = ReceiptPDF(0,receiptId,receiptPDF.header,receiptPDF.body,receiptPDF.signingName,receiptPDF.signingCouncil,receiptPDF.signingCPF,receiptPDF.footer,receiptPDF.pdfFileName)
            receiptPDFRepository.saveReceiptPDF(saveReceiptPDF)
        }
    }

    fun receiveReceipts(receipts: List<Receipt>, selected: List<Long>) {

        viewModelScope.launch {
            receipts.forEach { receipt ->
                if (selected.contains(receipt.receiptId)) {
                    receiptRepository.receiveReceipt(receipt,Date())
                }
            }
        }
    }

    fun deleteReceipts(receipts: List<Receipt>, selected: List<Long>) {
        viewModelScope.launch {
            receipts.forEach { receipt ->
                if (selected.contains(receipt.receiptId)) {
                    receiptRepository.deleteReceipt(receipt)
                }
            }
        }
    }

    fun deleteReceipt(receipt: Receipt) {
        viewModelScope.launch {
            receiptRepository.deleteReceipt(receipt)
        }
    }
}