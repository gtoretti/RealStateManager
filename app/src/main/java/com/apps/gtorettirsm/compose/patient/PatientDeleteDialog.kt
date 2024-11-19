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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.Patient
import com.apps.gtorettirsm.viewmodels.PatientViewModel

@Composable
fun PatientDeleteDialog(
    openPatientDeleteDialog: MutableState<Boolean>,
    openPatientDetailDialog: MutableState<Boolean>,
    patientViewModel: PatientViewModel,
    patient: Patient,
    context: Context
) {
    if (openPatientDeleteDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openPatientDeleteDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(250.dp),

            title = {
                Text(
                    text = "Excluir paciente:",
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
                    Text(
                        text = patient.name,
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        patientViewModel.deletePatient(patient)
                        openPatientDeleteDialog.value = false
                        openPatientDetailDialog.value = false
                        showToast("Paciente excluído com sucesso!", context)
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
                        openPatientDeleteDialog.value = false
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

