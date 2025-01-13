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
interface ProviderDao {
    @Query("SELECT * FROM provider where deleted = 0 ORDER BY name")
    fun getProviders(): Flow<List<Provider>>

    @Query("SELECT * FROM provider where id = :id")
    fun getProvider(id: Long): Flow<Provider>

    @Upsert
    suspend fun upsert(provider: Provider)

    @Query("UPDATE provider set deleted = 1 where id = :providerId")
    suspend fun delete(providerId: Long)
}
