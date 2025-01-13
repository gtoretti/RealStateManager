/*
 */

package com.apps.gtorettirsm.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProviderRepository @Inject constructor(private val providerDao: ProviderDao) {

    fun getProviders() = providerDao.getProviders()

    fun getProvider(id: Long) = providerDao.getProvider(id)

    suspend fun saveProvider(provider: Provider){
        providerDao.upsert(provider)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ProviderRepository? = null

        fun getInstance(providerDao: ProviderDao) =
            instance ?: synchronized(this) {
                instance ?: ProviderRepository(providerDao).also { instance = it }
            }
    }

    suspend fun deleteProvider(provider: Provider){
        providerDao.delete(provider.providerId)
    }
}
