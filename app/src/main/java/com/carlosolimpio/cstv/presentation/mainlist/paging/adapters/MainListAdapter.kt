package com.carlosolimpio.cstv.presentation.mainlist.paging.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.carlosolimpio.cstv.R
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
                imageTeamAEmblem.setImage(match?.teamA?.imageUrl!!)
                textTeamAName.text = match.teamA.name

                imageTeamBEmblem.setImage(match.teamB.imageUrl)
                textTeamBName.text = match.teamB.name

                imageLeagueEmblem.setImage(match.league.imageUrl)
                textLeagueSerie.text = "${match.league.name} ${match.serieName}"

                textMatchDate.text = match.matchTime
            }
        }
    }
}

fun ImageView.setImage(imageUrl: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(this)
        .load(imageUrl)
        .fitCenter()
        .placeholder(circularProgressDrawable)
        .error(R.drawable.icon_image_failed)
        .into(this)
}
