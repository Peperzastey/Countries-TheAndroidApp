package com.ppoljanski.countries.viewmodel

import android.app.Application
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ppoljanski.countries.R
import com.ppoljanski.countries.model.Repository
import com.ppoljanski.countries.util.SingleLiveEvent
import com.ppoljanski.countries.model.CountryWithDetails
import com.ppoljanski.countries.util.ImageLoader
import com.ppoljanski.countries.util.svgloading.SvgSoftwareLayerSetter

private const val TAG = "[pp]DetailsViewModel"

class CountryDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository
    private val imageLoader = ImageLoader()

    private val country = MutableLiveData<CountryWithDetails>()
    private val loadError = SingleLiveEvent<String>()
    private val progressbarVisible = MutableLiveData<Boolean>()

    private var storedFlagDrawable: Drawable? = null
    private var storedFlagUrl: String? = null

    fun country(): LiveData<CountryWithDetails> = country
    fun loadError(): LiveData<String> = loadError
    fun progressbarVisible(): LiveData<Boolean> = progressbarVisible

    fun loadCountryDetails(countryName: String) {
        if (country.value?.name == countryName)
            return
        showProgressbar()
        repository.getCountryDetails(
            countryName, {
                Log.d(TAG, it.toString())
                country.value = it
                // progressbar is hidden after map fragment is loaded
            }, {
                Log.e(TAG, it)
                loadError.value = it
                hideProgressbar()
            })
    }

    fun showProgressbar() {
        progressbarVisible.value = true
    }

    fun hideProgressbar() {
        progressbarVisible.value = false
    }

    @BindingAdapter("imageUrl")
    fun loadFlagImage(view: ImageView, url: String?) {
        if (url == null)
            return
        if (storedFlagUrl == url) {
            Log.d(TAG, "apply stored flag image")
            view.setImageDrawable(storedFlagDrawable)
        } else {
            // fetch flag image from the web
            Log.d(TAG, "fetch flag image")
            imageLoader.loadSvgImage(view, url, StoringRequestListener())
        }
    }

    inner class StoringRequestListener : RequestListener<PictureDrawable> {

        override fun onLoadFailed(ex: GlideException?, model: Any?, target: Target<PictureDrawable>?, isFirstResource: Boolean): Boolean = false

        override fun onResourceReady(resource: PictureDrawable?, model: Any?, target: Target<PictureDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            storedFlagDrawable = resource
            storedFlagUrl = model as String
            return false
        }
    }
}