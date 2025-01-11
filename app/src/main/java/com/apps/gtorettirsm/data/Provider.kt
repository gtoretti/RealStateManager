/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "provider")
data class Provider(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val providerId: Long,
    val name: String,
    val cpfCnpj: String,
    val pix: String,
    val phoneNumber: String,
    val email: String,
    val servicesHydraulic: Int,
    var servicesElectric: Int,
    val servicesBrickwork: Int,
    val servicesPainting: Int,
    val servicesArchitecture: Int,
    val servicesNotary: Int,
    val servicesElectronicGate: Int,
    val servicesElectricFence: Int,
    val servicesElectronicIntercom: Int,
    val servicesRoofer: Int,
    val servicesCleaning: Int,
    val servicesAluminumFrames: Int,
    val servicesSteelGatesRailings: Int,
    val servicesPoolMaintenance: Int,
    val servicesPoolCleaning: Int,


    ) {

    override fun toString() = name
}
