package com.pinto.mymovieappkt.presentation.screen.fullscreen_image

import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.FragmentFullscreenImageBinding
import com.pinto.mymovieappkt.presentation.adapter.FullScreenImageAdapter
import com.pinto.mymovieappkt.presentation.base.BaseFragment
import com.pinto.mymovieappkt.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class FullscreenImageFragment :
    BaseFragment<FragmentFullscreenImageBinding>(R.layout.fragment_fullscreen_image) {
    val isFullscreen = MutableStateFlow(false)
    val imagePositionText = MutableStateFlow("")

    override val viewModel: BaseViewModel?
        get() = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: FullscreenImageFragmentArgs by navArgs()
        val imageList = args.imageList.toList()
        val imagePosition = args.imagePosition
        val totalImageCount = imageList.size

        binding.vpImages.apply {
            adapter =
                FullScreenImageAdapter { toggleUiVisibility() }.apply { submitList(imageList) }
            setCurrentItem(imagePosition, false)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) {
                    imagePositionText.value = "${position + 1}/$totalImageCount"
                }
            })
        }
    }

    private fun toggleUiVisibility() {
        if (isFullscreen.value) showUi() else hideUi()
    }

    private fun hideUi() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(requireActivity().window, binding.frameLayout).let {
            it.hide(WindowInsetsCompat.Type.systemBars())
            it.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        isFullscreen.value = true
    }

    private fun showUi() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
        WindowInsetsControllerCompat(requireActivity().window, binding.frameLayout).show(
            WindowInsetsCompat.Type.systemBars()
        )
        isFullscreen.value = false


    }

}