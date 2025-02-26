/*
 */

package com.apps.gtorettirsm.compose.property


import android.content.Context
import android.util.Log
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
import com.apps.gtorettirsm.viewmodels.ExpenseViewModel
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ProviderViewModel
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date


val dropDownSelectPropertyId = mutableLongStateOf(0)
val dropDownSelectPropertyDesc = mutableStateOf("")
val dropDownSelectExpenseType = mutableStateOf("")
val dropDownSelectReceivingType = mutableStateOf("")
val dropDownSelectProviderId = mutableLongStateOf(0)
val dropDownSelectProviderName = mutableStateOf("")
val dropDownSelectProviderServices = mutableStateOf(ArrayList<String>())
val dropDownSelectProviderServiceDesc = mutableStateOf("")

@Composable
fun PropertyExpensesCreateDialog(
    openPropertyExpensesCreateDialog: MutableState<Boolean>,
    context: Context,
    expense: Expense
) {
    var propertyViewModel: PropertyViewModel = hiltViewModel()
    val propertiesFlow = propertyViewModel.properties
    val properties by propertiesFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    var providerViewModel: ProviderViewModel = hiltViewModel()
    val providerFlow = providerViewModel.providers
    val providers by providerFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    var expenseViewModel: ExpenseViewModel = hiltViewModel()

    PropertyExpensesCreateDialog(openPropertyExpensesCreateDialog,context,properties,providers,expenseViewModel,expense)
}


