/*
 */

package com.apps.gtorettirsm.compose.property

import android.content.Context
import android.content.ContextWrapper
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
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import kotlinx.coroutines.flow.Flow
import java.util.Date

import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.core.content.FileProvider
import com.apps.gtorettirsm.R
import com.apps.gtorettirsm.compose.utils.daysBetween
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getPhoneColor
import com.apps.gtorettirsm.compose.utils.screenToDouble
import com.apps.gtorettirsm.viewmodels.ReceivingViewModel
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Calendar

@Composable
fun PropertiesScreen(
) {
    var propertyViewModel: PropertyViewModel = hiltViewModel()
    var receivingViewModel: ReceivingViewModel = hiltViewModel()

    val properties = propertyViewModel.properties
    PropertiesScreen(
        propertiesFlow = properties,
        propertyViewModel = propertyViewModel,
        receivingViewModel = receivingViewModel
    )
}

@Composable
fun PropertiesScreen(
    propertiesFlow: Flow<List<Property>>,
    propertyViewModel: PropertyViewModel,
    receivingViewModel: ReceivingViewModel
) {

    var openPropertyCreateDialog = remember { mutableStateOf(false) }
    var openPropertyDetailDialog = remember { mutableStateOf(false) }
    var propertyId = remember { mutableLongStateOf(0L) }

    val fmt = SimpleDateFormat("dd/MM/yyyy")

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
                    openHelp(context)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = getButtonColor()
                ), modifier = Modifier.height(33.dp)
            ) {
                Text(
                    text = "Ajuda",
                    style = TextStyle(
                        fontSize = 14.sp,
                    )
                )
            }


            Button(
                onClick = {
                    openPropertyCreateDialog.value = true
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


        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
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

                    var streetAddress = item.streetAddress + ", " + item.number
                    if (item.complement.isNotEmpty())
                        streetAddress = streetAddress + " - " + item.complement


                    Column() {


                        Row() {

                            TextButton(
                                modifier = Modifier.padding(5.dp),
                                onClick =
                                {
                                    openMapWithAddress(
                                        item.streetAddress + ", " + item.number + ", " + item.district + ", " + item.city + ", " + item.state,
                                        context
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.location_on_24px),
                                    contentDescription = "Mapa",
                                    tint = getTextColor(),
                                    modifier = Modifier
                                        .padding(end = 2.dp)
                                        .size(16.dp)
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.spacedBy(2.dp)

                            ) {


                                Text(
                                    text = streetAddress, style = TextStyle(
                                        color = getTextColor(),
                                        fontSize = 14.sp,
                                    )
                                )

                                Text(
                                    text = item.district, style = TextStyle(
                                        color = getTextColor(),
                                        fontSize = 14.sp,
                                    )
                                )
                                Text(
                                    text = item.city + " - " + item.state, style = TextStyle(
                                        color = getTextColor(),
                                        fontSize = 14.sp,
                                    )
                                )


                                Text(
                                    text = "CEP: " + item.zipCode, style = TextStyle(
                                        color = getTextColor(),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                )

                                if (item.contractEndedDate.time>0){

                                        var occupiedColor = getTextColor()
                                        if (item.contractMonthlyBillingValue>0.0){
                                            occupiedColor = getPhoneColor()
                                        }

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Spacer(modifier = Modifier.height(3.dp))
                                    }

                                        Row(verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                                            modifier = Modifier.fillMaxWidth()){

                                            Row(){
                                                Icon(
                                                    imageVector = ImageVector.vectorResource(R.drawable.sensor_occupied_24px),
                                                    contentDescription = "Imóvel Alugado",
                                                    tint = occupiedColor,
                                                    modifier = Modifier
                                                        .padding(horizontal = 5.dp)
                                                        .size(24.dp)
                                                )
                                                var occupiedDateColor = getTextColor()
                                                var warningDate= Calendar.getInstance()
                                                if (daysBetween(warningDate.time,item.contractEndedDate) < 30){
                                                    occupiedDateColor = getRedTextColor()
                                                }
                                                Text(
                                                    text = fmt.format(item.contractEndedDate), style = TextStyle(
                                                        color = occupiedDateColor,
                                                        fontSize = 14.sp,
                                                    )
                                                )
                                            }

                                            Row(){
                                                var hasDebit = hasDebit(item,receivingViewModel)
                                                if (hasDebit.time!=0L) {
                                                    Icon(
                                                        imageVector = ImageVector.vectorResource(R.drawable.warning_24px),
                                                        contentDescription = "Débito Pendente",
                                                        tint = Color(0xFFD50000),
                                                        modifier = Modifier
                                                            .padding(end = 5.dp)
                                                            .size(22.dp)
                                                    )
                                                    Text(
                                                        text = fmt.format(hasDebit.time), style = TextStyle(
                                                            color = Color(0xFFD50000),
                                                            fontSize = 14.sp,
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Spacer(modifier = Modifier.height(3.dp))
                                    }
                                }
                            }
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
                    propertyId.value,
                    context
                )
            }
        }
        when {
            openPropertyCreateDialog.value -> {
                PropertyCreateScreen(openPropertyCreateDialog, propertyViewModel, context, Property(0L,"", "", "", "", "", "", "", 0.0,0,"", "", "", "","","", "", "", 0.0, "" , 0,  "",  "",  Date(0), Date(0), 0,0, "","", 0.0, "", "", "", "", "",  "", 0,0.0))
            }
        }
    }
}


fun openMapWithAddress(address: String, context: Context) {
    val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
}

fun openHelp(context: Context){

    val contextWrapper = ContextWrapper(context)
    val documentDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    val file = File(documentDirectory, "Gestão_de_Aluguel_de_Imoveis_Manual_do_Usuario.pdf")
    val src = context.assets.open("Gestão_de_Aluguel_de_Imoveis_Manual_do_Usuario.pdf")

    src.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    val fileURI = FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".provider",
        file
    )
    val browserIntent = Intent(Intent.ACTION_VIEW, fileURI)
    browserIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    context.startActivity(browserIntent)
}