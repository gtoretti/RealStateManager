/*
 */

package com.apps.gtorettirsm.di

import android.content.Context
import com.apps.gtorettirsm.data.AppDatabase
import com.apps.gtorettirsm.data.ExpenseDao
import com.apps.gtorettirsm.data.ReceivingDao
import com.apps.gtorettirsm.data.PropertyDao
import com.apps.gtorettirsm.data.ProviderDao
import com.apps.gtorettirsm.data.ReceiptPDFDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideProviderDao(appDatabase: AppDatabase): ProviderDao {
        return appDatabase.providerDao()
    }

    @Provides
    fun providePropertyDao(appDatabase: AppDatabase): PropertyDao {
        return appDatabase.patientDao()
    }

    @Provides
    fun provideReceiptPDFDao(appDatabase: AppDatabase): ReceiptPDFDao {
        return appDatabase.receiptPDFDao()
    }

    @Provides
    fun provideExpenseDao(appDatabase: AppDatabase): ExpenseDao {
        return appDatabase.expenseDao()
    }

    @Provides
    fun provideReceivingDao(appDatabase: AppDatabase): ReceivingDao {
        return appDatabase.receivingDao()
    }
}
