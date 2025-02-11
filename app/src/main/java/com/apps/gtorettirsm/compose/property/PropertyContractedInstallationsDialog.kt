/*
 */

package com.apps.gtorettirsm.compose.property


import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
fun PropertyContractedInstallationsDialog(
    openPropertyContractedInstallationsDialog: MutableState<Boolean>,
    propertyViewModel: PropertyViewModel,
    context: Context,
    property: Property
) {

    var cpflCurrentCPF by remember { mutableStateOf("") }
    var cpflCustomerId by remember { mutableStateOf("") }
    var cpflName by remember { mutableStateOf("") }
    var sanasaCurrentCPF by remember { mutableStateOf("") }
    var sanasaCustomerId by remember { mutableStateOf("") }
    var sanasaName by remember { mutableStateOf("") }

    var loaded by remember { mutableStateOf(false) }
    if (!loaded) {
        cpflCurrentCPF = property.cpflCurrentCPF
        cpflCustomerId = property.cpflCustomerId
        cpflName = property.cpflName
        sanasaCurrentCPF = property.sanasaCurrentCPF
        sanasaCustomerId = property.sanasaCustomerId
        sanasaName = property.sanasaName
        loaded = true
    }

    if (openPropertyContractedInstallationsDialog.value) {
        AlertDialog(shape = RoundedCornerShape(10.dp), onDismissRequest = {
            openPropertyContractedInstallationsDialog.value = false
        }, modifier = Modifier
            .width(550.dp)
            .height(700.dp),

            title = {
                Text(
                    text = "Instalações Contratadas:", style = TextStyle(
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


                            Text(
                                text = "Energia Elétrica:", style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif,
                                )
                            )

                            OutlinedTextField(
                                value = cpflName,
                                onValueChange = {
                                    cpflName = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),

                                label = {
                                    Text(
                                        text = "Nome da Empresa:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )

                            OutlinedTextField(
                                value = cpflCurrentCPF,
                                onValueChange = {
                                    cpflCurrentCPF = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),

                                label = {
                                    Text(
                                        text = "CPF do Titular:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )

                            OutlinedTextField(
                                value = cpflCustomerId,
                                onValueChange = {
                                    cpflCustomerId = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),

                                label = {
                                    Text(
                                        text = "Identificação do Consumidor/Instalação:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Spacer(modifier = Modifier.height(20.dp))
                            }

                            Text(
                                text = "Água e Esgoto:", style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif,
                                )
                            )

                            OutlinedTextField(
                                value = sanasaName,
                                onValueChange = {
                                    sanasaName = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),

                                label = {
                                    Text(
                                        text = "Nome da Empresa:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )

                            OutlinedTextField(
                                value = sanasaCurrentCPF,
                                onValueChange = {
                                    sanasaCurrentCPF = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),

                                label = {
                                    Text(
                                        text = "CPF do Titular:",
                                        style = TextStyle(
                                            color = getTextColor(), fontSize = 12.sp,
                                        )
                                    )
                                }
                            )

                            OutlinedTextField(
                                value = sanasaCustomerId,
                                onValueChange = {
                                    sanasaCustomerId = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = getTextColor(),
                                    fontWeight = FontWeight.Normal
                                ),

                                label = {
                                    Text(
                                        text = "Identificação do Consumidor/Instalação:",
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

                            cpflCurrentCPF=""
                            cpflCustomerId=""
                            cpflName=""
                            sanasaCurrentCPF=""
                            sanasaCustomerId=""
                            sanasaName=""
                            openPropertyContractedInstallationsDialog.value = false
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
                                cpflName = cpflName,
                                cpflCustomerId = cpflCustomerId,
                                cpflCurrentCPF = cpflCurrentCPF,
                                sanasaName = sanasaName,
                                sanasaCustomerId = sanasaCustomerId,
                                sanasaCurrentCPF = sanasaCurrentCPF,
                                iptuCartographicCode = property.iptuCartographicCode,
                                realEstateRegistration = property.realEstateRegistration,
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



                        cpflCurrentCPF=""
                        cpflCustomerId=""
                        cpflName=""
                        sanasaCurrentCPF=""
                        sanasaCustomerId=""
                        sanasaName=""
                        openPropertyContractedInstallationsDialog.value = false
                        showToast("Instalações atualizadas com sucesso!",context)
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

