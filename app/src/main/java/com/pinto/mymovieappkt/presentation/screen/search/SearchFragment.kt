package com.pinto.mymovieappkt.presentation.screen.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.FragmentSearchBinding
import com.pinto.mymovieappkt.presentation.adapter.GenreAdapter
import com.pinto.mymovieappkt.presentation.adapter.MovieAdapter
import com.pinto.mymovieappkt.presentation.adapter.PersonAdapter
import com.pinto.mymovieappkt.presentation.adapter.TvAdapter
import com.pinto.mymovieappkt.presentation.base.BaseFragment
import com.pinto.mymovieappkt.utils.Detail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    override val viewModel: SearchViewModel by viewModels()

    val adapterMovies by lazy { MovieAdapter() }
    val adapterTvs by lazy { TvAdapter() }
    val adapterPeople by lazy { PersonAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageRecyclerViewAdapterLifecycle(
            binding.rvGenres,
            binding.rvTvs,
            binding.rvMovies,
            binding.rvPeople,
        )

        setupSearchView()
        setupSpinner()

        collectFlows(
            listOf(
                ::collectMovieSearchResults,
                ::collectTvSearchResults,
                ::collectPersonSearchResults,
                ::collectUiState
            )
        )
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.rvMovies.scrollToPosition(0)
                binding.rvTvs.scrollToPosition(0)
                binding.rvPeople.scrollToPosition(0)

                if (!query.isNullOrEmpty()) viewModel.fetchInitialSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    private fun setupSpinner() {
        binding.spMediaType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                with(resources) {
                    binding.rvGenres.adapter = when (position) {
                        0 -> {
                            val movieGenreIds = getIntArray(R.array.movie_genre_ids).toTypedArray()
                            val movieGenreNames = getStringArray(R.array.movie_genre_names)

                            GenreAdapter(Detail.MOVIE).apply {
                                submitList(movieGenreIds.zip(movieGenreNames))
                            }
                        }

                        else -> {
                            val tvGenreIds = getIntArray(R.array.tv_genre_ids).toTypedArray()
                            val tvGenreNames = getStringArray(R.array.tv_genre_names)

                            GenreAdapter(Detail.TV).apply {
                                submitList(tvGenreIds.zip(tvGenreNames))
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    private suspend fun collectMovieSearchResults() {
        viewModel.movieResults.collect { movies ->
            adapterMovies.submitList(movies)
        }
    }

    private suspend fun collectTvSearchResults() {
        viewModel.tvResults.collect { tvs ->
            adapterTvs.submitList(tvs)
        }
    }

    private suspend fun collectPersonSearchResults() {
        viewModel.personResults.collect { people ->
            adapterPeople.submitList(people)
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(
                message = state.errorText!!,
                actionText = getString(R.string.button_retry),
                anchor = true
            ) {
                viewModel.retryConnection {
                    viewModel.initRequests()
                }
            }
        }
    }


    fun clearSearch() {
        viewModel.clearSearchResults()
        adapterMovies.submitList(null)
        adapterTvs.submitList(null)
        adapterPeople.submitList(null)
    }

}