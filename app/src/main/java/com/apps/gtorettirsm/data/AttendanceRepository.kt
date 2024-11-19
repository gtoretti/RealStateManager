/*
 */

package com.apps.gtorettirsm.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepository @Inject constructor(private val attendanceDao: AttendanceDao) {

    fun getAttendances(idPatient: Long) = attendanceDao.getAttendances(idPatient)

    fun getNonReceiptAttendances(idPatient: Long) = attendanceDao.getNonReceiptAttendances(idPatient)

    suspend fun saveAttendance(attendance: Attendance){
        attendanceDao.upsert(attendance)
    }

    suspend fun deleteAttendance(attendance: Attendance){
        attendanceDao.delete(attendance.attendanceId)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AttendanceRepository? = null

        fun getInstance(attendanceDao: AttendanceDao) =
            instance ?: synchronized(this) {
                instance ?: AttendanceRepository(attendanceDao).also { instance = it }
            }
    }
}
