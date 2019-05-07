package com.ppoljanski.countries.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ppoljanski.countries.data.CountriesDataSource
import com.ppoljanski.countries.model.CountryWithDetails
import com.ppoljanski.countries.restapi.RestCountriesFetcher

private const val TAG = "[pp]DetailsViewModel"

class CountryDetailsViewModel : ViewModel() {

    private val dataSource: CountriesDataSource = RestCountriesFetcher()
    private val country = MutableLiveData<CountryWithDetails>()

    fun country(): LiveData<CountryWithDetails> = country

    fun loadCountryDetails(countryName: String) {
        if (country.value?.name == countryName)
            return
        dataSource.getCountryDetails(countryName, { Log.d(TAG, it.toString()); country.value = it }, { Log.e(TAG, it) })
    }
}