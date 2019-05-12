package com.ppoljanski.countries.model.datasource

import com.ppoljanski.countries.model.Country
import com.ppoljanski.countries.model.CountryWithDetails

interface CountriesDataSource {

    fun getCountries(searchTerm: String? = null, onSuccess: (List<Country>) -> Unit, onError: (String) -> Unit)

    fun getCountryDetails(countryName: String, onSuccess: (CountryWithDetails) -> Unit, onError: (String) -> Unit)
}