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

    //https://cdn.apicep.com/file/apicep/13084-778.json
    //https://viacep.com.br/ws/SP/Campinas/Tranquillo+Prosperi/json/
    //https://viacep.com.br/ws/13148218/json/

    val rentalMontlyPrice: Double,
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