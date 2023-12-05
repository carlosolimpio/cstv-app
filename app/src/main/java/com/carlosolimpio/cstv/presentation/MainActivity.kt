package com.carlosolimpio.cstv.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.carlosolimpio.cstv.R
import com.carlosolimpio.cstv.presentation.common.UiState
import com.carlosolimpio.cstv.presentation.mainlist.MainListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.fetchMainList()

        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Success -> {
                    }
                    is UiState.Error -> {
                    }
                    UiState.Loading -> {
                    }
                }
            }
        }
    }
}
