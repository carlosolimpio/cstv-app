package com.carlosolimpio.cstv.presentation.matchdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.carlosolimpio.cstv.R
import com.carlosolimpio.cstv.databinding.FragmentMatchDetailsBinding
import com.carlosolimpio.cstv.databinding.LayoutPlayerLeftBinding
import com.carlosolimpio.cstv.databinding.LayoutPlayerRightBinding
import com.carlosolimpio.cstv.domain.mainlist.Match
import com.carlosolimpio.cstv.domain.matchdetails.MatchDetail
import com.carlosolimpio.cstv.domain.matchdetails.Player
import com.carlosolimpio.cstv.presentation.common.UiState
import com.carlosolimpio.cstv.presentation.common.parseDate
import com.carlosolimpio.cstv.presentation.common.setImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MatchDetailsFragment() : Fragment() {

    private lateinit var matchData: Match

    constructor(matchParam: Match) : this() {
        matchData = matchParam
    }

    private lateinit var binding: FragmentMatchDetailsBinding
    private val viewModel: MatchDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchMatchDetails(matchData)

        initViews()
        collectUiState()
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Success -> {
                        hideProgress()
                        setUpViews(state.data)
                        viewModel.saveMatchToState(state.data)
                    }
                    is UiState.Error -> {
                        hideProgress()
                        Toast.makeText(activity, state.message, Toast.LENGTH_LONG).show()
                    }
                    UiState.Loading -> {
                        showProgress()
                    }
                }
            }
        }
    }

    private fun initViews() {
        // Arrow back
        binding.iconArrowBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setUpViews(data: MatchDetail) {
        binding.apply {
            textLeagueSerieDetail.text = root.context.getString(
                R.string.league_serie,
                data.match.league.name,
                data.match.serieName
            )

            imageTeamAEmblem.setImage(data.match.teamA.imageUrl)
            textTeamAName.text = data.match.teamA.name

            imageTeamBEmblem.setImage(data.match.teamB.imageUrl)
            textTeamBName.text = data.match.teamB.name

            textMatchDetailsDate.text = data.match.matchTime.parseDate()

            setPlayersTeamAViews(data.teamPlayers.first.players)
            setPlayersTeamBViews(data.teamPlayers.second.players)
        }
    }

    private fun setPlayersTeamAViews(players: List<Player>) {
        binding.apply {
            playerA1.setPlayerADetails(players[0])
            playerA2.setPlayerADetails(players[1])
            playerA3.setPlayerADetails(players[2])
            playerA4.setPlayerADetails(players[3])
            playerA5.setPlayerADetails(players[4])
        }
    }

    private fun LayoutPlayerLeftBinding.setPlayerADetails(player: Player) {
        imagePlayerPicture.setImage(player.profilePictureUrl)
        textNickNameLeft.text = player.nickName
        textFullNameLeft.text = player.realName
    }

    private fun setPlayersTeamBViews(players: List<Player>) {
        binding.apply {
            playerB1.setPlayerBDetails(players[0])
            playerB2.setPlayerBDetails(players[1])
            playerB3.setPlayerBDetails(players[2])
            playerB4.setPlayerBDetails(players[3])
            playerB5.setPlayerBDetails(players[4])
        }
    }

    private fun LayoutPlayerRightBinding.setPlayerBDetails(player: Player) {
        imagePlayerPicture.setImage(player.profilePictureUrl)
        textNicknameRight.text = player.nickName
        textFullNameRight.text = player.realName
    }

    private fun showProgress() {
        binding.progressBar.isVisible = true
    }
    private fun hideProgress() {
        binding.progressBar.isVisible = false
    }
}
