/*
 */

package com.apps.gtorettirsm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.gtorettirsm.data.Provider
import com.apps.gtorettirsm.data.ProviderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProviderViewModel @Inject internal constructor(
    private val repository: ProviderRepository
) : ViewModel() {

    var providers = repository.getProviders()

    init {
        refreshData()
    }

    private fun refreshData() {
        try {
            providers = repository.getProviders()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveProvider(provider: Provider) {
        viewModelScope.launch {
            repository.saveProvider(provider)
        }
    }
}