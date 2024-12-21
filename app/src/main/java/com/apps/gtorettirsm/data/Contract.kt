/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "contract")
data class Contract(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val expenseId: Long,
    val startDate: Date,
    val months: Int,
    val propertyId: Long,
    val valueAdjustmentIndexName: String,
    val startingMonthlyBillingValue: Double,
    val renterName: String,
    val renterCPF: String,
    val guarantorName: String,
    val guarantorCPF: String,
)