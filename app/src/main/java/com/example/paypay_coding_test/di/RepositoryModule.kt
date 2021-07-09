package com.example.paypay_coding_test.di

import com.example.paypay_coding_test.data.database.dao.CurrencyDao
import com.example.paypay_coding_test.data.database.dao.RatesDao
import com.example.paypay_coding_test.network.ICurrencyLayer
import com.example.paypay_coding_test.repository.CurrencyRepository
import com.example.paypay_coding_test.repository.CurrencyRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCurrencyRepository(
        apiService: ICurrencyLayer,
        ratesDao: RatesDao,
        currencyDao: CurrencyDao
    ): CurrencyRepository {
        return CurrencyRepository_Impl(apiService, ratesDao, currencyDao)
    }
}