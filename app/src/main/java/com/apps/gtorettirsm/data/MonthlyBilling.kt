/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "monthlyBilling")
data class MonthlyBilling(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val monthlyBillingId: Long,
    val date: Date,
    val propertyId: Long,
    val totalValue: Double,
    var receiptId: Long,
    val comments: String,
)