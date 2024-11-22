/*
 */

package com.apps.gtorettirsm.compose.property

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.screenToDouble
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

import java.io.IOException
import java.util.Calendar


val streetAddress = mutableStateOf("")
val state = mutableStateOf("")
val city = mutableStateOf("")
val district = mutableStateOf("")
val number = mutableStateOf("")
val complement = mutableStateOf("")
val zipCode = mutableStateOf("")
val toastThis = mutableStateOf("")

@Composable
fun PropertyCreateScreen(
    openPropertyCreateDialog: MutableState<Boolean>,
    patientViewModel: PropertyViewModel,
    context: Context
) {

    if (toastThis.value.isNotEmpty()){
        showToast(toastThis.value.toString(),context)
        toastThis.value = ""
    }

    if (openPropertyCreateDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openPropertyCreateDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(750.dp),

            title = {
                Text(
                    text = "Adicionar imóvel:",
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
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)

                ) {



                    OutlinedTextField(
                        value = zipCode.value,
                        onValueChange = {
                            zipCode.value = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),placeholder = {Text("00000-000")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = {
                            Text(
                                text = "CEP:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp,
                                )
                            )
                        }
                    )

                    Button(
                        onClick = {
                            fillAddressByCEP()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Preencher endereço usando CEP",
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    OutlinedTextField(
                        value = streetAddress.value,
                        onValueChange = {
                            streetAddress.value = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),placeholder = {Text("Nome do Logradouro")},
                        label = {
                            Text(
                                text = "Logradouro:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp,
                                )
                            )
                        }
                    )

                    OutlinedTextField(
                        value = district.value,
                        onValueChange = {
                            district.value = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),placeholder = {Text("Nome do Bairro")},
                        label = {
                            Text(
                                text = "Bairro:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp,
                                )
                            )
                        }
                    )

                    OutlinedTextField(
                        value = city.value,
                        onValueChange = {
                            city.value = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),placeholder = {Text("Nome da Cidade")},
                        label = {
                            Text(
                                text = "Cidade:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp,
                                )
                            )
                        }
                    )

                    OutlinedTextField(
                        value = state.value,
                        onValueChange = {
                            state.value = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),
                        label = {
                            Text(
                                text = "UF:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp
                                )
                            )
                        },
                        placeholder = {Text("UF (exemplo: SP).")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    Button(
                        onClick = {
                            fillCEPByAddress()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Procurar CEP usando endereço",
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    OutlinedTextField(
                        value = number.value,
                        onValueChange = {
                            number.value = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),
                        label = {
                            Text(
                                text = "Número:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp
                                )
                            )
                        },
                        placeholder = {Text("Número.")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    OutlinedTextField(
                        value = complement.value,
                        onValueChange = {
                            complement.value = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),
                        label = {
                            Text(
                                text = "Complemento:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp
                                )
                            )
                        },
                        placeholder = {Text("Complemento.")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        )
                    )


                }

            },
            confirmButton = {
                Button(
                    onClick = {
                        //if (address.isEmpty() || address.isBlank()){
                        //    showToast("Por favor, informe o endereço do imóvel.",context)
                        //}else
                        //    if (rentalMontlyPrice.isEmpty() || rentalMontlyPrice.isBlank()){
                        //    showToast("Por favor, informe o valor mensal do aluguel.",context)
                        //}else
                        try {
                            //patientViewModel.saveProperty(
                            //    Property(
                            //        propertyId = 0,
                            //        streetAddress = address,
                            //        rentalMontlyPrice = rentalMontlyPrice.screenToDouble(),
                            //        deleted = 0
                            //    )
                            //)
                            openPropertyCreateDialog.value = false
                            showToast("Informações salvas com sucesso!",context)

                        } catch (ex: Exception) {
                            //rentalMontlyPrice = ""
                            //showToast("Por favor, informe o valor do atendimento.",context)
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
            }, dismissButton = {
                Button(
                    onClick = {
                        openPropertyCreateDialog.value = false
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
            }
        )
    }
}


fun fillAddressByCEP() {

    var request = Request.Builder()
        .url("https://viacep.com.br/ws/"+ zipCode.value.trimStart()
            .trimEnd().replace("-","").replace(" ","")
            .replace(",","").replace(".","") +"/json/")
        .build()

    val client = OkHttpClient()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {}
        override fun onResponse(call: Call, response: Response) {

            try {
                val jsonObj = JSONObject(response.body?.string())
                if (jsonObj.has("erro")){
                    streetAddress.value = ""
                    state.value = ""
                    city.value = ""
                    district.value = ""
                    zipCode.value = ""
                    toastThis.value = "CEP não encontrado."
                }else {
                    streetAddress.value = jsonObj.get("logradouro").toString()
                    state.value = jsonObj.get("uf").toString()
                    city.value = jsonObj.get("localidade").toString()
                    district.value = jsonObj.get("bairro").toString()
                    zipCode.value = jsonObj.get("cep").toString()
                    toastThis.value = "CEP encontrado com sucesso."
                }
            }catch (e: Exception){
                streetAddress.value = ""
                state.value = ""
                city.value = ""
                district.value = ""
                zipCode.value = ""
                toastThis.value = "CEP não encontrado."
            }
        }
    }
    )
}

fun fillCEPByAddress() {

    var request = Request.Builder()
        .url("https://viacep.com.br/ws/SP/Paulinia/Alexandre+Cazelatto/json/")
        .build()

    val client = OkHttpClient()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {}
        override fun onResponse(call: Call, response: Response) {

            try {

                val json = response.body?.string()
                val typeToken = object : TypeToken<List<Address>>() {}.type
                val adresses = Gson().fromJson<List<Address>>(json, typeToken)

                println("adresses.size "+adresses.size)
println(adresses)

                if (adresses.isEmpty()){
                    streetAddress.value = ""
                    state.value = ""
                    city.value = ""
                    district.value = ""
                    zipCode.value = ""
                    toastThis.value = "Endereço não encontrado."
                }else
                if (adresses.size==1) {
                    streetAddress.value = adresses.get(0).logradouro
                    state.value = adresses.get(0).uf
                    city.value = adresses.get(0).localidade
                    district.value = adresses.get(0).bairro
                    zipCode.value = adresses.get(0).cep
                    toastThis.value = "Endereço encontrado com sucesso."
                }else{
                    toastThis.value = "Vários endereços encontrados."
                }
            }catch (e: Exception){
                e.printStackTrace()
                streetAddress.value = ""
                state.value = ""
                city.value = ""
                district.value = ""
                zipCode.value = ""
                toastThis.value = "Endereço não encontrado."
            }
        }
    }
    )
}

data class Address(
    var cep: String,
    var logradouro: String,
    var bairro: String,
    var localidade: String,
    var uf: String,
 ) {
}