/*
 */

package com.apps.gtorettirsm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.gtorettirsm.data.Attendance
import com.apps.gtorettirsm.data.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject internal constructor(
    private val repository: AttendanceRepository
) : ViewModel() {

    fun getAttendances(idPatient: Long) = repository.getAttendances(idPatient)

    fun getNonReceiptAttendances(idPatient: Long) =
        repository.getNonReceiptAttendances(idPatient)

    fun saveAttendance(attendance: Attendance) {
        viewModelScope.launch {
            repository.saveAttendance(attendance)
        }
    }

    fun deleteAttendance(attendance: Attendance) {
        viewModelScope.launch {
            repository.deleteAttendance(attendance)
        }
    }
}