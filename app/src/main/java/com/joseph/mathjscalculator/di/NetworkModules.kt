package com.joseph.mathjscalculator.di

import coil.util.CoilUtils
import com.blankj.utilcode.util.GsonUtils
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.joseph.mathjscalculator.BuildConfig
import com.joseph.mathjscalculator.data.services.ApiInterfaces
import java.util.concurrent.TimeUnit
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val networkModules = module {
    single {
        ChuckerCollector(
            context = androidContext(),
            showNotification = BuildConfig.DEBUG,
            retentionPeriod = RetentionManager.Period.ONE_HOUR)
    }
    single {
        ChuckerInterceptor.Builder(androidContext())
            .apply {
                collector(get())
                maxContentLength(250_000L)
                alwaysReadResponseBody(true)
                if (!BuildConfig.DEBUG) redactHeaders("Authorization", "Bearer")
            }
            .build()
    }
    factory {
        OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .dns(Dns.SYSTEM)
            .cache(CoilUtils.createDefaultCache(androidContext()))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val request = get<Request> { parametersOf(chain.request()) }
                chain.proceed(request)
            }
            .addInterceptor(get<ChuckerInterceptor>())
            .build()
    }
    factory { (original: Request) ->
        val requestBuilder =
            original.newBuilder().url(original.url).addHeader("Content-Type", "application/json")
        requestBuilder.build()
    }
    factory { (okHttpClient: OkHttpClient) ->
        GsonUtils.setGson("default", GsonBuilder().disableHtmlEscaping().setLenient().create())
        Retrofit.Builder()
            .baseUrl("http://api.mathjs.org")
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson("default")))
            .build()
    }
    single { ApiInterfaces() }
}
