package com.joseph.mathjscalculator.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.joseph.mathjscalculator.domain.models.CalculateResponse
import com.joseph.mathjscalculator.domain.models.State
import com.joseph.mathjscalculator.domain.usecase.CalculateUseCase
import com.joseph.mathjscalculator.utils.BaseViewModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CalculateViewModel(private val useCase: CalculateUseCase) : BaseViewModel() {

    private val _calculate = MutableStateFlow<State<CalculateResponse>>(State.Idle())
    val calculate
        get() = _calculate.asImmutable()

    suspend fun resetCalculateState() {
        _calculate.reset()
    }

    fun calculate(expr: String, precision: Int) {
        viewModelScope.launch(context) {
            flow {
                emit(State.Loading())
                val result = useCase.calculate(expr, precision)
                emit(State.Success(result))
            }
                .onEach { currentCoroutineContext().ensureActive() }
                .buffer()
                .catch { _calculate.emit(State.Failed(it)) }
                .collect { _calculate.emit(it) }
        }
    }
}
