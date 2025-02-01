/*
 */

package com.apps.gtorettirsm.compose.property


import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.ExpenseViewModel
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ReceivingViewModel

@Composable
fun PropertyReceivingsDialog(
    openPropertyExpensesDialog: MutableState<Boolean>,
    context: Context
)  {
    var propertyViewModel: PropertyViewModel = hiltViewModel()
    val propertiesFlow = propertyViewModel.properties
    val properties by propertiesFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    PropertyReceivingsDialog(openPropertyExpensesDialog,context,properties)
}

@Composable
fun PropertyReceivingsDialog(
    openPropertyExpensesDialog: MutableState<Boolean>,
    context: Context,
    properties: List<Property>,
) {

    val openPropertyReceivingsCreateDialog = remember { mutableStateOf(false) }

    var receivingViewModel: ReceivingViewModel = hiltViewModel()
    val receivingsFlow = receivingViewModel.getReceivingsByProperty(dropDownSelectPropertyId.value)
    val receivings by receivingsFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    if (openPropertyExpensesDialog.value) {
        AlertDialog(shape = RoundedCornerShape(10.dp), onDismissRequest = {
            openPropertyExpensesDialog.value = false
        }, modifier = Modifier
            .width(550.dp)
            .height(800.dp),

            title = {
                Text(
                    text = "Recebimentos:", style = TextStyle(
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
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Para adicionar, clique aqui->",
                            style = TextStyle(
                                color = getRedTextColor(),
                                fontSize = 16.sp,
                                fontFamily = FontFamily.SansSerif,
                            )
                        )

                        TextButton(
                            modifier = Modifier.padding(5.dp),
                            onClick =
                            {
                                dropDownSelectPropertyId.value = 0L
                                dropDownSelectPropertyDesc.value = ""
                                dropDownSelectExpenseType.value = ""
                                dropDownSelectProviderId.value = 0L
                                dropDownSelectProviderName.value = ""
                                dropDownSelectProviderServices.value = ArrayList<String>()
                                dropDownSelectProviderServiceDesc.value = ""
                                openPropertyReceivingsCreateDialog.value = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "Novo Recebimento",
                                tint = getTextColor(),
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .size(24.dp)
                            )
                        }
                    }

                    PropertiesDropdownMenu(properties)
                    DrawScrollableView(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        content = {
                            Column {
                                receivings.forEach { item ->


                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .selectable(
                                                selected = false,
                                                onClick = {

                                                },
                                                role = Role.Button
                                            )
                                    ) {


                                        Text(text = item.totalValue.toScreen())

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
                        dropDownSelectPropertyId.value = 0L
                        dropDownSelectPropertyDesc.value = ""
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
                }
            }
        )

        when {
            openPropertyReceivingsCreateDialog.value -> {
                PropertyReceivingsCreateDialog(
                    openPropertyReceivingsCreateDialog = openPropertyReceivingsCreateDialog,
                    context = context,
                )
            }
        }
    }
}

