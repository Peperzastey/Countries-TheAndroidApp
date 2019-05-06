package com.ppoljanski.countries.data

import com.ppoljanski.countries.model.Country

interface CountriesDataSource {

    //fun getAllCountries(): List<Country>

    fun getCountries(searchTerm: String? = null, onSuccess: (List<Country>) -> Unit, onError: (String) -> Unit)
}