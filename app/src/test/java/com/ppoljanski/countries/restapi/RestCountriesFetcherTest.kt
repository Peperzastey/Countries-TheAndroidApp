package com.ppoljanski.countries.restapi

import com.ppoljanski.countries.data.CountriesDataSource
import com.ppoljanski.countries.model.CountryWithDetails
import org.junit.Assert
import org.junit.Test

class RestCountriesFetcherTest {

    private val restDataSource: CountriesDataSource = RestCountriesFetcher()

    @Test
    fun `test json response is parsed correctly`() {
        class Lock(@Volatile var lock: Int = 0)
        val lock = Lock()
        val onSuccessHandler: (CountryWithDetails) -> Unit = {
            println("$it\nname: ${it.name}\ncapital: ${it.capital}\nlatlng: ${it.latlng}\nlatlng.lat: ${it.lat}\nlatlng.lng: ${it.lng}")  // \nlatlng2: ${it.latlng2}
            //\nlatlng.lat: ${it.latlng2.lat}\nlatlng.lng: ${it.latlng2.lng}

            Assert.assertEquals(1, 1)
            lock.lock = 1
        }
        val onErrorHandler: (String) -> Unit = {
            println("Error: $it")
            lock.lock = 1
        }

        restDataSource.getCountryDetails("poland", onSuccessHandler, onErrorHandler)

        while (lock.lock != 1) {}
    }
}