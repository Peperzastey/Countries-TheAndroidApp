package com.ppoljanski.countries.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ppoljanski.countries.model.Repository
import com.ppoljanski.countries.util.SingleLiveEvent
import com.ppoljanski.countries.model.Country

private const val TAG = "[pp]CountriesViewModel"

class CountriesViewModel : ViewModel() {

    private val repository = Repository

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
        val searchTerm = if (searchName.isNotEmpty()) searchName else null
        repository.getCountries(searchTerm, ::onCountriesLoadSuccess, ::onCountriesLoadError)
    }

    private fun loadAllCountries() {
        progressbarVisible.value = true
        repository.getCountries(null, ::onCountriesLoadSuccess, ::onCountriesLoadError)
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