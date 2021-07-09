package com.example.paypay_coding_test

import android.util.Log
import com.example.paypay_coding_test.model.CurrenciesResponseObject
import com.example.paypay_coding_test.utils.Util
import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.JUnitCore
import org.junit.runner.RunWith
import java.util.*

class UtilTest {

    @Test
    fun testConversionToMap(){
        val jsonObject = TestObject("AED")
        val convertedMap = Util.convertObjectToMap(jsonObject)
        assertEquals(convertedMap["code"], "AED")
    }
}