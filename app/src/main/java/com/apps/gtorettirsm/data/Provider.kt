/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "provider")
data class Provider(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var providerId: Long,
    var name: String,
    var cpfCnpj: String,
    var pix: String,
    var phoneNumber: String,
    var email: String,
    var serviceRegion: String,

    var servicesAdministration: Int, //administração de obras
    var servicesBrickwork: Int, //Alvenaria
    var servicesArchitecture: Int, //arquitetura
    var servicesInsurer: Int, // asseguradora
    var servicesAutomation: Int, //automação residencial
    var servicesFireBrigade: Int, // brigada de incendio
    var servicesNotary: Int, //cartorio de imoveis
    var servicesAluminumFrames: Int, // esquadrias de aluminio
    var servicesPlasterer: Int,   // gesseiro
    var servicesElectric: Int,  //Instalações eletricas
    var servicesHydraulic: Int, //Instalações hidraulicas
    var servicesAirConditioningMaintenance: Int, // manutenção de ar-condicionado
    var servicesShowerStalls: Int, // Manutenção de Boxes para Banheiros
    var servicesRoofer: Int, // manutenção de calhas e telhado
    var servicesElectricFence: Int, // manutenção de cerca eletrica
    var servicesElevatorMaintenance: Int, //manutenção de elevador
    var servicesElectronicIntercom: Int, // manutenção de interfones
    var servicesGardening: Int, // manutenção de jardim
    var servicesPoolMaintenance: Int, // manutenção de piscinas
    var servicesPlaygroundMaintenance: Int,  // manutenção de playground
    var servicesElectronicGate: Int, // manutenção de portao eletronico
    var servicesCleaning: Int, // limpeza pos-obra
    var servicesPoolCleaning: Int, //limpeza de piscinas
    var servicesLandscaping: Int,  // paisagismo
    var servicesPainting: Int, // Pintura
    var servicesSteelGatesRailings: Int, // portoes e grades de aço
    var servicesPropertySecurity: Int, // segurança patrimonial
    var servicesSunshades: Int, // Manutenção de Coberturas e Toldos
    var servicesCurtains: Int, // Manutenção de Cortinas
    var servicesCabinetsJoinery: Int, // Manutenção de Armarios e Marcenaria
    val deleted: Int,


    ) {

    override fun toString() = name
}
