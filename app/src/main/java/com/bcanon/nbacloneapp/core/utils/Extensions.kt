package com.bcanon.nbacloneapp.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.url(url: String) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .into(this)
}