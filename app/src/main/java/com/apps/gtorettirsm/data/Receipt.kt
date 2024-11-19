/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "receipt")
data class Receipt(
    @PrimaryKey() @ColumnInfo(name = "id") val receiptId: Long,
    val date: Date,
    val propertyId: Long,
    val total: Double,
    val paymentDate: Date,
    val received: Int
)