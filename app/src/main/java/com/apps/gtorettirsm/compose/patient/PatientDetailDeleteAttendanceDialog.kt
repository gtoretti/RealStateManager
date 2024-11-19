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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.apps.gtorettirsm.data.Attendance
import com.apps.gtorettirsm.data.Patient
import com.apps.gtorettirsm.viewmodels.AttendanceViewModel
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun PatientDetailDeleteAttendanceDialog(
    openPatientDetailDeleteServingDialog: MutableState<Boolean>,
    attendanceViewModel: AttendanceViewModel,
    allAttendedDays: List<Attendance>,
    patient: Patient,
    context: Context
) {

    var attendanceId by remember { mutableLongStateOf(0) }

    if (openPatientDetailDeleteServingDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openPatientDetailDeleteServingDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(600.dp),

            title = {
                Text(
                    text = "Excluir atendimento:",
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
                    verticalArrangement = Arrangement.spacedBy(5.dp)

                ) {
                    Text(
                        text = patient.name,
                        style = TextStyle(
                            color = getTextColor(),

                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                        )
                    )

                    DrawScrollableView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        content = {
                            Column {
                                allAttendedDays.forEach { attendance ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .selectable(
                                                selected = (attendance.attendanceId == attendanceId),
                                                onClick = {
                                                    attendanceId = attendance.attendanceId
                                                },
                                                role = Role.RadioButton
                                            )
                                    ) {
                                        RadioButton(
                                            selected = (attendanceId == attendance.attendanceId),
                                            onClick = { attendanceId = attendance.attendanceId }
                                        )
                                        Text(
                                            text = SimpleDateFormat("dd / MM / yyyy").format(
                                                attendance.date
                                            ) + " - R$ " + attendance.sessionPriceAtAttendanceTime.toScreen()
                                                .replace(".", ","),
                                            style = TextStyle(
                                                color = getTextColor(),
                                                fontSize = 16.sp,
                                                fontFamily = FontFamily.SansSerif,
                                            ),
                                            modifier = Modifier.padding(start = 2.dp,end = 6.dp)
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (attendanceId == 0L) {
                            showToast("Por favor, selecione o atendimento.", context)
                        } else {
                            attendanceViewModel.deleteAttendance(
                                Attendance(
                                    attendanceId,
                                    Date(),
                                    patient.patientId,
                                    0.0, 0
                                )
                            )
                            openPatientDetailDeleteServingDialog.value = false
                            showToast("Atendimento excluído com sucesso!", context)
                        }
                    },colors = ButtonDefaults.buttonColors(
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
                        openPatientDetailDeleteServingDialog.value = false
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

