package com.carlosolimpio.cstv.presentation.mainlist.paging.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carlosolimpio.cstv.R
import com.carlosolimpio.cstv.databinding.LayoutMatchItemBinding
import com.carlosolimpio.cstv.domain.mainlist.Match
import com.carlosolimpio.cstv.domain.mainlist.MatchStatus
import com.carlosolimpio.cstv.presentation.common.parseDate
import com.carlosolimpio.cstv.presentation.common.setImage
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
            val context = binding.root.context

            binding.apply {
                imageTeamAEmblem.setImage(match?.teamA?.imageUrl!!)
                textTeamAName.text = match.teamA.name

                imageTeamBEmblem.setImage(match.teamB.imageUrl)
                textTeamBName.text = match.teamB.name

                imageLeagueEmblem.setImage(match.league.imageUrl)
                textLeagueSerie.text = context.getString(
                    R.string.league_serie,
                    match.league.name,
                    match.serieName
                )

                textMatchDate.apply {
                    if (match.status == MatchStatus.RUNNING) {
                        text = context.getString(R.string.agora)
                        backgroundTintList = ColorStateList.valueOf(
                            context.resources.getColor(R.color.red, null)
                        )
                    } else {
                        text = match.matchTime.parseDate()
                        backgroundTintList = ColorStateList.valueOf(
                            context.resources.getColor(R.color.match_date, null)
                        )
                    }
                }
            }
        }
    }
}
