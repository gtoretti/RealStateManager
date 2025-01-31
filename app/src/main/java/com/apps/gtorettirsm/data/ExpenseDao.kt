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
    @Query("SELECT * FROM expense where id = :expenseId ORDER BY date DESC")
    fun getExpenses(expenseId: Long): Flow<List<Expense>>

    @Upsert
    suspend fun upsert(expense: Expense)

}
