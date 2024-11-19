/*
 */

package com.apps.gtorettirsm.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientRepository @Inject constructor(private val patientDao: PatientDao) {

    fun getPatients() = patientDao.getPatients()

    fun getActivePatients() = patientDao.getActivePatients()

    fun getPatient(id: Long) = patientDao.getPatient(id)

    suspend fun savePatient(patient: Patient){
        patientDao.upsert(patient)
    }

    suspend fun deletePatient(patient: Patient){
        patientDao.delete(patient.patientId)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: PatientRepository? = null

        fun getInstance(patientDao: PatientDao) =
            instance ?: synchronized(this) {
                instance ?: PatientRepository(patientDao).also { instance = it }
            }
    }
}
