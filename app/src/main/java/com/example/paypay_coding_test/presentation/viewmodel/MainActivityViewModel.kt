package com.example.paypay_coding_test.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paypay_coding_test.data.database.entities.Currency
import com.example.paypay_coding_test.data.database.entities.Rates
import com.example.paypay_coding_test.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import javax.inject.Named


@RequiresApi(Build.VERSION_CODES.N)
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val currencyRepo: CurrencyRepository,
    @Named("api_key") private val apiKey: String
) :
    ViewModel() {
    var currencyData: MutableLiveData<Map<String, Any>> = MutableLiveData()
    var rateData: MutableLiveData<Map<String, Any>> = MutableLiveData()
    private var immutableRateData: Map<String, Any> = mapOf()

    val currencyRateData: LiveData<Map<String, Any>>
        get() = rateData

    init {
        getCurrencyOfflineOrOnline()
        getRatesOnlineOrOffline("USD")
    }

    private fun getRatesOnlineOrOffline(code: String, amount: Double? = 1.0) {
        viewModelScope.launch {
            val count = currencyRepo.ratesExists(code)
            if (count == 0) {
                getRate(code, amount)
            } else {
                getOfflineRate(code, amount)
            }
            Log.e("TAG", "getRatesOnlineOrOffline: $count")
        }
    }

    private fun getOfflineRate(code: String, amount: Double? = 1.0) {
        viewModelScope.launch {
            immutableRateData = currencyRepo.getAllRates(code).rates
            Log.e("TAG", "getOfflineRate: ${immutableRateData}")
            convertAmount(amount)
        }
    }

    private fun getCurrencyOfflineOrOnline() {
        viewModelScope.launch {
            val count = currencyRepo.currencyExists()
            if (count == 0) {
                getOnlineCurrencies()
            } else {
                getOfflineCurrencies()
            }
        }
    }

    private fun getOnlineCurrencies() {
        try {
            viewModelScope.launch {
                withTimeoutOrNull(10000L) {
                    currencyData.value = currencyRepo.getAllCurrencies(apiKey)
                    val currency = Currency(0, currencyData.value!!)
                    currencyRepo.insertCurrencies(currency)
                }
            }
        } catch (e: Exception) {
            Log.d("EXC", e.toString())
        }
    }

    private fun getOfflineCurrencies() {
        viewModelScope.launch {
            withTimeoutOrNull(10000L) {
                currencyData.value = currencyRepo.fetchAllCurrencies().currencies
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getRate(sourceCurrency: String, amount: Double? = 1.0) {
        viewModelScope.launch {
            immutableRateData = currencyRepo.getRatesForCurrency(apiKey, sourceCurrency)
            convertAmount(amount)
            val rate = Rates(sourceCurrency, immutableRateData)
            currencyRepo.insertRate(rate)

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun updateCurrencySource(sourceCurrency: String, amount: Double? = 1.0) {
        try {
            getRatesOnlineOrOffline(sourceCurrency, amount)
        } catch (e: Exception) {
            Log.d("EXC", e.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun convertAmount(amount: Double?) {
        if (amount != null) {
            val mutMap: MutableMap<String, Any> = immutableRateData.toMutableMap()
            mutMap.let {
                it.map { (k, _) ->
                    it.replace(k, immutableRateData[k] as Double * amount)
                }
            }
            rateData.value = mutMap
        } else {
            rateData.value = immutableRateData
        }
    }
}