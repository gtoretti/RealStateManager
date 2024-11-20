/*
 */

package com.apps.gtorettirsm.compose.property

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.MonthlyBilling
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.MonthlyBillingViewModel
import java.util.Calendar


@Composable
fun PropertyDetailDatePickerDialog(
    openPropertyDetailDatePickerDialog: MutableState<Boolean>,
    monthlyBillingViewModel: MonthlyBillingViewModel,
    patient: Property,
    context: Context
) {
    PropertyDetailDatePickerDialog(
        openPropertyDetailDatePickerDialog,
        patient,
        monthlyBillingViewModel,
        context
    )
}

@Composable
fun PropertyDetailDatePickerDialog(
    openPropertyDetailDatePickerDialog: MutableState<Boolean>,
    patient: Property,
    viewModel: MonthlyBillingViewModel,
    context: Context
) {

    var day by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) }
    var month by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    var year by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.YEAR)) }

    if (openPropertyDetailDatePickerDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openPropertyDetailDatePickerDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(600.dp),

            title = {
                Text(
                    text = "Adicionar atendimento:",
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
                    Text(
                        text = patient.address,
                        style = TextStyle(
                            color = getTextColor(),

                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                        )
                    )
                    day = DayDropDownMenu()
                    month = MonthDropDownMenu()
                    year = YearDropDownMenu()
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        var date = Calendar.getInstance()
                        date.set(Calendar.DAY_OF_MONTH, day)
                        date.set(Calendar.MONTH, month)
                        date.set(Calendar.YEAR, year)

                        viewModel.saveMonthlyBilling(
                            MonthlyBilling(
                                0,
                                date.time,
                                patient.propertyId,
                                patient.rentalMontlyPrice, 0
                            )
                        )
                        openPropertyDetailDatePickerDialog.value = false
                        showToast("Atendimento salvo com sucesso!", context)
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
                        openPropertyDetailDatePickerDialog.value = false
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

@Composable
fun DayDropDownMenu(): Int {
    var day by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) }
    var expanded by remember { mutableStateOf(false) }
    Box() {
        OutlinedTextField(
            readOnly = true,
            value = day.toString(),
            onValueChange = { },
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = getTextColor(),
                fontWeight = FontWeight.Normal
            ),
            label = {
                Text(
                    text = "Dia do mês",
                    style = TextStyle(
                        color = getTextColor()
                    ),fontSize = 12.sp,
                )
            }
        )
        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.offset(x = 240.dp, y = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Abrir",
                tint = getTextColor(),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.requiredSizeIn(maxHeight = 300.dp)
        ) {
            for (i in 1..31) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = i.toString(),
                            style = TextStyle(
                                color = getTextColor()
                            )
                        )
                    },
                    onClick = {
                        day = i
                        expanded = false
                    }
                )
            }
        }
    }
    return day
}


@Composable
fun MonthDropDownMenu(): Int {
    var expanded by remember { mutableStateOf(false) }
    var month by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    Box() {
        OutlinedTextField(
            readOnly = true,
            value = getMonthName(month),
            onValueChange = { },
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = getTextColor(),
                fontWeight = FontWeight.Normal
            ),
            label = {
                Text(
                    text = "Mês",
                    style = TextStyle(
                        color = getTextColor()
                    ),fontSize = 12.sp,
                )
            }
        )
        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.offset(x = 240.dp, y = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Abrir",
                tint = getTextColor(),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.requiredSizeIn(maxHeight = 600.dp)
        ) {

            DropdownMenuItem(
                text = {
                    Text(
                        text = getMonthName(0),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    month = 0
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = getMonthName(1),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    month = 1
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = getMonthName(2),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    month = 2
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = getMonthName(3),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    month = 3
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = getMonthName(4),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    month = 4
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = getMonthName(5),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    month = 5
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = getMonthName(6),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    month = 6
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = getMonthName(7),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    month = 7
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = getMonthName(8),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    month = 8
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = getMonthName(9),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    month = 9
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = getMonthName(10),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    month = 10
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = getMonthName(11),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    month = 11
                    expanded = false
                }
            )


        }
    }
    return month
}

fun getMonthName(i: Int): String {

    if (i == 0)
        return "Janeiro"
    if (i == 1)
        return "Fevereiro"
    if (i == 2)
        return "Março"
    if (i == 3)
        return "Abril"
    if (i == 4)
        return "Maio"
    if (i == 5)
        return "Junho"
    if (i == 6)
        return "Julho"
    if (i == 7)
        return "Agosto"
    if (i == 8)
        return "Setembro"
    if (i == 9)
        return "Outubro"
    if (i == 10)
        return "Novembro"
    if (i == 11)
        return "Dezembro"
    return ""
}


@Composable
fun YearDropDownMenu(): Int {
    var expanded by remember { mutableStateOf(false) }
    var year by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    Box() {
        OutlinedTextField(
            readOnly = true,
            value = year.toString(),
            onValueChange = { },
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = getTextColor(),
                fontWeight = FontWeight.Normal
            ),
            label = {
                Text(
                    text = "Ano",
                    style = TextStyle(
                        color = getTextColor()
                    ),fontSize = 12.sp,
                )
            }
        )
        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.offset(x = 240.dp, y = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Abrir",
                tint = getTextColor(),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.requiredSizeIn(maxHeight = 300.dp)
        ) {

            DropdownMenuItem(
                text = {
                    Text(
                        text = Calendar.getInstance().get(Calendar.YEAR).toString(),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    year = Calendar.getInstance().get(Calendar.YEAR)
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = (Calendar.getInstance().get(Calendar.YEAR) - 1).toString(),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    year = Calendar.getInstance().get(Calendar.YEAR) - 1
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = (Calendar.getInstance().get(Calendar.YEAR) - 2).toString(),
                        style = TextStyle(
                            color = getTextColor()
                        )
                    )
                },
                onClick = {
                    year = Calendar.getInstance().get(Calendar.YEAR) - 2
                    expanded = false
                }
            )

        }
    }
    return year
}
