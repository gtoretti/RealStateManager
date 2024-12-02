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
    val state: String,
    val city: String,
    val district: String,
    val number: String,
    val complement: String,
    val zipCode: String,
    val rentalMonthlyPrice: Double,
    val occupied: Int,
    val cpflCustomerId: String,
    val cpflCurrentCPF: String,
    val sanasaCustomerId: String,
    val sanasaCurrentCPF: String,
    val iptuCartographicCode: String,
    val urlGDriveFolder: String,
    val deleted: Int,
) {
    override fun toString() = streetAddress
}
