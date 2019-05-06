package com.ppoljanski.countries.restapi

import com.ppoljanski.countries.model.Country
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RestContriesApi {

    @GET("/rest/v2/all?fields=name")
    fun getAllCountriesName(): Call<List<Country>>

    @GET("/rest/v2/name/{name}?fields=name")
    fun getCountriesName(@Path("name") searchName: String): Call<List<Country>>
}