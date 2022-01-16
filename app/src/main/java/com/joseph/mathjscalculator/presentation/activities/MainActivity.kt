package com.joseph.mathjscalculator.presentation.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.joseph.mathjscalculator.R
import com.blankj.utilcode.util.SnackbarUtils
import com.joseph.mathjscalculator.databinding.ActivityCommonBinding
import com.joseph.mathjscalculator.domain.models.CalculateResponse
import com.joseph.mathjscalculator.presentation.viewmodels.CalculateContract
import com.joseph.mathjscalculator.presentation.viewmodels.CalculateObserver
import com.joseph.mathjscalculator.presentation.viewmodels.CalculateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity:
    AppCompatActivity(R.layout.activity_common),
    View.OnClickListener, CalculateContract {
    private val binding by viewBinding(ActivityCommonBinding::bind)
    private val viewModel by viewModel<CalculateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(CalculateObserver(this, viewModel))
        binding.listener = this
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnSubmit -> viewModel.calculate(
                binding.etInput.text.toString(), 14
            )
        }
    }

    override fun onCalculateFetching() {
        super.onCalculateFetching()
    }

    override fun onCalculateFetchSuccess(data: CalculateResponse) {
        super.onCalculateFetchSuccess(data)
        binding.tvOutput.text = data.result?.firstOrNull()
    }

    override fun onCalculateFetchFailed(e: Throwable?) {
        super.onCalculateFetchFailed(e)
        SnackbarUtils.with(binding.clRoot)
            .setMessage(e?.message ?: "Faild to calculate.")
            .showError()
    }
}