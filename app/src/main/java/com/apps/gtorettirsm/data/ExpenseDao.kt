/*
 */

package com.apps.gtorettirsm.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * The Data Access Object for the Plant class.
 */
@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expense where id = :id")
    fun getExpense(id: Long): Flow<Expense>

    @Query("SELECT * FROM expense where propertyId = :propertyId ORDER BY date DESC")
    fun getExpenses(propertyId: Long): Flow<List<Expense>>

    @Upsert
    suspend fun upsert(expense: Expense)

    @Query("DELETE FROM expense where id = :expenseId")
    suspend fun delete(expenseId: Long)

}
