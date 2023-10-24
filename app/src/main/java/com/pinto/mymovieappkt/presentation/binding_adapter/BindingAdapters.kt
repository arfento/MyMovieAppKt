package com.pinto.mymovieappkt.presentation.binding_adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.os.bundleOf
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.borabor.movieapp.domain.model.Image
import com.borabor.movieapp.domain.model.Person
import com.borabor.movieapp.domain.model.Video
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.pinto.mymovieappkt.R
import com.pinto.mymovieappkt.domain.model.Movie
import com.pinto.mymovieappkt.domain.model.Tv
import com.pinto.mymovieappkt.utils.Constants
import com.pinto.mymovieappkt.utils.Content
import com.pinto.mymovieappkt.utils.Detail
import com.pinto.mymovieappkt.utils.ImageQuality
import com.pinto.mymovieappkt.utils.InfiniteScrollListener
import com.pinto.mymovieappkt.utils.interceptTouch


@BindingAdapter(
    "detailType",
    "detailId",
    "detailImageUrl",
    "seasonNumber",
    "episodeNumber",
    requireAll = false
)
fun View.setDetailsNavigation(
    detailType: Detail,
    id: Int,
    imageUrl: String?,
    seasonNumber: Int?,
    episodeNumber: Int?,
) {
    var backgroundColor = ContextCompat.getColor(context, R.color.day_night_inverse)

    imageUrl?.let {
        Glide.with(context)
            .asBitmap()
            .load("https://image.tmdb.org/t/p/w92$it")
            .priority(Priority.HIGH)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }
}

@SuppressLint("CheckResult")
@BindingAdapter(
    "imageUrl",
    "imageQuality",
    "centerCrop",
    "fitTop",
    "isThumbnail",
    "errorImage",
    requireAll = false
)
fun ImageView.loadImage(
    posterPath: String?,
    quality: ImageQuality?,
    centerCrop: Boolean?,
    fitTop: Boolean,
    isThumbnail: Boolean,
    errorImage: Drawable?,
) {
}

@BindingAdapter("isVisible")
fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("isNested")
fun ViewPager2.handleNestedScroll(isNested: Boolean) {
    if (isNested) {
        val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewField.isAccessible = true
        val recyclerView = recyclerViewField.get(this) as RecyclerView
        recyclerView.interceptTouch()

    }
}



@BindingAdapter("isNested")
fun RecyclerView.handleNestedScroll(isNested: Boolean) {
    if (isNested) interceptTouch()
}

@BindingAdapter("fixedSize")
fun RecyclerView.setFixedSize(hasFixedSize: Boolean) {
    setHasFixedSize(hasFixedSize)
}

@BindingAdapter("type", "isGrid", "loadMore", "shouldLoadMore", requireAll = false)
fun RecyclerView.addInfiniteScrollListener(
    type: Any?,
    isGrid: Boolean,
    infiniteScroll: InfiniteScrollListener,
    shouldLoadMore: Boolean
) {
    if (shouldLoadMore) {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private val layoutManagerType = if(isGrid) layoutManager as GridLayoutManager else layoutManager as LinearLayoutManager
            private val visibleThreshold = 10
            private var loading = true
            private var previousTotal = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val visibleItemCount = layoutManagerType.childCount
                val totalItemCount = layoutManagerType.itemCount
                val firstVisibleItem = layoutManagerType.findFirstVisibleItemPosition()

                if (totalItemCount < previousTotal) previousTotal = 0

                if (loading && totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }

                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)){
                    infiniteScroll.onLoadMore(type)
                    loading = true
                }

            }
        })
    }
}

@BindingAdapter("transparentBackground")
fun View.setTransparentBackground(backgroundColor: Int) {
    setBackgroundColor(ColorUtils.setAlphaComponent(backgroundColor, 220))
}

@BindingAdapter(
    "contentType",
    "seeAllDetailType",
    "genreId",
    "stringId",
    "title",
    "backgroundColor",
    "region",
    "videoList",
    "castList",
    "imageList",
    "personMovieCreditsList",
    "personTvCreditsList",
    "movieRecommendationsList",
    "tvRecommendationsList",
    requireAll = false
)
fun View.setSeeAllNavigation(
    contentType: Content,
    detailType: Detail?,
    genreId: Int?,
    stringId: String?,
    title: String,
    backgroundColor: Int,
    region: String?,
    videoList: List<Video>?,
    castList: List<Person>?,
    imageList: List<Image>?,
    personMovieCreditsList: List<Movie>?,
    personTvCreditsList: List<Tv>?,
    movieRecommendationsList: List<Movie>?,
    tvRecommendationsList: List<Tv>?,
) {

    setOnClickListener {
        val bundle = bundleOf(
            Constants.CONTENT_TYPE to contentType as Parcelable,
            Constants.DETAIL_TYPE to detailType as Parcelable?,
            Constants.GENRE_ID to (genreId ?: 0),
            Constants.LIST_ID to stringId,
            Constants.TITLE to title,
            Constants.BACKGROUND_COLOR to backgroundColor,
            Constants.REGION to region as Parcelable,
            Constants.VIDEO_LIST to videoList?.toTypedArray(),
            Constants.IMAGE_LIST to imageList?.toTypedArray(),
            Constants.CAST_LIST to castList?.toTypedArray(),
            Constants.PERSON_MOVIE_CREDITS_LIST to personMovieCreditsList?.toTypedArray(),
            Constants.PERSON_TV_CREDITS_LIST to personTvCreditsList?.toTypedArray(),
            Constants.MOVIE_RECOMMENDATIONS_LIST to movieRecommendationsList?.toTypedArray(),
            Constants.TV_RECOMMENDATIONS_LIST to tvRecommendationsList?.toTypedArray(),
        )

    }

}



