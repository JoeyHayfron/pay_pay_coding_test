package com.example.paypay_coding_test.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Util {
    companion object{
        fun convertObjectToMap(obj: Any): Map<String, Any> {
            val gson = Gson()
            var convertedMap: Map<String, Any> =
                gson.fromJson(gson.toJson(obj), object : TypeToken<Map<String, Any>>() {}.type)
            return convertedMap
        }
    }
}