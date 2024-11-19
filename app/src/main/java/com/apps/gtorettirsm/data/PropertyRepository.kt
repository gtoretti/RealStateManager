/*
 */

package com.apps.gtorettirsm.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropertyRepository @Inject constructor(private val patientDao: PropertyDao) {

    fun getPropertys() = patientDao.getProperties()

    fun getActivePropertys() = patientDao.getActiveProperties()

    fun getProperty(id: Long) = patientDao.getProperty(id)

    suspend fun saveProperty(patient: Property){
        patientDao.upsert(patient)
    }

    suspend fun deleteProperty(patient: Property){
        patientDao.delete(patient.propertyId)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: PropertyRepository? = null

        fun getInstance(patientDao: PropertyDao) =
            instance ?: synchronized(this) {
                instance ?: PropertyRepository(patientDao).also { instance = it }
            }
    }
}
