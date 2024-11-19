/*
 */

package com.apps.gtorettirsm.compose.patient

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

    var name by remember { mutableStateOf("") }
    var parentName by remember { mutableStateOf("") }
    var sessionPrice by remember { mutableStateOf("") }

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
                    text = "Adicionar paciente:",
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
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),placeholder = {Text("Informe aqui o nome do(a) paciente.")},
                        label = {
                            Text(
                                text = "Nome",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp,
                                )
                            )
                        }
                    )

                    OutlinedTextField(
                        value = parentName,
                        onValueChange = {
                            parentName = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),placeholder = {Text("Informe aqui o nome do responsável pelo pagamento do recibo.")},

                        label = {
                            Text(
                                text = "Nome do Responsável",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp,
                                )
                            )
                        }
                    )

                    OutlinedTextField(
                        value = sessionPrice,
                        onValueChange = {
                            sessionPrice = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),
                        label = {
                            Text(
                                text = "Valor do Atendimento",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp
                                )
                            )
                        },
                        placeholder = {Text("Informe aqui o valor de cada atendimento.")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        )
                    )
                }

            },
            confirmButton = {
                Button(
                    onClick = {
                        if (name.isEmpty() || name.isBlank()){
                            showToast("Por favor, informe o nome do paciente.",context)
                        }else
                            if (parentName.isEmpty() || parentName.isBlank()){
                            showToast("Por favor, informe o nome do responsável pelo pagamento.",context)
                        }else
                        try {
                            patientViewModel.saveProperty(
                                Property(
                                    propertyId = 0,
                                    streetAddress = name,
                                    number = parentName,
                                    rentalMontlyPrice = sessionPrice.screenToDouble(),
                                    deleted = 0
                                )
                            )
                            openPropertyCreateDialog.value = false
                            showToast("Informações salvas com sucesso!",context)

                        } catch (ex: NumberFormatException) {
                            sessionPrice = ""
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

