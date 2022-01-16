package com.joseph.mathjscalculator.domain.models

import com.google.gson.annotations.SerializedName

data class CalculateRequest(
    @field:SerializedName("precision") val precision: Int? = null,
    @field:SerializedName("expr") val expr: List<String>? = null
)
