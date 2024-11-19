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
import com.apps.gtorettirsm.compose.utils.screenToDouble
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.compose.utils.toScreen
import com.apps.gtorettirsm.data.Attendance
import com.apps.gtorettirsm.data.Patient
import com.apps.gtorettirsm.data.Receipt
import com.apps.gtorettirsm.viewmodels.AttendanceViewModel
import com.apps.gtorettirsm.viewmodels.PatientViewModel
import com.apps.gtorettirsm.viewmodels.ReceiptPDFViewModel
import com.apps.gtorettirsm.viewmodels.ReceiptViewModel
import kotlinx.coroutines.flow.Flow


@Composable
fun PatientDetailScreen(
    openPatientDetailDialog: MutableState<Boolean>,
    patientViewModel: PatientViewModel = hiltViewModel(),
    attendanceViewModel: AttendanceViewModel = hiltViewModel(),
    receiptViewModel: ReceiptViewModel = hiltViewModel(),
    receiptPDFViewModel: ReceiptPDFViewModel = hiltViewModel(),
    patientId: Long,
    context: Context
) {
    val patient = patientViewModel.getPatient(patientId)
    val currentAttendancesFlow =
        attendanceViewModel.getNonReceiptAttendances(patientId)
    val currentAttendances by currentAttendancesFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    val allAttendancesFlow = attendanceViewModel.getNonReceiptAttendances(patientId)
    val allAttendances by allAttendancesFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    val unpaidFlow = receiptViewModel.getUnpaidReceipts(patientId)
    val unpaids by unpaidFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    PatientDetailScreen(
        openPatientDetailDialog = openPatientDetailDialog,
        patientFlow = patient,
        patientViewModel = patientViewModel,
        attendanceViewModel = attendanceViewModel,
        currentAttendedDays = currentAttendances,
        allAttendedDays = allAttendances,
        receiptViewModel = receiptViewModel,
        receiptPDFViewModel = receiptPDFViewModel,
        unpaids = unpaids,
        context = context
    )
}

