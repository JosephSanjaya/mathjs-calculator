package com.joseph.mathjscalculator.di

import com.joseph.mathjscalculator.data.repository.CalculateDataSource
import com.joseph.mathjscalculator.data.repository.CalculateRepo
import com.joseph.mathjscalculator.data.services.ApiInterfaces
import com.joseph.mathjscalculator.domain.usecase.CalculateInteractor
import com.joseph.mathjscalculator.domain.usecase.CalculateUseCase
import org.koin.dsl.module

val featuresModules = module {
    single { ApiInterfaces() }
    single<CalculateRepo> { CalculateDataSource(get()) }
    single<CalculateUseCase> { CalculateInteractor(get()) }
}
