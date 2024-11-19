/*
 */

package com.apps.gtorettirsm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.gtorettirsm.data.Profile
import com.apps.gtorettirsm.data.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject internal constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    var profiles = repository.getProfiles()

    init {
        refreshData()
    }

    fun refreshData() {
        try {
            profiles = repository.getProfiles()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveProfile(profile: Profile) {
        viewModelScope.launch {
            repository.saveProfile(profile)
        }
    }
}