package com.joseph.mathjscalculator.data.repository

import com.joseph.mathjscalculator.domain.models.CalculateResponse

interface CalculateRepo {
    suspend fun calculate(expr: String, precision: Int): CalculateResponse
}
