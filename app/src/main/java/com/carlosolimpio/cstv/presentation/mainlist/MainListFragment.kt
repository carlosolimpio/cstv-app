package com.carlosolimpio.cstv.presentation.mainlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlosolimpio.cstv.R
import com.carlosolimpio.cstv.databinding.FragmentMainListBinding
import com.carlosolimpio.cstv.domain.mainlist.Match
import com.carlosolimpio.cstv.presentation.mainlist.paging.adapters.MainListAdapter
import com.carlosolimpio.cstv.presentation.mainlist.paging.adapters.MainListFooterLoadStateAdapter
import com.carlosolimpio.cstv.presentation.matchdetails.MatchDetailsFragment
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
        listAdapter = MainListAdapter(
            onMatchClick = { matchData -> showMatchDetails(matchData) }
        )

        with(binding.rvMatches) {
            adapter = listAdapter.withLoadStateFooter(
                footer = MainListFooterLoadStateAdapter()
            )
            layoutManager = LinearLayoutManager(activity)
        }

        binding.layoutSwipeRefresh.setOnRefreshListener {
            listAdapter.refresh()
            binding.layoutSwipeRefresh.isRefreshing = false
        }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainListViewModel.fetchMainList().collectLatest { state ->
                listAdapter.submitData(state)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            listAdapter.loadStateFlow.collectLatest { loadState ->
                binding.progressBar.isVisible = loadState.refresh is LoadState.Loading

                if (loadState.refresh is LoadState.Error) {
                    val message = (loadState.refresh as LoadState.Error).error.localizedMessage
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showMatchDetails(matchData: Match) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container, MatchDetailsFragment(matchData), null)
            .addToBackStack(null)
            .commit()
    }
}
