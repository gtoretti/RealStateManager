/*
 */

package com.apps.gtorettirsm.compose.property

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.compose.utils.toScreen
import com.apps.gtorettirsm.data.Expense
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.Receiving
import com.apps.gtorettirsm.viewmodels.ExpenseViewModel
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ReceivingViewModel
import java.text.SimpleDateFormat

@Composable
fun PropertyReceivingDeleteDialog(
    openPropertyReceivingDeleteDialog: MutableState<Boolean>,
    openPropertyReceivingsCreateDialog: MutableState<Boolean>,
    receivingViewModel: ReceivingViewModel,
    receiving: Receiving,
    context: Context
) {
    if (openPropertyReceivingDeleteDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openPropertyReceivingDeleteDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(250.dp),

            title = {
                Text(
                    text = "Excluir Recebimento:",
                    style = TextStyle(
                        color = getTextColor(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(2.dp)

                ) {

                    val fmt = SimpleDateFormat("dd/MM/yyyy")

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Text(
                            text = fmt.format(receiving.receivingDate), style = TextStyle(
                                color = getTextColor(),
                                fontSize = 16.sp,
                            )
                        )

                        Text(
                            text = receiving.totalValue.toScreen(), style = TextStyle(
                                color = getTextColor(),
                                fontSize = 16.sp,
                            )
                        )
                    }
                    Text(
                        text = receiving.comments, style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                        )
                    )



                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        receivingViewModel.deleteReceiving(receiving)

                        dropDownSelectPropertyId.value = 0L
                        dropDownSelectPropertyDesc.value = ""
                        dropDownSelectReceivingType.value = ""

                        openPropertyReceivingDeleteDialog.value = false
                        openPropertyReceivingsCreateDialog.value = false
                        showToast("Recebimento excluído com sucesso!", context)
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = getButtonColor()
                    ),modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "Excluir",
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

            }, dismissButton = {
                Button(
                    onClick = {
                        openPropertyReceivingDeleteDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = getButtonColor()
                    ),modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "Cancelar",
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
            }
        )
    }
}

