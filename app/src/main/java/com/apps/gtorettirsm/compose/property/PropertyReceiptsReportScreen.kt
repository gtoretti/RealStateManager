/*
 */

package com.apps.gtorettirsm.compose.property


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.compose.utils.getRedTextColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.toScreen
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ReceiptPDFViewModel
import com.apps.gtorettirsm.viewmodels.ReceiptViewModel
import java.util.Calendar
import java.util.HashMap


@Composable
fun PropertyReceiptsReportScreen() {
    var patientViewModel: PropertyViewModel = hiltViewModel()
    var receiptViewModel: ReceiptViewModel = hiltViewModel()
    var receiptPDFViewModel: ReceiptPDFViewModel = hiltViewModel()
    val patientsFlow = patientViewModel.patients
    val patients by patientsFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    var patientsMap = HashMap<Long,String>()
    patients.forEach { item ->
        patientsMap.put(item.propertyId,item.streetAddress)
    }
    PropertyReceiptsReportScreen(receiptViewModel,patientsMap,receiptPDFViewModel)
}

@Composable
fun PropertyReceiptsReportScreen(
    receiptViewModel: ReceiptViewModel,
    patientsMap: Map<Long,String>,
    receiptPDFViewModel: ReceiptPDFViewModel
) {

    var month by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    var year by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    var total = 0.0
    val context = LocalContext.current

    var start = Calendar.getInstance()
    start.set(Calendar.MONTH,month)
    start.set(Calendar.YEAR,year)
    start.set(Calendar.DAY_OF_MONTH,1)

    var end = Calendar.getInstance()
    end.set(Calendar.MONTH,month)
    end.set(Calendar.YEAR,year)
    end.set(Calendar.DAY_OF_MONTH,1)
    end.add(Calendar.MONTH,1)

    val receiptsFlow = receiptViewModel.getReceivedReceiptsByDate(start.time,end.time)
    val receipts by receiptsFlow.collectAsStateWithLifecycle(initialValue = emptyList())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Pagamentos recebidos:"
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()).padding(start = 5.dp,end = 5.dp),
        ) {

            month = MonthDropDownMenu()
            year = YearDropDownMenu()



            if (receipts.size>0) {

                Text(
                    text = "Para abrir os recibos, clique sobre as linhas abaixo.",
                    style = TextStyle(
                        color = getRedTextColor(),
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                    )
                )


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp, top = 5.dp)
                ) {
                    Text(
                        text = "Paciente:",
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Valor:",
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                HorizontalDivider(thickness = 2.dp)

                receipts.forEach { item ->
                    total += item.total

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp, top = 5.dp)
                            .selectable(
                                selected = false,
                                onClick = {
                                    receiptPDFViewModel.openPDF(item.receiptId, context)
                                },
                                role = Role.Button
                            )
                    ) {

                        Text(
                            text = patientsMap.get(item.propertyId).toString(),
                            style = TextStyle(
                                color = getTextColor(),
                                fontSize = 16.sp,
                                fontFamily = FontFamily.SansSerif,
                            )

                        )

                        Text(
                            text = item.total.toScreen(),
                            style = TextStyle(
                                color = getTextColor(),
                                fontSize = 16.sp,
                                fontFamily = FontFamily.SansSerif,
                            )

                        )
                    }
                    HorizontalDivider(thickness = 2.dp)
                }

                HorizontalDivider(thickness = 2.dp)
                //total
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp, top = 5.dp)
                ) {

                    Text(
                        text = "Valor total recebido no período: " + total.toScreen(),
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold
                        )

                    )
                }
            }else{
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp, top = 15.dp)
                ) {

                    Text(
                        text = "Não foram registrados pagamentos no período selecionado.",
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold
                        )

                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        //AndroidViewAdView()
    }
}