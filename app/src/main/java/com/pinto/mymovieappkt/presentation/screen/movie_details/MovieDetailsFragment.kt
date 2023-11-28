package com.pinto.mymovieappkt.presentation.screen.movie_details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.FragmentMovieDetailsBinding
import com.pinto.mymovieappkt.presentation.adapter.ImageAdapter
import com.pinto.mymovieappkt.presentation.adapter.MovieAdapter
import com.pinto.mymovieappkt.presentation.adapter.PersonAdapter
import com.pinto.mymovieappkt.presentation.adapter.VideoAdapter
import com.pinto.mymovieappkt.presentation.base.BaseFragment
import com.pinto.mymovieappkt.utils.Detail
import com.pinto.mymovieappkt.utils.setGenreChips
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding>(R.layout.fragment_movie_details) {

    override val viewModel: MovieDetailsViewModel by viewModels()

    val adapterVideos = VideoAdapter {
        Log.d("url adapter movie detail", "message adapter movie: VideoAdapter ${it}")
        val action =
            MovieDetailsFragmentDirections.actionMovieDetailsFragmentToVideoFragment(
                it
            )
        findNavController().navigate(action)
//        playYouTubeVideo(it)
    }
    val adapterCast = PersonAdapter(isCast = true)
    val adapterImages = ImageAdapter()
    val adapterRecommendations = MovieAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageRecyclerViewAdapterLifecycle(
            binding.rvVideos,
            binding.rvCast,
            binding.rvImage,
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
            binding.cgGenres.setGenreChips(details.genres, Detail.MOVIE, backgroundColor)
            adapterVideos.submitList(details.videos.filterVideos())
            adapterCast.submitList(details.credits.cast)
            adapterImages.submitList(details.images.backdrops)
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