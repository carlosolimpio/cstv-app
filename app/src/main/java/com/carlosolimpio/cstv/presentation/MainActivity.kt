package com.carlosolimpio.cstv.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlosolimpio.cstv.databinding.ActivityMainBinding
import com.carlosolimpio.cstv.presentation.common.UiState
import com.carlosolimpio.cstv.presentation.mainlist.MainListAdapter
import com.carlosolimpio.cstv.presentation.mainlist.MainListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainListViewModel by viewModels()

    private lateinit var listAdapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listAdapter = MainListAdapter()
        binding.rvMatches.adapter = listAdapter
        binding.rvMatches.layoutManager = LinearLayoutManager(this)

        collectUiState()
    }

    private fun collectUiState() {
        lifecycleScope.launch {
            viewModel.fetchMainList().collect { state ->
                listAdapter.submitData(state)
            }
        }
    }
}
