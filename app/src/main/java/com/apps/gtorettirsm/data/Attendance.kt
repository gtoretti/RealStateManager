/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "attendance")
data class Attendance(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val attendanceId: Long,
    val date: Date,
    val patientId: Long,
    val sessionPriceAtAttendanceTime: Double,
    var receiptId: Long,
)