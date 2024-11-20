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
import com.apps.gtorettirsm.data.MonthlyBilling
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.MonthlyBillingViewModel
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun PropertyDetailDeleteMonthlyBillingDialog(
    openPropertyDetailDeleteServingDialog: MutableState<Boolean>,
    monthlyBillingViewModel: MonthlyBillingViewModel,
    allAttendedDays: List<MonthlyBilling>,
    patient: Property,
    context: Context
) {

    var monthlyBillingId by remember { mutableLongStateOf(0) }

    if (openPropertyDetailDeleteServingDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openPropertyDetailDeleteServingDialog.value = false
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
                        text = patient.address,
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
                                allAttendedDays.forEach { monthlyBilling ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .selectable(
                                                selected = (monthlyBilling.monthlyBillingId == monthlyBillingId),
                                                onClick = {
                                                    monthlyBillingId = monthlyBilling.monthlyBillingId
                                                },
                                                role = Role.RadioButton
                                            )
                                    ) {
                                        RadioButton(
                                            selected = (monthlyBillingId == monthlyBilling.monthlyBillingId),
                                            onClick = { monthlyBillingId = monthlyBilling.monthlyBillingId }
                                        )
                                        Text(
                                            text = SimpleDateFormat("dd / MM / yyyy").format(
                                                monthlyBilling.date
                                            ) + " - R$ " + monthlyBilling.rentalMontlyPrice.toScreen()
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
                        if (monthlyBillingId == 0L) {
                            showToast("Por favor, selecione o atendimento.", context)
                        } else {
                            monthlyBillingViewModel.deleteMonthlyBilling(
                                MonthlyBilling(
                                    monthlyBillingId,
                                    Date(),
                                    patient.propertyId,
                                    0.0, 0
                                )
                            )
                            openPropertyDetailDeleteServingDialog.value = false
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
                        openPropertyDetailDeleteServingDialog.value = false
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

