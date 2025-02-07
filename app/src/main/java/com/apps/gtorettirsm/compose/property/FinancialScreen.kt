/*
 */

package com.apps.gtorettirsm.compose.property


import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.setValue
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
import com.apps.gtorettirsm.compose.utils.daysBetween
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getRedTextColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.toScreen
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.ExpenseViewModel
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ReceivingViewModel
import java.text.SimpleDateFormat
import java.time.YearMonth
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

    var filterStartDate by remember { mutableStateOf("") }
    var filterEndDate by remember { mutableStateOf("") }
    val openStartDateDialog = remember { mutableStateOf(false) }
    val openEndDateDialog = remember { mutableStateOf(false) }

    val fmt = SimpleDateFormat("dd/MM/yyyy")


    val loaded = remember { mutableStateOf(false) }
    if (!loaded.value){
        val currentMonth = Calendar.getInstance()
        currentMonth.set(Calendar.DAY_OF_MONTH,1)
        filterStartDate = fmt.format(currentMonth.time)

        currentMonth.add(Calendar.MONTH,1)
        currentMonth.add(Calendar.DAY_OF_MONTH,-1)
        filterEndDate = fmt.format(currentMonth.time)
        loaded.value = true
    }

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
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(5.dp))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {


            Row(){
                OutlinedTextField(
                    modifier = Modifier
                        .width(120.dp)
                        .padding(horizontal = 3.dp)
                        .clickable {
                            openStartDateDialog.value = true
                        },
                    value = filterStartDate,
                    onValueChange = {
                    },
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        color = getTextColor(),
                        fontWeight = FontWeight.Normal
                    ),

                    label = {
                        Text(
                            text = "Data Inicial:",
                            style = TextStyle(
                                color = getTextColor(), fontSize = 14.sp,
                            )
                        )
                    },
                    enabled = false
                )

                TextButton(
                    modifier = Modifier.padding(horizontal = 1.dp),
                    onClick =
                    {
                        openStartDateDialog.value = true
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Alterar Data Inicial",
                        tint = getTextColor(),
                        modifier = Modifier
                            .padding(horizontal = 1.dp)
                            .size(24.dp)
                    )
                }
            }


Row(){
    OutlinedTextField(
        modifier = Modifier
            .width(120.dp)
            .padding(horizontal = 3.dp)
            .clickable {
                openEndDateDialog.value = true
            },
        value = filterEndDate,
        onValueChange = {
        },
        textStyle = TextStyle(
            fontSize = 15.sp,
            color = getTextColor(),
            fontWeight = FontWeight.Normal
        ),

        label = {
            Text(
                text = "Data Final:",
                style = TextStyle(
                    color = getTextColor(), fontSize = 14.sp,
                )
            )
        },
        enabled = false
    )

    TextButton(
        modifier = Modifier.padding(horizontal = 1.dp),
        onClick =
        {
            openEndDateDialog.value = true
        },
    ) {
        Icon(
            imageVector = Icons.Filled.DateRange,
            contentDescription = "Alterar Data Final",
            tint = getTextColor(),
            modifier = Modifier
                .padding(horizontal = 1.dp)
                .size(24.dp)
        )
    }
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
                    var reportList = getFinancialReport(property.propertyId, expenseViewModel, receivingViewModel, fmt.parse(filterStartDate),fmt.parse(filterEndDate))
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

                                    if (reportRecord.type.equals("DONE"))
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = "Realizado",
                                        tint = Color(0xFF08940E),
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .size(16.dp)
                                    )else
                                        if (reportRecord.type.equals("PENDING"))
                                            Icon(
                                                imageVector = ImageVector.vectorResource(R.drawable.warning_24px),
                                                contentDescription = "Pendente",
                                                tint = Color(0xFFD50000),
                                                modifier = Modifier
                                                    .padding(end = 10.dp)
                                                    .size(16.dp)
                                            )else
                                            if (reportRecord.type.equals("PREVIEW"))
                                                Icon(
                                                    imageVector = ImageVector.vectorResource(R.drawable.schedule_24px),
                                                    contentDescription = "Previsto",
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
                text = "Recebimentos de Aluguéis Pendentes",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.warning_24px),
                contentDescription = "Recebimentos de Aluguéis Pendentes",
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

    when {
        openStartDateDialog.value -> {
            DatePickerModal(
                onDateSelected = {
                    if (it != null) {
                        filterStartDate = SimpleDateFormat("dd/MM/yyyy").format(Date(it))
                    }
                }, openDialog = openStartDateDialog, title = "Data Inicial"
            )
        }
    }
    when {
        openEndDateDialog.value -> {
            DatePickerModal(
                onDateSelected = {
                    if (it != null) {
                        filterEndDate = SimpleDateFormat("dd/MM/yyyy").format(Date(it))
                    }
                }, openDialog = openEndDateDialog, title = "Data Final"
            )
        }
    }

}

data class FinancialReportRecord(
    var prefix: String,
    var date: Date,
    var value: Double,
    var description: String,
    var type: String, //DONE,PREVIEW/PENDING
) {
}

@Composable
fun getFinancialReport(propertyId: Long, expenseViewModel: ExpenseViewModel, receivingViewModel: ReceivingViewModel, startDate: Date, endDate: Date) : List<FinancialReportRecord>{

    val expFlow = expenseViewModel.getExpensesByProperty(propertyId,startDate,endDate)
    val expenses = expFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    val expensesList = expenses.value

    val recFlow = receivingViewModel.getReceivingsByProperty(propertyId,startDate,endDate)
    val receivings = recFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    val receivingList = receivings.value

    var ret = ArrayList<FinancialReportRecord>()

    for (item in expensesList) {
        ret.add(FinancialReportRecord("(-)",item.date,item.value,item.serviceDesc,"DONE"))
    }

    for (item in receivingList) {
        ret.add(FinancialReportRecord("(+)",item.receivingDate,item.totalValue,item.comments,"DONE"))
    }

    ret.sortByDescending { it.date }
    return ret

}