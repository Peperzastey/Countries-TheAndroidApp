package com.ppoljanski.countries.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ppoljanski.countries.R
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
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

    /*fun search(view: View) {
        val searchTerm = searchTextField.text
        startActivity(Intent(this, CountryDetailsActivity::class.java))
    }*/

    fun showCountryDetails(view: View) {
        val itemPosition = recyclerView.getChildLayoutPosition(view)
        val item = adapter.items.get(itemPosition)
        Toast.makeText(this, item.name, Toast.LENGTH_SHORT).show()
    }
}
