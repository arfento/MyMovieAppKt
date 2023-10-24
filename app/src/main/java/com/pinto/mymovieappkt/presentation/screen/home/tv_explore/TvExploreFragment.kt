package com.pinto.mymovieappkt.presentation.screen.home.tv_explore

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinto.mymovieappkt.R

class TvExploreFragment : Fragment() {

    companion object {
        fun newInstance() = TvExploreFragment()
    }

    private lateinit var viewModel: TvExploreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_explore, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TvExploreViewModel::class.java)
        // TODO: Use the ViewModel
    }

}