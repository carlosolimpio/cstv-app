package com.carlosolimpio.cstv.presentation.common

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.carlosolimpio.cstv.R

fun ImageView.setImage(imageUrl: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(this)
        .load(imageUrl)
        .fitCenter()
        .placeholder(circularProgressDrawable)
        .error(R.drawable.icon_image_failed)
        .into(this)
}
