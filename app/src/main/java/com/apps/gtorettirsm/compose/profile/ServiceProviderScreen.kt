/*
 */

package com.apps.gtorettirsm.compose.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apps.gtorettirsm.compose.property.PropertyDetailScreen
import com.apps.gtorettirsm.compose.property.ProviderDetailScreen
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getRedTextColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.data.Profile
import com.apps.gtorettirsm.data.Provider
import com.apps.gtorettirsm.viewmodels.ProviderViewModel
import kotlinx.coroutines.flow.Flow


@Composable
fun ServiceProviderScreen() {
    var providerViewModel: ProviderViewModel = hiltViewModel()
    val providerFlow = providerViewModel.providers
    ServiceProviderScreen(providerFlow = providerFlow, providerViewModel = providerViewModel)
}

@Composable
fun ServiceProviderScreen(
    providerFlow: Flow<List<Provider>>,
    providerViewModel: ProviderViewModel
) {

    val context = LocalContext.current

    var openProviderDetailDialog = remember { mutableStateOf(false) }

    val providers by providerFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    var provider = Provider(0L,"","","","","", 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)

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
                    text = "Prestadores de Serviços:"
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {

            if (providers.isEmpty()) {
                Text(
                    text = "Para adicionar prestadores, clique aqui-->",
                    style = TextStyle(
                        color = getRedTextColor(),
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                    )
                )
            }

            TextButton(
                modifier = Modifier.padding(5.dp),
                onClick =
                {
                    openProviderDetailDialog.value = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Adicionar Prestador",
                    tint = getTextColor(),
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(24.dp)
                )
            }
        }


        when {
            openProviderDetailDialog.value -> {
                ProviderDetailScreen(
                    openProviderDetailDialog,
                    providerViewModel,
                    context,
                    provider)
            }
        }

    }
}