@Composable
fun PropertyExpensesCreateDialog(
    openPropertyExpensesCreateDialog: MutableState<Boolean>,
    context: Context,
    properties: List<Property>,
    providers: List<Provider>,
    expenseViewModel: ExpenseViewModel,
    expense: Expense
) {

    val openPropertyExpenseDeleteDialog = remember { mutableStateOf(false) }

    var expenseValue by remember { mutableStateOf("") }
    var expenseDescription by remember { mutableStateOf("") }
    var paymentDate by remember { mutableStateOf("") }
    val openDateDialog = remember { mutableStateOf(false) }

    val fmt = SimpleDateFormat("dd/MM/yyyy")

    var loaded by remember { mutableStateOf("") }
    var header = "Novo Desembolso:"
    if (openPropertyExpensesCreateDialog.value) {

        if (expense.expenseId!= 0L) {
            header = "Alterar Desembolso:"
            for (item in providers) {
                if (item.providerId == expense.providerId){
                    dropDownSelectProviderServices.value = getProviderServicesList(item)
                    break
                }
            }
            if (loaded.trim().isEmpty()) {
                dropDownSelectPropertyId.value = expense.propertyId
                dropDownSelectExpenseType.value = expense.type
                dropDownSelectProviderId.value = expense.providerId
                dropDownSelectProviderName.value = expense.providerName
                dropDownSelectProviderServiceDesc.value = expense.serviceDesc
                expenseValue = expense.value.toScreen()
                expenseDescription = expense.comments
                paymentDate = fmt.format(expense.date)
                loaded = "true"
            }
        }


        AlertDialog(shape = RoundedCornerShape(10.dp), onDismissRequest = {
            openPropertyExpensesCreateDialog.value = false
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
                    modifier = Modifier.padding(horizontal = 10.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    PropertiesDropdownMenu(properties)

                    ExpenseTypeDropdownMenu()

if (dropDownSelectExpenseType.value == "Serviços Prestados") {

    ProvidersDropdownMenu(providers)

    ProviderServicesDropdownMenu(dropDownSelectProviderServices.value)

}else{
    dropDownSelectProviderId.value = 0L
    dropDownSelectProviderName.value = ""
    dropDownSelectProviderServices.value = ArrayList<String>()
    dropDownSelectProviderServiceDesc.value = ""
}

                    OutlinedTextField(
                        value = expenseValue,
                        onValueChange = {
                            expenseValue = it
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
                                text = "Valor:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp,
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
                            modifier = Modifier
                                .width(160.dp)
                                .clickable {
                                    openDateDialog.value = true
                                },
                            value = paymentDate,
                            onValueChange = {
                            },
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = getTextColor(),
                                fontWeight = FontWeight.Normal
                            ),

                            label = {
                                Text(
                                    text = "Data do Desembolso:",
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
                                contentDescription = "Alterar a Data do Desembolso",
                                tint = getTextColor(),
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .size(24.dp)
                            )
                        }
                    }

                    OutlinedTextField(
                        value = expenseDescription,
                        onValueChange = {
                            expenseDescription = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),placeholder = {Text("")},
                        label = {
                            Text(
                                text = "Descrição:",
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
                                    paymentDate = SimpleDateFormat("dd/MM/yyyy").format(Date(it))
                                }
                            }, openDialog = openDateDialog, title = "Data do Desembolso"
                        )
                    }
                }
                when {
                    openPropertyExpenseDeleteDialog.value -> {
                        PropertyExpenseDeleteDialog(openPropertyExpenseDeleteDialog,openPropertyExpensesCreateDialog,expenseViewModel,expense,context)
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
                        openPropertyExpensesCreateDialog.value = false
                        dropDownSelectPropertyId.value = 0L
                        dropDownSelectPropertyDesc.value = ""
                        dropDownSelectExpenseType.value = ""
                        dropDownSelectProviderId.value = 0L
                        dropDownSelectProviderName.value = ""
                        dropDownSelectProviderServices.value = ArrayList<String>()
                        dropDownSelectProviderServiceDesc.value = ""
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


                    if (expense.expenseId!= 0L) {
                        Button(
                            onClick = {
                                openPropertyExpenseDeleteDialog.value = true
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
                        if (dropDownSelectPropertyId.value == 0L){
                            showToast("Por favor, selecione o imóvel referente ao desembolso.",context)
                        }else
                            if (dropDownSelectExpenseType.value == ""){
                                showToast("Por favor, selecione o tipo do desembolso.",context)
                            }else
                                if (dropDownSelectExpenseType.value.equals("Serviços Prestados") && dropDownSelectProviderId.value == 0L){
                                    showToast("Por favor, selecione o prestador de serviço.",context)
                                }else
                                    if (dropDownSelectExpenseType.value.equals("Serviços Prestados") && dropDownSelectProviderServiceDesc.value == ""){
                                        showToast("Por favor, selecione o tipo de serviço prestado.",context)
                                    }else
                                        if (expenseValue.trim().isEmpty()){
                                        showToast("Por favor, informe o valor pago.",context)
                                    }else
                                        if (paymentDate.trim().isEmpty()){
                                                showToast("Por favor, informe a data do desembolso.",context)
                                        }else
                                            {

                                                var dateDt = fmt.parse(paymentDate)
                                                var serviceDescr = dropDownSelectProviderServiceDesc.value
                                                if (!dropDownSelectExpenseType.value.equals("Serviços Prestados")){
                                                    serviceDescr = dropDownSelectExpenseType.value
                                                }

                            expenseViewModel.saveExpense(Expense(expense.expenseId,dateDt,dropDownSelectPropertyId.value,expenseValue.screenToDouble(),expenseDescription,dropDownSelectExpenseType.value,serviceDescr,dropDownSelectProviderId.value,
                                dropDownSelectProviderName.value))
                            showToast("Desembolso registrado com sucesso!",context)

                            openPropertyExpensesCreateDialog.value = false
                            dropDownSelectPropertyId.value = 0L
                            dropDownSelectPropertyDesc.value = ""
                            dropDownSelectExpenseType.value = ""
                            dropDownSelectProviderId.value = 0L
                            dropDownSelectProviderName.value = ""
                            dropDownSelectProviderServices.value = ArrayList<String>()
                            dropDownSelectProviderServiceDesc.value = ""
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
fun PropertiesDropdownMenu(properties: List<Property>) {
    var expanded by remember { mutableStateOf(false) }

    var desc = "Selecione o Imóvel"
    if (dropDownSelectPropertyDesc.value.isNotEmpty())
        desc = dropDownSelectPropertyDesc.value
    Row() {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                },
            value =desc,
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
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Selecionar Imóvel")
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            properties.forEach { item ->

                                var streetAddress = item.streetAddress + ", " + item.number
                                if (item.complement.isNotEmpty())
                                    streetAddress = streetAddress + " - " + item.complement

                                DropdownMenuItem(
                                    text = {
                                        Column(
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.spacedBy(2.dp)

                                        ) {

                                            Text(
                                                text = streetAddress, style = TextStyle(
                                                    color = getTextColor(),
                                                    fontSize = 14.sp,
                                                )
                                            )

                                            Text(
                                                text = item.district, style = TextStyle(
                                                    color = getTextColor(),
                                                    fontSize = 14.sp,
                                                )
                                            )
                                            Text(
                                                text = item.city + " - " + item.state, style = TextStyle(
                                                    color = getTextColor(),
                                                    fontSize = 14.sp,
                                                )
                                            )
                                            Text(
                                                text = "CEP: " + item.zipCode, style = TextStyle(
                                                    color = getTextColor(),
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Bold,
                                                )
                                            )
                                            HorizontalDivider(thickness = 1.dp)

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.End,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Spacer(modifier = Modifier.height(5.dp))
                                            }
                                        }
                                    },
                                    onClick = {
                                        dropDownSelectPropertyId.value = item.propertyId
                                        dropDownSelectPropertyDesc.value = streetAddress + ", " + item.district + ", " +  item.city + " - " + item.state + ", " + item.zipCode
                                        expanded = !expanded
                                    }
                                )
                            }
                        }
                    }
                    Text(
                        text = "Imóvel:",
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
fun ExpenseTypeDropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    val types = listOf("Serviços Prestados", "Água e Esgoto","Energia Elétrica","Condomínio","Impostos", "Outros")
    Row() {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                },
            value = dropDownSelectExpenseType.value,
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
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Selecionar Tipo de Desembolso")
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
                                        dropDownSelectExpenseType.value = item
                                        expanded = !expanded
                                    }
                                )
                            }
                        }
                    }
                    Text(
                        text = "Tipo de Desembolso:",
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
fun ProvidersDropdownMenu(providers: List<Provider>) {
    var expanded by remember { mutableStateOf(false) }

    Row() {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                },
            value = dropDownSelectProviderName.value,
            onValueChange = { },
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = getTextColor(),
                fontWeight = FontWeight.Normal
            ), placeholder = { Text("") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            label = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        IconButton(onClick = { expanded = !expanded },
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(24.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Selecionar Imóvel")
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            providers.forEach { item ->

                                DropdownMenuItem(
                                    text = {
                                        Column(
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.spacedBy(2.dp)

                                        ) {

                                            Text(
                                                text = item.name, style = TextStyle(
                                                    color = getTextColor(),
                                                    fontSize = 14.sp,
                                                )
                                            )

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.End,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Spacer(modifier = Modifier.height(5.dp))
                                            }

                                        }
                                    },
                                    onClick = {
                                        dropDownSelectProviderServiceDesc.value = ""
                                        dropDownSelectProviderId.value = item.providerId
                                        dropDownSelectProviderName.value = item.name
                                        dropDownSelectProviderServices.value = getProviderServicesList(item)
                                        expanded = !expanded
                                    }
                                )
                            }
                        }
                    }
                    Text(
                        text = "Prestador de Serviço:",
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
fun ProviderServicesDropdownMenu(services: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Row() {
        OutlinedTextField(

            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                },

            value = dropDownSelectProviderServiceDesc.value,
            onValueChange = { },
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = getTextColor(),
                fontWeight = FontWeight.Normal
            ), placeholder = { Text("") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            label = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {



                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        IconButton(onClick = { expanded = !expanded },
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(24.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Selecionar Tipo de Serviço")
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            services.forEach { item ->

                                DropdownMenuItem(
                                    text = {
                                        Column(
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.spacedBy(2.dp)

                                        ) {

                                            Text(
                                                text = item, style = TextStyle(
                                                    color = getTextColor(),
                                                    fontSize = 14.sp,
                                                )
                                            )

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.End,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Spacer(modifier = Modifier.height(5.dp))
                                            }

                                        }
                                    },
                                    onClick = {
                                        dropDownSelectProviderServiceDesc.value = item
                                        expanded = !expanded
                                    }
                                )
                            }
                        }
                    }

                    Text(
                        text = "Tipo de Serviço Prestado:",
                        style = TextStyle(
                            color = getTextColor(), fontSize = 12.sp,
                        )
                    )
                }

            }, enabled = false
        )
    }
}