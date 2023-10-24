package com.pinto.mymovieappkt.presentation.screen.favorites.favorite_tvs

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinto.mymovieappkt.R

class FovoriteTvsFragment : Fragment() {

    companion object {
        fun newInstance() = FovoriteTvsFragment()
    }

    private lateinit var viewModel: FovoriteTvsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_fovorite_tvs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FovoriteTvsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}