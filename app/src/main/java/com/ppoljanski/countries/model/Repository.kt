package com.ppoljanski.countries.model

import com.ppoljanski.countries.model.datasource.CountriesDataSource
import com.ppoljanski.countries.model.datasource.restapi.RestCountriesFetcher

/**
 * Responsible for talking with the appropriate DataSources.
 */
object Repository {

    private val restapiDataSource: CountriesDataSource = RestCountriesFetcher()
    //possible enhancement: roomDataSource - storing country data for offline mode
    //possible enhancement: use paging library to load data in chunks

    fun getCountries(searchTerm: String? = null, onSuccess: (List<Country>) -> Unit, onError: (String) -> Unit) {
        restapiDataSource.getCountries(searchTerm, onSuccess, onError)
    }

    fun getCountryDetails(countryName: String, onSuccess: (CountryWithDetails) -> Unit, onError: (String) -> Unit) {
        restapiDataSource.getCountryDetails(countryName, onSuccess, onError)
    }
}
