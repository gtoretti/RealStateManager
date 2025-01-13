/*
 */

package com.apps.gtorettirsm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.gtorettirsm.data.Property
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

    fun getProvider(id:Long) = repository.getProvider(id)

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

    fun deleteProvider(provider: Provider) {
        viewModelScope.launch {
            repository.deleteProvider(provider)
        }
    }
}