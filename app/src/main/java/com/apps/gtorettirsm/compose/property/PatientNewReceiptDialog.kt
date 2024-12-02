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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.apps.gtorettirsm.compose.utils.DrawScrollableView
import com.apps.gtorettirsm.compose.utils.filterMonthlyBillingsByMonth
import com.apps.gtorettirsm.compose.utils.generateReceipt
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getProfileFromFlow
import com.apps.gtorettirsm.compose.utils.getRedTextColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.compose.utils.toScreen
import com.apps.gtorettirsm.data.MonthlyBilling
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.Profile
import com.apps.gtorettirsm.viewmodels.ProfileViewModel
import com.apps.gtorettirsm.viewmodels.ReceiptPDFViewModel
import com.apps.gtorettirsm.viewmodels.ReceiptViewModel
import java.text.SimpleDateFormat
import java.util.Calendar


@Composable
fun PropertyNewReceiptDialog(
    openPropertyReceiptsDialog: MutableState<Boolean>,
    currentAttendedDays: List<MonthlyBilling>,
    patient: Property,
    context: Context
) {
    var receiptViewModel: ReceiptViewModel = hiltViewModel()
    var receiptPDFViewModel: ReceiptPDFViewModel = hiltViewModel()
    var profileViewModel: ProfileViewModel = hiltViewModel()
    var profiles = profileViewModel.profiles

    val profile = getProfileFromFlow(profiles)
    PropertyNewReceiptDialog(
        openPropertyReceiptsDialog,
        currentAttendedDays,
        receiptViewModel,
        profile,
        patient,
        context
    )
}

val chosenDay = mutableStateOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
val chosenMonth = mutableStateOf(Calendar.getInstance().get(Calendar.MONTH))
val chosenYear = mutableStateOf(Calendar.getInstance().get(Calendar.YEAR))

