package com.pinto.mymovieappkt.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pinto.mymovieappkt.databinding.ItemMovieBinding
import com.pinto.mymovieappkt.databinding.ItemTrendingMovieBinding
import com.pinto.mymovieappkt.domain.model.Movie

class MovieAdapter(
    private val isGrid: Boolean = false,
    private val isCredits: Boolean = false,
    private val isTrending: Boolean = false,
    private val onTrendingFabClick: ((Int) -> Unit)? = null,
) : ListAdapter<Movie, RecyclerView.ViewHolder>(DiffCallback) {
    inner class HorizontalViewHolder private constructor(val view: ItemMovieBinding) :
        RecyclerView.ViewHolder(view.root) {
        constructor(parent: ViewGroup) : this(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class TrendingViewHolder private constructor(val view: ItemTrendingMovieBinding) :
        RecyclerView.ViewHolder(view.root) {
        constructor(parent: ViewGroup) : this(
            ItemTrendingMovieBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        ) {
            view.fabTrailer.setOnClickListener {
                onTrendingFabClick?.invoke(getItem(absoluteAdapterPosition).id)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isTrending) TrendingViewHolder(parent) else HorizontalViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HorizontalViewHolder -> {
                holder.view.apply {
                    isGrid = this@MovieAdapter.isGrid
                    isCredits = this@MovieAdapter.isCredits
                    movie = getItem(position)
                }
            }

            is TrendingViewHolder -> holder.view.movie = getItem(position)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem
    }
}