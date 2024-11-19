/*
 */

package com.apps.gtorettirsm.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

/**
 *
 */
@Dao
interface ReceiptPDFDao {
    @Query("SELECT * FROM receiptPDF where receiptId = :receiptId LIMIT 1")
    fun getPDF(receiptId: Long): ReceiptPDF

    @Upsert
    suspend fun upsert(receiptPDF: ReceiptPDF)

    @Query("DELETE FROM receiptPDF where receiptId = :receiptId")
    suspend fun delete(receiptId: Long)

}
