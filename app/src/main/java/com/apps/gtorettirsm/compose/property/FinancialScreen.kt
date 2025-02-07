/*
 */

package com.apps.gtorettirsm.compose.property


import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.R
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getRedTextColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.toScreen
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.ExpenseViewModel
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ReceivingViewModel
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun FinancialScreen() {
    var propertyViewModel: PropertyViewModel = hiltViewModel()
    val propertiesFlow = propertyViewModel.properties
    val properties by propertiesFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    var expenseViewModel: ExpenseViewModel = hiltViewModel()
    var receivingViewModel: ReceivingViewModel = hiltViewModel()
    FinancialScreen(properties,expenseViewModel,receivingViewModel)
}

@Composable
fun FinancialScreen(
    properties: List<Property>,
    expenseViewModel: ExpenseViewModel,
    receivingViewModel: ReceivingViewModel
) {

    val context = LocalContext.current
    var openPropertyExpensesDialog = remember { mutableStateOf(false) }
    var openPropertyReceivingsDialog = remember { mutableStateOf(false) }

    val fmt = SimpleDateFormat("dd/MM/yyyy")

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
                    text = "Desenbolsos",
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


            properties.forEach { property ->
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


                    var streetAddress = property.streetAddress + ", " + property.number
                    if (property.complement.isNotEmpty())
                        streetAddress = streetAddress + " - " + property.complement


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
                            text = property.city + " - " + property.state + " - CEP:" + property.zipCode,
                            style = TextStyle(
                                color = getTextColor(),
                                fontSize = 14.sp,
                            )
                        )
                    }

                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 3.dp),

                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(2.dp)

                ) {
                    var total: Double = 0.0
                    var reportList = getFinancialReport(property.propertyId, expenseViewModel, receivingViewModel)
                    reportList.forEach { reportRecord ->

                        if (reportRecord.prefix == "(-)") {
                            total -= reportRecord.value
                        }else{
                            total += reportRecord.value
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = fmt.format(reportRecord.date),
                                style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 14.sp,
                                )
                            )
                            Text(
                                text = reportRecord.description,
                                style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 14.sp,
                                )
                            )

                            Card(
                                modifier = Modifier
                                    .padding(3.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Transparent),
                                shape = RoundedCornerShape(0.dp)
                            ){
                                Row(){
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = "Realizados",
                                        tint = Color(0xFF08940E),
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .size(16.dp)
                                    )
                                    if (reportRecord.prefix == "(-)"){
                                        Text(
                                            text = reportRecord.prefix + " " + reportRecord.value.toScreen(),
                                            style = TextStyle(
                                                color = getRedTextColor(),
                                                fontSize = 14.sp,
                                            )
                                        )
                                    }else{
                                        Text(
                                            text = reportRecord.prefix + " " + reportRecord.value.toScreen(),
                                            style = TextStyle(
                                                color = getTextColor(),
                                                fontSize = 14.sp,
                                            )
                                        )
                                    }

                                }

                            }


                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Total:",
                            style = TextStyle(
                                color = getTextColor(),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )

                        if (total<0.0){
                            Text(
                                text = "(-) " + total.toScreen().replace("-",""),
                                style = TextStyle(
                                    color = getRedTextColor(),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                        }else{
                            Text(
                                text = "(+) " + total.toScreen(),
                                style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                        }




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
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(checked = (true),
                onCheckedChange = {})
            Text(
                text = "Recebimentos e Desenbolsos Realizados",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Recebimentos e Desenbolsos Realizados",
                tint = Color(0xFF08940E),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(20.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(checked = (true),
                onCheckedChange = {})
            Text(
                text = "Recebimentos de Aluguéis Previstos",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.schedule_24px),
                contentDescription = "Recebimentos de Aluguéis Previstos",
                tint = Color(0xFF08940E),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(20.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(checked = (true),
                onCheckedChange = {})
            Text(
                text = "Recebimentos de Aluguéis Atrasados",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.warning_24px),
                contentDescription = "Recebimentos de Aluguéis Atrasados",
                tint = Color(0xFFD50000),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(20.dp)
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
            text = "Total de Desenbolsos (-): ",
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

data class FinancialReportRecord(
    var prefix: String,
    var date: Date,
    var value: Double,
    var description: String,
) {
}

@Composable
fun getFinancialReport(propertyId: Long, expenseViewModel: ExpenseViewModel, receivingViewModel: ReceivingViewModel) : List<FinancialReportRecord>{

    val expFlow = expenseViewModel.getExpensesByProperty(propertyId)
    val expenses = expFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    val expensesList = expenses.value

    val recFlow = receivingViewModel.getReceivingsByProperty(propertyId)
    val receivings = recFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    val receivingList = receivings.value

    var ret = ArrayList<FinancialReportRecord>()

    for (item in expensesList) {
        ret.add(FinancialReportRecord("(-)",item.date,item.value,item.serviceDesc))
    }

    for (item in receivingList) {
        ret.add(FinancialReportRecord("(+)",item.receivingDate,item.totalValue,item.comments))
    }

    ret.sortByDescending { it.date }
    return ret

}