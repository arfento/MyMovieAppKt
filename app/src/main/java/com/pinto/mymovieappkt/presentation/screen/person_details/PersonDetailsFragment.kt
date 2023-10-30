package com.pinto.mymovieappkt.presentation.screen.person_details

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.FragmentPersonDetailsBinding
import com.pinto.mymovieappkt.domain.model.MovieCredits
import com.pinto.mymovieappkt.domain.model.TvCredits
import com.pinto.mymovieappkt.presentation.adapter.ImageAdapter
import com.pinto.mymovieappkt.presentation.adapter.MovieAdapter
import com.pinto.mymovieappkt.presentation.adapter.TvAdapter
import com.pinto.mymovieappkt.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailsFragment :
    BaseFragment<FragmentPersonDetailsBinding>(R.layout.fragment_person_details) {


    override val viewModel: PersonDetailsViewModel by viewModels()

    enum class CreditsType {
        CAST, CREW
    }

    val adapterImages = ImageAdapter(isPortrait = true)
    val adapterMovies = MovieAdapter(isCredits = true)
    val adapterTvs = TvAdapter(isCredits = true)

    private lateinit var movieCredits: MovieCredits
    private lateinit var tvCredits: TvCredits

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageRecyclerViewAdapterLifecycle(
            binding.rvImages,
            binding.rvMovieCredits,
            binding.rvTvCredits
        )

        viewModel.initRequest(detailId)

        setupMovieCreditsSpinner()
        setupTvCreditsSpinner()

        collectFlows(
            listOf(
                ::collectDetails,
                ::collectUiState
            )
        )
    }

    private suspend fun collectDetails() {
        viewModel.details.collect { details ->
            adapterImages.submitList(details.images.profiles)
            movieCredits = details.movieCredits
            tvCredits = details.tvCredits
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if(state.isError) showSnackbar(message = state.errorText!!, actionText = getString(R.string.button_retry)){
                viewModel.retryConnection {
                    viewModel.initRequest(id)
                }
            }

        }
    }

    private fun setupTvCreditsSpinner() {
        binding.spTvCredits.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                var creditsType = CreditsType.CAST
                adapterTvs.submitList(null)

                when (position) {
                    0 -> {
                        adapterTvs.submitList(tvCredits.cast)
                        creditsType = CreditsType.CAST
                    }

                    1 -> {
                        adapterTvs.submitList(tvCredits.crew)
                        creditsType = CreditsType.CREW
                    }
                }

                val title =
                    getString(if (creditsType == CreditsType.CAST) R.string.title_as_cast else R.string.title_as_crew)
                viewModel.setTvSeeAllList(creditsType, title)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupMovieCreditsSpinner() {
        binding.spMovieCredits.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    var creditsType = CreditsType.CAST
                    adapterMovies.submitList(null)

                    when (position) {
                        0 -> {
                            adapterMovies.submitList(movieCredits.cast)
                            creditsType = CreditsType.CAST
                        }

                        1 -> {
                            adapterMovies.submitList(movieCredits.crew)
                            creditsType = CreditsType.CREW
                        }
                    }

                    val title =
                        getString(if (creditsType == CreditsType.CAST) R.string.title_as_cast else R.string.title_as_crew)
                    viewModel.setMovieSeeAllList(creditsType, title)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }
}