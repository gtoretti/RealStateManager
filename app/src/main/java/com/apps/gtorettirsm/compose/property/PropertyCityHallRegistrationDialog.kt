/*
 */

package com.apps.gtorettirsm.compose.property


import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.gtorettirsm.compose.utils.DrawScrollableView
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.PropertyViewModel


@Composable
fun PropertyCityHallRegistrationDialog(
    openPropertyCityHallRegistrationDialog: MutableState<Boolean>,
    propertyViewModel: PropertyViewModel,
    context: Context,
    property: Property
) {

    var realEstateRegistration by remember { mutableStateOf("") }
    var iptuCartographicCode by remember { mutableStateOf("") }

    var loaded by remember { mutableStateOf(false) }
    if (!loaded) {
        realEstateRegistration = property.realEstateRegistration
        iptuCartographicCode = property.iptuCartographicCode
        loaded = true
    }

    if (openPropertyCityHallRegistrationDialog.value) {
        AlertDialog(shape = RoundedCornerShape(10.dp), onDismissRequest = {
            openPropertyCityHallRegistrationDialog.value = false
        }, modifier = Modifier
            .width(550.dp)
            .height(300.dp),

            title = {
                Text(
                    text = "Registro Municipal:", style = TextStyle(
                        color = getTextColor(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            }, text = {

                DrawScrollableView(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    content = {

                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {


                            OutlinedTextField(
                                value = realEstateRegistration,
                                onValueChange = {
                                    realEstateRegistration = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),

                                label = {
                                    Text(
                                        text = "Inscrição Imobiliária:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )

                            OutlinedTextField(
                                value = iptuCartographicCode,
                                onValueChange = {
                                    iptuCartographicCode = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),

                                label = {
                                    Text(
                                        text = "Código Cartográfico:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )


                        }

                    })

            }, confirmButton = {

            }, dismissButton = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        onClick = {
                            realEstateRegistration = ""
                            iptuCartographicCode = ""
                            openPropertyCityHallRegistrationDialog.value = false
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Cancelar", style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    Button(onClick = {

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
                                iptuCartographicCode = iptuCartographicCode,
                                realEstateRegistration = realEstateRegistration,
                                totalMunicipalTaxes = property.totalMunicipalTaxes,
                                urlGDriveFolder = property.urlGDriveFolder,
                                deleted = 0,
                                contractManagerName= property.contractManagerName,
                                contractManagerContactId = property.contractManagerContactId,
                                contractStartDate= property.contractStartDate,
                                contractEndedDate= property.contractEndedDate,
                                contractMonths= property.contractMonths,
                                contractDays = property.contractDays,
                                contractMonthsDaysDescr = property.contractMonthsDaysDescr,
                                contractValueAdjustmentIndexName= property.contractValueAdjustmentIndexName,
                                contractMonthlyBillingValue= property.contractMonthlyBillingValue,
                                contractRenterName= property.contractRenterName,
                                contractRenterCPF= property.contractRenterCPF,
                                contractRenterContactId = property.contractRenterContactId,
                                contractGuarantorName= property.contractGuarantorName,
                                contractGuarantorCPF= property.contractGuarantorCPF,
                                contractGuarantorContactId = property.contractGuarantorContactId,
                                contractPaymentDate= property.contractPaymentDate,
                                contractFinePerDelayedDay = property.contractFinePerDelayedDay
                            ))

                        realEstateRegistration = ""
                        iptuCartographicCode = ""
                        openPropertyCityHallRegistrationDialog.value = false
                        showToast("Registro Municipal atualizado com sucesso!",context)

                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Salvar", style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }
                }
            }
        )
    }
}

