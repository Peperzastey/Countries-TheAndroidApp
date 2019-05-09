package com.ppoljanski.countries.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ppoljanski.countries.R
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ppoljanski.countries.viewmodel.CountriesViewModel

private const val TAG = "[pp]MainActivity"

class MainActivity : AppCompatActivity() {
    //TODO? move RecyclerView with its Adapter to a separate Fragment

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) { ViewModelProviders.of(this)[CountriesViewModel::class.java] }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { CountriesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Q in onCreateView callback?
        recyclerView.adapter = adapter
        //TODO if no internet connection/rest api error -> show this test data (ultimately: data from Room local database)
        viewModel.countries().observe(this, Observer {
            adapter.items = it
        })
    }

    @Suppress("UNUSED_PARAMETER")
    fun search(view: View) {
        val searchTerm = searchTextField.text.toString()
        viewModel.searchForCountries(searchTerm)
        //searchTextField.setText("")
    }

    fun showCountryDetails(view: View) {
        val itemPosition = recyclerView.getChildLayoutPosition(view)
        val country = adapter.items[itemPosition]
        //Toast.makeText(this, country.name, Toast.LENGTH_SHORT).show()

        startActivity(
            Intent(this, CountryDetailsActivity::class.java)
                .putExtra(COUNTRY_NAME_EXTRA, country.name)
        )
    }
}
