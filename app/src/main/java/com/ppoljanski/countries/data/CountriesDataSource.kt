package com.ppoljanski.countries.data

import com.ppoljanski.countries.model.Country
import com.ppoljanski.countries.model.CountryWithDetails

interface CountriesDataSource {

    //fun getAllCountries(): List<Country>

    fun getCountries(searchTerm: String? = null, onSuccess: (List<Country>) -> Unit, onError: (String) -> Unit)

    //TODO? here or other CountryDetailsDataSource?
    fun getCountryDetails(countryName: String, onSuccess: (CountryWithDetails) -> Unit, onError: (String) -> Unit)
}