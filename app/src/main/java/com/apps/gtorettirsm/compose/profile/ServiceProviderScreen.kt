/*
 */

package com.apps.gtorettirsm.compose.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.compose.property.PropertyDetailScreen
import com.apps.gtorettirsm.compose.property.ProviderDetailScreen
import com.apps.gtorettirsm.compose.property.ProviderSearchScreen
import com.apps.gtorettirsm.compose.property.providerResult
import com.apps.gtorettirsm.compose.property.searchName
import com.apps.gtorettirsm.compose.property.searchServicesAdministration
import com.apps.gtorettirsm.compose.property.searchServicesAirConditioningMaintenance
import com.apps.gtorettirsm.compose.property.searchServicesAluminumFrames
import com.apps.gtorettirsm.compose.property.searchServicesArchitecture
import com.apps.gtorettirsm.compose.property.searchServicesAutomation
import com.apps.gtorettirsm.compose.property.searchServicesBrickwork
import com.apps.gtorettirsm.compose.property.searchServicesCabinetsJoinery
import com.apps.gtorettirsm.compose.property.searchServicesCleaning
import com.apps.gtorettirsm.compose.property.searchServicesCurtains
import com.apps.gtorettirsm.compose.property.searchServicesElectric
import com.apps.gtorettirsm.compose.property.searchServicesElectricFence
import com.apps.gtorettirsm.compose.property.searchServicesElectronicGate
import com.apps.gtorettirsm.compose.property.searchServicesElectronicIntercom
import com.apps.gtorettirsm.compose.property.searchServicesElevatorMaintenance
import com.apps.gtorettirsm.compose.property.searchServicesFireBrigade
import com.apps.gtorettirsm.compose.property.searchServicesGardening
import com.apps.gtorettirsm.compose.property.searchServicesHydraulic
import com.apps.gtorettirsm.compose.property.searchServicesInsurer
import com.apps.gtorettirsm.compose.property.searchServicesLandscaping
import com.apps.gtorettirsm.compose.property.searchServicesMaterialSupplier
import com.apps.gtorettirsm.compose.property.searchServicesNotary
import com.apps.gtorettirsm.compose.property.searchServicesOthers
import com.apps.gtorettirsm.compose.property.searchServicesPainting
import com.apps.gtorettirsm.compose.property.searchServicesPestControl
import com.apps.gtorettirsm.compose.property.searchServicesPlasterer
import com.apps.gtorettirsm.compose.property.searchServicesPlaygroundMaintenance
import com.apps.gtorettirsm.compose.property.searchServicesPoolCleaning
import com.apps.gtorettirsm.compose.property.searchServicesPoolMaintenance
import com.apps.gtorettirsm.compose.property.searchServicesPropertySecurity
import com.apps.gtorettirsm.compose.property.searchServicesRoofer
import com.apps.gtorettirsm.compose.property.searchServicesShowerStalls
import com.apps.gtorettirsm.compose.property.searchServicesSteelGatesRailings
import com.apps.gtorettirsm.compose.property.searchServicesSunshades
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getRedTextColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.Profile
import com.apps.gtorettirsm.data.Provider
import com.apps.gtorettirsm.viewmodels.ProviderViewModel
import kotlinx.coroutines.flow.Flow


val providerId = mutableLongStateOf(0L)

@Composable
fun ServiceProviderScreen() {
    var providerViewModel: ProviderViewModel = hiltViewModel()
    val providerFlow = providerViewModel.providers
    ServiceProviderScreen(providerFlow = providerFlow, providerViewModel = providerViewModel)
}

@Composable
fun ServiceProviderScreen(
    providerFlow: Flow<List<Provider>>,
    providerViewModel: ProviderViewModel
) {

    val context = LocalContext.current

    var openProviderDetailDialog = remember { mutableStateOf(false) }
    var openProviderSearchDialog = remember { mutableStateOf(false) }

    val providers by providerFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    providerResult.value=ArrayList(providers)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth().padding(horizontal = 5.dp)
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
                    text = "Prestadores de Serviços:"
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
                    openProviderSearchDialog.value = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = getButtonColor()
                ), modifier = Modifier.height(33.dp)
            ) {
                Text(
                    text = "Pesquisar",
                    style = TextStyle(
                        fontSize = 14.sp,
                    )
                )
            }

            Button(
                onClick = {
                    openProviderDetailDialog.value = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = getButtonColor()
                ), modifier = Modifier.height(33.dp)
            ) {
                Text(
                    text = "Adicionar",
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

        providers.forEach { item ->

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = false,
                        onClick = {
                            providerId.value = item.providerId
                            openProviderDetailDialog.value = true
                        },
                        role = Role.Button
                    )
            ) {


                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(2.dp)

                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){


                        TextButton(
                            modifier = Modifier.padding(5.dp),
                            onClick =
                            {
                                openContactDetails(item.contactId,context)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Call,
                                contentDescription = "Chamar Prestador",
                                tint = getTextColor(),
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .size(24.dp)
                            )
                        }

                        Text(
                            text = item.name,style = TextStyle(
                                color = getTextColor(),
                                fontSize = 14.sp,
                            )
                        )
                    }

                    HorizontalDivider(thickness = 1.dp)
                }
            }
        }


        when {
            openProviderDetailDialog.value -> {
                ProviderDetailScreen(
                    openProviderDetailDialog,
                    providerViewModel,
                    context,
                    providerId.value)
            }
        }

        when {
            openProviderSearchDialog.value -> {

                searchName.value = ""
                searchServicesAdministration.value = 0
                searchServicesHydraulic.value = 0
                searchServicesBrickwork.value = 0
                searchServicesElectric.value = 0
                searchServicesArchitecture.value = 0
                searchServicesInsurer.value = 0
                searchServicesAutomation.value = 0
                searchServicesFireBrigade.value = 0
                searchServicesNotary.value = 0
                searchServicesPlasterer.value = 0
                searchServicesElectricFence.value = 0
                searchServicesAluminumFrames.value = 0
                searchServicesAirConditioningMaintenance.value = 0
                searchServicesRoofer.value = 0
                searchServicesElevatorMaintenance.value = 0
                searchServicesElectronicIntercom.value = 0
                searchServicesGardening.value = 0
                searchServicesPoolMaintenance.value = 0
                searchServicesPlaygroundMaintenance.value = 0
                searchServicesElectronicGate.value = 0
                searchServicesCleaning.value = 0
                searchServicesOthers.value = 0
                searchServicesMaterialSupplier.value = 0
                searchServicesPoolCleaning.value = 0
                searchServicesLandscaping.value = 0
                searchServicesPainting.value = 0
                searchServicesSteelGatesRailings.value = 0
                searchServicesPropertySecurity.value = 0
                searchServicesCurtains.value = 0
                searchServicesShowerStalls.value = 0
                searchServicesSunshades.value = 0
                searchServicesCabinetsJoinery.value = 0
                searchServicesPestControl.value = 0

                ProviderSearchScreen(
                    openProviderSearchDialog,
                    providerViewModel,
                    context)
            }
        }
    }
}



fun openContactDetails(contactId: String, context: Context) {
    val uri: Uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contactId)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = uri
    }
    context.startActivity(intent)
}
