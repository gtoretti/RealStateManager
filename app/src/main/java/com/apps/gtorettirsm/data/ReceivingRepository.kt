/*
 */

package com.apps.gtorettirsm.data

import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReceivingRepository @Inject constructor(private val receivingDao: ReceivingDao) {

    fun getReceivings(idProperty: Long, startDate: Date, endDate: Date) = receivingDao.getReceivings(idProperty,startDate,endDate)

    fun getReceivings(idProperty: Long) = receivingDao.getReceivings(idProperty)

    fun getRentReceivings(idProperty: Long, contractStartDate: Date) = receivingDao.getRentReceivings(idProperty,contractStartDate)

    fun getRentReceivingsByDateFilter(idProperty: Long, contractStartDate: Date, startDateFilter: Date, endDateFilter: Date) = receivingDao.getRentReceivingsByDateFilter(idProperty,contractStartDate,startDateFilter,endDateFilter)

    suspend fun saveReceiving(receiving: Receiving){
        receivingDao.upsert(receiving)
    }

    suspend fun deleteReceiving(receiving: Receiving){
        receivingDao.delete(receiving.receivingId)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ReceivingRepository? = null

        fun getInstance(receivingDao: ReceivingDao) =
            instance ?: synchronized(this) {
                instance ?: ReceivingRepository(receivingDao).also { instance = it }
            }
    }
}
