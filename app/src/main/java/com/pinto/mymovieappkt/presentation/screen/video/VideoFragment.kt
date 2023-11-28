package com.pinto.mymovieappkt.presentation.screen.video

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.maxrave.kotlinyoutubeextractor.YTExtractor
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.FragmentVideoBinding
import com.pinto.mymovieappkt.presentation.base.BaseFragment
import com.pinto.mymovieappkt.presentation.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoFragment : BaseFragment<FragmentVideoBinding>(R.layout.fragment_video) {

    override val viewModel: BaseViewModel?
        get() = null
    private val args by navArgs<VideoFragmentArgs>()

    private val urlsList = mutableListOf<String>()


    private var playWhenReady = true
    private var mediaItemIndex = 0
    private var playbackPosition = 0L

    private val player: ExoPlayer? by lazy {
        ExoPlayer.Builder(requireContext()).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragmentContainerView
            scrimColor = Color.TRANSPARENT
            duration = 500
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun initForVideo() {
        urlsList.clear()

        args.url.split("**").forEach { urlsList.add(it) }
        player?.also {
            binding.playerView.player = it
        }
        Log.d("url image", "message url image: vpImages ${args.url}, $urlsList")
        val mediaSources = mutableListOf<MediaSource>()
        urlsList.forEach {
            viewLifecycleOwner.lifecycleScope.launch {
                val ytFiles = YTExtractor(requireContext()).getYtFile(it)
                val streamUrl = ytFiles?.get(22)?.url
                val video = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                    .createMediaSource(MediaItem.fromUri(streamUrl!!))
                mediaSources.add(video)
                player?.addMediaSource(video)
            }
        }
        player?.playWhenReady = playWhenReady
        player?.prepare()

    }

    private fun releasePlayer() {
        player?.let { player ->
            playbackPosition = player.currentPosition
            mediaItemIndex = player.currentMediaItemIndex
            playWhenReady = player.playWhenReady
            player.release()
        }
        player?.clearMediaItems()
        player?.clearVideoSurface()
        urlsList.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onStart() {
        super.onStart()
        initForVideo()
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (player == null) {
            initForVideo()
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(
            requireActivity().window,
            binding.playerView
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}