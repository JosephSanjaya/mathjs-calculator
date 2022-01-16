package com.joseph.mathjscalculator.data.services

import com.joseph.mathjscalculator.domain.models.CalculateRequest
import com.joseph.mathjscalculator.domain.models.CalculateResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterfaces {
    companion object {
        operator fun invoke(): ApiInterfaces {
            val baseService = ApiServices()
            return baseService.retrofit.create(ApiInterfaces::class.java)
        }
    }

    @POST("/v4") suspend fun calculate(@Body request: CalculateRequest): CalculateResponse
}
