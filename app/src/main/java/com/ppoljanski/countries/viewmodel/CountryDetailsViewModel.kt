package com.ppoljanski.countries.viewmodel

import android.app.Application
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.AndroidViewModel
import com.ppoljanski.countries.data.CountriesDataSource
import com.ppoljanski.countries.data.SingleLiveEvent
import com.ppoljanski.countries.model.CountryWithDetails
import com.ppoljanski.countries.restapi.ImageFetcher
import com.ppoljanski.countries.restapi.RestCountriesFetcher
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

private const val TAG = "[pp]DetailsViewModel"

class CountryDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val dataSource: CountriesDataSource = RestCountriesFetcher()
    private val imageFetcher = ImageFetcher()
    private val flagTarget = FlagTarget()

    private val country = MutableLiveData<CountryWithDetails>()
    private val flag = MutableLiveData<Drawable>()
    private val loadError = SingleLiveEvent<String>()
    private val progressbarVisible = MutableLiveData<Boolean>()

    fun country(): LiveData<CountryWithDetails> = country
    fun flag(): LiveData<Drawable> = flag
    fun loadError(): LiveData<String> = loadError
    fun progressbarVisible(): LiveData<Boolean> = progressbarVisible

    fun loadCountryDetails(countryName: String) {
        if (country.value?.name == countryName)
            return
        showProgressbar()
        dataSource.getCountryDetails(
            countryName, {
                Log.d(TAG, it.toString())
                country.value = it
                imageFetcher.getCountryFlag(country.value, flagTarget)
                // hide progressbar after map fragment is loaded
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

    private inner class FlagTarget : Target {

        val resources: Resources = getApplication<Application>().resources

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            flag.value = placeHolderDrawable
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            flag.value = errorDrawable
            loadError.value = "Failed to load flag image"
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            Log.d("BITMAP", "${bitmap?.toString()}")
            flag.value = BitmapDrawable(resources, bitmap)
        }

    }
}