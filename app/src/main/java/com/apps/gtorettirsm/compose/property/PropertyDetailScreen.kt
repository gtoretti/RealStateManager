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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.compose.utils.DrawScrollableView
import com.apps.gtorettirsm.compose.utils.getAttendedDaysDescr
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.MonthlyBilling
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.Receipt
import com.apps.gtorettirsm.viewmodels.MonthlyBillingViewModel
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ReceiptPDFViewModel
import com.apps.gtorettirsm.viewmodels.ReceiptViewModel
import kotlinx.coroutines.flow.Flow


@Composable
fun PropertyDetailScreen(
    openPropertyDetailDialog: MutableState<Boolean>,
    propertyViewModel: PropertyViewModel = hiltViewModel(),
    monthlyBillingViewModel: MonthlyBillingViewModel = hiltViewModel(),
    receiptViewModel: ReceiptViewModel = hiltViewModel(),
    receiptPDFViewModel: ReceiptPDFViewModel = hiltViewModel(),
    propertyId: Long,
    context: Context
) {
    val property = propertyViewModel.getProperty(propertyId)
    val currentMonthlyBillingsFlow =
        monthlyBillingViewModel.getNonReceiptMonthlyBillings(propertyId)
    val currentMonthlyBillings by currentMonthlyBillingsFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    val allMonthlyBillingsFlow = monthlyBillingViewModel.getNonReceiptMonthlyBillings(propertyId)
    val allMonthlyBillings by allMonthlyBillingsFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    val unpaidFlow = receiptViewModel.getUnpaidReceipts(propertyId)
    val unpaids by unpaidFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    PropertyDetailScreen(
        openPropertyDetailDialog = openPropertyDetailDialog,
        propertyFlow = property,
        propertyViewModel = propertyViewModel,
        monthlyBillingViewModel = monthlyBillingViewModel,
        currentAttendedDays = currentMonthlyBillings,
        allAttendedDays = allMonthlyBillings,
        receiptViewModel = receiptViewModel,
        receiptPDFViewModel = receiptPDFViewModel,
        unpaids = unpaids,
        context = context
    )
}

@Composable
fun PropertyDetailScreen(
    openPropertyDetailDialog: MutableState<Boolean>,
    propertyFlow: Flow<Property>,
    propertyViewModel: PropertyViewModel,
    monthlyBillingViewModel: MonthlyBillingViewModel,
    currentAttendedDays: List<MonthlyBilling>,
    allAttendedDays: List<MonthlyBilling>,
    receiptViewModel: ReceiptViewModel,
    receiptPDFViewModel: ReceiptPDFViewModel,
    unpaids: List<Receipt>,
    context: Context
) {

    val openPropertyDetailDatePickerDialog = remember { mutableStateOf(false) }
    val openPropertyDetailDeleteMonthlyBillingDialog = remember { mutableStateOf(false) }
    val openPropertyReceiptsDialog = remember { mutableStateOf(false) }
    val openReceivePaymentDialog = remember { mutableStateOf(false) }
    val openPropertyDeleteDialog = remember { mutableStateOf(false) }
    var openPropertyChangeAddressDialog = remember { mutableStateOf(false) }

    val property by propertyFlow.collectAsStateWithLifecycle(
        initialValue = Property(0,"", "", "", "", "", "", "", 0.0,0,"", "", "", "", "", "" , 0)
    )
    var rentalMontlyPrice by remember { mutableStateOf("") }

    var attendedDaysDescr = getAttendedDaysDescr(currentAttendedDays)

    if (openPropertyDetailDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openPropertyDetailDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(840.dp),

            title = {
                Text(
                    text = "Informações do Imóvel:",
                    style = TextStyle(
                        color = getTextColor(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            },
            text = {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(2.dp)

                ) {

                    var streetAddress = property.streetAddress  + ", " + property.number
                    if (property.complement.isNotEmpty())
                        streetAddress = streetAddress + " - " + property.complement

                    Text(
                        text = streetAddress
                    )

                    Text(
                        text = property.district
                    )
                    Text(
                        text = property.city + " - " + property.state
                    )
                    Text(
                        text = "CEP: "+property.zipCode , style = TextStyle(

                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                    Button(
                        onClick = {
                            openPropertyDeleteDialog.value = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Excluir",
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    Button(
                        onClick = {
                            openPropertyChangeAddressDialog.value = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Alterar Endereço",
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }
                    }

                    HorizontalDivider(thickness = 1.dp)
                    Text(
                        text = "Documentos do Imóvel no GDrive:",
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Configurar Pasta",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }

                        Button(
                            onClick = {

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Abrir Pasta",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }
                    }

                    HorizontalDivider(thickness = 1.dp)


                    Text(
                        text = "Fluxo de Caixa:",
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Gastos",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }

                        Button(
                            onClick = {

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Receitas",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }
                    }

                    HorizontalDivider(thickness = 1.dp)

                    Text(
                        text = "Instalações Contratadas:",
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "CPFL",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }

                        Button(
                            onClick = {

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Sanasa",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }
                    }

                    HorizontalDivider(thickness = 1.dp)

                    Text(
                        text = "Impostos:",
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "IPTU",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }



                    }

                    HorizontalDivider(thickness = 1.dp)

                }
            },
            confirmButton = {

            }, dismissButton = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        onClick = {
                            openPropertyDetailDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                }
            }
        )
        when {
            openPropertyDetailDatePickerDialog.value -> {
                //PropertyDetailDatePickerDialog(
                //    openPropertyDetailDatePickerDialog = openPropertyDetailDatePickerDialog,
                //    monthlyBillingViewModel = monthlyBillingViewModel,
                //    patient = patient,
                //    context = context
               // )
            }
        }
        when {
            openPropertyDetailDeleteMonthlyBillingDialog.value -> {
                //PropertyDetailDeleteMonthlyBillingDialog(
                //    openPropertyDetailDeleteServingDialog = openPropertyDetailDeleteMonthlyBillingDialog,
                //    monthlyBillingViewModel = monthlyBillingViewModel,
                //    allAttendedDays = allAttendedDays,
                //    patient = patient,
                //    context = context
                //)
            }
        }
        when {
            openPropertyReceiptsDialog.value -> {
                //PropertyNewReceiptDialog(
                //    openPropertyReceiptsDialog = openPropertyReceiptsDialog,
                //    currentAttendedDays = currentAttendedDays,
                //    patient = patient,
                //    context = context
               // )
            }
        }
        when {
            openReceivePaymentDialog.value -> {
                //PropertyReceivePaymentDialog(
                //    openReceivePaymentDialog = openReceivePaymentDialog,
                //    unpaids = unpaids,
                //    receiptViewModel = receiptViewModel,
                //    receiptPDFViewModel = receiptPDFViewModel,
                //    patient = patient,
                //    context = context,
               // )
            }
        }
        when {
            openPropertyDeleteDialog.value -> {
                //PropertyDeleteDialog(
                //    openPropertyDeleteDialog = openPropertyDeleteDialog,
                //    patientViewModel = patientViewModel,
                //    openPropertyDetailDialog = openPropertyDetailDialog,
                //    patient = patient,
                //    context = context,
               // )
            }
        }
        when {
            openPropertyChangeAddressDialog.value -> {
                PropertyCreateScreen(openPropertyChangeAddressDialog, propertyViewModel, context, property)
            }
        }
    }
}

