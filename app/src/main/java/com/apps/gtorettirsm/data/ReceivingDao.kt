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
interface ReceivingDao {

    @Query("SELECT * FROM receiving where id = :id")
    fun getReceiving(id: Long): Flow<Receiving>

    @Query("SELECT * FROM receiving where propertyId = :propertyId and receivingDate BETWEEN :startDate and :endDate ORDER BY receivingDate DESC")
    fun getReceivings(propertyId: Long, startDate: Date, endDate: Date): Flow<List<Receiving>>

    @Query("SELECT * FROM receiving where propertyId = :propertyId ORDER BY receivingDate DESC")
    fun getReceivings(propertyId: Long): Flow<List<Receiving>>

    @Query("SELECT * FROM receiving where propertyId = :propertyId and type = 'Aluguel' and rentBillingDueDate > :contractStartDate ORDER BY rentBillingDueDate")
    fun getRentReceivings(propertyId: Long, contractStartDate: Date): Flow<List<Receiving>>

    @Upsert
    suspend fun upsert(receiving: Receiving)

    @Query("DELETE FROM receiving where id = :receivingId")
    suspend fun delete(receivingId: Long)
}
