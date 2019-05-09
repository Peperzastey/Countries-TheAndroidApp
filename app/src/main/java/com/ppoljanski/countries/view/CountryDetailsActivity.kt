package com.ppoljanski.countries.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.ppoljanski.countries.R
import com.ppoljanski.countries.viewmodel.CountryDetailsViewModel
import kotlinx.android.synthetic.main.activity_country_details.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ppoljanski.countries.model.CountryWithDetails

const val COUNTRY_NAME_EXTRA = "countryName"

private const val TAG = "[pp]DetailsActivity"

class CountryDetailsActivity : AppCompatActivity() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) { ViewModelProviders.of(this)[CountryDetailsViewModel::class.java] }
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)

        viewModel.country().observe(this, Observer { displayCountryDetails(it) })

        val countryName = intent.getStringExtra(COUNTRY_NAME_EXTRA) ?: throw IllegalArgumentException("Country name must be provided to CountryDetailsActivity")
        Log.d(TAG, "countryName extra: $countryName")
        viewModel.loadCountryDetails(countryName)
    }

    private fun displayCountryDetails(country: CountryWithDetails) {
        detailsCountryName.text = country.name
        detailsSubregion.text = country.subregion
        detailsCapitalCity.text = country.capital
        detailsArea.text = country.area.toString()
        detailsPopulation.text = country.population.toString()
        detailsLanguages.text = country.languages.map { it.name }.joinToString()
        centerMapOnCountry(country)
    }

    private fun centerMapOnCountry(country: CountryWithDetails) {
        val doCenterMapOnCountry = {
            val latLng = LatLng(country.lat, country.lng)
            //map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5F)) /*CountryBounds.getCenter() -- LatLngBounds*/
            map.addMarker(MarkerOptions().position(latLng).title(country.name))
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        }

        if (::map.isInitialized) {  //TODO this::?
            doCenterMapOnCountry()
        } else {
            //TODO? Create and add Fragment programmatically?
            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync {
                googleMap ->
                map = googleMap
                doCenterMapOnCountry()
            }
        }
    }

    companion object {

        fun showDetailsFor(countryName: String, parentActivity: Activity) {
            parentActivity.startActivity(
                Intent(parentActivity, CountryDetailsActivity::class.java)
                    .putExtra(COUNTRY_NAME_EXTRA, countryName)
            )
        }
    }
}
