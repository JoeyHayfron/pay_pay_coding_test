package com.example.paypay_coding_test.network

import com.example.paypay_coding_test.model.CurrenciesResponseObject
import com.example.paypay_coding_test.model.RatesResponseObject
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface ICurrencyLayer {

    @GET("list")
    suspend fun getCurrencies(@Query("access_key") apiKey: String): CurrenciesResponseObject

    @GET("live")
    suspend fun getRatesForCurrency(@Query("access_key") apiKey: String, @Query("source") sourceCurrency: String): RatesResponseObject
}