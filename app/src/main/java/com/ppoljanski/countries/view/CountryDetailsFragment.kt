package com.ppoljanski.countries.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ppoljanski.countries.R
import com.ppoljanski.countries.model.CountryWithDetails
import com.ppoljanski.countries.viewmodel.CountryDetailsViewModel
import com.ppoljanski.countries.databinding.FragmentCountryDetailsBinding
import com.ppoljanski.countries.viewmodel.databinding.CountryBindingComponent

class CountryDetailsFragment : Fragment() {

    private lateinit var viewModel: CountryDetailsViewModel
    private lateinit var viewBinding: FragmentCountryDetailsBinding
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(CountryDetailsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_country_details, container, false, CountryBindingComponent(viewModel))
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //detailsCountryName.movementMethod = ScrollingMovementMethod()
        viewModel.country().observe(this, Observer { displayCountryDetails(it) })
    }

    private fun displayCountryDetails(country: CountryWithDetails) {
        viewBinding.country = country
        centerMapOnCountry(country)
    }

    private fun centerMapOnCountry(country: CountryWithDetails) {
        val doCenterMapOnCountry = centerFun@{
            if (country.lat.isNaN() || country.lng.isNaN())
                return@centerFun
            val latLng = LatLng(country.lat, country.lng)
            map.addMarker(MarkerOptions().position(latLng).title(country.name))
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        }

        if (::map.isInitialized) {
            doCenterMapOnCountry()
        } else {
            val mapFragment = activity!!.supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync {
                googleMap ->
                map = googleMap
                doCenterMapOnCountry()
                viewModel.hideProgressbar()
            }
        }
    }
}
