package com.joseph.mathjscalculator.domain.models

import com.google.gson.annotations.SerializedName

data class CalculateResponse(
    @field:SerializedName("result") val result: List<String>? = null,
    @field:SerializedName("error") val error: String? = null
)
