/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

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
    val realEstateRegistration: String,
    val totalMunicipalTaxes: Double,


    val urlGDriveFolder: String,
    val deleted: Int,

    val contractManagerName: String,
    val contractManagerUrl: String,
    val contractManagerPhoneNumber: String,
    val contractManagerEmail: String,

    val contractStartDate: Date,
    val contractEndedDate: Date,
    val contractMonths: Int,
    val contractValueAdjustmentIndexName: String,
    val contractMonthlyBillingValue: Double,
    val contractRenterName: String,
    val contractRenterCPF: String,
    val contractRenterPhone: String,
    val contractRenterEmail: String,
    val contractGuarantorName: String,
    val contractGuarantorCPF: String,
    val contractGuarantorPhone: String,
    val contractGuarantorEmail: String,
    val contractPaymentDate: Int,


    ) {
    override fun toString() = streetAddress
}
