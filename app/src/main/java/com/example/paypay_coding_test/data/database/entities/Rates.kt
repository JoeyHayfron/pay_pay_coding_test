package com.example.paypay_coding_test.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.paypay_coding_test.data.converters.ObjectToMapConverter

@Entity(tableName = "rates")
data class Rates(
    @PrimaryKey(autoGenerate = false)
    var currency_code: String = "",
    @TypeConverters(ObjectToMapConverter::class)
    var rates: Map<String, Any>
)
