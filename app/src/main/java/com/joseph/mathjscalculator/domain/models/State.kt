package com.joseph.mathjscalculator.domain.models

sealed class State<T> {
    data class Idle<T>(val payload: Any? = null) : State<T>()
    data class Loading<T>(val payload: Any? = null) : State<T>()
    data class Success<T>(val data: T) : State<T>()
    data class Failed<T>(val e: Throwable) : State<T>()
}
