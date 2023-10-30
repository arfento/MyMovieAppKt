package com.pinto.mymovieappkt.presentation.screen.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.FragmentSearchBinding
import com.pinto.mymovieappkt.presentation.adapter.MovieAdapter
import com.pinto.mymovieappkt.presentation.adapter.PersonAdapter
import com.pinto.mymovieappkt.presentation.adapter.TvAdapter
import com.pinto.mymovieappkt.presentation.base.BaseFragment
import com.pinto.mymovieappkt.presentation.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    override val viewModel: BaseViewModel? by viewModels()

    val adapterMovies by lazy { MovieAdapter() }
    val adapterTvs by lazy { TvAdapter() }
    val adapterPeople by lazy { PersonAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageRecyclerViewAdapterLifecycle(

        )
    }


}