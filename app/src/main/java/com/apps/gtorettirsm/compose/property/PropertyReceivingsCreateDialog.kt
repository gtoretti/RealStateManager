/*
 */

package com.apps.gtorettirsm.compose.property


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.compose.utils.DrawScrollableView
import com.apps.gtorettirsm.compose.utils.daysBetween
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getProviderServicesList
import com.apps.gtorettirsm.compose.utils.getRedTextColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.screenToDouble
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.compose.utils.toScreen
import com.apps.gtorettirsm.data.Expense
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.Provider
import com.apps.gtorettirsm.data.Receiving
import com.apps.gtorettirsm.viewmodels.ExpenseViewModel
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ProviderViewModel
import com.apps.gtorettirsm.viewmodels.ReceivingViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun PropertyReceivingsCreateDialog(
    openPropertyReceivingsCreateDialog: MutableState<Boolean>,
    context: Context,
    receiving: Receiving
) {
    var propertyViewModel: PropertyViewModel = hiltViewModel()
    val propertiesFlow = propertyViewModel.properties
    val properties by propertiesFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    var receivingViewModel: ReceivingViewModel = hiltViewModel()

    PropertyReceivingsCreateDialog(openPropertyReceivingsCreateDialog,context,properties,receivingViewModel,receiving)
}


