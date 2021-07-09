package com.example.paypay_coding_test.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.paypay_coding_test.data.converters.ObjectToMapConverter

import com.example.paypay_coding_test.data.database.dao.CurrencyDao
import com.example.paypay_coding_test.data.database.dao.RatesDao
import com.example.paypay_coding_test.data.database.entities.Currency
import com.example.paypay_coding_test.data.database.entities.Rates

@Database(entities = [Currency::class, Rates::class], version = 1, exportSchema = false)
@TypeConverters(ObjectToMapConverter::class)
abstract class PayPayDB() : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun ratesDao(): RatesDao

    companion object {
        @Volatile
        private var INSTANCE: PayPayDB? = null
        private var LOCK = Any()

        fun getInstance(application: Application) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(application).also { INSTANCE = it }
        }

        private fun buildDatabase(application: Application)  = Room.databaseBuilder(application,
            PayPayDB::class.java, "paypay_db")
            .fallbackToDestructiveMigration()
            .build()
    }
}