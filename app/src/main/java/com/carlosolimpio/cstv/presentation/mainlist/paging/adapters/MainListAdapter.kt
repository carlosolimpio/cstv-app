package com.carlosolimpio.cstv.presentation.mainlist.paging.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carlosolimpio.cstv.databinding.LayoutMatchItemBinding
import com.carlosolimpio.cstv.domain.mainlist.Match
import com.carlosolimpio.cstv.presentation.mainlist.paging.MainListDiffCallback
import com.carlosolimpio.cstv.presentation.mainlist.paging.adapters.MainListAdapter.MainListViewHolder

class MainListAdapter : PagingDataAdapter<Match, MainListViewHolder>(MainListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val binding = LayoutMatchItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MainListViewHolder(
        private val binding: LayoutMatchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(match: Match?) {
            binding.apply {
                textView.text = match?.teamA?.name
            }
        }
    }
}
