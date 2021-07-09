package com.example.paypay_coding_test

import com.example.gerald_interview.MockResponseFileReader
import com.example.paypay_coding_test.data.database.dao.CurrencyDao
import com.example.paypay_coding_test.data.database.dao.RatesDao
import com.example.paypay_coding_test.network.ICurrencyLayer
import com.example.paypay_coding_test.repository.CurrencyRepository
import com.example.paypay_coding_test.repository.CurrencyRepository_Impl
import com.google.gson.GsonBuilder
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Mock
    lateinit var repo: CurrencyRepository

    val server = MockWebServer()

    @Mock
    lateinit var apiService: ICurrencyLayer

    @Mock
    lateinit var currencyDao: CurrencyDao

    @Mock
    lateinit var rateDao: RatesDao

    @Before
    fun start() {
        server.start(8080)
        apiService = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ICurrencyLayer::class.java)

        repo = CurrencyRepository_Impl(apiService, rateDao, currencyDao)
    }

    @After
    fun end() {
        server.shutdown()
    }

    @Test
    fun testGetCurrencies() = runBlocking {
        var mockResponse =
            MockResponse().setBody(MockResponseFileReader("currencies_response.json").content)
        server.enqueue(mockResponse)

        val serverRes = repo.getAllCurrencies("b75cbc92fee94840f343b18a75228642")
        assertEquals(serverRes["AED"], "United Arab Emirates Dirham")
    }

    @Test
    fun testGetRatesForAED() = runBlocking {
        var mockResponse =
            MockResponse().setBody(MockResponseFileReader("sample_rate_response.json").content)
        server.enqueue(mockResponse)

        val serverRes = repo.getRatesForCurrency("b75cbc92fee94840f343b18a75228642", "AED")
        assertEquals(serverRes["AEDAED"], 1.0)
    }
}