@Composable
fun PropertyReceivingsCreateDialog(
    openPropertyReceivingsCreateDialog: MutableState<Boolean>,
    context: Context,
    properties: List<Property>,
    receivingViewModel: ReceivingViewModel,
    receiving: Receiving
) {

    val openPropertyReceivingDeleteDialog = remember { mutableStateOf(false) }

    var receivingValue by remember { mutableStateOf("") }
    var receivingDescription by remember { mutableStateOf("") }
    var receivingDate by remember { mutableStateOf("") }
    var rentBillingDueDate by remember { mutableStateOf("") }
    var fineValue by remember { mutableStateOf("") }
    var delayDays by remember { mutableStateOf("") }


    val openDateDialog = remember { mutableStateOf(false) }

    val fmt = SimpleDateFormat("dd/MM/yyyy")

    var loaded by remember { mutableStateOf("") }
    var header = "Novo Recebimento:"
    if (openPropertyReceivingsCreateDialog.value) {

        if (receiving.receivingId!= 0L) {
            header = "Alterar Recebimento:"

            if (loaded.trim().isEmpty()) {
                dropDownSelectPropertyId.value = receiving.propertyId
                receivingValue = receiving.totalValue.toScreen()
                receivingDescription = receiving.comments
                receivingDate = fmt.format(receiving.receivingDate)
                loaded = "true"
            }
        }else{
            if (dropDownSelectReceivingType.value == "Aluguel"){
                rentBillingDueDate =getNextNewRentReceivingDescr(dropDownSelectPropertyId.value, properties, receivingViewModel,context)
                if (rentBillingDueDate.trim().isEmpty()){
                    dropDownSelectReceivingType.value = "Outros"
                }
                if (receivingDate.trim().isNotEmpty() && rentBillingDueDate.trim().isNotEmpty()){
                    var delayDaysLong = daysBetween(fmt.parse(rentBillingDueDate),fmt.parse(receivingDate))
                    if (delayDaysLong<0)
                        delayDaysLong = 0
                    delayDays = delayDaysLong.toString()

                    var property = Property(0L,"", "", "", "", "", "", "", 0.0,0,"", "", "", "","","", "", "", 0.0, "" , 0,  "", "", "", "",  Date(0), Date(0), 0,0, "","", 0.0, "", "", "", "", "", "", "", "", 0,0.0)
                    for (item in properties) {
                        if (item.propertyId == dropDownSelectPropertyId.value)
                            property = item
                        break
                    }
                    fineValue = (property.contractFinePerDelayedDay * delayDaysLong).toScreen()

                    receivingValue = (property.contractMonthlyBillingValue + (property.contractFinePerDelayedDay * delayDaysLong)).toScreen()
                }
            }
        }


        AlertDialog(shape = RoundedCornerShape(10.dp), onDismissRequest = {
            openPropertyReceivingsCreateDialog.value = false
        }, modifier = Modifier
            .width(550.dp)
            .height(800.dp),


            title = {
                Text(
                    text = header, style = TextStyle(
                        color = getTextColor(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            }, text = {
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    PropertiesDropdownMenu(properties)

                    ReceivingTypeDropdownMenu()

                    if (dropDownSelectReceivingType.value.equals("Aluguel")){
                        OutlinedTextField(
                            value = rentBillingDueDate,
                            onValueChange = {

                            },
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = getTextColor(),
                                fontWeight = FontWeight.Normal
                            ),placeholder = {Text("")},
                            label = {
                                Text(
                                    text = "Vencimento do Próximo Aluguel a Receber:",
                                    style = TextStyle(
                                        color = getTextColor(),fontSize = 12.sp,
                                    )
                                )
                            }, enabled = false
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.width(160.dp)
                                .clickable {
                                    openDateDialog.value = true
                                },
                            value = receivingDate,
                            onValueChange = {
                            },
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = getTextColor(),
                                fontWeight = FontWeight.Normal
                            ),

                            label = {
                                Text(
                                    text = "Data do Recebimento:",
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
                                openDateDialog.value = true
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "Alterar a Data do Recebimento",
                                tint = getTextColor(),
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .size(24.dp)
                            )
                        }
                    }


                    if (dropDownSelectReceivingType.value.equals("Aluguel")){
                        OutlinedTextField(
                            value = delayDays,
                            onValueChange = {

                            },
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = getTextColor(),
                                fontWeight = FontWeight.Normal
                            ),placeholder = {Text("")},
                            label = {
                                Text(
                                    text = "Atraso em Dias:",
                                    style = TextStyle(
                                        color = getTextColor(),fontSize = 12.sp,
                                    )
                                )
                            }, enabled = false
                        )

                        OutlinedTextField(
                            value = fineValue,
                            onValueChange = {

                            },
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = getTextColor(),
                                fontWeight = FontWeight.Normal
                            ),placeholder = {Text("")},
                            label = {
                                Text(
                                    text = "Valor da Multa:",
                                    style = TextStyle(
                                        color = getTextColor(),fontSize = 12.sp,
                                    )
                                )
                            }, enabled = false
                        )
                    }

                    OutlinedTextField(
                        value = receivingValue,
                        onValueChange = {
                            receivingValue = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),placeholder = {Text("00.000,00")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        label = {
                            Text(
                                text = "Valor Recebido:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp,
                                )
                            )
                        }
                    )

                    OutlinedTextField(
                        value = receivingDescription,
                        onValueChange = {
                            receivingDescription = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),placeholder = {Text("")},
                        label = {
                            Text(
                                text = "Comentários:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp,
                                )
                            )
                        }
                    )


                }


                when {
                    openDateDialog.value -> {
                        DatePickerModal(
                            onDateSelected = {
                                if (it != null) {
                                    receivingDate = SimpleDateFormat("dd/MM/yyyy").format(Date(it))
                                    if (dropDownSelectReceivingType.value.equals("Aluguel") && rentBillingDueDate.trim().isNotEmpty()){
                                        delayDays = daysBetween(fmt.parse(rentBillingDueDate),Date(it)).toString()


                                    }
                                }
                            }, openDialog = openDateDialog, title = "Data do Recebimento"
                        )
                    }
                }
                when {
                    openPropertyReceivingDeleteDialog.value -> {
                        PropertyReceivingDeleteDialog(openPropertyReceivingDeleteDialog,openPropertyReceivingsCreateDialog,receivingViewModel,receiving,context)
                    }
                }


            }, confirmButton = {

            }, dismissButton = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = {
                        openPropertyReceivingsCreateDialog.value = false
                        dropDownSelectPropertyId.value = 0L
                        dropDownSelectPropertyDesc.value = ""
                        dropDownSelectReceivingType.value = ""
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Cancelar", style = TextStyle(
                                fontSize = 13.sp,
                            )
                        )
                    }

                    if (receiving.receivingId!= 0L) {
                        Button(
                            onClick = {
                                openPropertyReceivingDeleteDialog.value = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = getButtonColor()
                            ), modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Excluir", style = TextStyle(
                                    fontSize = 13.sp,
                                )
                            )
                        }
                    }

                    Button(onClick = {
                        if (dropDownSelectPropertyId.value == 0L) {
                            showToast(
                                "Por favor, selecione o imóvel referente ao recebimento.",
                                context
                            )
                        } else
                            if (receivingValue.trim().isEmpty()) {
                                showToast("Por favor, informe o valor recebido.", context)
                            } else
                                if (receivingDate.trim().isEmpty()) {
                                    showToast("Por favor, informe a data do recebimento.", context)
                                } else {
                                    var desc = receivingDescription
                                    var receivingDateDt = fmt.parse(receivingDate)


                                    var rentBillingDueDateDt = Date(0L)
                                    if (rentBillingDueDate.trim().isNotEmpty()) {
                                        rentBillingDueDateDt = fmt.parse(rentBillingDueDate)
                                    }

                                    var fineValueDouble = 0.0
                                    if (fineValue.trim().isNotEmpty())
                                        fineValueDouble = fineValue.screenToDouble()

                                    var delayDaysLong = 0L

                                    if (delayDays.trim().isNotEmpty()) {
                                        delayDaysLong = delayDays.toLong()
                                    }

                                    receivingViewModel.saveReceiving(
                                        Receiving(
                                            receiving.receivingId,
                                            receivingDateDt,
                                            dropDownSelectPropertyId.value,
                                            receivingValue.screenToDouble(),
                                            dropDownSelectReceivingType.value,
                                            desc,
                                            rentBillingDueDateDt,
                                            fineValueDouble,
                                            delayDaysLong
                                        )
                                    )

                                    showToast("Recebimento registrado com sucesso!", context)

                                    openPropertyReceivingsCreateDialog.value = false
                                    dropDownSelectPropertyId.value = 0L
                                    dropDownSelectPropertyDesc.value = ""
                                    dropDownSelectReceivingType.value = ""

                                }



                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Salvar", style = TextStyle(
                                fontSize = 13.sp,
                            )
                        )
                    }
                }
            }
        )
    }
}


