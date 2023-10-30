package com.pinto.mymovieappkt.presentation.screen.tv_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pinto.mymovieappkt.R

class TvDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = TvDetailsFragment()
    }

    private lateinit var viewModel: TvDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TvDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}