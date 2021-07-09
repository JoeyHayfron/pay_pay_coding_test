package com.example.paypay_coding_test.repository

import androidx.annotation.WorkerThread
import com.example.paypay_coding_test.data.database.entities.Currency
import com.example.paypay_coding_test.data.database.entities.Rates

interface CurrencyRepository {
    suspend fun getAllCurrencies(apiKey: String): Map<String, Any>

    suspend fun getRatesForCurrency(apiKey: String, sourceCurrency: String) : Map<String, Any>

    @WorkerThread
    suspend fun insertCurrencies(currency: Currency)

    suspend fun insertRate(rates: Rates)

    suspend fun getAllRates(code: String): Rates

    suspend fun ratesExists(code: String): Int

    suspend fun fetchAllCurrencies(): Currency

    suspend fun currencyExists(): Int

}