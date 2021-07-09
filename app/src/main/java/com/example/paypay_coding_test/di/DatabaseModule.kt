package com.example.paypay_coding_test.di

import android.content.Context
import androidx.room.Room
import com.example.paypay_coding_test.data.database.PayPayDB
import com.example.paypay_coding_test.data.database.dao.CurrencyDao
import com.example.paypay_coding_test.data.database.dao.RatesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideCurrencyDao(appDatabase: PayPayDB): CurrencyDao  {
        return appDatabase.currencyDao()
    }

    @Provides
    fun provideRateDao(appDatabase: PayPayDB): RatesDao {
        return appDatabase.ratesDao()
    }


    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): PayPayDB {
        return Room.databaseBuilder(
            appContext,
            PayPayDB::class.java,
            "paypay_db"
        ).build()
    }
}