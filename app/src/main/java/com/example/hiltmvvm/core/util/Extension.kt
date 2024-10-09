package com.example.hiltmvvm.core.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.hiltmvvm.R


@BindingAdapter("imageUrl")
fun ImageView.loadImage(
    url: String?,
) {
    url?.let {
        Glide.with(context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.photo_background)
                    .error(R.drawable.photo_error)
                    .transform(CenterCrop(), RoundedCorners(20))
            )
            .load(it)
            .into(this)
    }
}