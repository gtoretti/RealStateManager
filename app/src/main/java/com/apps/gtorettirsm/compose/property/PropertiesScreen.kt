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
import androidx.compose.ui.text.font.FontWeight
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
    var propertyViewModel: PropertyViewModel = hiltViewModel()
    var monthlyBillingViewModel: MonthlyBillingViewModel = hiltViewModel()
    var receiptViewModel: ReceiptViewModel = hiltViewModel()
    var receiptPDFViewModel: ReceiptPDFViewModel = hiltViewModel()
    val properties = propertyViewModel.activeProperties
    PropertiesScreen(
        propertiesFlow = properties,
        propertyViewModel = propertyViewModel,
        monthlyBillingViewModel = monthlyBillingViewModel,
        receiptViewModel = receiptViewModel,
        receiptPDFViewModel = receiptPDFViewModel
    )
}

@Composable
fun PropertiesScreen(
    propertiesFlow: Flow<List<Property>>,
    propertyViewModel: PropertyViewModel,
    monthlyBillingViewModel: MonthlyBillingViewModel,
    receiptViewModel: ReceiptViewModel,
    receiptPDFViewModel: ReceiptPDFViewModel
) {

    var openPropertyCreateDialog = remember { mutableStateOf(false) }
    var openPropertyDetailDialog = remember { mutableStateOf(false) }
    var propertyId = remember { mutableStateOf(0L) }

    val context = LocalContext.current
    val properties by propertiesFlow.collectAsStateWithLifecycle(initialValue = emptyList())

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

            if (properties.isEmpty()) {
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
                    contentDescription = "Adicionar Imóvel",
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


            properties.forEach { item ->

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


                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(2.dp)

                    ) {

                        var streetAddress = item.streetAddress + ", " + item.number
                        if (item.complement.isNotEmpty())
                            streetAddress = streetAddress + " - " + item.complement

                        Text(
                            text = streetAddress
                        )

                        Text(
                            text = item.district
                        )
                        Text(
                            text = item.city + " - " + item.state
                        )
                        Text(
                            text = "CEP: "+item.zipCode , style = TextStyle(

                                fontWeight = FontWeight.Bold,
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
                        HorizontalDivider(thickness = 1.dp)
                    }
                }
            }


        }
        Spacer(modifier = Modifier.weight(1f))
        //AndroidViewAdView()

        when {
            openPropertyDetailDialog.value -> {
                PropertyDetailScreen(
                    openPropertyDetailDialog,
                    propertyViewModel,
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
                PropertyCreateScreen(openPropertyCreateDialog, propertyViewModel, context, Property(0L,"", "", "", "", "", "", "", 0.0,0,"", "", "", "", "", "" , 0))
            }
        }
    }
}