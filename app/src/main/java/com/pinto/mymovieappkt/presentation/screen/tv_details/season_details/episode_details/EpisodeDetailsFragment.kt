package com.pinto.mymovieappkt.presentation.screen.tv_details.season_details.episode_details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.FragmentEpisodeDetailsBinding
import com.pinto.mymovieappkt.presentation.adapter.ImageAdapter
import com.pinto.mymovieappkt.presentation.adapter.PersonAdapter
import com.pinto.mymovieappkt.presentation.adapter.VideoAdapter
import com.pinto.mymovieappkt.presentation.base.BaseFragment
import com.pinto.mymovieappkt.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeDetailsFragment :
    BaseFragment<FragmentEpisodeDetailsBinding>(R.layout.fragment_episode_details) {


    override val viewModel: EpisodeDetailsViewModel by viewModels()
    val adapterVideos = VideoAdapter {
        val bundle = bundleOf(
            Constants.VIDEO_URL to it,
        )

        findNavController()
            .navigate(R.id.action_global_videoFragment, bundle)
    }
    val adapterCast = PersonAdapter(isCast = true)
    val adapterGuestStars = PersonAdapter(isCast = true)
    val adapterImages = ImageAdapter()

    val seasonNumber by lazy {
        arguments?.getInt(Constants.SEASON_NUMBER)!!
    }
    val episodeNumber by lazy {
        arguments?.getInt(Constants.EPISODE_NUMBER)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageRecyclerViewAdapterLifecycle(
            binding.rvVideos,
            binding.rvCast,
            binding.rvGuestStars,
            binding.rvImage,
        )

        viewModel.initRequest(detailId, seasonNumber, episodeNumber)

        collectFlows(
            listOf(
                ::collectSeasonDetails,
                ::collectUiState
            )
        )

    }

    private suspend fun collectSeasonDetails() {
        viewModel.details.collect { seasonDetails ->
            adapterVideos.submitList(seasonDetails.videos.filterVideos())
            adapterCast.submitList(seasonDetails.credits.cast)
            adapterImages.submitList(seasonDetails.images.stills)
            adapterGuestStars.submitList(seasonDetails.credits.guestStars)
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(
                message = state.errorText!!,
                actionText = getString(R.string.button_retry)
            ) {
                viewModel.retryConnection {
                    viewModel.initRequest(detailId, seasonNumber, episodeNumber)
                }
            }
        }
    }
}