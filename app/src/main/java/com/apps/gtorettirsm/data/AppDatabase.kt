/*
 */

package com.apps.gtorettirsm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.apps.gtorettirsm.utilities.DATABASE_NAME

/**
 * The Room database for this app
 */



@Database(entities = [Provider::class, Property::class, MonthlyBilling::class, Receipt::class, ReceiptPDF::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun providerDao(): ProviderDao
    abstract fun patientDao(): PropertyDao
    abstract fun monthlyBillingDao(): MonthlyBillingDao
    abstract fun receiptDao(): ReceiptDao
    abstract fun receiptPDFDao(): ReceiptPDFDao

        companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                        }
                    }
                )
                .build()
        }
    }
}
