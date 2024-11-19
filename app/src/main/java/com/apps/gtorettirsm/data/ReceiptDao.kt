/*
 */

package com.apps.gtorettirsm.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * The Data Access Object for the Plant class.
 */
@Dao
interface ReceiptDao {
    @Query("SELECT * FROM receipt where patientId = :patientId ORDER BY date DESC")
    fun getReceipts(patientId: Long): Flow<List<Receipt>>

    @Query("SELECT * FROM receipt where paymentDate BETWEEN :start AND :end and received = 1 ORDER BY date DESC")
    fun getReceivedReceiptsByDate(start: Date, end: Date): Flow<List<Receipt>>

    @Query("SELECT * FROM receipt where patientId = :patientId and received = 0 ORDER BY date DESC")
    fun getUnpaidReceipts(patientId: Long): Flow<List<Receipt>>

    @Upsert
    suspend fun upsert(receipt: Receipt)

    @Query("DELETE FROM receipt where id = :receiptId")
    suspend fun delete(receiptId: Long)

    @Query("UPDATE receipt SET received = 1, paymentDate = :paymentDate where id = :receiptId")
    suspend fun receive(receiptId: Long, paymentDate: Date)
}
