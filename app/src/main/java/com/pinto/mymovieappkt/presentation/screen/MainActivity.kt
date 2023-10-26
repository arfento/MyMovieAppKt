package com.pinto.mymovieappkt.presentation.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pinto.mymovieappkt.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}