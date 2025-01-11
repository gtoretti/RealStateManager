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
    @Query("SELECT * FROM provider")
    fun getProviders(): Flow<List<Provider>>

    @Upsert
    suspend fun upsert(provider: Provider)
}
