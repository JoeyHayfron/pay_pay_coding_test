package com.example.paypay_coding_test.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ObjectToMapConverter {

    // used when fetching
    @JvmStatic
    @TypeConverter
    fun anyToMap(obj: String): Map<String, Any> {
        val gson = Gson()
        val convertedMap: Map<String, Any> =
            gson.fromJson(obj, object : TypeToken<Map<String, Any>>() {}.type)
        return convertedMap
    }

    //used when saving
    @JvmStatic
    @TypeConverter
    fun mapToAny(map: Map<String, Any>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
}