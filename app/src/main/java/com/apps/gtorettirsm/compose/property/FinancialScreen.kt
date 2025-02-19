/*
 */

package com.apps.gtorettirsm.compose.property


import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.icu.util.Calendar
import android.os.Environment
import android.widget.Toast
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
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.R
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getPhoneColor
import com.apps.gtorettirsm.compose.utils.getRedTextColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.toCurrency
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.ExpenseViewModel
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ReceivingViewModel
import java.io.File
import java.io.FileOutputStream
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

    var filterStartDate by remember { mutableStateOf("") }
    var filterEndDate by remember { mutableStateOf("") }
    val openStartDateDialog = remember { mutableStateOf(false) }
    val openEndDateDialog = remember { mutableStateOf(false) }

    val filterRecordsDONE = remember { mutableStateOf(true) }
    val filterRecordsPREVIEW = remember { mutableStateOf(true) }
    val filterRecordsPENDING = remember { mutableStateOf(true) }

    var financialReport = remember { mutableStateOf(FinancialReport("","",false,false,false,"","","",ArrayList<FinancialReportProperty>())) }
    financialReport.value.filterRecordsDONE = filterRecordsDONE.value
    financialReport.value.filterRecordsPREVIEW = filterRecordsPREVIEW.value
    financialReport.value.filterRecordsPENDING = filterRecordsPENDING.value
    financialReport.value.startDateStr = filterStartDate
    financialReport.value.endDateStr = filterEndDate
    financialReport.value.properties = ArrayList()

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

    var receivingsTotal: Double = 0.0
    var expensesTotal: Double = 0.0

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
                    text = "Demonstração dos Fluxos de Caixa:"
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



        if (properties.isNotEmpty()) {


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
        }




        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(10.dp))
        }

        Text(
            text = "Período da Competência:",
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


        if (properties.isEmpty()){
            Column(
                modifier = Modifier.padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Não há imóveis cadastrados."
                    , style = TextStyle(
                        color = getTextColor(),
                        fontSize = 15.sp,
                    )
                )
            }
        }


        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp)
                .border(1.dp, Color.Gray)

        ) {


            properties.forEach { property ->

                var total: Double = 0.0
                val eachPropertyCheckbox = remember { mutableStateOf(true) }
                var reportList = getFinancialReport(property, expenseViewModel, receivingViewModel, fmt.parse(filterStartDate),fmt.parse(filterEndDate),filterRecordsDONE.value, filterRecordsPREVIEW.value, filterRecordsPENDING.value)

                if (eachPropertyCheckbox.value){
                    var finProp = FinancialReportProperty(property,reportList)
                    financialReport.value.properties.add(finProp)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (true),
                            onClick = {
                                if (eachPropertyCheckbox.value) {
                                    eachPropertyCheckbox.value = false
                                } else {
                                    eachPropertyCheckbox.value = true
                                }
                            },
                            role = Role.Checkbox

                        )
                ) {

                    Checkbox(checked = (eachPropertyCheckbox.value),
                        onCheckedChange = {
                            if (it){
                                eachPropertyCheckbox.value = true
                            }else{
                                eachPropertyCheckbox.value = false
                            }
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
                    reportList.forEach { reportRecord ->

                        if (eachPropertyCheckbox.value) {

                            if (reportRecord.prefix == "(-)") {
                                total -= reportRecord.value
                            } else {
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
                                        containerColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(0.dp)
                                ) {
                                    Row() {

                                        if (reportRecord.type.equals("DONE"))
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = "Realizado",
                                                tint = Color(0xFF08940E),
                                                modifier = Modifier
                                                    .padding(end = 10.dp)
                                                    .size(16.dp)
                                            ) else
                                            if (reportRecord.type.equals("PENDING"))
                                                Icon(
                                                    imageVector = ImageVector.vectorResource(R.drawable.warning_24px),
                                                    contentDescription = "Pendente",
                                                    tint = Color(0xFFD50000),
                                                    modifier = Modifier
                                                        .padding(end = 10.dp)
                                                        .size(18.dp)
                                                ) else
                                                if (reportRecord.type.equals("PREVIEW"))
                                                    Icon(
                                                        imageVector = ImageVector.vectorResource(R.drawable.schedule_24px),
                                                        contentDescription = "Previsto",
                                                        tint = Color(0xFF08940E),
                                                        modifier = Modifier
                                                            .padding(end = 10.dp)
                                                            .size(16.dp)
                                                    )



                                        if (reportRecord.prefix == "(-)") {
                                            expensesTotal += reportRecord.value
                                            Text(
                                                text = reportRecord.prefix + " " + reportRecord.value.toCurrency(),
                                                style = TextStyle(
                                                    color = getRedTextColor(),
                                                    fontSize = 14.sp,
                                                )
                                            )
                                        } else {
                                            receivingsTotal += reportRecord.value
                                            Text(
                                                text = reportRecord.prefix + " " + reportRecord.value.toCurrency(),
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
                    }

                    if (eachPropertyCheckbox.value) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Saldo:",
                                style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )

                            var totalStr = ""
                            if (total < 0.0) {
                                totalStr = "(-) " + total.toCurrency().replace("-", "")
                                Text(
                                    text = totalStr,
                                    style = TextStyle(
                                        color = getRedTextColor(),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                            } else {
                                totalStr = "(+) " + total.toCurrency()
                                Text(
                                    text = "(+) " + total.toCurrency(),
                                    style = TextStyle(
                                        color = getTextColor(),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                            }

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
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (filterRecordsDONE.value) {
                        filterRecordsDONE.value = false
                    } else {
                        filterRecordsDONE.value = true
                    }
                }
        ) {
            Checkbox(checked = (filterRecordsDONE.value),
                onCheckedChange = {
                    if (it){
                        filterRecordsDONE.value = true
                    }else{
                        filterRecordsDONE.value = false
                    }
                })
            Text(
                text = "Recebimentos e Desenbolsos",
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
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (filterRecordsPREVIEW.value) {
                        filterRecordsPREVIEW.value = false
                    } else {
                        filterRecordsPREVIEW.value = true
                    }
                }
        ) {
            Checkbox(checked = (filterRecordsPREVIEW.value),
                onCheckedChange = {
                    if (it){
                        filterRecordsPREVIEW.value = true
                    }else{
                        filterRecordsPREVIEW.value = false
                    }
                })
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
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (filterRecordsPENDING.value) {
                        filterRecordsPENDING.value = false
                    } else {
                        filterRecordsPENDING.value = true
                    }
                }
        ) {
            Checkbox(checked = (filterRecordsPENDING.value),
                onCheckedChange = {
                    if (it){
                        filterRecordsPENDING.value = true
                    }else{
                        filterRecordsPENDING.value = false
                    }
                })
            Text(
                text = "Aluguéis Atrasados Pendentes",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.warning_24px),
                contentDescription = "Aluguéis Atrasados Pendentes",
                tint = Color(0xFFD50000),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(20.dp)
            )
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
            .padding(horizontal = 10.dp)
    )
    {
    Text(
        text = "Total de Recebimentos (+): ",
        style = TextStyle(
            color = getTextColor(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    )
        financialReport.value.totalReceivings = "(+) " + receivingsTotal.toCurrency()
    Text(
        text = receivingsTotal.toCurrency(),
        style = TextStyle(
            color = getTextColor(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    )
}


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        {
            Text(
                text = "Total de Desenbolsos (-): ",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            financialReport.value.totalExpenses = "(-) " + expensesTotal.toCurrency()
            Text(
                text = expensesTotal.toCurrency(),
                style = TextStyle(
                    color = Color(0xFFD50000),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        {
            Text(
                text = "Saldo Total:",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            if (receivingsTotal - expensesTotal <0){
                financialReport.value.totalBalance = "(-) " + (receivingsTotal - expensesTotal).toCurrency().replace("-","")
                Text(
                    text = financialReport.value.totalBalance,
                    style = TextStyle(
                        color = getRedTextColor(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }else{
                financialReport.value.totalBalance = "(+) " + (receivingsTotal - expensesTotal).toCurrency()
                Text(
                    text = financialReport.value.totalBalance,
                    style = TextStyle(
                        color = getPhoneColor(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
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

        Button(
            onClick = {
                generatePDFReport(context,financialReport.value)
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
    var comments: String,
    var type: String, //DONE,PREVIEW/PENDING
) {
}

data class FinancialReport(
    var startDateStr: String,
    var endDateStr: String,
    var filterRecordsDONE: Boolean,
    var filterRecordsPREVIEW: Boolean,
    var filterRecordsPENDING: Boolean,
    var totalBalance: String,
    var totalExpenses: String,
    var totalReceivings: String,
    var properties: ArrayList<FinancialReportProperty>,
) {
}

data class FinancialReportProperty(
    var property: Property,
    var records: List<FinancialReportRecord>,
) {
}


@Composable
fun getFinancialReport(property: Property, expenseViewModel: ExpenseViewModel, receivingViewModel: ReceivingViewModel, startDate: Date, endDate: Date, filterRecordsDONE: Boolean, filterRecordsPREVIEW: Boolean, filterRecordsPENDING: Boolean) : List<FinancialReportRecord>{

    var ret = ArrayList<FinancialReportRecord>()

    if (filterRecordsDONE){
        val expFlow = expenseViewModel.getExpensesByProperty(property.propertyId,startDate,endDate)
        val expenses = expFlow.collectAsStateWithLifecycle(initialValue = emptyList())
        val expensesList = expenses.value

        val recFlow = receivingViewModel.getReceivingsByProperty(property.propertyId,startDate,endDate)
        val receivings = recFlow.collectAsStateWithLifecycle(initialValue = emptyList())
        val receivingList = receivings.value

        for (item in expensesList) {
            ret.add(FinancialReportRecord("(-)",item.date,item.value,item.serviceDesc,item.comments,"DONE"))
        }

        for (item in receivingList) {
            var descr = item.type
            ret.add(FinancialReportRecord("(+)",item.receivingDate,item.totalValue,descr,item.comments,"DONE"))
        }
    }

    //checar se preencheu contract start date
    if (filterRecordsPENDING || filterRecordsPREVIEW){

        val recFlow = receivingViewModel.getRentReceivingsByDateFilter(property.propertyId,property.contractStartDate, startDate,endDate)
        val receivedListFlow = recFlow.collectAsStateWithLifecycle(initialValue = emptyList())
        val receivedList = receivedListFlow.value

        var today = Calendar.getInstance()
        var eachBilling = Calendar.getInstance()
        eachBilling.time = property.contractStartDate
        eachBilling.set(Calendar.DAY_OF_MONTH,property.contractPaymentDate)
        var totalBillingsQtd = property.contractMonths
        if (property.contractDays>0)
            totalBillingsQtd += 1

        var i=0
        while (i<totalBillingsQtd){
            eachBilling.add(Calendar.MONTH,1)

            if (eachBilling.after(startDate) && eachBilling.before(endDate)){
                var paid = false
                //checa se foi pago. se nao foi é pendente ou previsto

                for (received in receivedList) {
                    var eachReceived = Calendar.getInstance()
                    eachReceived.time = received.rentBillingDueDate
                    if (eachReceived.get(Calendar.YEAR) == eachBilling.get(Calendar.YEAR) && eachReceived.get(Calendar.MONTH) == eachBilling.get(Calendar.MONTH)){
                        paid = true
                        break
                    }
                }

                if (!paid){

                    var billingValue = property.contractMonthlyBillingValue
                    if (i==(totalBillingsQtd-1)){
                        //ultima parcela: checar se é proporcional aos dias caso seja menos de 1 mes
                        if (property.contractDays > 0){
                            billingValue *= (property.contractDays / 30.0)
                        }
                    }

                    if (eachBilling.before(today)){
                        //atrasado
                        if (filterRecordsPENDING){
                            ret.add(FinancialReportRecord("(+)",eachBilling.time,billingValue,"Aluguel", "","PENDING"))
                        }

                    }else{
                        //previsto
                        if (filterRecordsPREVIEW){
                            ret.add(FinancialReportRecord("(+)",eachBilling.time,billingValue,"Aluguel","","PREVIEW"))
                        }
                    }
                }
            }
            i++
        }
    }
    ret.sortByDescending { it.date }
    return ret
}

fun generatePDFReport(context: Context,financialReport: FinancialReport) {

    val fmt = SimpleDateFormat("dd/MM/yyyy")
    val fmtFileName = SimpleDateFormat("dd_MM_yyyy_HH_mm")

    var filename = "relatorio_fluxo_de_caixa_"+fmtFileName.format(Calendar.getInstance().time)

    var pdfDocument: PdfDocument = PdfDocument()

    /**Dimension For A4 Size Paper**/
    var pageHeight = 842
    var pageWidth = 595

    var page = 1

    var myPageInfo: PdfDocument.PageInfo? = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, page).create()
    var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)
    var canvas: Canvas = myPage.canvas

    var headerStyle: Paint = Paint()
    headerStyle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
    headerStyle.textSize = 15F
    headerStyle.setColor(android.graphics.Color.BLUE)
    canvas.drawText("Demonstração dos Fluxos de Caixa", 185F, 50F, headerStyle)

    var periodTitle: Paint = Paint()
    periodTitle.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD))
    periodTitle.textSize = 12F
    periodTitle.color = android.graphics.Color.BLACK
    canvas.drawText("Período da Competência: "+ financialReport.startDateStr + " - " + financialReport.endDateStr, 50F, 100F, periodTitle)

    var y = 140F

    if (financialReport.filterRecordsDONE || financialReport.filterRecordsPREVIEW || financialReport.filterRecordsPENDING){
        var legendTitle: Paint = Paint()
        legendTitle.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD))
        legendTitle.textSize = 10F
        legendTitle.color = android.graphics.Color.BLACK
        canvas.drawText("Legenda:", 50F, 140F, legendTitle)
        y+=20
    }

    if (financialReport.filterRecordsDONE){
        var receivingsExpensesTitle: Paint = Paint()
        receivingsExpensesTitle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        receivingsExpensesTitle.textSize = 10F
        receivingsExpensesTitle.color = android.graphics.Color.BLACK
        canvas.drawText("Recebimentos e Desenbolsos Realizados:", 80F, y, receivingsExpensesTitle)

        val vectorDrawable = context.getDrawable(R.drawable.check_24px)
        if (vectorDrawable != null) {
            vectorDrawable.setBounds(50,(y-12).toInt(),66,(y+4).toInt())
            vectorDrawable.setTint(0xFF08940E.toInt())
            vectorDrawable.draw(canvas);
        }
        y+=20
    }

    if (financialReport.filterRecordsPREVIEW){
        var previewTitle: Paint = Paint()
        previewTitle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        previewTitle.textSize = 10F
        previewTitle.color = android.graphics.Color.BLACK
        canvas.drawText("Recebimentos de Aluguéis Previstos:", 80F, y, previewTitle)

        val vectorDrawable = context.getDrawable(R.drawable.schedule_24px)
        if (vectorDrawable != null) {
            vectorDrawable.setBounds(50,(y-12).toInt(),66,(y+4).toInt())
            vectorDrawable.setTint(0xFF08940E.toInt())
            vectorDrawable.draw(canvas);
        }
        y+=20
    }

    if (financialReport.filterRecordsPENDING) {
        var pendingTitle: Paint = Paint()
        pendingTitle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        pendingTitle.textSize = 10F
        pendingTitle.color = android.graphics.Color.BLACK
        canvas.drawText("Aluguéis Atrasados Pendentes:", 80F, y, pendingTitle)

        val vectorDrawable = context.getDrawable(R.drawable.warning_24px)
        if (vectorDrawable != null) {
            vectorDrawable.setBounds(50,(y-12).toInt(),66,(y+4).toInt())
            vectorDrawable.setTint(0xFFD50000.toInt())
            vectorDrawable.draw(canvas);
        }

        y += 20
    }

    y+=20
    if (pageHeight-y<50){
        var pageNumberPaint: Paint = Paint()
        pageNumberPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        pageNumberPaint.textSize = 10F
        pageNumberPaint.color = android.graphics.Color.BLACK
        canvas.drawText("página $page", (pageWidth-100).toFloat(), (pageHeight-20).toFloat(), pageNumberPaint)

        page++
        pdfDocument.finishPage(myPage)
        myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, page).create()
        myPage = pdfDocument.startPage(myPageInfo)
        canvas = myPage.canvas
        y = 50F
    }

    var line: Paint = Paint()
    canvas.drawLine(50F,y,550F,y,line)
    y+=20
    if (pageHeight-y<50){
        var pageNumberPaint: Paint = Paint()
        pageNumberPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        pageNumberPaint.textSize = 10F
        pageNumberPaint.color = android.graphics.Color.BLACK
        canvas.drawText("página $page", (pageWidth-100).toFloat(), (pageHeight-20).toFloat(), pageNumberPaint)

        page++
        pdfDocument.finishPage(myPage)
        myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, page).create()
        myPage = pdfDocument.startPage(myPageInfo)
        canvas = myPage.canvas
        y = 50F
    }


    financialReport.properties.forEach { finProperty ->

        var streetAddress = finProperty.property.streetAddress + ", " + finProperty.property.number
        if (finProperty.property.complement.isNotEmpty())
            streetAddress = streetAddress + " - " + finProperty.property.complement

        var eachAddress: Paint = Paint()
        eachAddress.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        eachAddress.textSize = 10F
        eachAddress.color = android.graphics.Color.BLACK
        canvas.drawText(streetAddress, 50F, y, eachAddress)

        y+=20
        if (pageHeight-y<50){
            var pageNumberPaint: Paint = Paint()
            pageNumberPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            pageNumberPaint.textSize = 10F
            pageNumberPaint.color = android.graphics.Color.BLACK
            canvas.drawText("página $page", (pageWidth-100).toFloat(), (pageHeight-20).toFloat(), pageNumberPaint)

            page++
            pdfDocument.finishPage(myPage)
            myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, page).create()
            myPage = pdfDocument.startPage(myPageInfo)
            canvas = myPage.canvas
            y = 50F
        }

        var eachAddressComp: Paint = Paint()
        eachAddressComp.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        eachAddressComp.textSize = 10F
        eachAddressComp.color = android.graphics.Color.BLACK
        canvas.drawText(finProperty.property.city + " - " + finProperty.property.state + " - CEP:" + finProperty.property.zipCode, 50F, y, eachAddressComp)

        y+=20
        if (pageHeight-y<50){
            var pageNumberPaint: Paint = Paint()
            pageNumberPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            pageNumberPaint.textSize = 10F
            pageNumberPaint.color = android.graphics.Color.BLACK
            canvas.drawText("página $page", (pageWidth-100).toFloat(), (pageHeight-20).toFloat(), pageNumberPaint)

            page++
            pdfDocument.finishPage(myPage)
            myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, page).create()
            myPage = pdfDocument.startPage(myPageInfo)
            canvas = myPage.canvas
            y = 50F
        }

        var monthlyBillingValue: Paint = Paint()
        monthlyBillingValue.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        monthlyBillingValue.textSize = 10F
        monthlyBillingValue.color = android.graphics.Color.BLACK
        canvas.drawText("Valor do aluguel mensal líquido: " +finProperty.property.contractMonthlyBillingValue.toCurrency(), 50F, y, monthlyBillingValue)

        y+=20
        if (pageHeight-y<50){
            var pageNumberPaint: Paint = Paint()
            pageNumberPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            pageNumberPaint.textSize = 10F
            pageNumberPaint.color = android.graphics.Color.BLACK
            canvas.drawText("página $page", (pageWidth-100).toFloat(), (pageHeight-20).toFloat(), pageNumberPaint)

            page++
            pdfDocument.finishPage(myPage)
            myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, page).create()
            myPage = pdfDocument.startPage(myPageInfo)
            canvas = myPage.canvas
            y = 50F
        }

        var renter: Paint = Paint()
        renter.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        renter.textSize = 10F
        renter.color = android.graphics.Color.BLACK
        canvas.drawText("Inquilino: " +finProperty.property.contractRenterName + " - CPF/CNPJ: " + finProperty.property.contractRenterCPF, 50F, y, renter)

        y+=20
        if (pageHeight-y<50){
            var pageNumberPaint: Paint = Paint()
            pageNumberPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            pageNumberPaint.textSize = 10F
            pageNumberPaint.color = android.graphics.Color.BLACK
            canvas.drawText("página $page", (pageWidth-100).toFloat(), (pageHeight-20).toFloat(), pageNumberPaint)

            page++
            pdfDocument.finishPage(myPage)
            myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, page).create()
            myPage = pdfDocument.startPage(myPageInfo)
            canvas = myPage.canvas
            y = 50F
        }

        canvas.drawLine(50F,y,550F,y,line)

        y+=20
        if (pageHeight-y<50){
            var pageNumberPaint: Paint = Paint()
            pageNumberPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            pageNumberPaint.textSize = 10F
            pageNumberPaint.color = android.graphics.Color.BLACK
            canvas.drawText("página $page", (pageWidth-100).toFloat(), (pageHeight-20).toFloat(), pageNumberPaint)

            page++
            pdfDocument.finishPage(myPage)
            myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, page).create()
            myPage = pdfDocument.startPage(myPageInfo)
            canvas = myPage.canvas
            y = 50F
        }

        var total: Double = 0.0
        finProperty.records.forEach { finRecord ->

            if (finRecord.type=="DONE"){
                var datePaint: Paint = Paint()
                datePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
                datePaint.textSize = 10F
                if (finRecord.prefix == "(+)"){
                    total += finRecord.value
                    datePaint.color = android.graphics.Color.BLACK
                }
                else{
                    total -= finRecord.value
                    datePaint.color = android.graphics.Color.RED
                }
                canvas.drawText(fmt.format(finRecord.date) + " - " + finRecord.description + " - " + finRecord.comments, 75F, y, datePaint)
                canvas.drawText(finRecord.prefix + " " + finRecord.value.toCurrency(), 450F, y, datePaint)

                val vectorDrawable = context.getDrawable(R.drawable.check_24px)
                if (vectorDrawable != null) {
                    vectorDrawable.setBounds(50,(y-12).toInt(),66,(y+4).toInt())
                    vectorDrawable.setTint(0xFF08940E.toInt())
                    vectorDrawable.draw(canvas);
                }
            }
            if (finRecord.type=="PREVIEW"){
                var datePaint: Paint = Paint()
                datePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
                datePaint.textSize = 10F
                if (finRecord.prefix == "(+)"){
                    total += finRecord.value
                    datePaint.color = android.graphics.Color.BLACK
                }
                else{
                    total -= finRecord.value
                    datePaint.color = android.graphics.Color.RED
                }
                canvas.drawText(fmt.format(finRecord.date) + " - " + finRecord.description, 75F, y, datePaint)
                canvas.drawText(finRecord.prefix + " " + finRecord.value.toCurrency(), 450F, y, datePaint)

                val vectorDrawable = context.getDrawable(R.drawable.schedule_24px)
                if (vectorDrawable != null) {
                    vectorDrawable.setBounds(50,(y-12).toInt(),66,(y+4).toInt())
                    vectorDrawable.setTint(0xFF08940E.toInt())
                    vectorDrawable.draw(canvas);
                }
            }
            if (finRecord.type=="PENDING"){
                var datePaint: Paint = Paint()
                datePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
                datePaint.textSize = 10F
                if (finRecord.prefix == "(+)"){
                    total += finRecord.value
                    datePaint.color = android.graphics.Color.BLACK
                }
                else{
                    total -= finRecord.value
                    datePaint.color = android.graphics.Color.RED
                }
                canvas.drawText(fmt.format(finRecord.date) + " - " + finRecord.description, 75F, y, datePaint)
                canvas.drawText(finRecord.prefix + " " + finRecord.value.toCurrency(), 450F, y, datePaint)

                val vectorDrawable = context.getDrawable(R.drawable.warning_24px)
                if (vectorDrawable != null) {
                    vectorDrawable.setBounds(50,(y-12).toInt(),66,(y+4).toInt())
                    vectorDrawable.setTint(0xFFD50000.toInt())
                    vectorDrawable.draw(canvas);
                }
            }

            y+=20
            if (pageHeight-y<50){
                var pageNumberPaint: Paint = Paint()
                pageNumberPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
                pageNumberPaint.textSize = 10F
                pageNumberPaint.color = android.graphics.Color.BLACK
                canvas.drawText("página $page", (pageWidth-100).toFloat(), (pageHeight-20).toFloat(), pageNumberPaint)

                page++
                pdfDocument.finishPage(myPage)
                myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, page).create()
                myPage = pdfDocument.startPage(myPageInfo)
                canvas = myPage.canvas
                y = 50F
            }

        }
        var propertyFooterAddress: Paint = Paint()
        propertyFooterAddress.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        propertyFooterAddress.textSize = 10F
        propertyFooterAddress.color = android.graphics.Color.BLACK
        canvas.drawText("Saldo:", 50F, y, propertyFooterAddress)
        var t = total.toCurrency().replace("-","")
        if (total<0.0){
            t = "(-) $t"
            propertyFooterAddress.color = android.graphics.Color.RED
        }
        else{
            t = "(+) $t"
            propertyFooterAddress.color = android.graphics.Color.BLACK
        }
        canvas.drawText(t, 450F, y, propertyFooterAddress)

        y+=20
        if (pageHeight-y<50){
            var pageNumberPaint: Paint = Paint()
            pageNumberPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            pageNumberPaint.textSize = 10F
            pageNumberPaint.color = android.graphics.Color.BLACK
            canvas.drawText("página $page", (pageWidth-100).toFloat(), (pageHeight-20).toFloat(), pageNumberPaint)

            page++
            pdfDocument.finishPage(myPage)
            myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, page).create()
            myPage = pdfDocument.startPage(myPageInfo)
            canvas = myPage.canvas
            y = 50F
        }

        canvas.drawLine(50F,y,550F,y,line)

        y+=20
        if (pageHeight-y<50){
            var pageNumberPaint: Paint = Paint()
            pageNumberPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            pageNumberPaint.textSize = 10F
            pageNumberPaint.color = android.graphics.Color.BLACK
            canvas.drawText("página $page", (pageWidth-100).toFloat(), (pageHeight-20).toFloat(), pageNumberPaint)

            page++
            pdfDocument.finishPage(myPage)
            myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, page).create()
            myPage = pdfDocument.startPage(myPageInfo)
            canvas = myPage.canvas
            y = 50F
        }

    }

    var reportFooterTotalReceivings: Paint = Paint()
    reportFooterTotalReceivings.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
    reportFooterTotalReceivings.textSize = 10F
    reportFooterTotalReceivings.color = android.graphics.Color.BLACK
    canvas.drawText("Total de Recebimentos (+):", 50F, y, reportFooterTotalReceivings)
    canvas.drawText(financialReport.totalReceivings, 450F, y, reportFooterTotalReceivings)

    y+=20
    if (pageHeight-y<50){
        var pageNumberPaint: Paint = Paint()
        pageNumberPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        pageNumberPaint.textSize = 10F
        pageNumberPaint.color = android.graphics.Color.BLACK
        canvas.drawText("página $page", (pageWidth-100).toFloat(), (pageHeight-20).toFloat(), pageNumberPaint)

        page++
        pdfDocument.finishPage(myPage)
        myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, page).create()
        myPage = pdfDocument.startPage(myPageInfo)
        canvas = myPage.canvas
        y = 50F
    }

    var reportFooterTotalExpenses: Paint = Paint()
    reportFooterTotalExpenses.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
    reportFooterTotalExpenses.textSize = 10F
    reportFooterTotalExpenses.color = android.graphics.Color.BLACK
    canvas.drawText("Total de Desenbolsos (-):", 50F, y, reportFooterTotalExpenses)
    reportFooterTotalExpenses.color = android.graphics.Color.RED
    canvas.drawText(financialReport.totalExpenses, 450F, y, reportFooterTotalExpenses)

    y+=20
    if (pageHeight-y<50){
        var pageNumberPaint: Paint = Paint()
        pageNumberPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        pageNumberPaint.textSize = 10F
        pageNumberPaint.color = android.graphics.Color.BLACK
        canvas.drawText("página $page", (pageWidth-100).toFloat(), (pageHeight-20).toFloat(), pageNumberPaint)

        page++
        pdfDocument.finishPage(myPage)
        myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, page).create()
        myPage = pdfDocument.startPage(myPageInfo)
        canvas = myPage.canvas
        y = 50F
    }

    var reportFooterTotalBalance: Paint = Paint()
    reportFooterTotalBalance.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
    reportFooterTotalBalance.textSize = 10F
    reportFooterTotalBalance.color = android.graphics.Color.BLACK
    canvas.drawText("Saldo Total:", 50F, y, reportFooterTotalBalance)
    if (financialReport.totalBalance.contains("-")){
        reportFooterTotalBalance.color = android.graphics.Color.RED
        canvas.drawText(financialReport.totalBalance.replace("-R","R"), 450F, y, reportFooterTotalBalance)
    }else{
        reportFooterTotalBalance.color = android.graphics.Color.GREEN
        canvas.drawText(financialReport.totalBalance, 450F, y, reportFooterTotalBalance)
    }

    var pageNumberPaint: Paint = Paint()
    pageNumberPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
    pageNumberPaint.textSize = 10F
    pageNumberPaint.color = android.graphics.Color.BLACK
    canvas.drawText("página $page", (pageWidth-100).toFloat(), (pageHeight-20).toFloat(), pageNumberPaint)

    pdfDocument.finishPage(myPage)

    val contextWrapper = ContextWrapper(context)
    val documentDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    val file = File(documentDirectory, "$filename.pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        val fileURI = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            file
        )

        val browserIntent = Intent(Intent.ACTION_VIEW, fileURI)
        browserIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(browserIntent)

    } catch (e: Exception) {
        Toast.makeText(context, "Erro ao gerar arquivo PDF.", Toast.LENGTH_SHORT).show()
    }
    pdfDocument.close()

}




@Composable
fun hasDebit(property: Property, receivingViewModel: ReceivingViewModel) : Date {

    var ret = Date(0)

    var today = Calendar.getInstance()

        val recFlow = receivingViewModel.getRentReceivingsByDateFilter(property.propertyId,property.contractStartDate, property.contractStartDate,today.time)
        val receivedListFlow = recFlow.collectAsStateWithLifecycle(initialValue = emptyList())
        val receivedList = receivedListFlow.value


        var eachBilling = Calendar.getInstance()
        eachBilling.time = property.contractStartDate
        eachBilling.set(Calendar.DAY_OF_MONTH,property.contractPaymentDate)
        var totalBillingsQtd = property.contractMonths
        if (property.contractDays>0)
            totalBillingsQtd += 1

        var i=0
        while (i<totalBillingsQtd){
            eachBilling.add(Calendar.MONTH,1)

            if (eachBilling.before(today)){
                var paid = false
                //checa se foi pago. se nao foi é pendente ou previsto

                for (received in receivedList) {
                    var eachReceived = Calendar.getInstance()
                    eachReceived.time = received.rentBillingDueDate
                    if (eachReceived.get(Calendar.YEAR) == eachBilling.get(Calendar.YEAR) && eachReceived.get(Calendar.MONTH) == eachBilling.get(Calendar.MONTH)){
                        paid = true
                        break
                    }
                }

                if (!paid){

                    var billingValue = property.contractMonthlyBillingValue
                    if (i==(totalBillingsQtd-1)){
                        //ultima parcela: checar se é proporcional aos dias caso seja menos de 1 mes
                        if (property.contractDays > 0){
                            billingValue *= (property.contractDays / 30.0)
                        }
                    }

                    if (eachBilling.before(today)){
                        //atrasado
                        return eachBilling.time
                }
            }

        }
            i++
    }
    return ret
}