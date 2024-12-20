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
) {

    override fun toString() = name
}
