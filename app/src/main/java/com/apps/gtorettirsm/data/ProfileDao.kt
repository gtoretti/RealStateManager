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
interface ProfileDao {
    @Query("SELECT * FROM profile")
    fun getProfiles(): Flow<List<Profile>>

    @Upsert
    suspend fun upsert(profile: Profile)
}
