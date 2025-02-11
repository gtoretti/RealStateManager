/*
 */

package com.apps.gtorettirsm.compose.property

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.compose.profile.openContactDetails
import com.apps.gtorettirsm.compose.utils.DrawScrollableView
import com.apps.gtorettirsm.compose.utils.defaultNaoInformado
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getProviderDesc
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import kotlinx.coroutines.flow.Flow
import java.util.Date


@Composable
fun PropertyDetailScreen(
    openPropertyDetailDialog: MutableState<Boolean>,
    propertyViewModel: PropertyViewModel = hiltViewModel(),
    propertyId: Long,
    context: Context
) {
    val property = propertyViewModel.getProperty(propertyId)

    PropertyDetailScreen(
        openPropertyDetailDialog = openPropertyDetailDialog,
        propertyFlow = property,
        propertyViewModel = propertyViewModel,
        context = context
    )
}

@Composable
fun PropertyDetailScreen(
    openPropertyDetailDialog: MutableState<Boolean>,
    propertyFlow: Flow<Property>,
    propertyViewModel: PropertyViewModel,
    context: Context
) {

    val openPropertyDeleteDialog = remember { mutableStateOf(false) }
    val openPropertyChangeAddressDialog = remember { mutableStateOf(false) }
    val openPropertyCurrentContractDialog = remember { mutableStateOf(false) }
    val displayPropertyCurrentContract = remember { mutableStateOf(false) }
    val openPropertyCityHallRegistrationDialog = remember { mutableStateOf(false) }
    val displayPropertyCityHallRegistration = remember { mutableStateOf(false) }
    val openPropertyContractManagerDialog = remember { mutableStateOf(false) }
    val displayPropertyContractManager = remember { mutableStateOf(false) }
    val openPropertyContractedInstallationsDialog = remember { mutableStateOf(false) }
    val displayPropertyContractedInstallations = remember { mutableStateOf(false) }
    val openPropertyGDriveInputDialog = remember { mutableStateOf(false) }
    val displayPropertyGDriveConf = remember { mutableStateOf(false) }

    val property by propertyFlow.collectAsStateWithLifecycle(
        initialValue = Property(0L,"", "", "", "", "", "", "", 0.0,0,"", "", "", "", "", "","","", 0.0, "" , 0,  "",  "",  Date(0), Date(0), 0,0, "","", 0.0, "", "", "", "", "", "",  0,0.0)
    )

    if (openPropertyDetailDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openPropertyDetailDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(840.dp),

            title = {
                Text(
                    text = "Informações do Imóvel:",
                    style = TextStyle(
                        color = getTextColor(),
                        fontSize = 18.sp,
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
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(2.dp)

                        ) {

                            Text(
                                text = "Endereço:",
                                style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )

                            var streetAddress = property.streetAddress + ", " + property.number
                            if (property.complement.isNotEmpty())
                                streetAddress = streetAddress + " - " + property.complement

                            Text(
                                text = streetAddress
                            )

                            Text(
                                text = property.district
                            )
                            Text(
                                text = property.city + " - " + property.state
                            )
                            Text(
                                text = "CEP: " + property.zipCode, style = TextStyle(

                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif,
                                )
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Spacer(modifier = Modifier.height(20.dp))
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = {
                                        openPropertyChangeAddressDialog.value = true

                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = getButtonColor()
                                    ),
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Text(
                                        text = "Alterar Endereço",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                        )
                                    )
                                }

                            }
                            Row(
                                    verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                            ) {


                                Button(
                                    onClick = {
                                        openPropertyDeleteDialog.value = true
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = getButtonColor()
                                    ),
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Text(
                                        text = "Excluir Imóvel",
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
                                Spacer(modifier = Modifier.height(20.dp))
                            }

                            HorizontalDivider(thickness = 1.dp)


                            if (!displayPropertyCityHallRegistration.value) {
                                TextButton(
                                    modifier = Modifier.padding(5.dp),
                                    onClick =
                                    {
                                        displayPropertyCityHallRegistration.value = true
                                    },
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.KeyboardArrowRight,
                                            contentDescription = "Registro Municipal",
                                            tint = getTextColor(),
                                            modifier = Modifier
                                                .padding(end = 12.dp)
                                                .size(24.dp)
                                        )
                                        Text(
                                            text = "Registro Municipal:",
                                            style = TextStyle(
                                                color = getTextColor(),
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                            )
                                        )
                                    }
                                }
                            }else{


    TextButton(
        modifier = Modifier.padding(5.dp),
        onClick =
        {
            displayPropertyCityHallRegistration.value=false
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Registro Municipal",
                tint = getTextColor(),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
            Text(
                text = "Registro Municipal:",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }



    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
    }

    Text(
        text = "Inscrição Imobiliária:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.realEstateRegistration)
    )

    Text(
        text = "Código Cartográfico:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.iptuCartographicCode)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        Button(
            onClick = {
                openPropertyCityHallRegistrationDialog.value = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = getButtonColor()
            ),
            modifier = Modifier.height(30.dp)
        ) {
            Text(
                text = "Alterar Registro Municipal",
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
        Spacer(modifier = Modifier.height(20.dp))
    }

    HorizontalDivider(thickness = 1.dp)
}



if (!displayPropertyContractedInstallations.value){
    TextButton(
        modifier = Modifier.padding(5.dp),
        onClick =
        {
            displayPropertyContractedInstallations.value=true
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Instalações Contratadas",
                tint = getTextColor(),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
            Text(
                text = "Instalações Contratadas:",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }
}else{

    TextButton(
        modifier = Modifier.padding(5.dp),
        onClick =
        {
            displayPropertyContractedInstallations.value=false
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Instalações Contratadas",
                tint = getTextColor(),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
            Text(
                text = "Instalações Contratadas:",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }



    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
    }

    Text(
        text = "Energia Elétrica:",
        style = TextStyle(
            color = getTextColor(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = "Empresa Fornecedora:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.cpflName)
    )

    Text(
        text = "CPF do Titular:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.cpflCurrentCPF)
    )

    Text(
        text = "Identificação do Consumidor/Instalação:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.cpflCustomerId)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
    }

    Text(
        text = "Água e Esgoto:",
        style = TextStyle(
            color = getTextColor(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = "Empresa Fornecedora:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.sanasaName)
    )


    Text(
        text = "CPF do Titular:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.sanasaCurrentCPF)
    )


    Text(
        text = "Identificação do Consumidor/Instalação:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.sanasaCustomerId)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                openPropertyContractedInstallationsDialog.value=true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = getButtonColor()
            ),
            modifier = Modifier.height(30.dp)
        ) {
            Text(
                text = "Alterar Instalações",
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
        Spacer(modifier = Modifier.height(20.dp))
    }

    HorizontalDivider(thickness = 1.dp)

}



if (!displayPropertyContractManager.value){
    TextButton(
        modifier = Modifier.padding(5.dp),
        onClick =
        {
            displayPropertyContractManager.value=true
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Imobiliária",
                tint = getTextColor(),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
            Text(
                text = "Imobiliária:",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }
}else{
    TextButton(
        modifier = Modifier.padding(5.dp),
        onClick =
        {
            displayPropertyContractManager.value=false
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Imobiliária",
                tint = getTextColor(),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
            Text(
                text = "Imobiliária:",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
    }


    Text(
        text = "Nome da Imobiliária:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ){
        Text(
            text = defaultNaoInformado(property.contractManagerName),
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
        if (property.contractManagerName.trim().isNotEmpty()) {
            TextButton(
                modifier = Modifier.padding(5.dp),
                onClick =
                {
                    openContactDetails(property.contractManagerContactId, context)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = "Chamar Imobiliária",
                    tint = getTextColor(),
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .size(16.dp)
                )
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                openPropertyContractManagerDialog.value = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = getButtonColor()
            ),
            modifier = Modifier.height(30.dp)
        ) {
            Text(
                text = "Alterar Imobiliária",
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
        Spacer(modifier = Modifier.height(20.dp))
    }
    HorizontalDivider(thickness = 1.dp)

}




if (!displayPropertyCurrentContract.value){
    TextButton(
        modifier = Modifier.padding(5.dp),
        onClick =
        {
            displayPropertyCurrentContract.value=true
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Contrato",
                tint = getTextColor(),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
            Text(
                text = "Contrato:",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }

}else{
    TextButton(
        modifier = Modifier.padding(5.dp),
        onClick =
        {
            displayPropertyCurrentContract.value=false
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Contrato",
                tint = getTextColor(),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
            Text(
                text = "Contrato:",
                style = TextStyle(
                    color = getTextColor(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
    }

    Text(
        text = "Valor Atual de Aluguel Mensal:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.contractMonthlyBillingValue)
    )
    Text(
        text = "Dia de Pagamento no Mês:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.contractPaymentDate)
    )

    Text(
        text = "Valor da Multa por Dia de Atraso:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.contractFinePerDelayedDay)
    )



    Text(
        text = "Índice de Reajuste Anual:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.contractValueAdjustmentIndexName)
    )


    Text(
        text = "Data de Início:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.contractStartDate)
    )

    Text(
        text = "Data de Término:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.contractEndedDate)
    )

    Text(
        text = "Período do Contrato em meses:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.contractMonthsDaysDescr)
    )


    Text(
        text = "Nome do Inquilino:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ){
        Text(
            text = defaultNaoInformado(property.contractRenterName),
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

        if (property.contractRenterName.trim().isNotEmpty()) {
            TextButton(
                modifier = Modifier.padding(5.dp),
                onClick =
                {
                    openContactDetails(property.contractRenterContactId, context)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = "Chamar Inquilino",
                    tint = getTextColor(),
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .size(16.dp)
                )
            }
        }
    }

    Text(
        text = "CPF/CNPJ do Inquilino:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.contractRenterCPF)
    )

    Text(
        text = "Nome do Fiador:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ){
        Text(
            text = defaultNaoInformado(property.contractGuarantorName),
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

        if (property.contractGuarantorName.trim().isNotEmpty()) {
            TextButton(
                modifier = Modifier.padding(5.dp),
                onClick =
                {
                    openContactDetails(property.contractGuarantorContactId, context)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = "Chamar Fiador",
                    tint = getTextColor(),
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .size(16.dp)
                )
            }
        }
    }

    Text(
        text = "CPF/CNPJ do Fiador:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = defaultNaoInformado(property.contractGuarantorCPF)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                openPropertyCurrentContractDialog.value = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = getButtonColor()
            ),
            modifier = Modifier.height(30.dp)
        ) {
            Text(
                text = "Alterar Contrato",
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
        Spacer(modifier = Modifier.height(20.dp))
    }
    HorizontalDivider(thickness = 1.dp)
}

                            if (!displayPropertyGDriveConf.value) {
                                TextButton(
                                    modifier = Modifier.padding(5.dp),
                                    onClick =
                                    {
                                        displayPropertyGDriveConf.value = true
                                    },
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.KeyboardArrowRight,
                                            contentDescription = "Registro Municipal",
                                            tint = getTextColor(),
                                            modifier = Modifier
                                                .padding(end = 12.dp)
                                                .size(24.dp)
                                        )
                                        Text(
                                            text = "Documentos no GDrive:",
                                            style = TextStyle(
                                                color = getTextColor(),
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                            )
                                        )
                                    }
                                }
                            }else{
                                TextButton(
                                    modifier = Modifier.padding(5.dp),
                                    onClick =
                                    {
                                        displayPropertyGDriveConf.value=false
                                    },
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.KeyboardArrowDown,
                                            contentDescription = "Documentos no GDrive",
                                            tint = getTextColor(),
                                            modifier = Modifier
                                                .padding(end = 12.dp)
                                                .size(24.dp)
                                        )
                                        Text(
                                            text = "Documentos no GDrive:",
                                            style = TextStyle(
                                                color = getTextColor(),
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                            )
                                        )
                                    }
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.End,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Spacer(modifier = Modifier.height(20.dp))
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Button(
                                        onClick = {
                                            openGDriveFolder(context, property.urlGDriveFolder)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = getButtonColor()
                                        ),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Text(
                                            text = "Abrir URL",
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                            )
                                        )
                                    }
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                    Button(
                                        onClick = {
                                            openPropertyGDriveInputDialog.value=true
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = getButtonColor()
                                        ),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Text(
                                            text = "Configurar URL",
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
                                    Spacer(modifier = Modifier.height(20.dp))
                                }

                                HorizontalDivider(thickness = 1.dp)
                            }
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
                            openPropertyDetailDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }

                    /*
                    Button(
                        onClick = {
                            openPropertyDetailDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getButtonColor()
                        ),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Exportar PDF",
                            style = TextStyle(
                                fontSize = 14.sp,
                            )
                        )
                    }
                    */


                }
            }
        )
        when {
            openPropertyDeleteDialog.value -> {
                PropertyDeleteDialog(
                    openPropertyDeleteDialog = openPropertyDeleteDialog,
                    propertyViewModel = propertyViewModel,
                    openPropertyDetailDialog = openPropertyDetailDialog,
                    property = property,
                    context = context,
               )
            }
        }
        when {
            openPropertyChangeAddressDialog.value -> {
                PropertyCreateScreen(openPropertyChangeAddressDialog, propertyViewModel, context, property)
            }
        }

        when {
            openPropertyCurrentContractDialog.value -> {
                PropertyCurrentContractDialog(
                    openPropertyCurrentContractDialog = openPropertyCurrentContractDialog,
                    propertyViewModel = propertyViewModel,
                    property = property,
                    context = context
                )
            }
        }
        when {
            openPropertyCityHallRegistrationDialog.value -> {
                PropertyCityHallRegistrationDialog(
                    openPropertyCityHallRegistrationDialog = openPropertyCityHallRegistrationDialog,
                    propertyViewModel = propertyViewModel,
                    property = property,
                    context = context
                )
            }
        }
        when {
            openPropertyContractManagerDialog.value -> {
                PropertyContractManagerDialog(
                    openPropertyContractManagerDialog = openPropertyContractManagerDialog,
                    propertyViewModel = propertyViewModel,
                    property = property,
                    context = context
                )
            }
        }
        when {
            openPropertyContractedInstallationsDialog.value -> {
                PropertyContractedInstallationsDialog(
                    openPropertyContractedInstallationsDialog = openPropertyContractedInstallationsDialog,
                    propertyViewModel = propertyViewModel,
                    property = property,
                    context = context
                )
            }
        }
        when {
            openPropertyGDriveInputDialog.value -> {
                PropertyGDriveInputDialog(
                    openPropertyGDriveInputDialog = openPropertyGDriveInputDialog,
                    propertyViewModel = propertyViewModel,
                    property = property,
                    context = context
                )
            }
        }
    }
}

fun openGDriveFolder(context: Context, urlGDriveFolder: String){

    try{
        var url = urlGDriveFolder;
        if (url.isEmpty()){
            url = "https://drive.google.com/drive/my-drive"
            showToast("A pasta do imóvel não está configurada. Abrindo Meu Drive.",context)
        }
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }catch (e: Exception){
        showToast("Por favor, verifique se a URL configurada está correta.",context)
    }

}

