package com.ppoljanski.countries.util

import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.ppoljanski.countries.R
import com.ppoljanski.countries.util.svgloading.SvgSoftwareLayerSetter

class ImageLoader {

    fun loadSvgImage(toView: ImageView, imageUrl: String, listener: RequestListener<PictureDrawable>) {
        val requestBuilder = Glide.with(toView.context)
            .`as`(PictureDrawable::class.java)
            .placeholder(R.drawable.flag_loading_placeholder)
            .error(R.drawable.flag_loading_error)
            .transition(DrawableTransitionOptions.withCrossFade())
            .addListener(SvgSoftwareLayerSetter())

        requestBuilder
            .load(imageUrl)
            .addListener(listener) // listener is called on the main thread
            .into(toView)
    }
}