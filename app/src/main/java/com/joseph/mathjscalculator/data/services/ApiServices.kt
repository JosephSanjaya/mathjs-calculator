package com.joseph.mathjscalculator.data.services

import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import retrofit2.Retrofit

class ApiServices : KoinComponent {
    private val okHttpClient by inject<OkHttpClient>()
    val retrofit by inject<Retrofit> { parametersOf(okHttpClient) }
}
