package com.joseph.mathjscalculator.presentation.viewmodels

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.joseph.mathjscalculator.domain.models.State
import kotlinx.coroutines.launch

class CalculateObserver(
    private val view: CalculateContract,
    private val viewModel: CalculateViewModel,
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        owner.lifecycleScope.launch {
            viewModel.calculate.flowWithLifecycle(owner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is State.Idle -> view.onCalculateIdle()
                        is State.Loading -> view.onCalculateFetching()
                        is State.Success -> {
                            view.onCalculateFetchSuccess(it.data)
                            viewModel.resetCalculateState()
                        }
                        is State.Failed -> {
                            view.onCalculateFetchFailed(it.e)
                            viewModel.resetCalculateState()
                        }
                    }
                }
        }
    }
}
