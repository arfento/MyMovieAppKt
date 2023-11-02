package com.pinto.mymovieappkt.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.databinding.ItemVideoBinding
import com.pinto.mymovieappkt.domain.model.Video

class VideoAdapter(
    private val isGrid: Boolean = false,
    private val onItemClick: (String) -> Unit,
) : ListAdapter<Video, VideoAdapter.ViewHolder>(DiffCallback) {
    inner class ViewHolder(val view: ItemVideoBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            view.linearLayout.setOnClickListener {
                onItemClick.invoke(getItem(absoluteAdapterPosition).key)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_video, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.apply {
            video = getItem(position)
            isGrid = this@VideoAdapter.isGrid
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean =
            oldItem.key == newItem.key

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean =
            oldItem == newItem
    }
}