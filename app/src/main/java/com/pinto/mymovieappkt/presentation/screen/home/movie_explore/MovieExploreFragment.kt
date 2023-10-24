package com.pinto.mymovieappkt.presentation.screen.home.movie_explore

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinto.mymovieappkt.R

class MovieExploreFragment : Fragment() {

    companion object {
        fun newInstance() = MovieExploreFragment()
    }

    private lateinit var viewModel: MovieExploreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_explore, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieExploreViewModel::class.java)
        // TODO: Use the ViewModel
    }

}