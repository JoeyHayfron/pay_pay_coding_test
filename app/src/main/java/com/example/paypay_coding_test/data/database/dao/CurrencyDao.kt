package com.example.paypay_coding_test.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paypay_coding_test.data.database.entities.Currency
import java.util.concurrent.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: Currency)

    @Query("select * from currency")
    suspend fun getAllCurrencies(): Currency

    @Query("select count(*) from currency")
    suspend fun currencyExists(): Int
}