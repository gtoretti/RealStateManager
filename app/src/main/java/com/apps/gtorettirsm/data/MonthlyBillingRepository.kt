/*
 */

package com.apps.gtorettirsm.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MonthlyBillingRepository @Inject constructor(private val monthlyBillingDao: MonthlyBillingDao) {

    fun getMonthlyBillings(idProperty: Long) = monthlyBillingDao.getMonthlyBillings(idProperty)

    fun getNonReceiptMonthlyBillings(idProperty: Long) = monthlyBillingDao.getNonReceiptMonthlyBillings(idProperty)

    suspend fun saveMonthlyBilling(monthlyBilling: MonthlyBilling){
        monthlyBillingDao.upsert(monthlyBilling)
    }

    suspend fun deleteMonthlyBilling(monthlyBilling: MonthlyBilling){
        monthlyBillingDao.delete(monthlyBilling.monthlyBillingId)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: MonthlyBillingRepository? = null

        fun getInstance(monthlyBillingDao: MonthlyBillingDao) =
            instance ?: synchronized(this) {
                instance ?: MonthlyBillingRepository(monthlyBillingDao).also { instance = it }
            }
    }
}
