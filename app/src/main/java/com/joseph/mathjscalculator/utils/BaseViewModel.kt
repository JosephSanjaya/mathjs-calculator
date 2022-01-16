package com.joseph.mathjscalculator.utils

import androidx.lifecycle.ViewModel
import com.joseph.mathjscalculator.domain.models.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {
    private val job = SupervisorJob()
    protected val context = Dispatchers.IO + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    suspend fun <T> MutableStateFlow<State<T>>.reset() {
        emit(State.Idle())
    }

    fun <T> MutableStateFlow<T>.asImmutable() = this as StateFlow<T>
}