@Composable
fun PropertyNewReceiptDialog(
    openPropertyReceiptsDialog: MutableState<Boolean>,
    currentAttendedDays: List<MonthlyBilling>,
    receiptViewModel: ReceiptViewModel,
    profile: Profile,
    patient: Property,
    context: Context
) {

    // month & year used as filter for listing monthlyBillings
    var month by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    var year by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.YEAR)) }

    val openNewReceiptDatePickerDialog = remember { mutableStateOf(false) }
    var filteredByDateMonthlyBillings = filterMonthlyBillingsByMonth(currentAttendedDays, month, year)


    // receiptDate displayed and used to generate receipt
    var receiptDate = Calendar.getInstance()
    val userChoseReceiptDate = remember { mutableStateOf(false) }
    if (userChoseReceiptDate.value){
        receiptDate.set(Calendar.DAY_OF_MONTH, chosenDay.value)
        receiptDate.set(Calendar.MONTH, chosenMonth.value)
        receiptDate.set(Calendar.YEAR, chosenYear.value)
    }else{
        receiptDate.set(Calendar.DAY_OF_MONTH, 1)
        receiptDate.set(Calendar.MONTH, month)
        receiptDate.set(Calendar.YEAR, year)
        receiptDate.add(Calendar.MONTH, 1)
        receiptDate.set(Calendar.DAY_OF_MONTH, 1)
    }
    val sdf = SimpleDateFormat("dd / MM / yyyy")


    val select = remember { mutableStateListOf(-1L) }
    if (select.contains(-1)) {
        select.remove(-1)
        filteredByDateMonthlyBillings.forEach { attend ->
            select.add(attend.monthlyBillingId)
        }
    }

    var receiptTotal = 0.0
    filteredByDateMonthlyBillings.forEach { a ->
        if (select.contains(a.monthlyBillingId)) {
            receiptTotal = receiptTotal + a.rentalMonthlyPrice
        }
    }

    if (openPropertyReceiptsDialog.value) {
        AlertDialog(shape = RoundedCornerShape(10.dp), onDismissRequest = {
            openPropertyReceiptsDialog.value = false
            userChoseReceiptDate.value = false
        }, modifier = Modifier
            .width(550.dp)
            .height(800.dp),

            title = {
                Text(
                    text = "Gerar recibo:", style = TextStyle(
                        color = getTextColor(),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            }, text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(2.dp)

                ) {
                    Text(
                        text = patient.streetAddress, style = TextStyle(
                            color = getTextColor(),

                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                        )
                    )

                    Text(
                        text = "Mês dos atendimentos do recibo: ", style = TextStyle(
                            color = getTextColor(),

                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                        )
                    )

                    month = MonthDropDownMenu()
                    year = YearDropDownMenu()

                    if (filteredByDateMonthlyBillings.isNotEmpty()) {

                        DrawScrollableView(
                            modifier = Modifier
                                .fillMaxWidth(),
                                //.fillMaxHeight(),
                            content = {
                                Column {


                                    Text(
                                        text = "Atendimentos do recibo: ", style = TextStyle(
                                            color = getTextColor(),

                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.SansSerif,
                                        )
                                    )

                                    Column(
                                        horizontalAlignment = Alignment.Start,
                                        modifier = Modifier
                                            .height(180.dp),
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {

                                        DrawScrollableView(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .fillMaxHeight(),
                                            content = {
                                                Column {

                                                    filteredByDateMonthlyBillings.forEach { monthlyBilling ->
                                                        Row(
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Start,
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .selectable(
                                                                    selected = (select.contains(
                                                                        monthlyBilling.monthlyBillingId
                                                                    )),
                                                                    onClick = {
                                                                        if (select.contains(
                                                                                monthlyBilling.monthlyBillingId
                                                                            )
                                                                        ) select.remove(
                                                                            monthlyBilling.monthlyBillingId
                                                                        )
                                                                        else select.add(monthlyBilling.monthlyBillingId)
                                                                    },
                                                                    role = Role.Checkbox

                                                                )
                                                        ) {
                                                            Checkbox(checked = (select.contains(
                                                                monthlyBilling.monthlyBillingId
                                                            )),
                                                                onCheckedChange = {
                                                                    if (it) select.add(monthlyBilling.monthlyBillingId)
                                                                    else select.remove(monthlyBilling.monthlyBillingId)
                                                                })
                                                            Text(
                                                                text = SimpleDateFormat("dd / MM / yyyy").format(
                                                                    monthlyBilling.date
                                                                ),
                                                                style = TextStyle(
                                                                    color = getTextColor(),
                                                                    fontSize = 16.sp,
                                                                    fontFamily = FontFamily.SansSerif,
                                                                ),
                                                                modifier = Modifier.padding(
                                                                    start = 2.dp,
                                                                    end = 6.dp
                                                                )
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        )
                                    }


                                    HorizontalDivider(thickness = 2.dp)

                                    Text(
                                        text = "Valor total do recibo: " + receiptTotal.toScreen(),
                                        style = TextStyle(
                                            color = getTextColor(),

                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.SansSerif,
                                        )
                                    )

                                    Text(
                                        text = "Data do recibo: " + sdf.format(receiptDate.time),
                                        style = TextStyle(
                                            color = getRedTextColor(),
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.SansSerif,
                                        )
                                    )

                                    Button(
                                        onClick = {
                                            openNewReceiptDatePickerDialog.value = true
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = getButtonColor()
                                        ),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Text(
                                            text = "Alterar data do recibo", style = TextStyle(
                                                fontSize = 14.sp,
                                            )
                                        )
                                    }

                                }
                            })





                    }else{
                        Text(
                            text = "Não foram encontrados atendimentos com a data selecionada.", style = TextStyle(
                                color = getTextColor(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif,
                            )
                        )
                    }



                    HorizontalDivider(thickness = 2.dp)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Button(
                            onClick = {
                                openPropertyReceiptsDialog.value = false
                                userChoseReceiptDate.value = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Cancelar", style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }

                        if (filteredByDateMonthlyBillings.isNotEmpty()) {
                        Button(
                            onClick = {
                                if (profile.name.isBlank() || profile.name.isEmpty()) {
                                    showToast(
                                        "A aba Locador está incompleta. Por favor, informe o seu nome.",
                                        context
                                    )
                                } else
                                    if (profile.cpfCnpj.isBlank() || profile.cpfCnpj.isEmpty()) {
                                        showToast(
                                            "A aba Locador está incompleta. Por favor, informe o seu CPF.",
                                            context
                                        )
                                    } else
                                            if (profile.city.isBlank() || profile.city.isEmpty()) {
                                                showToast(
                                                    "A aba Locador está incompleta. Por favor, informe a cidade.",
                                                    context
                                                )
                                            } else
                                                if (profile.address.isBlank() || profile.address.isEmpty()) {
                                                    showToast(
                                                        "A aba Locador está incompleta. Por favor, informe o seu endereço comercial.",
                                                        context
                                                    )
                                                } else
                                                    if (profile.phoneNumber.isBlank() || profile.phoneNumber.isEmpty()) {
                                                        showToast(
                                                            "A aba Locador está incompleta. Por favor, informe o seu telefone comercial.",
                                                            context
                                                        )
                                                    } else
                                                                if (profile.uf.isBlank() || profile.uf.isEmpty()) {
                                                                    showToast(
                                                                        "A aba Locador está incompleta. Por favor, informe o estado.",
                                                                        context
                                                                    )
                                                                } else

                                                                    if (select.isEmpty()) {
                                                                        showToast(
                                                                            "Por favor, selecione os atendimentos do recibo.",
                                                                            context
                                                                        )
                                                                    } else {
                                                                        generateReceipt(
                                                                            profile,
                                                                            patient,
                                                                            filteredByDateMonthlyBillings,
                                                                            select,
                                                                            receiptDate,
                                                                            context,
                                                                            receiptViewModel
                                                                        )
                                                                        openPropertyReceiptsDialog.value = false
                                                                        userChoseReceiptDate.value = false
                                                                    }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Gerar", style = TextStyle(
                                    fontSize = 14.sp,
                                )
                            )
                        }
                    }
                    }

                }

            }, confirmButton = {

            }, dismissButton = {

            })


        when {
            openNewReceiptDatePickerDialog.value -> {
                PropertyNewReceiptDatePickerDialog(
                    openPropertyNewReceiptDatePickerDialog = openNewReceiptDatePickerDialog,
                    userChoseReceiptDate = userChoseReceiptDate
                )
            }
        }
    }
}

