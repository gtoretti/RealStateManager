/*
 */

package com.apps.gtorettirsm.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the Plant class.
 */
@Dao
interface AttendanceDao {
    @Query("SELECT * FROM attendance where patientId = :patientId ORDER BY date DESC")
    fun getAttendances(patientId: Long): Flow<List<Attendance>>

    @Query("SELECT * FROM attendance where patientId = :patientId and receiptId = 0 ORDER BY date")
    fun getNonReceiptAttendances(patientId: Long): Flow<List<Attendance>>

    @Upsert
    suspend fun upsert(attendance: Attendance)

    @Query("DELETE FROM attendance where id = :attendanceId")
    suspend fun delete(attendanceId: Long)
}
