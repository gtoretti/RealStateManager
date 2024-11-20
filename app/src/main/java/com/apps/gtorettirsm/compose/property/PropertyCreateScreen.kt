/*
 */

package com.apps.gtorettirsm.compose.property

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.screenToDouble
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.PropertyViewModel


@Composable
fun PropertyCreateScreen(
    openPropertyCreateDialog: MutableState<Boolean>,
    patientViewModel: PropertyViewModel,
    context: Context
) {

    var address by remember { mutableStateOf("") }
    var rentalMontlyPrice by remember { mutableStateOf("") }

    if (openPropertyCreateDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openPropertyCreateDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(550.dp),

            title = {
                Text(
                    text = "Adicionar imóvel:",
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
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)

                ) {
                    OutlinedTextField(
                        value = address,
                        onValueChange = {
                            address = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),placeholder = {Text("Endereço completo.")},
                        label = {
                            Text(
                                text = "Endereço completo",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp,
                                )
                            )
                        }
                    )

                    OutlinedTextField(
                        value = rentalMontlyPrice,
                        onValueChange = {
                            rentalMontlyPrice = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),
                        label = {
                            Text(
                                text = "Valor mensal do aluguel",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp
                                )
                            )
                        },
                        placeholder = {Text("Informe aqui o valor mensal do aluguel.")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        )
                    )
                }

            },
            confirmButton = {
                Button(
                    onClick = {
                        if (address.isEmpty() || address.isBlank()){
                            showToast("Por favor, informe o endereço do imóvel.",context)
                        }else
                            if (rentalMontlyPrice.isEmpty() || rentalMontlyPrice.isBlank()){
                            showToast("Por favor, informe o valor mensal do aluguel.",context)
                        }else
                        try {
                            patientViewModel.saveProperty(
                                Property(
                                    propertyId = 0,
                                    address = address,
                                    rentalMontlyPrice = rentalMontlyPrice.screenToDouble(),
                                    deleted = 0
                                )
                            )
                            openPropertyCreateDialog.value = false
                            showToast("Informações salvas com sucesso!",context)

                        } catch (ex: NumberFormatException) {
                            rentalMontlyPrice = ""
                            showToast("Por favor, informe o valor do atendimento.",context)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = getButtonColor()
                    ),modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "Salvar",
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }
            }, dismissButton = {
                Button(
                    onClick = {
                        openPropertyCreateDialog.value = false
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

