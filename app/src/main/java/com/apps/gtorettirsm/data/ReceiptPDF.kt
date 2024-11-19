/*
 */

package com.apps.gtorettirsm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receiptPDF")
data class ReceiptPDF(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val pdfId: Long,
    val receiptId: Long,
    val header: String,
    val body: String,
    val signingName: String,
    val signingCPF: String,
    val footer: String,
    val pdfFileName: String,
)