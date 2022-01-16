package com.joseph.mathjscalculator.data.repository

import androidx.annotation.WorkerThread
import com.blankj.utilcode.util.GsonUtils
import com.joseph.mathjscalculator.data.services.ApiInterfaces
import com.joseph.mathjscalculator.domain.models.CalculateRequest
import com.joseph.mathjscalculator.domain.models.CalculateResponse
import retrofit2.HttpException

@WorkerThread
class CalculateDataSource(private val api: ApiInterfaces) : CalculateRepo {

    override suspend fun calculate(expr: String, precision: Int): CalculateResponse {
        val job =
            runCatching { api.calculate(CalculateRequest(precision, listOf(expr))) }.onFailure {
                if (it is HttpException) {
                    val error =
                        GsonUtils.getGson("default")
                            .fromJson(
                                it.response()?.errorBody()?.string(), CalculateResponse::class.java)
                    throw Error(error.error)
                }
            }
        return job.getOrThrow()
    }
}
