/*
 */

package com.apps.gtorettirsm.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReceiptPDFRepository @Inject constructor(private val receiptPDFDao: ReceiptPDFDao) {

    fun getReceiptPDF(receiptId: Long) = receiptPDFDao.getPDF(receiptId)

    suspend fun saveReceiptPDF(receiptPDF: ReceiptPDF) {
        receiptPDFDao.upsert(receiptPDF)
    }

    suspend fun deleteReceipt(receiptId: Long) {
        receiptPDFDao.delete(receiptId)
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: ReceiptPDFRepository? = null

        fun getInstance(receiptPDFDao: ReceiptPDFDao) =
            instance ?: synchronized(this) {
                instance ?: ReceiptPDFRepository(receiptPDFDao).also { instance = it }
            }
    }
}
