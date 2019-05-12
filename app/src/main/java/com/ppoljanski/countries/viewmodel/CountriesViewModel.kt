package com.ppoljanski.countries.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ppoljanski.countries.data.CountriesDataSource
import com.ppoljanski.countries.util.SingleLiveEvent
import com.ppoljanski.countries.model.Country
import com.ppoljanski.countries.restapi.RestCountriesFetcher

private const val TAG = "[pp]CountriesViewModel"

class CountriesViewModel : ViewModel() {

    private val dataSource: CountriesDataSource = RestCountriesFetcher()

    private val countries = MutableLiveData<List<Country>>()
    private val loadError = SingleLiveEvent<String>()
    private val progressbarVisible = MutableLiveData<Boolean>()

    init {
        loadAllCountries()
    }

    fun countries(): LiveData<List<Country>> = countries
    fun loadError(): LiveData<String> = loadError
    fun progressbarVisible(): LiveData<Boolean> = progressbarVisible

    fun searchForCountries(searchName: String) {
        progressbarVisible.value = true
        //TODO use Repository instead of DataSource (Repository should be responsible for talking with the appropriate DataSource)
        val searchTerm = if (searchName.isNotEmpty()) searchName else null
        dataSource.getCountries(searchTerm, ::onCountriesLoadSuccess, ::onCountriesLoadError)
    }

    private fun loadAllCountries() {
        progressbarVisible.value = true
        //TODO use Repository instead of DataSource (Repository should be responsible for talking with the appropriate DataSource)
        dataSource.getCountries(null, ::onCountriesLoadSuccess, ::onCountriesLoadError)
    }

    private fun onCountriesLoadSuccess(loadedCountries: List<Country>) {
        Log.d(TAG, loadedCountries.toString())
        countries.value = loadedCountries
        progressbarVisible.value = false
    }

    private fun onCountriesLoadError(errorMessage: String) {
        Log.d(TAG, errorMessage)
        loadError.value = errorMessage
        progressbarVisible.value = false
    }
}