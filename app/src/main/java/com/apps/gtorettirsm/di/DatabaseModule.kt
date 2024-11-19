/*
 */

package com.apps.gtorettirsm.di

import android.content.Context
import com.apps.gtorettirsm.data.AppDatabase
import com.apps.gtorettirsm.data.AttendanceDao
import com.apps.gtorettirsm.data.PatientDao
import com.apps.gtorettirsm.data.ProfileDao
import com.apps.gtorettirsm.data.ReceiptDao
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
    fun provideProfileDao(appDatabase: AppDatabase): ProfileDao {
        return appDatabase.profileDao()
    }

    @Provides
    fun providePatientDao(appDatabase: AppDatabase): PatientDao {
        return appDatabase.patientDao()
    }

    @Provides
    fun provideAttendanceDao(appDatabase: AppDatabase): AttendanceDao {
        return appDatabase.attendanceDao()
    }

    @Provides
    fun provideReceiptDao(appDatabase: AppDatabase): ReceiptDao {
        return appDatabase.receiptDao()
    }

    @Provides
    fun provideReceiptPDFDao(appDatabase: AppDatabase): ReceiptPDFDao {
        return appDatabase.receiptPDFDao()
    }
}
