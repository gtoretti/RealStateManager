/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "receiving")
data class Receiving(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val receivingId: Long,
    val receivingDate: Date,
    val propertyId: Long,
    val totalValue: Double,
    val type: String,
    val comments: String,
    val rentBillingDueDate: Date,
    val fineValue: Double,
    val delayDays: Long,
    val renterName: String,
    val renterCPF: String,
)