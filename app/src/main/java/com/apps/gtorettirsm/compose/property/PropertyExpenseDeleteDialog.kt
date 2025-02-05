/*
 */

package com.apps.gtorettirsm.compose.property

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.apps.gtorettirsm.compose.utils.getButtonColor
import com.apps.gtorettirsm.compose.utils.getTextColor
import com.apps.gtorettirsm.compose.utils.showToast
import com.apps.gtorettirsm.compose.utils.toScreen
import com.apps.gtorettirsm.data.Expense
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.viewmodels.ExpenseViewModel
import com.apps.gtorettirsm.viewmodels.PropertyViewModel
import java.text.SimpleDateFormat
import java.util.ArrayList

@Composable
fun PropertyExpenseDeleteDialog(
    openPropertyExpenseDeleteDialog: MutableState<Boolean>,
    openPropertyExpensesCreateDialog: MutableState<Boolean>,
    expenseViewModel: ExpenseViewModel,
    expense: Expense,
    context: Context
) {
    if (openPropertyExpenseDeleteDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                openPropertyExpenseDeleteDialog.value = false
            },
            modifier = Modifier
                .width(550.dp)
                .height(250.dp),

            title = {
                Text(
                    text = "Excluir Desenbolso:",
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

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        val fmt = SimpleDateFormat("dd/MM/yyyy")
                        Text(
                            text = fmt.format(expense.date), style = TextStyle(
                                color = getTextColor(),
                                fontSize = 16.sp,
                            )
                        )

                        Text(
                            text = expense.value.toScreen(), style = TextStyle(
                                color = getTextColor(),
                                fontSize = 16.sp,
                            )
                        )
                    }
                    var desc = expense.serviceDesc
                    if (expense.providerName.trim().isNotEmpty()){
                        desc = desc + " (" + expense.providerName + ") "
                    }
                    if (expense.comments.trim().isNotEmpty()){
                        desc = desc + " : " + expense.comments
                    }

                    Text(
                        text = desc, style = TextStyle(
                            color = getTextColor(),
                            fontSize = 16.sp,
                        )
                    )

                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        expenseViewModel.deleteExpense(expense)
                        dropDownSelectPropertyId.value = 0L
                        dropDownSelectPropertyDesc.value = ""
                        dropDownSelectExpenseType.value = ""
                        dropDownSelectProviderId.value = 0L
                        dropDownSelectProviderName.value = ""
                        dropDownSelectProviderServices.value = ArrayList<String>()
                        dropDownSelectProviderServiceDesc.value = ""
                        dropDownSelectReceivingType.value = ""

                        openPropertyExpenseDeleteDialog.value = false
                        openPropertyExpensesCreateDialog.value = false
                        showToast("Desenbolso excluído com sucesso!", context)
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
                        openPropertyExpenseDeleteDialog.value = false
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

