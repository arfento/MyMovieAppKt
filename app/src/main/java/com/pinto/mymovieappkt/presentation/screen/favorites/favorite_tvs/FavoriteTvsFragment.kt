package com.pinto.mymovieappkt.presentation.screen.favorites.favorite_tvs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.FragmentFavoriteTvsBinding
import com.pinto.mymovieappkt.domain.model.FavoriteTv
import com.pinto.mymovieappkt.presentation.adapter.FavoriteTvAdapter
import com.pinto.mymovieappkt.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteTvsFragment : BaseFragment<FragmentFavoriteTvsBinding>(R.layout.fragment_favorite_tvs) {

    override val viewModel: FavoriteTvsViewModel by viewModels()

    val adapterFavorites = FavoriteTvAdapter { removeTv(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageRecyclerViewAdapterLifecycle(binding.rvFavoriteTvs)
        collectFlows(listOf(::collectFavoriteTvs))
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFavoriteTvs()
    }

    private fun removeTv(tv: FavoriteTv) {
        viewModel.removeTvFromFavorites(tv)

        showSnackbar(
            message = getString(R.string.snackbar_removed_item),
            actionText = getString(R.string.snackbar_action_undo),
            anchor = true
        ) {
            viewModel.addTvToFavorites(tv)
        }
    }

    private suspend fun collectFavoriteTvs() {
        viewModel.favoriteTvs.collect { favoriteTvs ->
            adapterFavorites.submitList(favoriteTvs)
        }
    }
}