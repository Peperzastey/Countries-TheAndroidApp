package com.ppoljanski.countries.model.datasource.restapi

import com.ppoljanski.countries.model.datasource.CountriesDataSource
import com.ppoljanski.countries.model.Country
import com.ppoljanski.countries.model.CountryWithDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestCountriesFetcher : CountriesDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val countriesApi = retrofit.create(RestCountriesApi::class.java)

    override fun getCountries(searchTerm: String?, onSuccess: (List<Country>) -> Unit, onError: (String) -> Unit) {
        val call = when(searchTerm) {
            null -> countriesApi.getAllCountriesName()
            else -> countriesApi.getCountriesName(searchTerm)
        }

        call.enqueue(object : Callback<List<Country>> {

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                onError(t.message ?: "No message")
            }

            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if (response.isSuccessful || response.code() == 404)
                    onSuccess(response.body() ?: emptyList())
                else
                    onError("Server response code: ${response.code()}")
            }
        })
    }

    override fun getCountryDetails(countryName: String, onSuccess: (CountryWithDetails) -> Unit, onError: (String) -> Unit) {
        val call = countriesApi.getCountryDetails(countryName)

        call.enqueue(object : Callback<List<CountryWithDetails>> {

            override fun onFailure(call: Call<List<CountryWithDetails>>, t: Throwable) {
                onError(t.message ?: "No message")
            }

            override fun onResponse(call: Call<List<CountryWithDetails>>, response: Response<List<CountryWithDetails>>) {
                if (response.isSuccessful) {
                    val countryDetails = response.body()?.getOrNull(0)
                    if (countryDetails == null) {
                        onError("Empty response body")
                    } else {
                        onSuccess(countryDetails)
                    }
                } else {
                    onError("Server response code: ${response.code()}")
                }
            }
        })
    }
}