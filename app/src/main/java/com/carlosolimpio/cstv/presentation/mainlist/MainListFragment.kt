package com.carlosolimpio.cstv.presentation.mainlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlosolimpio.cstv.databinding.FragmentMainListBinding
import com.carlosolimpio.cstv.presentation.mainlist.paging.adapters.MainListAdapter
import com.carlosolimpio.cstv.presentation.mainlist.paging.adapters.MainListLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainListFragment : Fragment() {

    private lateinit var binding: FragmentMainListBinding

    private val mainListViewModel: MainListViewModel by viewModels()
    private lateinit var listAdapter: MainListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        collectUiState()
    }

    private fun initViews() {
        listAdapter = MainListAdapter()

        with(binding.rvMatches) {
            adapter = listAdapter.withLoadStateFooter(
                footer = MainListLoadStateAdapter()
            )
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainListViewModel.fetchMainList().collect { state ->
                listAdapter.submitData(state)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            listAdapter.loadStateFlow.collectLatest { loadState ->
                binding.apply {
                    progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                }
            }
        }
    }
}