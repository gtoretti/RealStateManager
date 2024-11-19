/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "property")
data class Property(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val propertyId: Long,
    val streetAddress: String,
    val number: String,
    val rentalMontlyPrice: Double,
    val deleted: Int,
) {
    override fun toString() = streetAddress
}
