/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val profileId: Long,
    val name: String,
    val cpfCnpj: String,
    val address: String,
    val city: String,
    val uf: String,
    val phoneNumber: String,
) {

    override fun toString() = name
}
