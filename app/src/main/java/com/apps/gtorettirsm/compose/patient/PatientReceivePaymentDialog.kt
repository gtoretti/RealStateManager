/*
 */

package com.apps.gtorettirsm.compose.patient


import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.gtorettirsm.compose.utils.DrawScrollableView
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.compose.utils.toScreen
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.Receipt
import com.apps.gtorettirsm.viewmodels.ReceiptPDFViewModel
import com.apps.gtorettirsm.viewmodels.ReceiptViewModel
import java.text.SimpleDateFormat


@Composable
fun PropertyReceivePaymentDialog(
    openReceivePaymentDialog: MutableState<Boolean>,
    unpaids: List<Receipt>,
    receiptViewModel: ReceiptViewModel,
    receiptPDFViewModel: ReceiptPDFViewModel,
    patient: Property,
    context: Context
) {

    val select = remember { mutableStateListOf(-1L) }
    if (select.contains(-1)) {
        select.remove(-1)
        unpaids.forEach { receipt ->
            select.add(receipt.receiptId)
        }
    }

    var receiptsTotal = 0.0
    unpaids.forEach { r ->
        if (select.contains(r.receiptId)) {
            receiptsTotal = receiptsTotal + r.total
        }
    }

    if (openReceivePaymentDialog.value) {
        AlertDialog(shape = RoundedCornerShape(10.dp), onDismissRequest = {
            openReceivePaymentDialog.value = false
        }, modifier = Modifier
            .width(550.dp)
            .height(800.dp),

            title = {
                Text(
                    text = "Receber pagamento:", style = TextStyle(
                        color = getTextColor(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            }, text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(2.dp)

                ) {
                    Text(
                        text = patient.streetAddress, style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                        )
                    )

                    if (unpaids.size > 0) {
                        Text(
                            text = "Recibos a receber: (data - valor)", style = TextStyle(
                                color = getTextColor(),

                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif,
                            )
                        )
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .height(190.dp)

                        ) {

                            DrawScrollableView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                content = {
                                    Column {

                                        unpaids.forEach { receipt ->
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = (select.contains(receipt.receiptId)),
                                                        onClick = {
                                                            if (select.contains(receipt.receiptId)) select.remove(
                                                                receipt.receiptId
                                                            )
                                                            else select.add(receipt.receiptId)
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (select.contains(receipt.receiptId)),
                                                    onCheckedChange = {
                                                        if (it) select.add(receipt.receiptId)
                                                        else select.remove(receipt.receiptId)
                                                    })
                                                Text(
                                                    text = SimpleDateFormat("dd / MM / yyyy").format(
                                                        receipt.date
                                                    ) + " - R$ " + receipt.total.toScreen(),
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            )
                        }

                        Text(
                            text = "Valor total dos recibos: " + receiptsTotal.toScreen(),
                            style = TextStyle(
                                color = getTextColor(),

                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif,
                            )
                        )
                    } else {
                        openReceivePaymentDialog.value = false
                    }

                    HorizontalDivider(thickness = 2.dp)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        if (unpaids.size>0) {

                        Button(
                            onClick = {
                                if (select.isEmpty()) {
                                    showToast("Por favor, selecione o recibo.", context)
                                } else {
                                    receiptViewModel.deleteReceipts(unpaids, select)
                                    showToast("Recibo(s) excluido(s) com sucesso!", context)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Excluir", style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }

                        Button(
                            onClick = {
                                if (select.isEmpty()) {
                                    showToast("Por favor, selecione o recibo.", context)
                                } else
                                    if (select.size > 1) {
                                        showToast("Por favor, selecione um único recibo por vez.", context)
                                    } else {
                                        receiptPDFViewModel.openPDF(select.get(0), context)
                                    }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Exibir PDF", style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }
                    }
                }

                    HorizontalDivider(thickness = 2.dp)

                }

            }, confirmButton = {

            }, dismissButton = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = {
                        openReceivePaymentDialog.value = false
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Cancelar", style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    if (unpaids.size>0) {
                        Button(
                            onClick = {
                                if (select.isEmpty()) {
                                    showToast("Por favor, selecione o recibo.", context)
                                } else {
                                    receiptViewModel.receiveReceipts(unpaids, select)
                                    openReceivePaymentDialog.value = false
                                    showToast("Pagamento registrado com sucesso!", context)
                                }

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Confirmar", style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }
                    }
                }
            }
        )
    }
}

