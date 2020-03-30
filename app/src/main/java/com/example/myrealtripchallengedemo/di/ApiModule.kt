package com.example.myrealtripchallengedemo.di

import com.example.myrealtripchallengedemo.data.api.RssHtmlService
import com.example.myrealtripchallengedemo.data.api.RssService
import com.example.myrealtripchallengedemo.data.repository.RssRepository
import com.example.myrealtripchallengedemo.data.repository.RssRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

val apiModule = module {
    factory { provideOkHttpClient() }
    factory { provideRssService(provideRssRetrofit(get())) }
    factory { provideRssHtmlService(provideRssHtmlRetrofit(get())) }

    single<RssRepository> { RssRepositoryImpl(get(), get()) }
}

fun provideOkHttpClient(): OkHttpClient {
    val httpClientBuilder = OkHttpClient().newBuilder()
    val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
    httpClientBuilder.addInterceptor(logging)

    return httpClientBuilder.build()
}

fun provideRssRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://news.google.com")
        .client(okHttpClient)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

fun provideRssHtmlRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://news.google.com")
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

fun provideRssService(retrofit: Retrofit): RssService = retrofit.create(RssService::class.java)
fun provideRssHtmlService(retrofit: Retrofit): RssHtmlService = retrofit.create(RssHtmlService::class.java)