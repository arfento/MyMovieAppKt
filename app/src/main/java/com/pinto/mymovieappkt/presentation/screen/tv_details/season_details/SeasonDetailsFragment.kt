package com.pinto.mymovieappkt.presentation.screen.tv_details.season_details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.FragmentSeasonDetailsBinding
import com.pinto.mymovieappkt.presentation.adapter.EpisodeAdapter
import com.pinto.mymovieappkt.presentation.adapter.ImageAdapter
import com.pinto.mymovieappkt.presentation.adapter.PersonAdapter
import com.pinto.mymovieappkt.presentation.adapter.VideoAdapter
import com.pinto.mymovieappkt.presentation.base.BaseFragment
import com.pinto.mymovieappkt.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeasonDetailsFragment :
    BaseFragment<FragmentSeasonDetailsBinding>(R.layout.fragment_season_details) {

    override val viewModel: SeasonDetailsViewModel by viewModels()

    val adapterVideos = VideoAdapter {
        val bundle = bundleOf(
            Constants.VIDEO_URL to it,
        )

        findNavController()
            .navigate(R.id.action_global_videoFragment, bundle)
    }
    val adapterCast = PersonAdapter(isCast = true)
    val adapterImages = ImageAdapter(isPortrait = true)
    val adapterEpisodes by lazy {
        EpisodeAdapter(detailId, backgroundColor)
    }
    private val seasonNumber by lazy {
        arguments?.getInt(Constants.SEASON_NUMBER)!!
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageRecyclerViewAdapterLifecycle(
            binding.rvVideos,
            binding.rvCast,
            binding.rvEpisodes,
            binding.rvImage,
        )

        viewModel.initRequest(detailId, seasonNumber)

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
            adapterImages.submitList(seasonDetails.images.posters)
            adapterEpisodes.submitList(seasonDetails.episodes)
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(
                message = state.errorText!!,
                actionText = getString(R.string.button_retry)
            ) {
                viewModel.retryConnection {
                    viewModel.initRequest(id, seasonNumber)
                }
            }
        }
    }

}