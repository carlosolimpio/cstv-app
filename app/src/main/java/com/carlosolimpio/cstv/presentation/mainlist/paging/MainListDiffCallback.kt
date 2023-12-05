package com.carlosolimpio.cstv.presentation.mainlist.paging

import androidx.recyclerview.widget.DiffUtil
import com.carlosolimpio.cstv.domain.mainlist.Match

class MainListDiffCallback : DiffUtil.ItemCallback<Match>() {
    override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean {
        return oldItem == newItem
    }
}
