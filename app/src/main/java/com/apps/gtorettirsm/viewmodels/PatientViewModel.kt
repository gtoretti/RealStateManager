/*
 */

package com.apps.gtorettirsm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject internal constructor(
    private val repository: PropertyRepository
) : ViewModel() {

    var patients = repository.getPropertys()

    var activePropertys = repository.getActivePropertys()

    fun getProperty(id:Long) = repository.getProperty(id)

    init {
        refreshData()
    }

    fun refreshData() {
        try {
            patients = repository.getPropertys()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveProperty(patient: Property) {
        viewModelScope.launch {
            repository.saveProperty(patient)
        }
    }

    fun deleteProperty(patient: Property) {
        viewModelScope.launch {
            repository.deleteProperty(patient)
        }
    }
}