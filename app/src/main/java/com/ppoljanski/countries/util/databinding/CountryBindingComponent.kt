package com.ppoljanski.countries.util.databinding

import androidx.databinding.DataBindingComponent
import com.ppoljanski.countries.viewmodel.CountryDetailsViewModel

class CountryBindingComponent(private val viewModel: CountryDetailsViewModel) : DataBindingComponent {

    override fun getCountryDetailsViewModel() = viewModel
}