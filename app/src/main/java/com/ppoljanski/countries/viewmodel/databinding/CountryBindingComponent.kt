package com.ppoljanski.countries.viewmodel.databinding

import androidx.databinding.DataBindingComponent
import com.ppoljanski.countries.viewmodel.CountryDetailsViewModel

class CountryBindingComponent(private val viewModel: CountryDetailsViewModel) : DataBindingComponent {

    override fun getCountryDetailsViewModel() = viewModel
}