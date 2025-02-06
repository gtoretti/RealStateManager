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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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


val streetAddress = mutableStateOf("")
val state = mutableStateOf("")
val city = mutableStateOf("")
val district = mutableStateOf("")
val number = mutableStateOf("")
val complement = mutableStateOf("")
val zipCode = mutableStateOf("")
val toastThis = mutableStateOf("")
val adresses = mutableStateListOf(Address("","","","","",  "",""))
val loadProperty = mutableStateOf("true")

@Composable
fun PropertyCreateScreen(
    openPropertyCreateDialog: MutableState<Boolean>,
    propertyViewModel: PropertyViewModel,
    context: Context,
    property: Property
) {

    if (property.propertyId!=0L && loadProperty.value.equals("true")){
        streetAddress.value = property.streetAddress
        state.value = property.state
        city.value = property.city
        district.value = property.district
        number.value = property.number
        complement.value = property.complement
        zipCode.value = property.zipCode
        loadProperty.value="false"
    }

    if (toastThis.value.isNotEmpty()){
        showToast(toastThis.value.toString(),context)
        toastThis.value = ""
    }

    var openCEPListDialog = remember { mutableStateOf(false) }
    if (adresses.size==1 && adresses.get(0).cep.isEmpty()) {
        adresses.removeAll(adresses.toList())
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
                var text = "Adicionar imóvel:"
                if (property.propertyId!=0L ){
                    text = "Alterar Endereço do Imóvel:"
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
                            if (zipCode.value.isEmpty()){
                                toastThis.value= "Por favor, informe o CEP."
                            }else{
                                fillAddressByCEP()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Preencher endereço por CEP",
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
                    )

                    Button(
                        onClick = {

                            if (streetAddress.value.isEmpty()){
                                toastThis.value= "Por favor, informe o logradouro."
                            }else
                                if (city.value.isEmpty()){
                                    toastThis.value= "Por favor, informe a cidade."
                                }else
                                if (state.value.isEmpty()){
                                    toastThis.value= "Por favor, informe o UF."
                                }else
                            {
                                fillCEPByAddress(openCEPListDialog)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Procurar CEP por endereço",
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
                            openPropertyCreateDialog.value = false
                            streetAddress.value = ""
                            state.value = ""
                            city.value = ""
                            district.value = ""
                            number.value = ""
                            complement.value = ""
                            zipCode.value = ""
                            loadProperty.value="true"
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
                            if (zipCode.value.isEmpty() || zipCode.value.isBlank()){
                                showToast("Por favor, informe o CEP do imóvel.",context)
                            }else
                                if (streetAddress.value.isEmpty() || streetAddress.value.isBlank()){
                                    showToast("Por favor, informe o logradouro do imóvel.",context)
                                }else
                                    if (district.value.isEmpty() || district.value.isBlank()){
                                        showToast("Por favor, informe o bairro do imóvel.",context)
                                    }else
                                        if (city.value.isEmpty() || city.value.isBlank()){
                                            showToast("Por favor, informe a cidade do imóvel.",context)
                                        }else
                                            if (state.value.isEmpty() || state.value.isBlank()){
                                                showToast("Por favor, informe o UF do imóvel.",context)
                                            }else
                                            {
                                                try {
                                                    propertyViewModel.saveProperty(
                                                        Property(
                                                            propertyId = property.propertyId,
                                                            streetAddress = streetAddress.value,
                                                            state = state.value,
                                                            city = city.value,
                                                            district = district.value,
                                                            number = number.value,
                                                            complement = complement.value,
                                                            zipCode = zipCode.value,
                                                            rentalMonthlyPrice = property.rentalMonthlyPrice,
                                                            occupied = property.occupied,
                                                            cpflName = property.cpflName,
                                                            cpflCustomerId = property.cpflCustomerId,
                                                            cpflCurrentCPF = property.cpflCurrentCPF,
                                                            sanasaName = property.sanasaName,
                                                            sanasaCustomerId = property.sanasaCustomerId,
                                                            sanasaCurrentCPF = property.sanasaCurrentCPF,
                                                            iptuCartographicCode = property.iptuCartographicCode,
                                                            realEstateRegistration = property.realEstateRegistration,
                                                            totalMunicipalTaxes = property.totalMunicipalTaxes,

                                                            urlGDriveFolder = property.urlGDriveFolder,
                                                            deleted = 0,

                                                            contractManagerName= property.contractManagerName,
                                                            contractManagerUrl = property.contractManagerUrl,
                                                            contractManagerPhoneNumber= property.contractManagerPhoneNumber,
                                                            contractManagerEmail= property.contractManagerEmail,

                                                            contractStartDate= property.contractStartDate,
                                                            contractEndedDate= property.contractEndedDate,
                                                            contractMonths= property.contractMonths,
                                                            contractDays = property.contractDays,
                                                            contractMonthsDaysDescr = property.contractMonthsDaysDescr,
                                                            contractValueAdjustmentIndexName= property.contractValueAdjustmentIndexName,
                                                            contractMonthlyBillingValue= property.contractMonthlyBillingValue,
                                                            contractRenterName= property.contractRenterName,
                                                            contractRenterCPF= property.contractRenterCPF,
                                                            contractRenterPhone= property.contractRenterPhone,
                                                            contractRenterEmail= property.contractRenterEmail,
                                                            contractGuarantorName= property.contractGuarantorName,
                                                            contractGuarantorCPF= property.contractGuarantorCPF,
                                                            contractGuarantorPhone= property.contractGuarantorPhone,
                                                            contractGuarantorEmail= property.contractGuarantorEmail,
                                                            contractPaymentDate= property.contractPaymentDate,
                                                            contractFinePerDelayedDay = property.contractFinePerDelayedDay

                                                        )
                                                    )
                                                    openPropertyCreateDialog.value = false
                                                    streetAddress.value = ""
                                                    state.value = ""
                                                    city.value = ""
                                                    district.value = ""
                                                    number.value = ""
                                                    complement.value = ""
                                                    zipCode.value = ""
                                                    loadProperty.value="true"
                                                    if (property.propertyId==0L){
                                                        showToast("Imóvel adicionado com sucesso!",context)
                                                    }else{
                                                        showToast("Endereço alterado com sucesso!",context)
                                                    }

                                                } catch (ex: Exception) {

                                                }
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


    if (openCEPListDialog.value) {

        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openCEPListDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(750.dp),

            title = {
                Text(
                    text = "CEPs encontrados:",
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
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .height(700.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    DrawScrollableView(
                        modifier = Modifier
                            .fillMaxWidth().padding(horizontal = 3.dp)
                            .fillMaxHeight(),
                        content = {
                            Column {

                                adresses.forEach { address ->
                                    Column(
                                        modifier = Modifier.padding(horizontal = 5.dp),
                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.spacedBy(2.dp)

                                    ) {
                                        var logradouro = address.logradouro
                                        if (address.complemento.isNotEmpty())
                                            logradouro = logradouro + " - " + address.complemento
                                        Text(
                                            text = address.cep + ": ", style = TextStyle(

                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.SansSerif,
                                                )
                                            )

                                        Text(
                                            text =logradouro
                                        )
                                        if (address.unidade.isNotEmpty()){
                                            Text(
                                                text = address.unidade
                                            )
                                        }

                                        Text(
                                            text = address.bairro + " - " + address.localidade + " - " + address.uf
                                        )

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.End,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Button(
                                                onClick = {

                                                    streetAddress.value = address.logradouro
                                                    state.value = address.uf
                                                    city.value = address.localidade
                                                    district.value = address.bairro
                                                    zipCode.value = address.cep

                                                    openCEPListDialog.value = false
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = getButtonColor()
                                                ),modifier = Modifier.height(30.dp)
                                            ) {
                                                Text(
                                                    text = "Selecionar",
                                                    style = TextStyle(
                                                        fontSize = 12.sp,
                                                    )
                                                )
                                            }
                                        }


                                        HorizontalDivider(thickness = 2.dp)

                                    }

                                }
                            }
                        }
                    )
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
                            openCEPListDialog.value = false
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


            }
        )

    }
}

fun fillAddressByCEP() {

    toastThis.value = "Pesquisando CEP. Por favor aguarde."

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
                    toastThis.value = "Endereço não encontrado."
                }else {
                    streetAddress.value = jsonObj.get("logradouro").toString()
                    state.value = jsonObj.get("uf").toString()
                    city.value = jsonObj.get("localidade").toString()
                    district.value = jsonObj.get("bairro").toString()
                    zipCode.value = jsonObj.get("cep").toString()
                    toastThis.value = "CEP encontrado com sucesso."
                }
            }catch (e: Exception){
                e.message?.let { Log.w("fillAddressByCEP", it) }
                toastThis.value = "O site dos Correios está indisponível. Você pode salvar as informações e tentar mais tarde."
            }
        }
    }
    )
}

fun fillCEPByAddress(openCEPListDialog: MutableState<Boolean>) {

    toastThis.value = "Pesquisando CEP. Por favor aguarde."

    var url = "https://viacep.com.br/ws/"+
            state.value + "/" +
            city.value + "/" +
            streetAddress.value.trimStart().trimEnd().replace(" ","+") + "/" +
            "json/"

    var request = Request.Builder()
        .url(url)
        .build()

    val client = OkHttpClient()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {}
        override fun onResponse(call: Call, response: Response) {

            try {

                val json = response.body?.string()
                val typeToken = object : TypeToken<List<Address>>() {}.type

                val newAdresses = Gson().fromJson<List<Address>>(json, typeToken)
                adresses.removeAll(adresses.toList())
                newAdresses.forEach { attend ->
                    adresses.add(attend)
                }

                if (adresses.isEmpty()){
                    toastThis.value = "CEP não encontrado."
                }else
                if (adresses.size==1) {
                    streetAddress.value = adresses.get(0).logradouro
                    state.value = adresses.get(0).uf
                    city.value = adresses.get(0).localidade
                    district.value = adresses.get(0).bairro
                    zipCode.value = adresses.get(0).cep
                    toastThis.value = "CEP encontrado com sucesso."
                }else{
                    openCEPListDialog.value = true
                }
            }catch (e: Exception){
                e.message?.let { Log.w("fillAddressByCEP", it) }
                toastThis.value = "O site dos Correios está indisponível. Você pode salvar as informações e tentar mais tarde."
            }
        }
    }
    )
}

//https://cdn.apicep.com/file/apicep/13084-778.json
//https://viacep.com.br/ws/SP/Campinas/Tranquillo+Prosperi/json/
//https://viacep.com.br/ws/13148218/json/

data class Address(
    var cep: String,
    var logradouro: String,
    var bairro: String,
    var localidade: String,
    var uf: String,
    var complemento: String,
    var unidade: String,
 ) {
}

