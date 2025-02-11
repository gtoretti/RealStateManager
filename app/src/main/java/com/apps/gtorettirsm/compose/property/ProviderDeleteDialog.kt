/*
 */

package com.apps.gtorettirsm.compose.property

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apps.gtorettirsm.compose.profile.providerId
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.Provider
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ProviderViewModel

@Composable
fun ProviderDeleteDialog(
    openProviderDeleteDialog: MutableState<Boolean>,
    openProviderDetailDialog: MutableState<Boolean>,
    providerViewModel: ProviderViewModel,
    provider: Provider,
    context: Context
) {
    if (openProviderDeleteDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openProviderDeleteDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(200.dp),

            title = {
                Text(
                    text = "Excluir Prestador:",
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


                    Text(
                        text = provider.name
                    )



                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        providerViewModel.deleteProvider(provider)
                        openProviderDeleteDialog.value = false
                        openProviderDetailDialog.value = false

                        name.value = ""
                        pix.value = ""
                        contactId.value = ""
                        serviceRegion.value = ""
                        servicesAdministration.value = 0
                        servicesHydraulic.value = 0
                        servicesBrickwork.value = 0
                        servicesElectric.value = 0
                        servicesArchitecture.value = 0
                        servicesInsurer.value = 0
                        servicesAutomation.value = 0
                        servicesFireBrigade.value = 0
                        servicesNotary.value = 0
                        servicesPlasterer.value = 0
                        servicesElectricFence.value = 0
                        servicesAluminumFrames.value = 0
                        servicesAirConditioningMaintenance.value = 0
                        servicesRoofer.value = 0
                        servicesElevatorMaintenance.value = 0
                        servicesElectronicIntercom.value = 0
                        servicesGardening.value = 0
                        servicesPoolMaintenance.value = 0
                        servicesPlaygroundMaintenance.value = 0
                        servicesElectronicGate.value = 0
                        servicesCleaning.value = 0
                        servicesOthers.value = 0
                        servicesMaterialSupplier.value = 0
                        servicesPoolCleaning.value = 0
                        servicesLandscaping.value = 0
                        servicesPainting.value = 0
                        servicesSteelGatesRailings.value = 0
                        servicesPropertySecurity.value = 0
                        servicesCurtains.value = 0
                        servicesShowerStalls.value = 0
                        servicesSunshades.value = 0
                        servicesCabinetsJoinery.value = 0
                        servicesPestControl.value = 0

                        providerId.value = 0L

                        showToast("Prestador excluído com sucesso!", context)
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = getButtonColor()
                    ),modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "Excluir",
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                }

            }, dismissButton = {
                Button(
                    onClick = {
                        openProviderDeleteDialog.value = false
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

