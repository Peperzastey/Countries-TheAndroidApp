package com.ppoljanski.countries.restapi

import com.ppoljanski.countries.data.CountriesDataSource
import com.ppoljanski.countries.model.Country
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestCountriesFetcher : CountriesDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.eu")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val countriesApi = retrofit.create(RestContriesApi::class.java)

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
                if (response.isSuccessful)
                    onSuccess(response.body() ?: emptyList())
                else
                    onError("Server response code: ${response.code()}")
            }
        })
    }

    /*override fun getAllCountries(): List<Country> {

    }*/
}