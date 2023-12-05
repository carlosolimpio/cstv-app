package com.carlosolimpio.cstv.presentation.mainlist.paging.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carlosolimpio.cstv.databinding.LoadNextPageItemBinding
import com.carlosolimpio.cstv.presentation.mainlist.paging.adapters.MainListLoadStateAdapter.LoadStateViewHolder

class MainListLoadStateAdapter : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = LoadNextPageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(
        private val binding: LoadNextPageItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.progressAppend.isVisible = loadState is LoadState.Loading
        }
    }
}