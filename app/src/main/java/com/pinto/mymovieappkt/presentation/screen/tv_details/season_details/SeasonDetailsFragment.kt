package com.pinto.mymovieappkt.presentation.screen.tv_details.season_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pinto.mymovieappkt.R

class SeasonDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = SeasonDetailsFragment()
    }

    private lateinit var viewModel: SeasonDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_season_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SeasonDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}