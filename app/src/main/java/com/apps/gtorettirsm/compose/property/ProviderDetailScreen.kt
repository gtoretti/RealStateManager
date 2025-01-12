/*
 */

package com.apps.gtorettirsm.compose.property

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import com.apps.gtorettirsm.compose.utils.DrawScrollableView
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.Provider
import com.apps.gtorettirsm.viewmodels.ProviderViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

import java.io.IOException
import java.text.SimpleDateFormat


@Composable
fun ProviderDetailScreen(
    openProviderDetailDialog: MutableState<Boolean>,
    providerViewModel: ProviderViewModel,
    context: Context,
    provider: Provider
) {

    var name by remember { mutableStateOf("") }
    var cpfCnpj by remember { mutableStateOf("") }
    var pix by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var servicesAdministration by remember { mutableIntStateOf(0) }
    var servicesHydraulic by remember { mutableIntStateOf(0) }
    var servicesBrickwork by remember { mutableIntStateOf(0) }
    var servicesElectric by remember { mutableIntStateOf(0) }
    var servicesArchitecture by remember { mutableIntStateOf(0) }
    var servicesInsurer by remember { mutableIntStateOf(0) }
    var servicesAutomation by remember { mutableIntStateOf(0) }
    var servicesFireBrigade by remember { mutableIntStateOf(0) }
    var servicesNotary by remember { mutableIntStateOf(0) }
    var servicesPlasterer by remember { mutableIntStateOf(0) }
    var servicesElectricFence by remember { mutableIntStateOf(0) }
    var servicesAluminumFrames by remember { mutableIntStateOf(0) }
    var servicesAirConditioningMaintenance by remember { mutableIntStateOf(0) }
    var servicesRoofer by remember { mutableIntStateOf(0) }
    var servicesElevatorMaintenance by remember { mutableIntStateOf(0) }

    var servicesElectronicIntercom by remember { mutableIntStateOf(0) }
    var servicesGardening by remember { mutableIntStateOf(0) }
    var servicesPoolMaintenance by remember { mutableIntStateOf(0) }
    var servicesPlaygroundMaintenance by remember { mutableIntStateOf(0) }
    var servicesElectronicGate by remember { mutableIntStateOf(0) }
    var servicesCleaning by remember { mutableIntStateOf(0) }
    var servicesPoolCleaning by remember { mutableIntStateOf(0) }
    var servicesLandscaping by remember { mutableIntStateOf(0) }
    var servicesPainting by remember { mutableIntStateOf(0) }
    var servicesSteelGatesRailings by remember { mutableIntStateOf(0) }
    var servicesPropertySecurity by remember { mutableIntStateOf(0) }



    if (openProviderDetailDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openProviderDetailDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(750.dp),

            title = {
                var text = "Adicionar Prestador:"
                if (provider.providerId!=0L ){
                    text = "Alterar Prestador:"
                }
                Text(
                    text = text,
                    style = TextStyle(
                        color = getTextColor(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            },
            text = {

                DrawScrollableView(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    content = {
                Column(

                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)

                ) {



                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
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
                                    color = getTextColor(),fontSize = 12.sp
                                )
                            )
                        },
                        placeholder = {Text("")},

                    )

                    OutlinedTextField(
                        value = cpfCnpj,
                        onValueChange = {
                            cpfCnpj = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),
                        label = {
                            Text(
                                text = "CPF/CNPJ:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp
                                )
                            )
                        },
                        placeholder = {Text("")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    OutlinedTextField(
                        value = pix,
                        onValueChange = {
                            pix = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),
                        label = {
                            Text(
                                text = "Chave Pix:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp
                                )
                            )
                        },
                        placeholder = {Text("")},

                    )


                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = {
                            phoneNumber = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),
                        label = {
                            Text(
                                text = "Telefone:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp
                                )
                            )
                        },
                        placeholder = {Text("")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),
                        label = {
                            Text(
                                text = "E-mail:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp
                                )
                            )
                        },
                        placeholder = {Text("")},

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
                            .fillMaxHeight(),
                        content = {
                            Column {


                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .selectable(
                                                selected = servicesAdministration == 1,
                                                onClick = {
                                                    if (servicesAdministration == 0) servicesAdministration=1
                                                    else servicesAdministration=0
                                                          },
                                                role = Role.Checkbox
                                            )
                                    ) {
                                        Checkbox(checked = (servicesAdministration == 1),
                                            onCheckedChange = {
                                                if (it) servicesAdministration=1
                                                else servicesAdministration=0
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
                                            selected = servicesBrickwork == 1,
                                            onClick = {
                                                if (servicesBrickwork == 0) servicesBrickwork=1
                                                else servicesBrickwork=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesBrickwork == 1),
                                        onCheckedChange = {
                                            if (it) servicesBrickwork=1
                                            else servicesBrickwork=0
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
                                            selected = servicesArchitecture == 1,
                                            onClick = {
                                                if (servicesArchitecture == 0) servicesArchitecture=1
                                                else servicesArchitecture=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesArchitecture == 1),
                                        onCheckedChange = {
                                            if (it) servicesArchitecture=1
                                            else servicesArchitecture=0
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
                                            selected = servicesInsurer == 1,
                                            onClick = {
                                                if (servicesInsurer == 0) servicesInsurer=1
                                                else servicesInsurer=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesInsurer == 1),
                                        onCheckedChange = {
                                            if (it) servicesInsurer=1
                                            else servicesInsurer=0
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
                                            selected = servicesAutomation == 1,
                                            onClick = {
                                                if (servicesAutomation == 0) servicesAutomation=1
                                                else servicesAutomation=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesAutomation == 1),
                                        onCheckedChange = {
                                            if (it) servicesAutomation=1
                                            else servicesAutomation=0
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
                                            selected = servicesFireBrigade == 1,
                                            onClick = {
                                                if (servicesFireBrigade == 0) servicesFireBrigade=1
                                                else servicesFireBrigade=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesFireBrigade == 1),
                                        onCheckedChange = {
                                            if (it) servicesFireBrigade=1
                                            else servicesFireBrigade=0
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
                                            selected = servicesNotary == 1,
                                            onClick = {
                                                if (servicesNotary == 0) servicesNotary=1
                                                else servicesNotary=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesNotary == 1),
                                        onCheckedChange = {
                                            if (it) servicesNotary=1
                                            else servicesNotary=0
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
                                            selected = servicesAluminumFrames == 1,
                                            onClick = {
                                                if (servicesAluminumFrames == 0) servicesAluminumFrames=1
                                                else servicesAluminumFrames=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesAluminumFrames == 1),
                                        onCheckedChange = {
                                            if (it) servicesAluminumFrames=1
                                            else servicesAluminumFrames=0
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
                                            selected = servicesPlasterer == 1,
                                            onClick = {
                                                if (servicesPlasterer == 0) servicesPlasterer=1
                                                else servicesPlasterer=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesPlasterer == 1),
                                        onCheckedChange = {
                                            if (it) servicesPlasterer=1
                                            else servicesPlasterer=0
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
                                            selected = servicesElectric == 1,
                                            onClick = {
                                                if (servicesElectric == 0) servicesElectric=1
                                                else servicesElectric=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesElectric == 1),
                                        onCheckedChange = {
                                            if (it) servicesElectric=1
                                            else servicesElectric=0
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
                                            selected = servicesHydraulic == 1,
                                            onClick = {
                                                if (servicesHydraulic == 0) servicesHydraulic=1
                                                else servicesHydraulic=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesHydraulic == 1),
                                        onCheckedChange = {
                                            if (it) servicesHydraulic=1
                                            else servicesHydraulic=0
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
                                            selected = servicesCleaning == 1,
                                            onClick = {
                                                if (servicesCleaning == 0) servicesCleaning=1
                                                else servicesCleaning=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesCleaning == 1),
                                        onCheckedChange = {
                                            if (it) servicesCleaning=1
                                            else servicesCleaning=0
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
                                            selected = servicesPoolCleaning == 1,
                                            onClick = {
                                                if (servicesPoolCleaning == 0) servicesPoolCleaning=1
                                                else servicesPoolCleaning=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesPoolCleaning == 1),
                                        onCheckedChange = {
                                            if (it) servicesPoolCleaning=1
                                            else servicesPoolCleaning=0
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
                                            selected = servicesAirConditioningMaintenance == 1,
                                            onClick = {
                                                if (servicesAirConditioningMaintenance == 0) servicesAirConditioningMaintenance=1
                                                else servicesAirConditioningMaintenance=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesAirConditioningMaintenance == 1),
                                        onCheckedChange = {
                                            if (it) servicesAirConditioningMaintenance=1
                                            else servicesAirConditioningMaintenance=0
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
                                            selected = servicesRoofer == 1,
                                            onClick = {
                                                if (servicesRoofer == 0) servicesRoofer=1
                                                else servicesRoofer=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesRoofer == 1),
                                        onCheckedChange = {
                                            if (it) servicesRoofer=1
                                            else servicesRoofer=0
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
                                            selected = servicesElectricFence == 1,
                                            onClick = {
                                                if (servicesElectricFence == 0) servicesElectricFence=1
                                                else servicesElectricFence=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesElectricFence == 1),
                                        onCheckedChange = {
                                            if (it) servicesElectricFence=1
                                            else servicesElectricFence=0
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
                                            selected = servicesElevatorMaintenance == 1,
                                            onClick = {
                                                if (servicesElevatorMaintenance == 0) servicesElevatorMaintenance=1
                                                else servicesElevatorMaintenance=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesElevatorMaintenance == 1),
                                        onCheckedChange = {
                                            if (it) servicesElevatorMaintenance=1
                                            else servicesElevatorMaintenance=0
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
                                            selected = servicesElectronicIntercom == 1,
                                            onClick = {
                                                if (servicesElectronicIntercom == 0) servicesElectronicIntercom=1
                                                else servicesElectronicIntercom=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesElectronicIntercom == 1),
                                        onCheckedChange = {
                                            if (it) servicesElectronicIntercom=1
                                            else servicesElectronicIntercom=0
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
                                            selected = servicesGardening == 1,
                                            onClick = {
                                                if (servicesGardening == 0) servicesGardening=1
                                                else servicesGardening=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesGardening == 1),
                                        onCheckedChange = {
                                            if (it) servicesGardening=1
                                            else servicesGardening=0
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
                                            selected = servicesPoolMaintenance == 1,
                                            onClick = {
                                                if (servicesPoolMaintenance == 0) servicesPoolMaintenance=1
                                                else servicesPoolMaintenance=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesPoolMaintenance == 1),
                                        onCheckedChange = {
                                            if (it) servicesPoolMaintenance=1
                                            else servicesPoolMaintenance=0
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
                                            selected = servicesPlaygroundMaintenance == 1,
                                            onClick = {
                                                if (servicesPlaygroundMaintenance == 0) servicesPlaygroundMaintenance=1
                                                else servicesPlaygroundMaintenance=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesPlaygroundMaintenance == 1),
                                        onCheckedChange = {
                                            if (it) servicesPlaygroundMaintenance=1
                                            else servicesPlaygroundMaintenance=0
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
                                            selected = servicesElectronicGate == 1,
                                            onClick = {
                                                if (servicesElectronicGate == 0) servicesElectronicGate=1
                                                else servicesElectronicGate=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesElectronicGate == 1),
                                        onCheckedChange = {
                                            if (it) servicesElectronicGate=1
                                            else servicesElectronicGate=0
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
                                            selected = servicesLandscaping == 1,
                                            onClick = {
                                                if (servicesLandscaping == 0) servicesLandscaping=1
                                                else servicesLandscaping=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesLandscaping == 1),
                                        onCheckedChange = {
                                            if (it) servicesLandscaping=1
                                            else servicesLandscaping=0
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
                                            selected = servicesPainting == 1,
                                            onClick = {
                                                if (servicesPainting == 0) servicesPainting=1
                                                else servicesPainting=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesPainting == 1),
                                        onCheckedChange = {
                                            if (it) servicesPainting=1
                                            else servicesPainting=0
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
                                            selected = servicesSteelGatesRailings == 1,
                                            onClick = {
                                                if (servicesSteelGatesRailings == 0) servicesSteelGatesRailings=1
                                                else servicesSteelGatesRailings=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesSteelGatesRailings == 1),
                                        onCheckedChange = {
                                            if (it) servicesSteelGatesRailings=1
                                            else servicesSteelGatesRailings=0
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
                                            selected = servicesPropertySecurity == 1,
                                            onClick = {
                                                if (servicesPropertySecurity == 0) servicesPropertySecurity=1
                                                else servicesPropertySecurity=0
                                            },
                                            role = Role.Checkbox
                                        )
                                ) {
                                    Checkbox(checked = (servicesPropertySecurity == 1),
                                        onCheckedChange = {
                                            if (it) servicesPropertySecurity=1
                                            else servicesPropertySecurity=0
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
                            openProviderDetailDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }


                    Button(
                        onClick = {

                            openProviderDetailDialog.value = false

                            if (provider.providerId==0L){
                                showToast("Prestador adicionado com sucesso!",context)
                            }else{
                                showToast("Prestador alterado com sucesso!",context)
                            }

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Salvar",
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                }

            }
        )
    }



}


