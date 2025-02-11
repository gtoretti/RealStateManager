/*
 */

package com.apps.gtorettirsm.compose.property


import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
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
import androidx.core.app.ActivityCompat
import com.apps.gtorettirsm.compose.utils.DrawScrollableView
import com.apps.gtorettirsm.compose.utils.defaultNaoInformado
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.PropertyViewModel


@Composable
fun PropertyContractManagerDialog(
    openPropertyContractManagerDialog: MutableState<Boolean>,
    propertyViewModel: PropertyViewModel,
    context: Context,
    property: Property
) {

    var contractManagerName by remember { mutableStateOf("") }
    var contractManagerContactId by remember { mutableStateOf("") }

    if (pickContactNameImobiliaria.value.trim().isNotEmpty()){
        contractManagerName = pickContactNameImobiliaria.value
        pickContactNameImobiliaria.value = ""
    }

    if (pickContactIdImobiliaria.value.trim().isNotEmpty()){
        contractManagerContactId = pickContactIdImobiliaria.value
        pickContactIdImobiliaria.value = ""
    }

    var loaded by remember { mutableStateOf(false) }
    if (!loaded) {
        contractManagerName = property.contractManagerName
        loaded = true
    }

    if (openPropertyContractManagerDialog.value) {
        AlertDialog(shape = RoundedCornerShape(10.dp), onDismissRequest = {
            openPropertyContractManagerDialog.value = false
        }, modifier = Modifier
            .width(550.dp)
            .height(450.dp),

            title = {
                Text(
                    text = "Imobiliária Administradora:", style = TextStyle(
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


                            Button(
                                onClick = {
                                    val activity = context.getActivity()
                                    contactPickerScreen.value = "imobiliaria"
                                    if (hasContactPermission(context)) {
                                        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)

                                        if (activity != null) {
                                            ActivityCompat.startActivityForResult(activity, intent, 1, null)
                                        }
                                    } else {
                                        if (activity != null) {
                                            requestContactPermission(context, activity)
                                        }
                                    }
                                },colors = ButtonDefaults.buttonColors(
                                    containerColor = getButtonColor()
                                ),modifier = Modifier.height(30.dp)
                            ) {
                                Text(
                                    text = "Selecionar Contato",
                                    style = TextStyle(
                                        fontSize = 13.sp,
                                    )
                                )
                            }

                            Text(
                                text = defaultNaoInformado(contractManagerName),
                                style = TextStyle(
                                    color = getTextColor(), fontSize = 15.sp,
                                )
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
                            contractManagerName = ""
                            contractManagerContactId = ""
                            openPropertyContractManagerDialog.value = false
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
                                iptuCartographicCode = property.iptuCartographicCode,
                                realEstateRegistration = property.realEstateRegistration,
                                totalMunicipalTaxes = property.totalMunicipalTaxes,
                                urlGDriveFolder = property.urlGDriveFolder,
                                deleted = 0,
                                contractManagerName= contractManagerName,
                                contractManagerContactId = contractManagerContactId,
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


                        contractManagerName = ""
                        contractManagerContactId = ""
                        openPropertyContractManagerDialog.value = false
                        showToast("Imobiliária atualizada com sucesso!",context)
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

