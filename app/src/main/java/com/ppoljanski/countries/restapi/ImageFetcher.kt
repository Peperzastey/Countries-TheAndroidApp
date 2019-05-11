package com.ppoljanski.countries.restapi

import android.content.Context
import com.ppoljanski.countries.R
import com.ppoljanski.countries.model.CountryWithDetails
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class ImageFetcher/*(private val context: Context)*/ {

    fun getCountryFlag(country: CountryWithDetails?, imageTarget: Target) {
        Picasso.get()
            .load(country?.flag)
            .placeholder(R.drawable.flag_loading_placeholder)
            .error(R.drawable.flag_loading_error)
            .into(imageTarget)
    }
}