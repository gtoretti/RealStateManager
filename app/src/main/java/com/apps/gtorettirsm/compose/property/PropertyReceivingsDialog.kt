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
import androidx.compose.ui.graphics.Color
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
import com.apps.gtorettirsm.data.Receiving
import com.apps.gtorettirsm.viewmodels.ExpenseViewModel
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ReceivingViewModel
import java.text.SimpleDateFormat
import java.util.Date

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
    openPropertyReceivingsDialog: MutableState<Boolean>,
    context: Context,
    properties: List<Property>,
) {

    val openPropertyReceivingsCreateDialog = remember { mutableStateOf(false) }

    var receivingViewModel: ReceivingViewModel = hiltViewModel()
    val receivingsFlow = receivingViewModel.getReceivingsByProperty(dropDownSelectPropertyId.value)
    val receivings by receivingsFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    var receiving = remember { mutableStateOf(Receiving(0L, Date(0),0L,0.0,"","",Date(0L),0.0,0L,"","")) }

    if (openPropertyReceivingsDialog.value) {
        AlertDialog(shape = RoundedCornerShape(10.dp), onDismissRequest = {
            openPropertyReceivingsDialog.value = false
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
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Text(
                        text = "Recebimentos Realizados:",
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
                                receivings.forEach { item ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .selectable(
                                                selected = false,
                                                onClick = {
                                                    receiving.value = item
                                                    openPropertyReceivingsCreateDialog.value = true
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
                                                    text = fmt.format(item.receivingDate), style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 15.sp,
                                                    )
                                                )

                                                Text(
                                                    text = item.totalValue.toScreen(), style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 15.sp,
                                                    )
                                                )
                                            }
                                            var descr=""
                                            if (item.type.equals("Aluguel")){
                                                descr = "Aluguel Vencto: " + fmt.format(item.rentBillingDueDate)
                                            }else{
                                                descr = item.comments
                                            }
                                            Text(
                                                text = descr, style = TextStyle(
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
                        receiving.value = Receiving(0L, Date(0),0L,0.0,"","",Date(0),0.0,0L,"","")
                        dropDownSelectPropertyId.value = 0L
                        dropDownSelectPropertyDesc.value = ""
                        dropDownSelectExpenseType.value = ""
                        dropDownSelectProviderId.value = 0L
                        dropDownSelectProviderName.value = ""
                        dropDownSelectProviderServices.value = ArrayList<String>()
                        dropDownSelectProviderServiceDesc.value = ""
                        dropDownSelectReceivingType.value = ""
                        openPropertyReceivingsDialog.value = false
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


                    Button(
                        modifier = Modifier.height(30.dp),
                        onClick =
                        {
                            receiving.value =
                                Receiving(0L, Date(0), 0L, 0.0, "", "", Date(0), 0.0, 0L,"","")
                            dropDownSelectPropertyId.value = 0L
                            dropDownSelectPropertyDesc.value = ""
                            dropDownSelectExpenseType.value = ""
                            dropDownSelectProviderId.value = 0L
                            dropDownSelectProviderName.value = ""
                            dropDownSelectProviderServices.value = ArrayList<String>()
                            dropDownSelectProviderServiceDesc.value = ""
                            dropDownSelectReceivingType.value = ""
                            openPropertyReceivingsCreateDialog.value = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),
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
            openPropertyReceivingsCreateDialog.value -> {
                PropertyReceivingsCreateDialog(
                    openPropertyReceivingsCreateDialog = openPropertyReceivingsCreateDialog,
                    context = context,
                    receiving.value
                )
            }
        }
    }
}

