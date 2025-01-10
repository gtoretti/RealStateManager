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
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.compose.utils.DrawScrollableView
import com.apps.gtorettirsm.compose.utils.getAttendedDaysDescr
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.compose.utils.toScreen
import com.apps.gtorettirsm.data.MonthlyBilling
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.Receipt
import com.apps.gtorettirsm.viewmodels.MonthlyBillingViewModel
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import com.apps.gtorettirsm.viewmodels.ReceiptPDFViewModel
import com.apps.gtorettirsm.viewmodels.ReceiptViewModel
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

    val property by propertyFlow.collectAsStateWithLifecycle(
        initialValue = Property(0L,"", "", "", "", "", "", "", 0.0,0,"", "", "", "", "", "","","", 0.0, "" , 0,  "", "", "", "",  Date(0), Date(0), 0, "", 0.0, "", "", "", "", "", "", "", "", 0)
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
        text = property.realEstateRegistration
    )

    Text(
        text = "Código Cartográfico:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.iptuCartographicCode
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
        text = property.cpflName
    )

    Text(
        text = "CPF do Titular:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.cpflCurrentCPF
    )

    Text(
        text = "Identificação do Consumidor/Instalação:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.cpflCustomerId
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
        text = property.sanasaName
    )


    Text(
        text = "CPF do Titular:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.sanasaCurrentCPF
    )


    Text(
        text = "Identificação do Consumidor/Instalação:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.sanasaCustomerId
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
        text = "Nome:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractManagerName
    )


    Text(
        text = "Telefone:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractManagerPhoneNumber
    )

    Text(
        text = "E-mail:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractManagerEmail
    )


    Text(
        text = "Site:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractManagerUrl
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
        text = property.contractMonthlyBillingValue.toScreen()
    )
    Text(
        text = "Dia de Pagamento no Mês:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractPaymentDate.toString()
    )

    Text(
        text = "Índice de Reajuste Anual:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractValueAdjustmentIndexName
    )


    Text(
        text = "Data de Início:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractStartDate.time.toString()
    )

    Text(
        text = "Período do Contrato em meses:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractMonths.toString()
    )


    Text(
        text = "Data de Término:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractEndedDate.time.toString()
    )


    Text(
        text = "Nome do Inquilino:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractRenterName
    )



    Text(
        text = "CPF/CNPJ do Inquilino:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractRenterCPF
    )


    Text(
        text = "Telefone do Inquilino:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractRenterPhone
    )



    Text(
        text = "E-mail do Inquilino:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractRenterEmail
    )


    Text(
        text = "Nome do Fiador:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractGuarantorName
    )


    Text(
        text = "CPF/CNPJ do Fiador:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractGuarantorCPF
    )



    Text(
        text = "Telefone do Fiador:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractGuarantorPhone
    )


    Text(
        text = "E-mail do Fiador:",
        style = TextStyle(
            color = getTextColor(),
            fontWeight = FontWeight.Bold,
        )
    )
    Text(
        text = property.contractGuarantorEmail
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




                            Text(
                                text = "Documentos do Imóvel no GDrive:",
                                style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
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


    }
}

fun openGDriveFolder(context: Context, urlGDriveFolder: String){
    var url = urlGDriveFolder;
    if (url.isEmpty()){
        url = "https://drive.google.com/drive/my-drive"
        showToast("A pasta do imóvel não está configurada. Abrindo Meu Drive.",context)
    }
   val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(browserIntent)
}