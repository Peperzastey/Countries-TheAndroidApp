package com.ppoljanski.countries.model.datasource.restapi

import com.ppoljanski.countries.model.Country
import com.ppoljanski.countries.model.CountryWithDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RestCountriesApi {

    @GET("/rest/v2/all?fields=name")
    fun getAllCountriesName(): Call<List<Country>>

    @GET("/rest/v2/name/{name}?fields=name")
    fun getCountriesName(@Path("name") searchName: String): Call<List<Country>>

    // by alpha3Code instead of name?
    @GET("/rest/v2/name/{name}?fullText=true&fields=name;subregion;capital;area;population;languages;flag;latlng")
    fun getCountryDetails(@Path("name") countryName: String): Call<List<CountryWithDetails>>
}