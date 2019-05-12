package com.ppoljanski.countries.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.ppoljanski.countries.R
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ppoljanski.countries.viewmodel.CountriesViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) { ViewModelProviders.of(this)[CountriesViewModel::class.java] }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { CountriesAdapter() }

    private var dialogRef: DialogInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = adapter

        viewModel.countries().observe(this, Observer {
            adapter.items = it
        })
        viewModel.progressbarVisible().observe(this, Observer {
            progressbar.visibility = if (it == true) View.VISIBLE else View.GONE
        })
        viewModel.loadError().observe(this, Observer {
            dialogRef =
                AlertDialog.Builder(this)
                    .setTitle(R.string.error_dialog_title)
                    .setMessage(it)
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
        })
    }

    override fun onDestroy() {
        // dismiss not dismissed dialog to prevent window leak
        dialogRef?.dismiss()
        dialogRef = null
        super.onDestroy()
    }

    @Suppress("UNUSED_PARAMETER")
    fun search(view: View) {
        val searchTerm = searchTextField.text.toString()
        viewModel.searchForCountries(searchTerm)
    }

    fun showCountryDetails(view: View) {
        val itemPosition = recyclerView.getChildLayoutPosition(view)
        val country = adapter.items[itemPosition]

        startActivity(
            Intent(this, CountryDetailsActivity::class.java)
                .putExtra(COUNTRY_NAME_EXTRA, country.name)
        )
    }
}
