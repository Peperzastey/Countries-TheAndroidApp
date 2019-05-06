package com.ppoljanski.countries.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ppoljanski.countries.data.CountriesDataSource
import com.ppoljanski.countries.model.Country
import com.ppoljanski.countries.restapi.RestCountriesFetcher

private const val TAG = "[pp]CountriesViewModel"

class CountriesViewModel : ViewModel() {

    private val dataSource: CountriesDataSource = RestCountriesFetcher()

    private val countries: MutableLiveData<List<Country>> = MutableLiveData()
    //TODO SingleLiveEvent for loadError

    init {
        loadAllCountries()
    }

    fun countries(): LiveData<List<Country>> = countries

    private fun loadAllCountries() {
        //TODO use Repository instead of DataSource (Repository should be responsible for talking with the appropriate DataSource)
        dataSource.getCountries(null, { Log.d(TAG, it.toString()); countries.value = it }, { Log.e(TAG, it) })
    }
}