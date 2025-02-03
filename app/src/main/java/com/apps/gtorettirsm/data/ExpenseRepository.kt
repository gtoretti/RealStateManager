/*
 */

package com.apps.gtorettirsm.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(private val expenseDao: ExpenseDao) {

    fun getExpense(id:Long) = expenseDao.getExpense(id)

    fun getExpensesByProperty(id:Long) = expenseDao.getExpenses(id)

    suspend fun saveExpense(expense: Expense){
        expenseDao.upsert(expense)
    }

    suspend fun deleteExpense(expense: Expense){
        expenseDao.delete(expense.expenseId)
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
