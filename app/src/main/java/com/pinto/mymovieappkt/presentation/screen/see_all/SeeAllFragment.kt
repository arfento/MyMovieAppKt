package com.pinto.mymovieappkt.presentation.screen.see_all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pinto.mymovieappkt.R

class SeeAllFragment : Fragment() {

    companion object {
        fun newInstance() = SeeAllFragment()
    }

    private lateinit var viewModel: SeeAllViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_see_all, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SeeAllViewModel::class.java)
        // TODO: Use the ViewModel
    }

}