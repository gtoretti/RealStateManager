/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contractManager")
data class ContractManager(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val contractManagerId: Long,
    val name: String,
    val cnpj: String,
    val address: String,
    val city: String,
    val uf: String,
    val phoneNumber: String,
    val email: String,
) {

    override fun toString() = name
}
