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
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getProviderServicesList
import com.apps.gtorettirsm.compose.utils.getRedTextColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.Provider
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ProviderViewModel
import java.util.ArrayList
import java.util.Date


val dropDownSelectPropertyId = mutableLongStateOf(0)
val dropDownSelectPropertyDesc = mutableStateOf("")
val dropDownSelectExpenseType = mutableStateOf("")
val dropDownSelectProviderId = mutableLongStateOf(0)
val dropDownSelectProviderName = mutableStateOf("")
val dropDownSelectProviderServices = mutableStateOf(ArrayList<String>())
val dropDownSelectProviderServiceDesc = mutableStateOf("")

@Composable
fun PropertyExpensesCreateDialog(
    openPropertyExpensesCreateDialog: MutableState<Boolean>,
    context: Context
) {
    var propertyViewModel: PropertyViewModel = hiltViewModel()
    val propertiesFlow = propertyViewModel.properties
    val properties by propertiesFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    var providerViewModel: ProviderViewModel = hiltViewModel()
    val providerFlow = providerViewModel.providers
    val providers by providerFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    PropertyExpensesCreateDialog(openPropertyExpensesCreateDialog,context,properties,providers)
}


@Composable
fun PropertyExpensesCreateDialog(
    openPropertyExpensesCreateDialog: MutableState<Boolean>,
    context: Context,
    properties: List<Property>,
    providers: List<Provider>
) {

    var expenseValue by remember { mutableStateOf("") }
    var expenseDescription by remember { mutableStateOf("") }
    var paymentDate by remember { mutableStateOf("") }

    if (openPropertyExpensesCreateDialog.value) {
        AlertDialog(shape = RoundedCornerShape(10.dp), onDismissRequest = {
            openPropertyExpensesCreateDialog.value = false
        }, modifier = Modifier
            .width(550.dp)
            .height(800.dp),

            title = {
                Text(
                    text = "Novo Pagamento:", style = TextStyle(
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


Row() {
    OutlinedTextField(

        value =dropDownSelectPropertyDesc.value,
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
                PropertiesDropdownMenu(properties)
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



                    val types = listOf("Serviços Prestados", "Água e Esgoto","Energia Elétrica","Condomínio","Impostos", "Outros")
                    Row() {
                        OutlinedTextField(

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
                                    ExpenseTypeDropdownMenu(types)
                                    Text(
                                        text = "Tipo de Pagamento:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }

                            }, enabled = false
                        )

                    }



if (dropDownSelectExpenseType.value.equals("Serviços Prestados")) {

    Row() {
        OutlinedTextField(

            value = dropDownSelectProviderName.value,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ProvidersDropdownMenu(providers)
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

    Row() {
        OutlinedTextField(

            value = dropDownSelectProviderServiceDesc.value,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ProviderServicesDropdownMenu(dropDownSelectProviderServices.value)
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
                            modifier = Modifier.width(160.dp),
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
                                    text = "Data do Pagamento:",
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
                                //openStartDateDialog.value = true
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "Alterar a Data do Pagamento",
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
                        ),placeholder = {Text("Exemplos: reforma de telhado; impostos; conta de água...")},
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

                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Cancelar", style = TextStyle(
                                fontSize = 14.sp,
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
}


@Composable
fun ExpenseTypeDropdownMenu(types: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(10.dp)
    ) {
        IconButton(onClick = { expanded = !expanded },
            modifier = Modifier
                .padding(end = 12.dp)
                .size(24.dp)
        ) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Selecionar Tipo de Pagamento")
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
}


@Composable
fun ProvidersDropdownMenu(providers: List<Provider>) {
    var expanded by remember { mutableStateOf(false) }

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
}


@Composable
fun ProviderServicesDropdownMenu(services: List<String>) {
    var expanded by remember { mutableStateOf(false) }

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
}