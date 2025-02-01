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
interface ReceivingDao {
    @Query("SELECT * FROM receiving where propertyId = :propertyId ORDER BY receivingDate DESC")
    fun getReceivings(propertyId: Long): Flow<List<Receiving>>

    @Upsert
    suspend fun upsert(receiving: Receiving)

    @Query("DELETE FROM receiving where id = :receivingId")
    suspend fun delete(receivingId: Long)
}
