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
    var servicesElectric by remember { mutableIntStateOf(0) }
    var servicesHydraulic by remember { mutableIntStateOf(0) }
    var servicesBrickwork by remember { mutableIntStateOf(0) }
    var servicesPainting by remember { mutableIntStateOf(0) }
    var servicesArchitecture by remember { mutableIntStateOf(0) }
    var servicesNotary by remember { mutableIntStateOf(0) }
    var servicesElectronicGate by remember { mutableIntStateOf(0) }
    var servicesElectricFence by remember { mutableIntStateOf(0) }
    var servicesElectronicIntercom by remember { mutableIntStateOf(0) }
    var servicesRoofer by remember { mutableIntStateOf(0) }
    var servicesCleaning by remember { mutableIntStateOf(0) }
    var servicesAluminumFrames by remember { mutableIntStateOf(0) }
    var servicesSteelGatesRailings by remember { mutableIntStateOf(0) }
    var servicesPoolMaintenance by remember { mutableIntStateOf(0) }
    var servicesPoolCleaning by remember { mutableIntStateOf(0) }

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
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
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
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
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
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
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
                                            text = "Eletricista",
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


