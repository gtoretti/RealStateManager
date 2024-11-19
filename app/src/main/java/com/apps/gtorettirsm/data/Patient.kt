/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patient")
data class Patient(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val patientId: Long,
    val name: String,
    val parentName: String,
    val sessionPrice: Double,
    val deleted: Int,
) {
    override fun toString() = name
}
