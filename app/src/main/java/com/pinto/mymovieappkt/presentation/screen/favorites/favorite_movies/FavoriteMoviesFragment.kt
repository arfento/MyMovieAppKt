package com.pinto.mymovieappkt.presentation.screen.favorites.favorite_movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.FragmentFavoriteMoviesBinding
import com.pinto.mymovieappkt.domain.model.FavoriteMovie
import com.pinto.mymovieappkt.presentation.adapter.FavoriteMovieAdapter
import com.pinto.mymovieappkt.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment :
    BaseFragment<FragmentFavoriteMoviesBinding>(R.layout.fragment_favorite_movies) {

    override val viewModel: FavoriteMoviesViewModel by viewModels()

    val adapterFavorites = FavoriteMovieAdapter { removeMovie(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageRecyclerViewAdapterLifecycle(binding.rvFavoriteMovies)
        collectFlows(listOf(::collectFavoriteMovies))
    }

    private fun removeMovie(movie: FavoriteMovie) {
        viewModel.removeMovieFromFavorites(movie)
        showSnackbar(
            message = getString(R.string.snackbar_removed_item),
            actionText = getString(R.string.snackbar_action_undo),
            anchor = true
        ) {
            viewModel.addMovieToFavorites(movie)
        }
    }

    private suspend fun collectFavoriteMovies() {
        viewModel.favoriteMovies.collect { favoriteMovies ->
            adapterFavorites.submitList(favoriteMovies)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFavoriteMovies()
    }
}