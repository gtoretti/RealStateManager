/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "contract")
data class Contract(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val contractId: Long,
    val startDate: Date,
    val endedDate: Date,
    val months: Int,
    val propertyId: Long,
    val valueAdjustmentIndexName: String,
    val monthlyBillingValue: Double,
    val renterName: String,
    val renterCPF: String,
    val renterPhone: String,
    val renterEmail: String,
    val guarantorName: String,
    val guarantorCPF: String,
    val guarantorPhone: String,
    val guarantorEmail: String,
    val paymentDate: Int,
)