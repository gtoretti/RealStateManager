/*
 */

package com.apps.gtorettirsm.compose.property

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun PropertyGDriveInputDialog(
    openPropertyGDriveInputDialog: MutableState<Boolean>,
    propertyViewModel: PropertyViewModel,
    property: Property,
    context: Context
) {

    var gdriveUrl by remember { mutableStateOf("") }
    var loaded by remember { mutableStateOf("") }

    if (loaded.trim().isEmpty()){
        gdriveUrl = property.urlGDriveFolder
        loaded = "true"
    }

    if (openPropertyGDriveInputDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openPropertyGDriveInputDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(250.dp),

            title = {
                Text(
                    text = "URL Google Drive:",
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
                    verticalArrangement = Arrangement.spacedBy(2.dp)

                ) {

                    OutlinedTextField(
                        value = gdriveUrl,
                        onValueChange = {
                            gdriveUrl = it
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = getTextColor(),
                            fontWeight = FontWeight.Normal
                        ),placeholder = {Text("")},

                        label = {
                            Text(
                                text = "URL:",
                                style = TextStyle(
                                    color = getTextColor(),fontSize = 12.sp,
                                )
                            )
                        }
                    )


                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (gdriveUrl.trim().isEmpty()){
                            showToast("Por favor, informe a URL da pasta do imóvel!", context)
                        }else{
                            propertyViewModel.saveProperty(
                                Property(
                                    propertyId = property.propertyId,
                                    streetAddress = property.streetAddress,
                                    state = property.state,
                                    city = property.city,
                                    district = property.district,
                                    number = property.number,
                                    complement = property.complement,
                                    zipCode = property.zipCode,
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
                                    urlGDriveFolder = gdriveUrl,
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
                                ))

                            gdriveUrl = ""
                            openPropertyGDriveInputDialog.value = false
                            showToast("URL GDrive atualizada com sucesso!", context)
                        }
                    }, colors = ButtonDefaults.buttonColors(
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
                        openPropertyGDriveInputDialog.value = false
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

