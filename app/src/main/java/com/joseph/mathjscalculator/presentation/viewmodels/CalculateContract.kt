package com.joseph.mathjscalculator.presentation.viewmodels

import com.joseph.mathjscalculator.domain.models.CalculateResponse

interface CalculateContract {
    fun onCalculateIdle() {
        // Do Nothing
    }

    fun onCalculateFetching() {
        // Do Nothing
    }

    fun onCalculateFetchSuccess(data: CalculateResponse) {
        // Do Nothing
    }

    fun onCalculateFetchFailed(e: Throwable?) {
        // Do Nothing
    }
}
