/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val expenseId: Long,
    val date: Date,
    val propertyId: Long,
    val value: Double,
    val description: String,
    val repeat: String,
    var providerId: Long,
)