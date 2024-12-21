/*
 */

package com.apps.gtorettirsm.compose.property


import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.gtorettirsm.compose.utils.DrawScrollableView
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getRedTextColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.compose.utils.toScreen
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.Receipt
import com.apps.gtorettirsm.viewmodels.ReceiptPDFViewModel
import com.apps.gtorettirsm.viewmodels.ReceiptViewModel
import java.text.SimpleDateFormat


@Composable
fun PropertyReceivePaymentCreateDialog(
    openPropertyReceivePaymentCreateDialog: MutableState<Boolean>,
    context: Context
) {

    var monthlyBillingValue by remember { mutableStateOf("") }


    if (openPropertyReceivePaymentCreateDialog.value) {
        AlertDialog(shape = RoundedCornerShape(10.dp), onDismissRequest = {
            openPropertyReceivePaymentCreateDialog.value = false
        }, modifier = Modifier
            .width(550.dp)
            .height(800.dp),

            title = {
                Text(
                    text = "Adicionar Receita:", style = TextStyle(
                        color = getTextColor(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            }, text = {
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    OutlinedTextField(
                        value = monthlyBillingValue,
                        onValueChange = {
                            monthlyBillingValue = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),placeholder = {Text("00.000,00")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        label = {
                            Text(
                                text = "Valor recebido:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp,
                                )
                            )
                        }
                    )



                }

            }, confirmButton = {

            }, dismissButton = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = {
                        openPropertyReceivePaymentCreateDialog.value = false
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
                }
            }
        )
    }
}

