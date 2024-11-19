/*
 */

package com.apps.gtorettirsm.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the Plant class.
 */
@Dao
interface MonthlyBillingDao {
    @Query("SELECT * FROM monthlyBilling where propertyId = :propertyId ORDER BY date DESC")
    fun getMonthlyBillings(propertyId: Long): Flow<List<MonthlyBilling>>

    @Query("SELECT * FROM monthlyBilling where propertyId = :propertyId and receiptId = 0 ORDER BY date")
    fun getNonReceiptMonthlyBillings(propertyId: Long): Flow<List<MonthlyBilling>>

    @Upsert
    suspend fun upsert(monthlyBilling: MonthlyBilling)

    @Query("DELETE FROM monthlyBilling where id = :monthlyBillingId")
    suspend fun delete(monthlyBillingId: Long)
}
