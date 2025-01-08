/*
 */

package com.apps.gtorettirsm.compose.property


import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.apps.gtorettirsm.compose.utils.DrawScrollableView
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.screenToDouble
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun PropertyCurrentContractDialog(
    openPropertyCurrentContractDialog: MutableState<Boolean>,
    propertyViewModel: PropertyViewModel,
    context: Context,
    property: Property
) {

    var startDate by remember { mutableStateOf("") }
    var endedDate by remember { mutableStateOf("") }
    var monthlyBillingValue by remember { mutableStateOf("") }
    var months by remember { mutableStateOf("") }
    var valueAdjustmentIndexName by remember { mutableStateOf("") }
    var renterName by remember { mutableStateOf("") }
    var renterCPF by remember { mutableStateOf("") }
    var renterPhone by remember { mutableStateOf("") }
    var renterEmail by remember { mutableStateOf("") }
    var guarantorName by remember { mutableStateOf("") }
    var guarantorCPF by remember { mutableStateOf("") }
    var guarantorPhone by remember { mutableStateOf("") }
    var guarantorEmail by remember { mutableStateOf("") }
    var paymentDate by remember { mutableStateOf("") }

    val openStartDateDialog = remember { mutableStateOf(false) }
    val openEndedDateDialog = remember { mutableStateOf(false) }

    if (openPropertyCurrentContractDialog.value) {
        AlertDialog(shape = RoundedCornerShape(10.dp), onDismissRequest = {
            openPropertyCurrentContractDialog.value = false
        }, modifier = Modifier
            .width(550.dp)
            .height(800.dp),

            title = {
                Text(
                    text = "Contrato Atual:", style = TextStyle(
                        color = getTextColor(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            }, text = {

                DrawScrollableView(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    content = {

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
                                ), placeholder = { Text("00.000,00") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal
                                ),
                                label = {
                                    Text(
                                        text = "Valor Atual de Aluguel Mensal:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )

                            OutlinedTextField(
                                value = paymentDate,
                                onValueChange = {
                                    paymentDate = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),placeholder = { Text("") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),

                                label = {
                                    Text(
                                        text = "Dia de Pagamento no Mês:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )

                            OutlinedTextField(
                                value = valueAdjustmentIndexName,
                                onValueChange = {
                                    valueAdjustmentIndexName = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),

                                label = {
                                    Text(
                                        text = "Índice de Reajuste Anual:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    modifier = Modifier.width(160.dp),
                                    value = startDate,
                                    onValueChange = {
                                    },
                                    textStyle = TextStyle(
                                        fontSize = 16.sp,
                                        color = getTextColor(),
                                        fontWeight = FontWeight.Normal
                                    ),

                                    label = {
                                        Text(
                                            text = "Data de Início:",
                                            style = TextStyle(
                                                color = getTextColor(), fontSize = 12.sp,
                                            )
                                        )
                                    },
                                    enabled = false
                                )

                                TextButton(
                                    modifier = Modifier.padding(5.dp),
                                    onClick =
                                    {
                                        openStartDateDialog.value = true
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.DateRange,
                                        contentDescription = "Alterar Data de Início",
                                        tint = getTextColor(),
                                        modifier = Modifier
                                            .padding(end = 12.dp)
                                            .size(24.dp)
                                    )
                                }
                            }




                            OutlinedTextField(
                                value = months,
                                onValueChange = {
                                    months = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),
                                placeholder = { Text("36") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                label = {
                                    Text(
                                        text = "Período do Contrato em Meses:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )



                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    modifier = Modifier.width(160.dp),
                                    value = endedDate,
                                    onValueChange = {
                                    },
                                    textStyle = TextStyle(
                                        fontSize = 16.sp,
                                        color = getTextColor(),
                                        fontWeight = FontWeight.Normal
                                    ),

                                    label = {
                                        Text(
                                            text = "Data de Término:",
                                            style = TextStyle(
                                                color = getTextColor(), fontSize = 12.sp,
                                            )
                                        )
                                    },
                                    enabled = false
                                )

                                TextButton(
                                    modifier = Modifier.padding(5.dp),
                                    onClick =
                                    {
                                        openEndedDateDialog.value = true
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.DateRange,
                                        contentDescription = "Alterar Data de Término",
                                        tint = getTextColor(),
                                        modifier = Modifier
                                            .padding(end = 12.dp)
                                            .size(24.dp)
                                    )
                                }
                            }

                            OutlinedTextField(
                                value = renterName,
                                onValueChange = {
                                    renterName = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),

                                label = {
                                    Text(
                                        text = "Nome do Inquilino:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )

                            OutlinedTextField(
                                value = renterCPF,
                                onValueChange = {
                                    renterCPF = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),
                                placeholder = { Text("") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                label = {
                                    Text(
                                        text = "CPF/CNPJ do Inquilino:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )



                            OutlinedTextField(
                                value = renterPhone,
                                onValueChange = {
                                    renterPhone = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),
                                placeholder = { Text("") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                label = {
                                    Text(
                                        text = "Telefone do Inquilino:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )

                            OutlinedTextField(
                                value = renterEmail,
                                onValueChange = {
                                    renterEmail = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),

                                label = {
                                    Text(
                                        text = "E-mail do Inquilino:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )

                            OutlinedTextField(
                                value = guarantorName,
                                onValueChange = {
                                    guarantorName = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),

                                label = {
                                    Text(
                                        text = "Nome do Fiador:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )

                            OutlinedTextField(
                                value = guarantorCPF,
                                onValueChange = {
                                    guarantorCPF = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),
                                placeholder = { Text("") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                label = {
                                    Text(
                                        text = "CPF/CNPJ do Fiador:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )



                            OutlinedTextField(
                                value = guarantorPhone,
                                onValueChange = {
                                    guarantorPhone = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),
                                placeholder = { Text("") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                label = {
                                    Text(
                                        text = "Telefone do Fiador:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )

                            OutlinedTextField(
                                value = guarantorEmail,
                                onValueChange = {
                                    guarantorEmail = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),

                                label = {
                                    Text(
                                        text = "E-mail do Fiador:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )
                        }
                    })

                when {
                    openStartDateDialog.value -> {
                        DatePickerModal(
                            onDateSelected = {
                                if (it != null) {
                                    startDate = SimpleDateFormat("dd/MM/yyyy").format(Date(it))
                                }
                        },openDialog = openStartDateDialog, title = "Data de Início"
                        )
                    }
                }

                when {
                    openEndedDateDialog.value -> {
                        DatePickerModal(
                            onDateSelected = {
                                if (it != null) {
                                    endedDate = SimpleDateFormat("dd/MM/yyyy").format(Date(it))
                                }
                            }, openDialog = openEndedDateDialog, title = "Data de Término"
                        )
                    }
                }

            }, confirmButton = {

            }, dismissButton = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {



                    Button(
                        onClick = {
                            startDate=""
                            endedDate=""
                            monthlyBillingValue=""
                            months=""
                            valueAdjustmentIndexName=""
                            renterName=""
                            renterCPF=""
                            renterPhone=""
                            renterEmail=""
                            guarantorName=""
                            guarantorCPF=""
                            guarantorPhone=""
                            guarantorEmail=""
                            paymentDate=""

                            openStartDateDialog.value = false
                            openEndedDateDialog.value = false
                            openPropertyCurrentContractDialog.value = false
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

                    Button(onClick = {
                        if (monthlyBillingValue.trim().isEmpty()){
                            monthlyBillingValue="0"
                        }

                        if (months.trim().isEmpty())
                            months = "0"

                        if (paymentDate.trim().isEmpty())
                            paymentDate = "0"


                        val fmt = SimpleDateFormat("dd/MM/yyyy")
                        var startDt = property.contractStartDate
                        if (startDate.isNotEmpty())
                            startDt = fmt.parse(startDate)
                        var endedDt = property.contractEndedDate
                        if (endedDate.isNotEmpty())
                            endedDt = fmt.parse(endedDate)

                        propertyViewModel.saveProperty(
                            Property(
                                propertyId = property.propertyId,
                                streetAddress = property.streetAddress,
                                state = property.state,
                                city = property.city,
                                district = property.district,
                                number = property.number,
                                complement = property.complement,
                                zipCode = property.zipCode,
                                rentalMonthlyPrice = property.rentalMonthlyPrice,
                                occupied = property.occupied,
                                cpflName = property.cpflName,
                                cpflCustomerId = property.cpflCustomerId,
                                cpflCurrentCPF = property.cpflCurrentCPF,
                                sanasaName = property.sanasaName,
                                sanasaCustomerId = property.sanasaCustomerId,
                                sanasaCurrentCPF = property.sanasaCurrentCPF,
                                iptuCartographicCode = property.iptuCartographicCode,
                                realEstateRegistration = property.realEstateRegistration,
                                totalMunicipalTaxes = property.totalMunicipalTaxes,
                                urlGDriveFolder = property.urlGDriveFolder,
                                deleted = 0,
                                contractManagerName= property.contractManagerName,
                                contractManagerUrl = property.contractManagerUrl,
                                contractManagerPhoneNumber= property.contractManagerPhoneNumber,
                                contractManagerEmail= property.contractManagerEmail,
                                contractStartDate= startDt,
                                contractEndedDate= endedDt,
                                contractMonths= Integer.parseInt(months),
                                contractValueAdjustmentIndexName= property.contractValueAdjustmentIndexName,
                                contractMonthlyBillingValue= monthlyBillingValue.screenToDouble(),
                                contractRenterName= renterName,
                                contractRenterCPF= renterCPF,
                                contractRenterPhone= renterPhone,
                                contractRenterEmail= renterEmail,
                                contractGuarantorName= guarantorName,
                                contractGuarantorCPF= guarantorCPF,
                                contractGuarantorPhone= guarantorPhone,
                                contractGuarantorEmail= guarantorEmail,
                                contractPaymentDate= Integer.parseInt(paymentDate)
                            ))

                        startDate=""
                        endedDate=""
                        monthlyBillingValue=""
                        months=""
                        valueAdjustmentIndexName=""
                        renterName=""
                        renterCPF=""
                        renterPhone=""
                        renterEmail=""
                        guarantorName=""
                        guarantorCPF=""
                        guarantorPhone=""
                        guarantorEmail=""
                        paymentDate=""

                        openStartDateDialog.value = false
                        openEndedDateDialog.value = false
                        openPropertyCurrentContractDialog.value = false
                        showToast("Contrato atualizado com sucesso!",context)

                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Salvar", style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }
                }
            }
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    openDialog: MutableState<Boolean>,
    onDateSelected: (Long?) -> Unit,
    title: String
) {

    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest={},
        confirmButton={
            TextButton(onClick = {
                openDialog.value = false
                onDateSelected(datePickerState.selectedDateMillis)
            }) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                openDialog.value = false
            }) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(title = {}, state = datePickerState, headline = {
            Text(title)
        }, showModeToggle = false)
    }
}




