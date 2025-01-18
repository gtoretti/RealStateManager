/*
 */

package com.apps.gtorettirsm.compose.property

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.compose.profile.providerId
import com.apps.gtorettirsm.compose.utils.DrawScrollableView
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getProviderDesc
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.Provider
import com.apps.gtorettirsm.viewmodels.ProviderViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


val providerResult = mutableStateOf(ArrayList<Provider>())

val searchName = mutableStateOf("")
val searchServicesAdministration = mutableIntStateOf(0)
val searchServicesHydraulic = mutableIntStateOf(0)
val searchServicesBrickwork = mutableIntStateOf(0)
val searchServicesElectric = mutableIntStateOf(0)
val searchServicesArchitecture = mutableIntStateOf(0)
val searchServicesInsurer = mutableIntStateOf(0)
val searchServicesAutomation = mutableIntStateOf(0)
val searchServicesFireBrigade = mutableIntStateOf(0)
val searchServicesNotary = mutableIntStateOf(0)
val searchServicesPlasterer = mutableIntStateOf(0)
val searchServicesElectricFence = mutableIntStateOf(0)
val searchServicesAluminumFrames = mutableIntStateOf(0)
val searchServicesAirConditioningMaintenance = mutableIntStateOf(0)
val searchServicesRoofer = mutableIntStateOf(0)
val searchServicesElevatorMaintenance = mutableIntStateOf(0)
val searchServicesElectronicIntercom = mutableIntStateOf(0)
val searchServicesGardening = mutableIntStateOf(0)
val searchServicesPoolMaintenance = mutableIntStateOf(0)
val searchServicesPlaygroundMaintenance = mutableIntStateOf(0)
val searchServicesElectronicGate = mutableIntStateOf(0)
val searchServicesCleaning = mutableIntStateOf(0)
val searchServicesPoolCleaning = mutableIntStateOf(0)
val searchServicesLandscaping = mutableIntStateOf(0)
val searchServicesPainting = mutableIntStateOf(0)
val searchServicesSteelGatesRailings = mutableIntStateOf(0)
val searchServicesPropertySecurity = mutableIntStateOf(0)
val searchServicesCurtains = mutableIntStateOf(0)
val searchServicesShowerStalls = mutableIntStateOf(0)
val searchServicesSunshades = mutableIntStateOf(0)
val searchServicesCabinetsJoinery = mutableIntStateOf(0)

