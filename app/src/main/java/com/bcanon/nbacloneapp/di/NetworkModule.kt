package com.bcanon.nbacloneapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addNetworkInterceptor {
            val requestBuilder = it.request().newBuilder()
            requestBuilder.header("X-RapidAPI-Host", "free-nba.p.rapidapi.com")
            requestBuilder.header(
                "X-RapidAPI-Key",
                "5a735d970emsh29973caafa4e2d8p1a2adbjsnf7a7a6972a24"
            )
            it.proceed(requestBuilder.build())
        }.addInterceptor(logger)
            .build()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: MoshiConverterFactory): Retrofit =
        Retrofit.Builder().baseUrl("https://free-nba.p.rapidapi.com/")
            .addConverterFactory(moshi)
            .client(okHttpClient)
            .build()

}