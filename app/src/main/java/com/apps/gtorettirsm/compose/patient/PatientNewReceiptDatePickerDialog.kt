/*
 */

package com.apps.gtorettirsm.compose.patient

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import java.util.Calendar


@Composable
fun PatientNewReceiptDatePickerDialog(
    openPatientNewReceiptDatePickerDialog: MutableState<Boolean>,
    userChoseReceiptDate: MutableState<Boolean>,
) {



    var day by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) }
    var month by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    var year by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.YEAR)) }

    if (openPatientNewReceiptDatePickerDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openPatientNewReceiptDatePickerDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(600.dp),

            title = {
                Text(
                    text = "Data do Recibo:",
                    style = TextStyle(
                        color = getTextColor(),
                        fontSize = 25.sp,
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
                    day = DayDropDownMenu()
                    month = MonthDropDownMenu()
                    year = YearDropDownMenu()
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        chosenDay.value = day
                        chosenMonth.value = month
                        chosenYear.value = year
                        userChoseReceiptDate.value = true
                        openPatientNewReceiptDatePickerDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = getButtonColor()
                    ),modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "Confirmar",
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

            }, dismissButton = {
                Button(
                    onClick = {
                        openPatientNewReceiptDatePickerDialog.value = false
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

