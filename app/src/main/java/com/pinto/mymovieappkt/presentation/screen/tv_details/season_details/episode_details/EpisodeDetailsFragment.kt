package com.pinto.mymovieappkt.presentation.screen.tv_details.season_details.episode_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pinto.mymovieappkt.R

class EpisodeDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = EpisodeDetailsFragment()
    }

    private lateinit var viewModel: EpisodeDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_episode_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EpisodeDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}