@Composable
fun PatientDetailScreen(
    openPatientDetailDialog: MutableState<Boolean>,
    patientFlow: Flow<Patient>,
    patientViewModel: PatientViewModel,
    attendanceViewModel: AttendanceViewModel,
    currentAttendedDays: List<Attendance>,
    allAttendedDays: List<Attendance>,
    receiptViewModel: ReceiptViewModel,
    receiptPDFViewModel: ReceiptPDFViewModel,
    unpaids: List<Receipt>,
    context: Context
) {

    val openPatientDetailDatePickerDialog = remember { mutableStateOf(false) }
    val openPatientDetailDeleteAttendanceDialog = remember { mutableStateOf(false) }
    val openPatientReceiptsDialog = remember { mutableStateOf(false) }
    val openReceivePaymentDialog = remember { mutableStateOf(false) }
    val openPatientDeleteDialog = remember { mutableStateOf(false) }

    val patient by patientFlow.collectAsStateWithLifecycle(
        initialValue = Patient(
            0,
            "",
            "",
            0.0,
            0
        )
    )
    var name by remember { mutableStateOf("") }
    var parentName by remember { mutableStateOf("") }
    var sessionPrice by remember { mutableStateOf("") }

    sessionPrice = patient.sessionPrice.toScreen()
    name = patient.name
    parentName = patient.parentName

    var attendedDaysDescr = getAttendedDaysDescr(currentAttendedDays)

    if (openPatientDetailDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openPatientDetailDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(840.dp),

            title = {
                Text(
                    text = "Paciente:",
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

                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),placeholder = {Text("Informe aqui o nome do(a) paciente.")},
                        label = {
                            Text(
                                text = "Nome",
                                style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 12.sp,
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
                            fontSize = 14.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),placeholder = {Text("Informe aqui o nome do responsável pelo pagamento do recibo.")},
                        label = {
                            Text(
                                text = "Nome do Responsável",
                                style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 12.sp,
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
                            fontSize = 14.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),
                        placeholder = {Text("Informe aqui o valor de cada atendimento.")},
                        label = {
                            Text(
                                text = "Valor do Atendimento",
                                style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 12.sp,
                                )
                            )
                        }, keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                openPatientDeleteDialog.value = true
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
                                if (name.isEmpty() || name.isBlank()) {
                                    showToast("Por favor, informe o nome do(a) paciente.", context)
                                } else
                                    if (parentName.isEmpty() || parentName.isBlank()) {
                                        showToast("Por favor, informe o nome do responsável pelo pagamento.",context)
                                    } else
                                        try {
                                            patientViewModel.savePatient(
                                                Patient(
                                                    patientId = patient.patientId,
                                                    name = name,
                                                    parentName = parentName,
                                                    sessionPrice = sessionPrice.screenToDouble(),
                                                    deleted = 0
                                                )
                                            )
                                            openPatientDetailDialog.value = false
                                            showToast("Informações salvas com sucesso!", context)
                                        } catch (ex: NumberFormatException) {
                                            sessionPrice = ""
                                            showToast("Por favor, informe o valor do atendimento.", context)
                                        }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Salvar",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }
                    }

                    HorizontalDivider(thickness = 2.dp)

                    //scroll
                    DrawScrollableView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        content = {
                            Column {


                    Text(
                        text = "Atendimentos realizados:",
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )

                    Text(
                        text = "Atendimentos a serem cobrados no próximo recibo: " + currentAttendedDays.size,
                        modifier = Modifier.padding(5.dp),
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                        )
                    )
                    if (currentAttendedDays.size > 0) {
                        Text(
                            text = "Dias dos atendimentos: (dia/mês)",
                            modifier = Modifier.padding(5.dp),
                            style = TextStyle(
                                color = getTextColor(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }




                                Column {
                                    Text(
                                        text = attendedDaysDescr,
                                        modifier = Modifier.padding(5.dp),
                                        style = TextStyle(
                                            color = getTextColor(),
                                            fontSize = 16.sp,
                                        )
                                    )
                                }



                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (currentAttendedDays.size > 0) {
                            Button(
                                onClick = {
                                    openPatientDetailDeleteAttendanceDialog.value = true
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
                        }

                        Button(
                            onClick = {
                                openPatientDetailDatePickerDialog.value = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Adicionar",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }

                    }

                    HorizontalDivider(thickness = 2.dp)

                    Text(
                        text = "Recibos do paciente:",
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )

                    Text(
                        text = "Recibos aguardando pagamento: " + unpaids.size,
                        modifier = Modifier.padding(5.dp),
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                        )
                    )





                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        if (unpaids.size > 0) {
                            Button(
                                onClick = {
                                    openReceivePaymentDialog.value = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = getButtonColor()
                                ),
                                modifier = Modifier.height(33.dp)
                            ) {
                                Text(
                                    text = "Receber Pagamento / Gerar PDF",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                    )
                                )
                            }
                        }

                    }




                    }})





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
                            openPatientDetailDialog.value = false
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

                    if (currentAttendedDays.size > 0) {
                        Button(
                            onClick = {
                                openPatientReceiptsDialog.value = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Criar recibo",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }
                    }
                }
            }
        )
        when {
            openPatientDetailDatePickerDialog.value -> {
                PatientDetailDatePickerDialog(
                    openPatientDetailDatePickerDialog = openPatientDetailDatePickerDialog,
                    attendanceViewModel = attendanceViewModel,
                    patient = patient,
                    context = context
                )
            }
        }
        when {
            openPatientDetailDeleteAttendanceDialog.value -> {
                PatientDetailDeleteAttendanceDialog(
                    openPatientDetailDeleteServingDialog = openPatientDetailDeleteAttendanceDialog,
                    attendanceViewModel = attendanceViewModel,
                    allAttendedDays = allAttendedDays,
                    patient = patient,
                    context = context
                )
            }
        }
        when {
            openPatientReceiptsDialog.value -> {
                PatientNewReceiptDialog(
                    openPatientReceiptsDialog = openPatientReceiptsDialog,
                    currentAttendedDays = currentAttendedDays,
                    patient = patient,
                    context = context
                )
            }
        }
        when {
            openReceivePaymentDialog.value -> {
                PatientReceivePaymentDialog(
                    openReceivePaymentDialog = openReceivePaymentDialog,
                    unpaids = unpaids,
                    receiptViewModel = receiptViewModel,
                    receiptPDFViewModel = receiptPDFViewModel,
                    patient = patient,
                    context = context,
                )
            }
        }
        when {
            openPatientDeleteDialog.value -> {
                PatientDeleteDialog(
                    openPatientDeleteDialog = openPatientDeleteDialog,
                    patientViewModel = patientViewModel,
                    openPatientDetailDialog = openPatientDetailDialog,
                    patient = patient,
                    context = context,
                )
            }
        }
    }
}
