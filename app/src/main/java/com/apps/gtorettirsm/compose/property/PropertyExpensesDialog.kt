/*
 */

package com.apps.gtorettirsm.compose.property


import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.compose.utils.DrawScrollableView
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getRedTextColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.toScreen
import com.apps.gtorettirsm.data.Expense
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.Provider
import com.apps.gtorettirsm.viewmodels.ExpenseViewModel
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ProviderViewModel
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun PropertyExpensesDialog(
    openPropertyExpensesDialog: MutableState<Boolean>,
    context: Context
) {
    var propertyViewModel: PropertyViewModel = hiltViewModel()
    val propertiesFlow = propertyViewModel.properties
    val properties by propertiesFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    PropertyExpensesDialog(openPropertyExpensesDialog,context,properties)
}

@Composable
fun PropertyExpensesDialog(
    openPropertyExpensesDialog: MutableState<Boolean>,
    context: Context,
    properties: List<Property>,
) {

    val openPropertyExpensesCreateDialog = remember { mutableStateOf(false) }

    var expenseViewModel: ExpenseViewModel = hiltViewModel()
    val expensesFlow = expenseViewModel.getExpensesByProperty(dropDownSelectPropertyId.value)
    val expenses by expensesFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    var expense = remember { mutableStateOf(Expense(0L,Date(0),0L,0.0,"","","",0L,"")) }

    if (openPropertyExpensesDialog.value) {
        AlertDialog(shape = RoundedCornerShape(10.dp), onDismissRequest = {
            openPropertyExpensesDialog.value = false
        }, modifier = Modifier
            .width(550.dp)
            .height(800.dp),

            title = {
                Text(
                    text = "Desenbolsos:", style = TextStyle(
                        color = getTextColor(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            }, text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(2.dp)

                ) {



                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Text(
                        text = "Desenbolsos Realizados:",
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    PropertiesDropdownMenu(properties)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    DrawScrollableView(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        content = {
                            Column {

                                val fmt = SimpleDateFormat("dd/MM/yyyy")
                            expenses.forEach { item ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .selectable(
                                            selected = false,
                                            onClick = {
                                                expense.value = item
                                                openPropertyExpensesCreateDialog.value = true
                                            },
                                            role = Role.Button
                                        )
                                ) {

                                    Column(
                                        modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 5.dp))
                                    {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ){
                                            Text(
                                                text = fmt.format(item.date), style = TextStyle(
                                                    color = getTextColor(),
                                                    fontSize = 15.sp,
                                                )
                                            )

                                            Text(
                                                text = item.value.toScreen(), style = TextStyle(
                                                    color = getTextColor(),
                                                    fontSize = 15.sp,
                                                )
                                            )
                                        }
                                        var desc = item.serviceDesc
                                        if (item.providerName.trim().isNotEmpty()){
                                            desc = desc + " (" + item.providerName + ") "
                                        }
                                        if (item.comments.trim().isNotEmpty()){
                                            desc = desc + " : " + item.comments
                                        }

                                        Text(
                                            text = desc, style = TextStyle(
                                                color = getTextColor(),
                                                fontSize = 15.sp,
                                            )
                                        )
                                        HorizontalDivider(thickness = 1.dp)
                                    }

                                    }
                                }

                            }

                        })

                }

            }, confirmButton = {

            }, dismissButton = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = {
                        expense.value = Expense(0L,Date(0),0L,0.0,"","","",0L,"")
                        dropDownSelectPropertyId.value = 0L
                        dropDownSelectPropertyDesc.value = ""
                        dropDownSelectExpenseType.value = ""
                        dropDownSelectProviderId.value = 0L
                        dropDownSelectProviderName.value = ""
                        dropDownSelectProviderServices.value = ArrayList<String>()
                        dropDownSelectProviderServiceDesc.value = ""
                        dropDownSelectReceivingType.value = ""
                        openPropertyExpensesDialog.value = false
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


                    Button(onClick = {
                        expense.value = Expense(0L,Date(0),0L,0.0,"","","",0L,"")
                        dropDownSelectPropertyId.value = 0L
                        dropDownSelectPropertyDesc.value = ""
                        dropDownSelectExpenseType.value = ""
                        dropDownSelectProviderId.value = 0L
                        dropDownSelectProviderName.value = ""
                        dropDownSelectProviderServices.value = ArrayList<String>()
                        dropDownSelectProviderServiceDesc.value = ""
                        dropDownSelectReceivingType.value = ""
                        openPropertyExpensesCreateDialog.value = true
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Adicionar", style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }
                }
            }
        )

        when {
            openPropertyExpensesCreateDialog.value -> {
                PropertyExpensesCreateDialog(
                    openPropertyExpensesCreateDialog = openPropertyExpensesCreateDialog,
                    context = context,
                    expense.value
                )
            }
        }
    }
}

