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

@Composable
fun ProviderSearchScreen(
    openProviderSearchDialog: MutableState<Boolean>,
    providerViewModel: ProviderViewModel,
    context: Context
) {
    val searchName = remember { mutableStateOf("") }
    var openProviderDetailDialog = remember { mutableStateOf(false) }

    if (openProviderSearchDialog.value) {
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
                                                        selected = servicesAdministration.value == 1,
                                                        onClick = {
                                                            if (servicesAdministration.value == 0) servicesAdministration.value =
                                                                1
                                                            else servicesAdministration.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesAdministration.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesAdministration.value = 1
                                                        else servicesAdministration.value = 0
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
                                                        selected = servicesBrickwork.value == 1,
                                                        onClick = {
                                                            if (servicesBrickwork.value == 0) servicesBrickwork.value =
                                                                1
                                                            else servicesBrickwork.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesBrickwork.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesBrickwork.value = 1
                                                        else servicesBrickwork.value = 0
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
                                                        selected = servicesArchitecture.value == 1,
                                                        onClick = {
                                                            if (servicesArchitecture.value == 0) servicesArchitecture.value =
                                                                1
                                                            else servicesArchitecture.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesArchitecture.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesArchitecture.value = 1
                                                        else servicesArchitecture.value = 0
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
                                                        selected = servicesInsurer.value == 1,
                                                        onClick = {
                                                            if (servicesInsurer.value == 0) servicesInsurer.value =
                                                                1
                                                            else servicesInsurer.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesInsurer.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesInsurer.value = 1
                                                        else servicesInsurer.value = 0
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
                                                        selected = servicesAutomation.value == 1,
                                                        onClick = {
                                                            if (servicesAutomation.value == 0) servicesAutomation.value =
                                                                1
                                                            else servicesAutomation.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesAutomation.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesAutomation.value = 1
                                                        else servicesAutomation.value = 0
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
                                                        selected = servicesFireBrigade.value == 1,
                                                        onClick = {
                                                            if (servicesFireBrigade.value == 0) servicesFireBrigade.value =
                                                                1
                                                            else servicesFireBrigade.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesFireBrigade.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesFireBrigade.value = 1
                                                        else servicesFireBrigade.value = 0
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
                                                        selected = servicesNotary.value == 1,
                                                        onClick = {
                                                            if (servicesNotary.value == 0) servicesNotary.value =
                                                                1
                                                            else servicesNotary.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesNotary.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesNotary.value = 1
                                                        else servicesNotary.value = 0
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
                                                        selected = servicesAluminumFrames.value == 1,
                                                        onClick = {
                                                            if (servicesAluminumFrames.value == 0) servicesAluminumFrames.value =
                                                                1
                                                            else servicesAluminumFrames.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesAluminumFrames.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesAluminumFrames.value = 1
                                                        else servicesAluminumFrames.value = 0
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
                                                        selected = servicesPlasterer.value == 1,
                                                        onClick = {
                                                            if (servicesPlasterer.value == 0) servicesPlasterer.value =
                                                                1
                                                            else servicesPlasterer.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesPlasterer.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesPlasterer.value = 1
                                                        else servicesPlasterer.value = 0
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
                                                        selected = servicesElectric.value == 1,
                                                        onClick = {
                                                            if (servicesElectric.value == 0) servicesElectric.value =
                                                                1
                                                            else servicesElectric.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesElectric.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesElectric.value = 1
                                                        else servicesElectric.value = 0
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
                                                        selected = servicesHydraulic.value == 1,
                                                        onClick = {
                                                            if (servicesHydraulic.value == 0) servicesHydraulic.value =
                                                                1
                                                            else servicesHydraulic.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesHydraulic.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesHydraulic.value = 1
                                                        else servicesHydraulic.value = 0
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
                                                        selected = servicesCleaning.value == 1,
                                                        onClick = {
                                                            if (servicesCleaning.value == 0) servicesCleaning.value =
                                                                1
                                                            else servicesCleaning.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesCleaning.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesCleaning.value = 1
                                                        else servicesCleaning.value = 0
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
                                                        selected = servicesPoolCleaning.value == 1,
                                                        onClick = {
                                                            if (servicesPoolCleaning.value == 0) servicesPoolCleaning.value =
                                                                1
                                                            else servicesPoolCleaning.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesPoolCleaning.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesPoolCleaning.value = 1
                                                        else servicesPoolCleaning.value = 0
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
                                                        selected = servicesAirConditioningMaintenance.value == 1,
                                                        onClick = {
                                                            if (servicesAirConditioningMaintenance.value == 0) servicesAirConditioningMaintenance.value =
                                                                1
                                                            else servicesAirConditioningMaintenance.value =
                                                                0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesAirConditioningMaintenance.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesAirConditioningMaintenance.value =
                                                            1
                                                        else servicesAirConditioningMaintenance.value =
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
                                                        selected = servicesCabinetsJoinery.value == 1,
                                                        onClick = {
                                                            if (servicesCabinetsJoinery.value == 0) servicesCabinetsJoinery.value =
                                                                1
                                                            else servicesCabinetsJoinery.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesCabinetsJoinery.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesCabinetsJoinery.value = 1
                                                        else servicesCabinetsJoinery.value = 0
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
                                                        selected = servicesShowerStalls.value == 1,
                                                        onClick = {
                                                            if (servicesShowerStalls.value == 0) servicesShowerStalls.value =
                                                                1
                                                            else servicesShowerStalls.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesShowerStalls.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesShowerStalls.value = 1
                                                        else servicesShowerStalls.value = 0
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
                                                        selected = servicesRoofer.value == 1,
                                                        onClick = {
                                                            if (servicesRoofer.value == 0) servicesRoofer.value =
                                                                1
                                                            else servicesRoofer.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesRoofer.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesRoofer.value = 1
                                                        else servicesRoofer.value = 0
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
                                                        selected = servicesElectricFence.value == 1,
                                                        onClick = {
                                                            if (servicesElectricFence.value == 0) servicesElectricFence.value =
                                                                1
                                                            else servicesElectricFence.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesElectricFence.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesElectricFence.value = 1
                                                        else servicesElectricFence.value = 0
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
                                                        selected = servicesSunshades.value == 1,
                                                        onClick = {
                                                            if (servicesSunshades.value == 0) servicesSunshades.value =
                                                                1
                                                            else servicesSunshades.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesSunshades.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesSunshades.value = 1
                                                        else servicesSunshades.value = 0
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
                                                        selected = servicesCurtains.value == 1,
                                                        onClick = {
                                                            if (servicesCurtains.value == 0) servicesCurtains.value =
                                                                1
                                                            else servicesCurtains.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesCurtains.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesCurtains.value = 1
                                                        else servicesCurtains.value = 0
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
                                                        selected = servicesElevatorMaintenance.value == 1,
                                                        onClick = {
                                                            if (servicesElevatorMaintenance.value == 0) servicesElevatorMaintenance.value =
                                                                1
                                                            else servicesElevatorMaintenance.value =
                                                                0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesElevatorMaintenance.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesElevatorMaintenance.value =
                                                            1
                                                        else servicesElevatorMaintenance.value = 0
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
                                                        selected = servicesElectronicIntercom.value == 1,
                                                        onClick = {
                                                            if (servicesElectronicIntercom.value == 0) servicesElectronicIntercom.value =
                                                                1
                                                            else servicesElectronicIntercom.value =
                                                                0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesElectronicIntercom.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesElectronicIntercom.value = 1
                                                        else servicesElectronicIntercom.value = 0
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
                                                        selected = servicesGardening.value == 1,
                                                        onClick = {
                                                            if (servicesGardening.value == 0) servicesGardening.value =
                                                                1
                                                            else servicesGardening.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesGardening.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesGardening.value = 1
                                                        else servicesGardening.value = 0
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
                                                        selected = servicesPoolMaintenance.value == 1,
                                                        onClick = {
                                                            if (servicesPoolMaintenance.value == 0) servicesPoolMaintenance.value =
                                                                1
                                                            else servicesPoolMaintenance.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesPoolMaintenance.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesPoolMaintenance.value = 1
                                                        else servicesPoolMaintenance.value = 0
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
                                                        selected = servicesPlaygroundMaintenance.value == 1,
                                                        onClick = {
                                                            if (servicesPlaygroundMaintenance.value == 0) servicesPlaygroundMaintenance.value =
                                                                1
                                                            else servicesPlaygroundMaintenance.value =
                                                                0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesPlaygroundMaintenance.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesPlaygroundMaintenance.value =
                                                            1
                                                        else servicesPlaygroundMaintenance.value = 0
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
                                                        selected = servicesElectronicGate.value == 1,
                                                        onClick = {
                                                            if (servicesElectronicGate.value == 0) servicesElectronicGate.value =
                                                                1
                                                            else servicesElectronicGate.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesElectronicGate.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesElectronicGate.value = 1
                                                        else servicesElectronicGate.value = 0
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
                                                        selected = servicesLandscaping.value == 1,
                                                        onClick = {
                                                            if (servicesLandscaping.value == 0) servicesLandscaping.value =
                                                                1
                                                            else servicesLandscaping.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesLandscaping.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesLandscaping.value = 1
                                                        else servicesLandscaping.value = 0
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
                                                        selected = servicesPainting.value == 1,
                                                        onClick = {
                                                            if (servicesPainting.value == 0) servicesPainting.value =
                                                                1
                                                            else servicesPainting.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesPainting.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesPainting.value = 1
                                                        else servicesPainting.value = 0
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
                                                        selected = servicesSteelGatesRailings.value == 1,
                                                        onClick = {
                                                            if (servicesSteelGatesRailings.value == 0) servicesSteelGatesRailings.value =
                                                                1
                                                            else servicesSteelGatesRailings.value =
                                                                0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesSteelGatesRailings.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesSteelGatesRailings.value = 1
                                                        else servicesSteelGatesRailings.value = 0
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
                                                        selected = servicesPropertySecurity.value == 1,
                                                        onClick = {
                                                            if (servicesPropertySecurity.value == 0) servicesPropertySecurity.value =
                                                                1
                                                            else servicesPropertySecurity.value = 0
                                                        },
                                                        role = Role.Checkbox
                                                    )
                                            ) {
                                                Checkbox(checked = (servicesPropertySecurity.value == 1),
                                                    onCheckedChange = {
                                                        if (it) servicesPropertySecurity.value = 1
                                                        else servicesPropertySecurity.value = 0
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

                                        when {
                                            openProviderSearchDialog.value -> {
                                                // ProviderDeleteDialog(
                                                //    openProviderDeleteDialog,
                                                //  openProviderDetailDialog,
                                                //    providerViewModel,
                                                //provider,
                                                //  context)
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



                                providerResult.value.forEach { item ->

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


