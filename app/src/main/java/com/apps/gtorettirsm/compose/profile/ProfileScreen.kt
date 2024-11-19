/*
 */

package com.apps.gtorettirsm.compose.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.Profile
import com.apps.gtorettirsm.viewmodels.ProfileViewModel
import kotlinx.coroutines.flow.Flow


@Composable
fun ProfileScreen() {
    var profileViewModel: ProfileViewModel = hiltViewModel()
    val profilesFlow = profileViewModel.profiles
    ProfileScreen(profilesFlow = profilesFlow, profileViewModel = profileViewModel)
}

@Composable
fun ProfileScreen(
    profilesFlow: Flow<List<Profile>>,
    profileViewModel: ProfileViewModel
) {
    var name by remember { mutableStateOf("") }
    var cpfCnpj by remember { mutableStateOf("") }
    var regionalCouncil by remember { mutableStateOf("") }
    var regionalCouncilNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var uf by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var speciality by remember { mutableStateOf("") }

    var loaded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val profiles by profilesFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    if (profiles.isNotEmpty() && !loaded) {
        name = profiles[0].name
        cpfCnpj = profiles[0].cpfCnpj
        regionalCouncil = profiles[0].regionalCouncil
        regionalCouncilNumber = profiles[0].regionalCouncilNumber
        address = profiles[0].address
        city = profiles[0].city
        uf = profiles[0].uf
        phoneNumber = profiles[0].phoneNumber
        speciality = profiles[0].speciality
        loaded = true
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Informações do prestador no recibo:"
                )
            }
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            label = {
                Text("Nome:")
            }, placeholder = { Text("Informe seu nome") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = cpfCnpj,
            onValueChange = { cpfCnpj = it },
            label = {
                Text("CPF:")
            }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text("Informe seu CPF") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = regionalCouncil,
            onValueChange = { regionalCouncil = it },
            label = {
                Text("Sigla do conselho regional:")
            },
            placeholder = { Text("Informe a sigla do conselho regional. Ex: CREFITO, CRFa, etc.") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = regionalCouncilNumber,
            onValueChange = { regionalCouncilNumber = it },
            label = {
                Text("Número no conselho regional:")
            }, placeholder = { Text("Informe seu número do conselho regional.") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = address,
            onValueChange = { address = it },
            label = {
                Text("Endereço:")
            }, placeholder = { Text("Informe seu endereço de emissão do recibo.") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = city,
            onValueChange = { city = it },
            label = {
                Text("Cidade:")
            }, placeholder = { Text("Informe a cidade de emissão do recibo.") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = uf,
            onValueChange = { uf = it },
            label = {
                Text("Estado:")
            }, placeholder = { Text("Informe o estado de emissão do recibo.") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = {
                Text("Telefone:")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text("Informe seu telefone de contato comercial.") }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = speciality,
            onValueChange = { speciality = it },
            label = {
                Text("Nome da terapia:")
            },
            placeholder = { Text("Informe o nome da terapia. Ex: Fonoaudiologia, Terapia Motora, Terapia Respiratória, Terapia Ocupacional, etc.") }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(20.dp))
        }

        Button(
            onClick = {

                var tmpCPF =
                    cpfCnpj.trimEnd().trimStart().replace(".", "").replace(",", "").replace("-", "")
                        .replace(" ", "")
                if (tmpCPF.length > 0 && tmpCPF.length != 11) {
                    showToast("Por favor, verifique seu CPF.", context)
                } else
                    if (regionalCouncil.length > 20) {
                        showToast("Por favor, verifique a sigla do conselho regional.", context)
                    } else
                        if (regionalCouncilNumber.length > 20) {
                            showToast("Por favor, verifique o número do conselho regional.", context)
                        } else
                            if (phoneNumber.length > 20) {
                                showToast("Por favor, verifique o telefone.", context)
                            } else
                        {

                        if (tmpCPF.isNotEmpty()) {
                            tmpCPF = tmpCPF.substring(0, 3) + "." + tmpCPF.substring(3,6) + "." + tmpCPF.substring(6, 9) + "-" + tmpCPF.substring(9, 11)
                            cpfCnpj = tmpCPF
                        }

                        profileViewModel.saveProfile(
                            Profile(
                                profileId = 1,
                                name = name,
                                cpfCnpj = cpfCnpj,
                                regionalCouncil = regionalCouncil,
                                regionalCouncilNumber = regionalCouncilNumber,
                                address = address,
                                city = city,
                                uf = uf,
                                phoneNumber = phoneNumber,
                                speciality = speciality
                            )
                        )
                        showToast("Informações salvas com sucesso!", context)
                    }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = getButtonColor()
            ), modifier = Modifier.height(30.dp), content = {
                Text(
                    text = "Salvar",
                    style = TextStyle(
                        fontSize = 14.sp,
                    )
                )
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(100.dp))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(100.dp))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(100.dp))
        }
        //AndroidViewAdView()
    }
}