@Composable
fun ProviderSearchScreen(
    openProviderSearchDialog: MutableState<Boolean>,
    providerViewModel: ProviderViewModel,
    context: Context
) {

    var openProviderDetailDialog = remember { mutableStateOf(false) }

    if (openProviderSearchDialog.value) {

        var tmp = ArrayList<Provider>()

        providerResult.value.forEach { item ->
            if (item.name.contains(searchName.value.trim()) && searchName.value.trim().isNotEmpty())
                tmp.add(item)
            else if (item.servicesAdministration==1 && searchServicesAdministration.value==1)
                tmp.add(item)
            else if (item.servicesBrickwork==1 && searchServicesBrickwork.value==1)
                tmp.add(item)
            else if (item.servicesArchitecture==1 && searchServicesArchitecture.value==1)
                tmp.add(item)
            else if (item.servicesInsurer==1 && searchServicesInsurer.value==1)
                tmp.add(item)
            else if (item.servicesAutomation==1 && searchServicesAutomation.value==1)
                tmp.add(item)
            else if (item.servicesFireBrigade==1 && searchServicesFireBrigade.value==1)
                tmp.add(item)
            else if (item.servicesNotary==1 && searchServicesNotary.value==1)
                tmp.add(item)
            else if (item.servicesAluminumFrames==1 && searchServicesAluminumFrames.value==1)
                tmp.add(item)
            else if (item.servicesPlasterer==1 && searchServicesPlasterer.value==1)
                tmp.add(item)
            else if (item.servicesElectric==1 && searchServicesElectric.value==1)
                tmp.add(item)
            else if (item.servicesHydraulic==1 && searchServicesHydraulic.value==1)
                tmp.add(item)
            else if (item.servicesAirConditioningMaintenance==1 && searchServicesAirConditioningMaintenance.value==1)
                tmp.add(item)
            else if (item.servicesShowerStalls==1 && searchServicesShowerStalls.value==1)
                tmp.add(item)
            else if (item.servicesRoofer==1 && searchServicesRoofer.value==1)
                tmp.add(item)
            else if (item.servicesElectricFence==1 && searchServicesElectricFence.value==1)
                tmp.add(item)
            else if (item.servicesElevatorMaintenance==1 && searchServicesElevatorMaintenance.value==1)
                tmp.add(item)
            else if (item.servicesElectronicIntercom==1 && searchServicesElectronicIntercom.value==1)
                tmp.add(item)
            else if (item.servicesGardening==1 && searchServicesGardening.value==1)
                tmp.add(item)
            else if (item.servicesPoolMaintenance==1 && searchServicesPoolMaintenance.value==1)
                tmp.add(item)
            else if (item.servicesPlaygroundMaintenance==1 && searchServicesPlaygroundMaintenance.value==1)
                tmp.add(item)
            else if (item.servicesElectronicGate==1 && searchServicesElectronicGate.value==1)
                tmp.add(item)
            else if (item.servicesCleaning==1 && searchServicesCleaning.value==1)
                tmp.add(item)
            else if (item.servicesPoolCleaning==1 && searchServicesPoolCleaning.value==1)
                tmp.add(item)
            else if (item.servicesLandscaping==1 && searchServicesLandscaping.value==1)
                tmp.add(item)
            else if (item.servicesPainting==1 && searchServicesPainting.value==1)
                tmp.add(item)
            else if (item.servicesSteelGatesRailings==1 && searchServicesSteelGatesRailings.value==1)
                tmp.add(item)
            else if (item.servicesPropertySecurity==1 && searchServicesPropertySecurity.value==1)
                tmp.add(item)
            else if (item.servicesSunshades==1 && searchServicesSunshades.value==1)
                tmp.add(item)
            else if (item.servicesCurtains==1 && searchServicesCurtains.value==1)
                tmp.add(item)
            else if (item.servicesCabinetsJoinery==1 && searchServicesCabinetsJoinery.value==1)
                tmp.add(item)
        }


        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openProviderSearchDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(750.dp),

            title = {
                Text(
                    text = "Procurar Prestador:",
                    style = TextStyle(
                        color = getTextColor(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            },
            text = {
                Column(

                ) {


                    DrawScrollableView(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        content = {
                            Column(

                                modifier = Modifier.padding(horizontal = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)

                            ) {

                                OutlinedTextField(
                                    value = searchName.value,
                                    onValueChange = {
                                        searchName.value = it
                                    },
                                    textStyle = TextStyle(
                                        fontSize = 16.sp,
                                        color = getTextColor(),
                                        fontWeight = FontWeight.Normal
                                    ),
                                    label = {
                                        Text(
                                            text = "Nome:",
                                            style = TextStyle(
                                                color = getTextColor(), fontSize = 12.sp
                                            )
                                        )
                                    },
                                    placeholder = { Text("") },

                                    )

                                Text(
                                    text = "Tipos de Serviços Prestados:",
                                    style = TextStyle(
                                        color = getTextColor(),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )

                                DrawScrollableView(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp),
                                    content = {
                                        Column {


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesAdministration.value == 1,
                                                        onClick = {
                                                            if (searchServicesAdministration.value == 0) searchServicesAdministration.value =
                                                                1
                                                            else searchServicesAdministration.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesAdministration.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesAdministration.value = 1
                                                        else searchServicesAdministration.value = 0
                                                    })
                                                Text(
                                                    text = "Administração de reformas",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }





                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesBrickwork.value == 1,
                                                        onClick = {
                                                            if (searchServicesBrickwork.value == 0) searchServicesBrickwork.value =
                                                                1
                                                            else searchServicesBrickwork.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesBrickwork.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesBrickwork.value = 1
                                                        else searchServicesBrickwork.value = 0
                                                    })
                                                Text(
                                                    text = "Alvenaria",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }



                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesArchitecture.value == 1,
                                                        onClick = {
                                                            if (searchServicesArchitecture.value == 0) searchServicesArchitecture.value =
                                                                1
                                                            else searchServicesArchitecture.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesArchitecture.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesArchitecture.value = 1
                                                        else searchServicesArchitecture.value = 0
                                                    })
                                                Text(
                                                    text = "Arquitetura",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }



                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesInsurer.value == 1,
                                                        onClick = {
                                                            if (searchServicesInsurer.value == 0) searchServicesInsurer.value =
                                                                1
                                                            else searchServicesInsurer.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesInsurer.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesInsurer.value = 1
                                                        else searchServicesInsurer.value = 0
                                                    })
                                                Text(
                                                    text = "Asseguradora",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }



                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesAutomation.value == 1,
                                                        onClick = {
                                                            if (searchServicesAutomation.value == 0) searchServicesAutomation.value =
                                                                1
                                                            else searchServicesAutomation.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesAutomation.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesAutomation.value = 1
                                                        else searchServicesAutomation.value = 0
                                                    })
                                                Text(
                                                    text = "Automação Residencial",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }



                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesFireBrigade.value == 1,
                                                        onClick = {
                                                            if (searchServicesFireBrigade.value == 0) searchServicesFireBrigade.value =
                                                                1
                                                            else searchServicesFireBrigade.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesFireBrigade.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesFireBrigade.value = 1
                                                        else searchServicesFireBrigade.value = 0
                                                    })
                                                Text(
                                                    text = "Brigada de Incêncio",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesNotary.value == 1,
                                                        onClick = {
                                                            if (searchServicesNotary.value == 0) searchServicesNotary.value =
                                                                1
                                                            else searchServicesNotary.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesNotary.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesNotary.value = 1
                                                        else searchServicesNotary.value = 0
                                                    })
                                                Text(
                                                    text = "Cartório de Imóveis",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }



                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesAluminumFrames.value == 1,
                                                        onClick = {
                                                            if (searchServicesAluminumFrames.value == 0) searchServicesAluminumFrames.value =
                                                                1
                                                            else searchServicesAluminumFrames.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesAluminumFrames.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesAluminumFrames.value = 1
                                                        else searchServicesAluminumFrames.value = 0
                                                    })
                                                Text(
                                                    text = "Esquadrias de Alumínio",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }



                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesPlasterer.value == 1,
                                                        onClick = {
                                                            if (searchServicesPlasterer.value == 0) searchServicesPlasterer.value =
                                                                1
                                                            else searchServicesPlasterer.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesPlasterer.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesPlasterer.value = 1
                                                        else searchServicesPlasterer.value = 0
                                                    })
                                                Text(
                                                    text = "Gesseiro",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesElectric.value == 1,
                                                        onClick = {
                                                            if (searchServicesElectric.value == 0) searchServicesElectric.value =
                                                                1
                                                            else searchServicesElectric.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesElectric.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesElectric.value = 1
                                                        else searchServicesElectric.value = 0
                                                    })
                                                Text(
                                                    text = "Instalações Elétricas",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesHydraulic.value == 1,
                                                        onClick = {
                                                            if (searchServicesHydraulic.value == 0) searchServicesHydraulic.value =
                                                                1
                                                            else searchServicesHydraulic.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesHydraulic.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesHydraulic.value = 1
                                                        else searchServicesHydraulic.value = 0
                                                    })
                                                Text(
                                                    text = "Instalações Hidráulicas",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesCleaning.value == 1,
                                                        onClick = {
                                                            if (searchServicesCleaning.value == 0) searchServicesCleaning.value =
                                                                1
                                                            else searchServicesCleaning.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesCleaning.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesCleaning.value = 1
                                                        else searchServicesCleaning.value = 0
                                                    })
                                                Text(
                                                    text = "Limpeza Pós-Obra",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesPoolCleaning.value == 1,
                                                        onClick = {
                                                            if (searchServicesPoolCleaning.value == 0) searchServicesPoolCleaning.value =
                                                                1
                                                            else searchServicesPoolCleaning.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesPoolCleaning.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesPoolCleaning.value = 1
                                                        else searchServicesPoolCleaning.value = 0
                                                    })
                                                Text(
                                                    text = "Limpeza de Piscina",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesAirConditioningMaintenance.value == 1,
                                                        onClick = {
                                                            if (searchServicesAirConditioningMaintenance.value == 0) searchServicesAirConditioningMaintenance.value =
                                                                1
                                                            else searchServicesAirConditioningMaintenance.value =
                                                                0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesAirConditioningMaintenance.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesAirConditioningMaintenance.value =
                                                            1
                                                        else searchServicesAirConditioningMaintenance.value =
                                                            0
                                                    })
                                                Text(
                                                    text = "Manutenção de Ar-Condicionado",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesCabinetsJoinery.value == 1,
                                                        onClick = {
                                                            if (searchServicesCabinetsJoinery.value == 0) searchServicesCabinetsJoinery.value =
                                                                1
                                                            else searchServicesCabinetsJoinery.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesCabinetsJoinery.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesCabinetsJoinery.value = 1
                                                        else searchServicesCabinetsJoinery.value = 0
                                                    })
                                                Text(
                                                    text = "Manutenção de Armários e Marcenaria",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }



                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesShowerStalls.value == 1,
                                                        onClick = {
                                                            if (searchServicesShowerStalls.value == 0) searchServicesShowerStalls.value =
                                                                1
                                                            else searchServicesShowerStalls.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesShowerStalls.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesShowerStalls.value = 1
                                                        else searchServicesShowerStalls.value = 0
                                                    })
                                                Text(
                                                    text = "Manutenção de Boxes para Banheiros",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesRoofer.value == 1,
                                                        onClick = {
                                                            if (searchServicesRoofer.value == 0) searchServicesRoofer.value =
                                                                1
                                                            else searchServicesRoofer.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesRoofer.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesRoofer.value = 1
                                                        else searchServicesRoofer.value = 0
                                                    })
                                                Text(
                                                    text = "Manutenção de Calhas e Telhado",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesElectricFence.value == 1,
                                                        onClick = {
                                                            if (searchServicesElectricFence.value == 0) searchServicesElectricFence.value =
                                                                1
                                                            else searchServicesElectricFence.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesElectricFence.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesElectricFence.value = 1
                                                        else searchServicesElectricFence.value = 0
                                                    })
                                                Text(
                                                    text = "Manutenção de Cerca Elétrica",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesSunshades.value == 1,
                                                        onClick = {
                                                            if (searchServicesSunshades.value == 0) searchServicesSunshades.value =
                                                                1
                                                            else searchServicesSunshades.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesSunshades.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesSunshades.value = 1
                                                        else searchServicesSunshades.value = 0
                                                    })
                                                Text(
                                                    text = "Manutenção de Coberturas e Toldos",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesCurtains.value == 1,
                                                        onClick = {
                                                            if (searchServicesCurtains.value == 0) searchServicesCurtains.value =
                                                                1
                                                            else searchServicesCurtains.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesCurtains.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesCurtains.value = 1
                                                        else searchServicesCurtains.value = 0
                                                    })
                                                Text(
                                                    text = "Manutenção de Cortinas",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesElevatorMaintenance.value == 1,
                                                        onClick = {
                                                            if (searchServicesElevatorMaintenance.value == 0) searchServicesElevatorMaintenance.value =
                                                                1
                                                            else searchServicesElevatorMaintenance.value =
                                                                0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesElevatorMaintenance.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesElevatorMaintenance.value =
                                                            1
                                                        else searchServicesElevatorMaintenance.value = 0
                                                    })
                                                Text(
                                                    text = "Manutenção de Elevador",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesElectronicIntercom.value == 1,
                                                        onClick = {
                                                            if (searchServicesElectronicIntercom.value == 0) searchServicesElectronicIntercom.value =
                                                                1
                                                            else searchServicesElectronicIntercom.value =
                                                                0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesElectronicIntercom.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesElectronicIntercom.value = 1
                                                        else searchServicesElectronicIntercom.value = 0
                                                    })
                                                Text(
                                                    text = "Manutenção de Interfones",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }



                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesGardening.value == 1,
                                                        onClick = {
                                                            if (searchServicesGardening.value == 0) searchServicesGardening.value =
                                                                1
                                                            else searchServicesGardening.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesGardening.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesGardening.value = 1
                                                        else searchServicesGardening.value = 0
                                                    })
                                                Text(
                                                    text = "Manutenção de Jardim",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesPoolMaintenance.value == 1,
                                                        onClick = {
                                                            if (searchServicesPoolMaintenance.value == 0) searchServicesPoolMaintenance.value =
                                                                1
                                                            else searchServicesPoolMaintenance.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesPoolMaintenance.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesPoolMaintenance.value = 1
                                                        else searchServicesPoolMaintenance.value = 0
                                                    })
                                                Text(
                                                    text = "Manutenção de Piscina",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }



                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesPlaygroundMaintenance.value == 1,
                                                        onClick = {
                                                            if (searchServicesPlaygroundMaintenance.value == 0) searchServicesPlaygroundMaintenance.value =
                                                                1
                                                            else searchServicesPlaygroundMaintenance.value =
                                                                0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesPlaygroundMaintenance.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesPlaygroundMaintenance.value =
                                                            1
                                                        else searchServicesPlaygroundMaintenance.value = 0
                                                    })
                                                Text(
                                                    text = "Manutenção de Playground",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesElectronicGate.value == 1,
                                                        onClick = {
                                                            if (searchServicesElectronicGate.value == 0) searchServicesElectronicGate.value =
                                                                1
                                                            else searchServicesElectronicGate.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesElectronicGate.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesElectronicGate.value = 1
                                                        else searchServicesElectronicGate.value = 0
                                                    })
                                                Text(
                                                    text = "Manutenção de Portão Eletrônico",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }



                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesLandscaping.value == 1,
                                                        onClick = {
                                                            if (searchServicesLandscaping.value == 0) searchServicesLandscaping.value =
                                                                1
                                                            else searchServicesLandscaping.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesLandscaping.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesLandscaping.value = 1
                                                        else searchServicesLandscaping.value = 0
                                                    })
                                                Text(
                                                    text = "Paisagismo",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesPainting.value == 1,
                                                        onClick = {
                                                            if (searchServicesPainting.value == 0) searchServicesPainting.value =
                                                                1
                                                            else searchServicesPainting.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesPainting.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesPainting.value = 1
                                                        else searchServicesPainting.value = 0
                                                    })
                                                Text(
                                                    text = "Pintura",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesSteelGatesRailings.value == 1,
                                                        onClick = {
                                                            if (searchServicesSteelGatesRailings.value == 0) searchServicesSteelGatesRailings.value =
                                                                1
                                                            else searchServicesSteelGatesRailings.value =
                                                                0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesSteelGatesRailings.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesSteelGatesRailings.value = 1
                                                        else searchServicesSteelGatesRailings.value = 0
                                                    })
                                                Text(
                                                    text = "Portões e Grades de Aço",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .selectable(
                                                        selected = searchServicesPropertySecurity.value == 1,
                                                        onClick = {
                                                            if (searchServicesPropertySecurity.value == 0) searchServicesPropertySecurity.value =
                                                                1
                                                            else searchServicesPropertySecurity.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (searchServicesPropertySecurity.value == 1),
                                                    onCheckedChange = {
                                                        if (it) searchServicesPropertySecurity.value = 1
                                                        else searchServicesPropertySecurity.value = 0
                                                    })
                                                Text(
                                                    text = "Segurança Patrimonial",
                                                    style = TextStyle(
                                                        color = getTextColor(),
                                                        fontSize = 16.sp,
                                                        fontFamily = FontFamily.SansSerif,
                                                    ),
                                                    modifier = Modifier.padding(
                                                        start = 2.dp,
                                                        end = 6.dp
                                                    )
                                                )
                                            }


                                        }

                                    }
                                )

                            }
                        })


                    HorizontalDivider(thickness = 1.dp)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    Text(
                        text = "Resultados da Procura:",
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

                    DrawScrollableView(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        content = {
                            Column(

                                modifier = Modifier.padding(horizontal = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)

                            ) {



                                tmp.forEach { item ->

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = false,
                                        onClick = {
                                            openProviderDetailDialog.value = true
                                            providerId.value = item.providerId
                                        },
                                        role = Role.Button
                                    )
                            ) {


                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.spacedBy(2.dp)

                                ) {


                                    Text(
                                        text = item.name,
                                        style = TextStyle(
                                            color = getTextColor(),
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily.SansSerif,
                                        ),
                                        modifier = Modifier.padding(
                                            start = 2.dp,
                                            end = 6.dp
                                        )
                                    )
                                    Text(
                                        text = getProviderDesc(item),
                                        style = TextStyle(
                                            color = getTextColor(),
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily.SansSerif,
                                        ),
                                        modifier = Modifier.padding(
                                            start = 2.dp,
                                            end = 6.dp
                                        )
                                    )



                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.End,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Spacer(modifier = Modifier.height(10.dp))
                                    }
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

                    }
                        })

                }

            },
            confirmButton = {

            }, dismissButton = {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        onClick = {
                            openProviderSearchDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            style = TextStyle(
                                fontSize = 13.sp,
                            )
                        )
                    }


                }

            }
        )
    }



}


