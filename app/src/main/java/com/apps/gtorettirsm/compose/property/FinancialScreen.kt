/*
 */

package com.apps.gtorettirsm.compose.property


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.PropertyViewModel


@Composable
fun FinancialScreen() {
    var propertyViewModel: PropertyViewModel = hiltViewModel()
    val propertiesFlow = propertyViewModel.properties
    val properties by propertiesFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    FinancialScreen(properties)
}

@Composable
fun FinancialScreen(
    properties: List<Property>,
) {

    val context = LocalContext.current
    var openPropertyExpensesDialog = remember { mutableStateOf(false) }
    var openPropertyReceivingsDialog = remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Fluxo de Caixa e Relatórios:"
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(10.dp))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
        ) {

            Button(
                onClick = {
                    openPropertyExpensesDialog.value = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = getButtonColor()
                ), modifier = Modifier.height(33.dp)
            ) {
                Text(
                    text = "Pagamentos",
                    style = TextStyle(
                        fontSize = 14.sp,
                    )
                )
            }



            Button(
                onClick = {
                    openPropertyReceivingsDialog.value = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = getButtonColor()
                ), modifier = Modifier.height(33.dp)
            ) {
                Text(
                    text = "Recebimentos",
                    style = TextStyle(
                        fontSize = 14.sp,
                    )
                )
            }

        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(10.dp))
        }

        Text(
            text = "Previsões e Extratos:",
            style = TextStyle(
                color = getTextColor(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .width(112.dp)
                    .padding(horizontal = 3.dp),
                value = "",
                onValueChange = {
                },
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    color = getTextColor(),
                    fontWeight = FontWeight.Normal
                ),

                label = {
                    Text(
                        text = "Data Inicial:",
                        style = TextStyle(
                            color = getTextColor(), fontSize = 12.sp,
                        )
                    )
                },
                enabled = false
            )

            TextButton(
                modifier = Modifier.padding(horizontal = 5.dp),
                onClick =
                {
                    //openStartDateDialog.value = true
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Alterar Data Inicial",
                    tint = getTextColor(),
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .size(24.dp)
                )
            }


            OutlinedTextField(
                modifier = Modifier
                    .width(112.dp)
                    .padding(horizontal = 3.dp),
                value = "",
                onValueChange = {
                },
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    color = getTextColor(),
                    fontWeight = FontWeight.Normal
                ),

                label = {
                    Text(
                        text = "Data Final:",
                        style = TextStyle(
                            color = getTextColor(), fontSize = 12.sp,
                        )
                    )
                },
                enabled = false
            )

            TextButton(
                modifier = Modifier.padding(horizontal = 5.dp),
                onClick =
                {
                    //openEndedDateDialog.value = true
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Alterar Data Final",
                    tint = getTextColor(),
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .size(24.dp)
                )
            }

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(10.dp))
        }
        Text(
            text = "Imóveis:",
            style = TextStyle(
                color = getTextColor(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        )



        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp)
                .border(1.dp, Color.Gray)
                //.verticalScroll(rememberScrollState()),
        ) {


            properties.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (true),
                            onClick = {

                            },
                            role = Role.Checkbox

                        )
                ) {
                    Checkbox(checked = (true),
                        onCheckedChange = {

                        })


                    var streetAddress = item.streetAddress + ", " + item.number
                    if (item.complement.isNotEmpty())
                        streetAddress = streetAddress + " - " + item.complement


                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(2.dp)

                    ) {
                        Text(
                            text = streetAddress,
                            style = TextStyle(
                                color = getTextColor(),
                                fontSize = 14.sp,
                            )
                        )
                        Text(
                            text = item.city + " - " + item.state + " - CEP:" + item.zipCode,
                            style = TextStyle(
                                color = getTextColor(),
                                fontSize = 14.sp,
                            )
                        )
                    }
                }
                HorizontalDivider(thickness = 1.dp)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(10.dp))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(checked = (true),
                onCheckedChange = {})
            Text(
                text = "Realizados",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Realizados",
                tint = Color(0xFF08940E),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(16.dp)
            )

            Checkbox(checked = (true),
                onCheckedChange = {})
            Text(
                text = "Pendentes",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Pendentes",
                tint = Color(0xFFD50000),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(16.dp)
            )

        }


        Text(
            text = "Total de Recebimentos (+): ",
            style = TextStyle(
                color = getTextColor(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        )
        Text(
            text = "Total de Pagamentos (-): ",
            style = TextStyle(
                color = getTextColor(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        )


        Text(
            text = "Saldo: ",
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

        Button(
            onClick = {
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = getButtonColor()
            ), modifier = Modifier.height(33.dp)
        ) {
            Text(
                text = "Gerar Relatório",
                style = TextStyle(
                    fontSize = 14.sp,
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(20.dp))
        }

        //AndroidViewAdView()
    }

    when {
        openPropertyExpensesDialog.value -> {
            PropertyExpensesDialog(
                openPropertyExpensesDialog,
                context)
        }
    }

    when {
        openPropertyReceivingsDialog.value -> {
            PropertyReceivingsDialog(
                openPropertyReceivingsDialog,
                context)
        }
    }

}