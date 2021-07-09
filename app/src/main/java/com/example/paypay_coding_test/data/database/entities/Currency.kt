package com.example.paypay_coding_test.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.paypay_coding_test.data.converters.ObjectToMapConverter

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @TypeConverters(ObjectToMapConverter::class)
    var currencies: Map<String, Any>
)
