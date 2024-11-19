/*
 */

package com.apps.gtorettirsm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.gtorettirsm.data.Patient
import com.apps.gtorettirsm.data.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject internal constructor(
    private val repository: PatientRepository
) : ViewModel() {

    var patients = repository.getPatients()

    var activePatients = repository.getActivePatients()

    fun getPatient(id:Long) = repository.getPatient(id)

    init {
        refreshData()
    }

    fun refreshData() {
        try {
            patients = repository.getPatients()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun savePatient(patient: Patient) {
        viewModelScope.launch {
            repository.savePatient(patient)
        }
    }

    fun deletePatient(patient: Patient) {
        viewModelScope.launch {
            repository.deletePatient(patient)
        }
    }
}