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
interface PropertyDao {
    @Query("SELECT * FROM property ORDER BY address")
    fun getProperties(): Flow<List<Property>>

    @Query("SELECT * FROM property where deleted = 0 ORDER BY address")
    fun getActiveProperties(): Flow<List<Property>>

    @Query("SELECT * FROM property where id = :id")
    fun getProperty(id: Long): Flow<Property>

    @Upsert
    suspend fun upsert(property: Property)

    @Query("UPDATE property set deleted = 1 where id = :propertyId")
    suspend fun delete(propertyId: Long)
}
