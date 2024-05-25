package com.nbs.gemmaexample

import android.view.View
import java.util.concurrent.TimeUnit

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun getReadableResponseTime(start: Long, end: Long) : String {
    val seconds = TimeUnit.MILLISECONDS.toSeconds(end.minus(start))
    return "Response complete in $seconds seconds"
}