package com.pinto.mymovieappkt.presentation.screen.movie_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.FragmentMovieDetailsBinding
import com.pinto.mymovieappkt.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding>(R.layout.fragment_movie_details) {

    override val viewModel: MovieDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}