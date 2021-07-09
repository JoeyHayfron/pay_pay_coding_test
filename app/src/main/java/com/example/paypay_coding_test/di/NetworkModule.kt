package com.example.paypay_coding_test.di

import com.example.paypay_coding_test.BuildConfig
import com.example.paypay_coding_test.network.ICurrencyLayer
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(): ICurrencyLayer {
        return Retrofit.Builder()
            .baseUrl("https://apilayer.net/api/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ICurrencyLayer::class.java)
    }

    @Singleton
    @Provides
    @Named("api_key")
    fun provideApiKey(): String {
        return BuildConfig.API_KEY
    }
}