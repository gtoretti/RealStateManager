/*
 */

package com.apps.gtorettirsm.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(private val expenseDao: ExpenseDao) {

    fun getExpensesByProperty(id:Long) = expenseDao.getExpenses(id)

    suspend fun saveExpense(expense: Expense){
        expenseDao.upsert(expense)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ExpenseRepository? = null

        fun getInstance(expenseDao: ExpenseDao) =
            instance ?: synchronized(this) {
                instance ?: ExpenseRepository(expenseDao).also { instance = it }
            }
    }


}
