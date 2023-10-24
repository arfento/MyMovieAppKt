package com.pinto.mymovieappkt.presentation.screen.favorites.favorite_movies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinto.mymovieappkt.R

class FavoriteMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteMoviesFragment()
    }

    private lateinit var viewModel: FavoriteMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteMoviesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}