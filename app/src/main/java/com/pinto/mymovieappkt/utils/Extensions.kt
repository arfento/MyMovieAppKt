package com.pinto.mymovieappkt.utils

import android.content.Context
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.pinto.mymovieappkt.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

fun String?.formatDate(): String {
    val outputFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.US)
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return if (!this.isNullOrEmpty()) outputFormat.format(inputFormat.parse(this)) else ""
}

fun Double.round(): Double = (this * 10.0).roundToInt() / 10.0

fun Double.asPercent(): String = "%${(this * 10).toInt()}"

fun Long.thousandsSeparator(context: Context): String = with(DecimalFormatSymbols()) {
    groupingSeparator = context.getString(R.string.thousand_separator).toCharArray()[0]
    DecimalFormat("#,###", this).format(this@thousandsSeparator)
}

fun Int?.formatTime(context: Context): String? = this?.let {
    when {
        it == 0 -> return null
        it >= 60 -> {
            val hours = it / 60
            val minutes = it % 60
            "${hours}${context.getString(R.string.hour_short)} ${
                if (minutes == 0) "" else "$minutes${
                    context.getString(
                        R.string.minute_short
                    )
                }"
            }"
        }

        else -> "${it}${context.getString(R.string.minute_short)}"
    }
}

fun RecyclerView.interceptTouch() {
    addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            return canScrollHorizontally(RecyclerView.FOCUS_FORWARD).let {
                rv.parent.requestDisallowInterceptTouchEvent(it && e.action == MotionEvent.ACTION_MOVE)
                if (!it) removeOnItemTouchListener(this)
                !it
            }
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        }
    })
}