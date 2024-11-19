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
interface PatientDao {
    @Query("SELECT * FROM patient ORDER BY name")
    fun getPatients(): Flow<List<Patient>>

    @Query("SELECT * FROM patient where deleted = 0 ORDER BY name")
    fun getActivePatients(): Flow<List<Patient>>

    @Query("SELECT * FROM patient where id = :id")
    fun getPatient(id: Long): Flow<Patient>

    @Upsert
    suspend fun upsert(patient: Patient)

    @Query("UPDATE patient set deleted = 1 where id = :patientId")
    suspend fun delete(patientId: Long)
}
