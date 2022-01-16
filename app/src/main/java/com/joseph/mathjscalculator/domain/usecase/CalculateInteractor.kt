package com.joseph.mathjscalculator.domain.usecase

import com.joseph.mathjscalculator.data.repository.CalculateRepo
import com.joseph.mathjscalculator.domain.models.CalculateResponse

class CalculateInteractor(private val repo: CalculateRepo) : CalculateUseCase {
    override suspend fun calculate(expr: String, precision: Int): CalculateResponse {
        return repo.calculate(expr, precision)
    }
}
