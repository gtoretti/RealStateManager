/*
 */

package com.apps.gtorettirsm.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val profileDao: ProfileDao) {

    fun getProfiles() = profileDao.getProfiles()

    suspend fun saveProfile(profile: Profile){
        profileDao.upsert(profile)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ProfileRepository? = null

        fun getInstance(profileDao: ProfileDao) =
            instance ?: synchronized(this) {
                instance ?: ProfileRepository(profileDao).also { instance = it }
            }
    }
}
