package com.example.paypay_coding_test.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paypay_coding_test.data.database.entities.Rates


@Dao
interface RatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(rates: Rates)

    @Query("select * from rates where currency_code = :code")
    suspend fun getAllRates(code: String): Rates

    @Query("select count(*) from rates where currency_code = :code")
    suspend fun ratesExists(code: String): Int
}

