package com.example.paypay_coding_test.repository

import com.example.paypay_coding_test.data.database.dao.CurrencyDao
import com.example.paypay_coding_test.data.database.dao.RatesDao
import com.example.paypay_coding_test.data.database.entities.Currency
import com.example.paypay_coding_test.data.database.entities.Rates
import com.example.paypay_coding_test.network.ICurrencyLayer
import com.example.paypay_coding_test.utils.Util
import javax.inject.Inject

class CurrencyRepository_Impl
@Inject constructor(
    private val apiService: ICurrencyLayer,
    private val ratesDao: RatesDao,
    private val currencyDao: CurrencyDao,
) : CurrencyRepository {

    override suspend fun getAllCurrencies(apiKey: String): Map<String, Any> {
        return Util.convertObjectToMap(apiService.getCurrencies(apiKey).currencies)
    }

    override suspend fun getRatesForCurrency(
        apiKey: String,
        sourceCurrency: String
    ): Map<String, Any> {
        return Util.convertObjectToMap(apiService.getRatesForCurrency(apiKey, sourceCurrency).quotes)
    }

    override suspend fun insertCurrencies(currency: Currency) {
        currencyDao.insert(currency)
    }

    override suspend fun insertRate(rates: Rates) {
        ratesDao.insertRate(rates)
    }

    override suspend fun getAllRates(code: String): Rates {
        return ratesDao.getAllRates(code)
    }

    override suspend fun ratesExists(code: String): Int {
        return ratesDao.ratesExists(code)
    }

    override suspend fun fetchAllCurrencies(): Currency {
        return currencyDao.getAllCurrencies()
    }

    override suspend fun currencyExists(): Int {
        return  currencyDao.currencyExists()
    }
}