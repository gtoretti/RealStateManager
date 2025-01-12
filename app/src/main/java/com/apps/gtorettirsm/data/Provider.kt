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
    val serviceRegion: String,

    val servicesAdministration: Int, //administração de obras
    val servicesBrickwork: Int, //Alvenaria
    val servicesArchitecture: Int, //arquitetura
    val servicesInsurer: Int, // asseguradora
    val servicesAutomation: Int, //automação residencial
    val servicesFireBrigade: Int, // brigada de incendio
    val servicesNotary: Int, //cartorio de imoveis
    val servicesAluminumFrames: Int, // esquadrias de aluminio
    val servicesPlasterer: Int,   // gesseiro
    var servicesElectric: Int,  //Instalações eletricas
    val servicesHydraulic: Int, //Instalações hidraulicas
    val servicesAirConditioningMaintenance: Int, // manutenção de ar-condicionado
    val servicesRoofer: Int, // manutenção de calhas e telhado
    val servicesElectricFence: Int, // manutenção de cerca eletrica
    val servicesElevatorMaintenance: Int, //manutenção de elevador
    val servicesElectronicIntercom: Int, // manutenção de interfones
    val servicesGardening: Int, // manutenção de jardim
    val servicesPoolMaintenance: Int, // manutenção de piscinas
    val servicesPlaygroundMaintenance: Int,  // manutenção de playground
    val servicesElectronicGate: Int, // manutenção de portao eletronico
    val servicesCleaning: Int, // limpeza pos-obra
    val servicesPoolCleaning: Int, //limpeza de piscinas
    val servicesLandscaping: Int,  // paisagismo
    val servicesPainting: Int, // Pintura
    val servicesSteelGatesRailings: Int, // portoes e grades de aço
    val servicesPropertySecurity: Int, // segurança patrimonial



    ) {

    override fun toString() = name
}
