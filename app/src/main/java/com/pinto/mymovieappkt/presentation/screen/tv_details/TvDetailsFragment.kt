package com.pinto.mymovieappkt.presentation.screen.tv_details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.FragmentTvDetailsBinding
import com.pinto.mymovieappkt.presentation.adapter.ImageAdapter
import com.pinto.mymovieappkt.presentation.adapter.PersonAdapter
import com.pinto.mymovieappkt.presentation.adapter.SeasonAdapter
import com.pinto.mymovieappkt.presentation.adapter.TvAdapter
import com.pinto.mymovieappkt.presentation.adapter.VideoAdapter
import com.pinto.mymovieappkt.presentation.base.BaseFragment
import com.pinto.mymovieappkt.utils.Constants
import com.pinto.mymovieappkt.utils.Detail
import com.pinto.mymovieappkt.utils.setGenreChips
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvDetailsFragment :
    BaseFragment<FragmentTvDetailsBinding>(R.layout.fragment_tv_details) {

    override val viewModel: TvDetailsViewModel by viewModels()

    val adapterVideos = VideoAdapter {

        val bundle = bundleOf(
            Constants.VIDEO_URL to it,
        )

        findNavController()
            .navigate(R.id.action_global_videoFragment, bundle)
    }

    val adapterCast = PersonAdapter(isCast = true)
    val adapterImages = ImageAdapter()
    val adapterRecommendations = TvAdapter()
    val adapterSeasons by lazy { SeasonAdapter(detailId) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageRecyclerViewAdapterLifecycle(
            binding.rvVideos,
            binding.rvCast,
            binding.rvImage,
            binding.rvSeasons,
            binding.rvRecommendations
        )

        viewModel.initRequests(detailId)

        collectFlows(
            listOf(
                ::collectDetails,
                ::collectUiState
            )
        )

    }
    private suspend fun collectDetails(){
        viewModel.details.collect{ details ->
            binding.cgGenres.setGenreChips(details.genres, Detail.TV, backgroundColor)
            adapterVideos.submitList(details.videos.filterVideos())
            adapterCast.submitList(details.credits.cast)
            adapterImages.submitList(details.images.backdrops)
            adapterSeasons.submitList(details.seasons)
            adapterRecommendations.submitList(details.recommendations.results)
        }
    }
    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(
                message = state.errorText!!,
                actionText = getString(R.string.button_retry)
            ) {
                viewModel.retryConnection {
                    viewModel.initRequests(id)
                }
            }
        }
    }

}