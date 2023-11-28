package com.pinto.mymovieappkt.presentation.screen.fullscreen_image

import android.Manifest
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import androidx.work.BackoffPolicy
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.transition.MaterialContainerTransform
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.FragmentFullscreenImageBinding
import com.pinto.mymovieappkt.presentation.adapter.FullScreenImageAdapter
import com.pinto.mymovieappkt.presentation.base.BaseFragment
import com.pinto.mymovieappkt.presentation.base.BaseViewModel
import com.pinto.mymovieappkt.presentation.screen.fullscreen_image.worker.DownloadImageWorker
import com.pinto.mymovieappkt.utils.checkPermission

import com.pinto.mymovieappkt.utils.unAvailableFeature
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.Duration

class FullscreenImageFragment :
    BaseFragment<FragmentFullscreenImageBinding>(R.layout.fragment_fullscreen_image) {
    val isFullscreen = MutableStateFlow(false)
    val imagePositionText = MutableStateFlow("")

    private val args: FullscreenImageFragmentArgs by navArgs()

    override val viewModel: BaseViewModel?
        get() = null

    private lateinit var oneTimeWork: OneTimeWorkRequest

    //
    private val saveImageUnavailableMessage = "Save Image feature is unavailable " +
            "because you denied the Permission, you can Grant us the Permission to save the image " +
            "to your Device."

    private val saveImageNeededMessage =
        "This permission is needed to write to your contacts. to help you to hide them from Social Media apps\n" +
                "this permission is primary in the app the whole app is relying on it."

    //
    private lateinit var saveImagePermissionLauncher: ActivityResultLauncher<String>
    private lateinit var url: String
    private val urlsList = mutableListOf<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        saveImagePermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    WorkManager.getInstance(requireContext()).enqueue(oneTimeWork)
                } else {
                    requireContext().unAvailableFeature(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        saveImageUnavailableMessage,
                        saveImagePermissionLauncher
                    )
                }
            }
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragmentContainerView
            scrimColor = Color.TRANSPARENT
            duration = 500
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = args.imageList.toList()
        val imagePosition = args.imagePosition
        val totalImageCount = imageList.size

        binding.vpImages.apply {
            adapter =
                FullScreenImageAdapter {
                    toggleUiVisibility()

                }.apply { submitList(imageList) }
            setCurrentItem(imagePosition, false)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) {
                    imagePositionText.value = "${position + 1}/$totalImageCount"
                    url =
                        "https://image.tmdb.org/t/p/original" + args.imageList[position].filePath
                    Log.d("url image", "message url image: vpImages $url")
                    initWorker()
                }
            })

        }

        binding.btnDownload.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                requireContext().checkPermission(
                    this@FullscreenImageFragment,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    {
                        WorkManager.getInstance(requireContext())
                            .enqueue(oneTimeWork)
                    },
                    saveImageNeededMessage,
                    saveImagePermissionLauncher
                )
            } else {
                WorkManager.getInstance(requireContext()).enqueue(oneTimeWork)
            }
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initWorker() {
        val data = Data.Builder()
            .putString("imageUrl", url)
            .build()
        oneTimeWork = OneTimeWorkRequestBuilder<DownloadImageWorker>()
            .setInputData(data)
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.LINEAR,
                duration = Duration.ofSeconds(10)
            )
            .build()
    }


}