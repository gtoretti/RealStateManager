/*
 */

package com.apps.gtorettirsm.compose.property

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.compose.utils.getRedTextColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.MonthlyBillingViewModel
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ReceiptPDFViewModel
import com.apps.gtorettirsm.viewmodels.ReceiptViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun PropertiesScreen(
) {
    var patientViewModel: PropertyViewModel = hiltViewModel()
    var monthlyBillingViewModel: MonthlyBillingViewModel = hiltViewModel()
    var receiptViewModel: ReceiptViewModel = hiltViewModel()
    var receiptPDFViewModel: ReceiptPDFViewModel = hiltViewModel()
    val patients = patientViewModel.activePropertys
    PropertiesScreen(
        patientsFlow = patients,
        patientViewModel = patientViewModel,
        monthlyBillingViewModel = monthlyBillingViewModel,
        receiptViewModel = receiptViewModel,
        receiptPDFViewModel = receiptPDFViewModel
    )
}

@Composable
fun PropertiesScreen(
    patientsFlow: Flow<List<Property>>,
    patientViewModel: PropertyViewModel,
    monthlyBillingViewModel: MonthlyBillingViewModel,
    receiptViewModel: ReceiptViewModel,
    receiptPDFViewModel: ReceiptPDFViewModel
) {

    var openPropertyCreateDialog = remember { mutableStateOf(false) }
    var openPropertyDetailDialog = remember { mutableStateOf(false) }
    var propertyId = remember { mutableStateOf(0L) }

    val context = LocalContext.current
    val patients by patientsFlow.collectAsStateWithLifecycle(initialValue = emptyList())

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
                    text = "Imóveis:",
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {

            if (patients.isEmpty()) {
                Text(
                    text = "Para adicionar Imóveis, clique aqui-->",
                    style = TextStyle(
                        color = getRedTextColor(),
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            }

            TextButton(
                modifier = Modifier.padding(5.dp),
                onClick =
                {
                    openPropertyCreateDialog.value = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Adicionar Paciente",
                    tint = getTextColor(),
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(24.dp)
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {


            patients.forEach { item ->

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = false,
                            onClick = {
                                openPropertyDetailDialog.value = true
                                propertyId.value = item.propertyId
                            },
                            role = Role.Button
                        )
                ) {
                    Text(
                        text = item.address,
                        style = TextStyle(
                            color = getTextColor(),
                            fontSize = 18.sp,
                            fontFamily = FontFamily.SansSerif,
                        )
                    )
                    TextButton(
                        modifier = Modifier.padding(5.dp),
                        onClick =
                        {
                            openPropertyDetailDialog.value = true
                            propertyId.value = item.propertyId
                        }
                    ) {
                    }
                }
                HorizontalDivider(thickness = 2.dp)
            }


        }
        Spacer(modifier = Modifier.weight(1f))
        //AndroidViewAdView()

        when {
            openPropertyDetailDialog.value -> {
                PropertyDetailScreen(
                    openPropertyDetailDialog,
                    patientViewModel,
                    monthlyBillingViewModel,
                    receiptViewModel,
                    receiptPDFViewModel,
                    propertyId.value,
                    context
                )
            }
        }
        when {
            openPropertyCreateDialog.value -> {
                PropertyCreateScreen(openPropertyCreateDialog, patientViewModel, context)
            }
        }
    }
}