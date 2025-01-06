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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.core.app.ActivityCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.compose.utils.DrawScrollableView
import com.apps.gtorettirsm.compose.utils.getAttendedDaysDescr
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
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
    receiptViewModel: ReceiptViewModel = hiltViewModel(),
    propertyId: Long,
    context: Context
) {
    val property = propertyViewModel.getProperty(propertyId)

    PropertyDetailScreen(
        openPropertyDetailDialog = openPropertyDetailDialog,
        propertyFlow = property,
        propertyViewModel = propertyViewModel,
        receiptViewModel = receiptViewModel,
        propertyId = propertyId,
        context = context
    )
}

@Composable
fun PropertyDetailScreen(
    openPropertyDetailDialog: MutableState<Boolean>,
    propertyFlow: Flow<Property>,
    propertyViewModel: PropertyViewModel,
    receiptViewModel: ReceiptViewModel,
    propertyId: Long,
    context: Context
) {

    val openPropertyDeleteDialog = remember { mutableStateOf(false) }
    val openPropertyChangeAddressDialog = remember { mutableStateOf(false) }
    val openReceivePaymentDialog = remember { mutableStateOf(false) }
    val openPropertyExpensesDialog = remember { mutableStateOf(false) }
    val openPropertyCurrentContractDialog = remember { mutableStateOf(false) }



    val property by propertyFlow.collectAsStateWithLifecycle(
        initialValue = Property(0L,"", "", "", "", "", "", "", 0.0,0,"", "", "", "", "", "", 0.0, "" , 0, "", "", "", "", "", "",  Date(0), Date(0), 0, "", 0.0, "", "", "", "", "", "", "", "", 0)
    )


    val unpaidFlow = receiptViewModel.getUnpaidReceipts(propertyId)
    val unpaids by unpaidFlow.collectAsStateWithLifecycle(initialValue = emptyList())


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
                                        openPropertyDeleteDialog.value = true
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
                                        openPropertyChangeAddressDialog.value = true
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

                            Text(
                                text = "Registro Municipal:",
                                style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )

                            Text(
                                text = "Inscrição Imobiliária:"
                            )
                            Text(
                                text = "Código Cartográfico:"
                            )
                            Text(
                                text = "Gastos Anuais com Impostos e Taxas (IPTU):"
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
                                        openReceivePaymentDialog.value = true
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = getButtonColor()
                                    ),
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Text(
                                        text = "Alterar Informações",
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


                            Text(
                                text = "Fluxo de Caixa de Aluguel:",
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
                                        openPropertyExpensesDialog.value = true
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = getButtonColor()
                                    ),
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Text(
                                        text = "Gastos",
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
                                        openReceivePaymentDialog.value = true
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = getButtonColor()
                                    ),
                                    modifier = Modifier.height(30.dp)
                                ) {
                                    Text(
                                        text = "Receitas",
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

                            Text(
                                text = "Imobiliária Administradora:",
                                style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )

                            Text(
                                text = "Nome:"
                            )
                            Text(
                                text = "Telefone:"
                            )
                            Text(
                                text = "E-mail:"
                            )
                            Text(
                                text = "Site:"
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

                            Text(
                                text = "Informações do Contrato:",
                                style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )

                            Text(
                                text = "Valor Atual de Aluguel Mensal:"
                            )
                            Text(
                                text = "Dia de Pagamento no Mês:"
                            )
                            Text(
                                text = "Índice de Reajuste Anual:"
                            )
                            Text(
                                text = "Data de Início:"
                            )
                            Text(
                                text = "Período do Contrato em meses:"
                            )
                            Text(
                                text = "Data de Término:"
                            )
                            Text(
                                text = "Nome do Inquilino:"
                            )
                            Text(
                                text = "CPF/CNPJ do Inquilino:"
                            )
                            Text(
                                text = "Telefone do Inquilino:"
                            )
                            Text(
                                text = "E-mail do Inquilino:"
                            )
                            Text(
                                text = "Nome do Fiador:"
                            )
                            Text(
                                text = "CPF/CNPJ do Fiador:"
                            )
                            Text(
                                text = "Telefone do Fiador:"
                            )
                            Text(
                                text = "E-mail do Fiador:"
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
                                        text = "Alterar Informações do Contrato",
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

                            Text(
                                text = "Instalações Contratadas:",
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

                            Text(
                                text = "Energia Elétrica:",
                                style = TextStyle(
                                    color = getTextColor(),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                            Text(
                                text = "Empresa Fornecedora:"
                            )
                            Text(
                                text = "CPF do Titular:"
                            )
                            Text(
                                text = "Código do Consumidor/Instalação:"
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
                                text = "Empresa Fornecedora:"
                            )
                            Text(
                                text = "CPF do Titular:"
                            )
                            Text(
                                text = "Código do Consumidor/Instalação:"
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
            openReceivePaymentDialog.value -> {
                PropertyReceivePaymentDialog(
                    openReceivePaymentDialog = openReceivePaymentDialog,
                    unpaids = unpaids,
                    receiptViewModel = hiltViewModel(),
                    receiptPDFViewModel = hiltViewModel(),
                    property = property,
                    context = context
                )
            }
        }
        when {
            openPropertyExpensesDialog.value -> {
                PropertyExpensesDialog(
                    openPropertyExpensesDialog = openPropertyExpensesDialog,
                    unpaids = unpaids,
                    receiptViewModel = hiltViewModel(),
                    receiptPDFViewModel = hiltViewModel(),
                    property = property,
                    context = context
                )
            }
        }
        when {
            openPropertyCurrentContractDialog.value -> {
                PropertyCurrentContractDialog(
                    openPropertyCurrentContractDialog = openPropertyCurrentContractDialog,
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