@Composable
fun ReceivingTypeDropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    val types = listOf("Aluguel", "Outros")
    Row() {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                },
            value = dropDownSelectReceivingType.value,
            onValueChange = {

            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = getTextColor(),
                fontWeight = FontWeight.Normal
            ), placeholder = { Text("") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            label = {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()){
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        IconButton(onClick = { expanded = !expanded },
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(24.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Selecionar Tipo de Recebimento")
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            types.forEach { item ->

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = item, style = TextStyle(
                                                color = getTextColor(),
                                                fontSize = 14.sp,
                                            )
                                        )
                                    },
                                    onClick = {
                                        dropDownSelectReceivingType.value = item
                                        expanded = !expanded
                                    }
                                )
                            }
                        }
                    }
                    Text(
                        text = "Tipo de Recebimento:",
                        style = TextStyle(
                            color = getTextColor(), fontSize = 12.sp,
                        )
                    )
                }

            }, enabled = false
        )
    }

}


@Composable
fun getNextNewRentReceivingDescr(propertyId:Long, properties: List<Property>, receivingViewModel: ReceivingViewModel, context: Context): String{
    var ret = ""
    val fmt = SimpleDateFormat("dd/MM/yyyy")
    var property = Property(0L,"", "", "", "", "", "", "", 0.0,0,"", "", "", "","","", "", "", 0.0, "" , 0,  "", "", "", "",  Date(0), Date(0), 0,0, "","", 0.0, "", "", "", "", "", "", "", "", 0,0.0)
    for (item in properties) {
        if (item.propertyId == propertyId)
            property = item
        break
    }

    if (property.contractPaymentDate==0){
        showToast("Por favor, informe o Dia de Pagamento no Mês nas informações do contrato.",context)
        return ""
    }

    if (property.contractStartDate.time==0L || property.contractEndedDate.time==0L || (property.contractMonths==0 && property.contractDays == 0)){
        showToast("Por favor, informe as datas de Início e Término nas informações do contrato.",context)
        return ""
    }

    var startBillingDate = Calendar.getInstance()
    startBillingDate.time = property.contractStartDate
    startBillingDate.set(Calendar.DAY_OF_MONTH,1)

    var rentFlow = receivingViewModel.getRentReceivings(propertyId,startBillingDate.time)
    val payments by rentFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    val paymentsQtd = payments.size


    var totalBillingQtd = property.contractMonths
    if (property.contractDays > 0)
        totalBillingQtd++

    if (paymentsQtd >= totalBillingQtd){
        showToast("O período do contrato está quitado. Por favor verifique o período do contrato.",context)
        return ""
    }else{
        //proximo vencimento a quitar:

        //checa se tem algum no meio que nao foi pago:

        var i = 0
        var billing = Calendar.getInstance()

        //corre a lista de cobranças mensais e checa se houve pagamento
        while (i < totalBillingQtd) {
            startBillingDate.add(Calendar.MONTH,1)

            var paid = false
            //checa lista de pagamentos realizados se tem cada cobrança
            for (payment in payments) {
                var dueDate = Calendar.getInstance()
                dueDate.time = payment.rentBillingDueDate
                if (dueDate.get(Calendar.YEAR) == startBillingDate.get(Calendar.YEAR) && dueDate.get(Calendar.MONTH) == startBillingDate.get(Calendar.MONTH) )
                    paid = true
                break
            }
            //se nao tem a cobrança, essa é a proxima a pagar
            if (!paid){
                billing.time = startBillingDate.time
                billing.set(Calendar.DAY_OF_MONTH,property.contractPaymentDate)
                break
            }
            i++
        }
        ret = fmt.format(billing.time)
    }
    return ret
}

