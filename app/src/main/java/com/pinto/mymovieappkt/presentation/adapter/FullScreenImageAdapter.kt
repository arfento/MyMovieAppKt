package com.pinto.mymovieappkt.presentation.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.ItemFullscreenBinding
import com.pinto.mymovieappkt.domain.model.Image

class FullScreenImageAdapter(
    private val onClick: () -> Unit,
) : ListAdapter<Image, FullScreenImageAdapter.ViewHolder>(DiffCallback) {
    inner class ViewHolder(val view: ItemFullscreenBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            view.photoView.setOnClickListener { onClick() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_fullscreen,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.apply {
            image = getItem(position)
            buttonDownload.setOnClickListener {
                Log.d(TAG, "button download clicked")
//                showSnackbar(
//                    message = getString(R.string.trending_trailer_error),
//                    indefinite = false,
//                    anchor = true
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem.filePath == newItem.filePath

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem == newItem
    }
}