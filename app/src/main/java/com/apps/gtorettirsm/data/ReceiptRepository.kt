/*
 */

package com.apps.gtorettirsm.data

import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReceiptRepository @Inject constructor(private val receiptDao: ReceiptDao) {

    fun getReceipts(idPatient: Long) = receiptDao.getReceipts(idPatient)

    fun getReceivedReceiptsByDate(start: Date, end: Date) = receiptDao.getReceivedReceiptsByDate(start, end)

    fun getUnpaidReceipts(idPatient: Long) = receiptDao.getUnpaidReceipts(idPatient)


    suspend fun saveReceipt(receipt: Receipt) {
        receiptDao.upsert(receipt)
    }

    suspend fun receiveReceipt(receipt: Receipt, paymentDate: Date) {
        receiptDao.receive(receipt.receiptId,paymentDate)
    }

    suspend fun deleteReceipt(receipt: Receipt) {
        receiptDao.delete(receipt.receiptId)
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: ReceiptRepository? = null

        fun getInstance(receiptDao: ReceiptDao) =
            instance ?: synchronized(this) {
                instance ?: ReceiptRepository(receiptDao).also { instance = it }
            }
    }
}
