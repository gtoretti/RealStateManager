/*
 */

package com.apps.gtorettirsm.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropertyRepository @Inject constructor(private val propertyDao: PropertyDao) {

    fun getProperties() = propertyDao.getActiveProperties()

    fun getProperty(id: Long) = propertyDao.getProperty(id)

    suspend fun saveProperty(property: Property){
        propertyDao.upsert(property)
    }

    suspend fun deleteProperty(property: Property){
        propertyDao.delete(property.propertyId)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: PropertyRepository? = null

        fun getInstance(propertyDao: PropertyDao) =
            instance ?: synchronized(this) {
                instance ?: PropertyRepository(propertyDao).also { instance = it }
            }
    }
}
