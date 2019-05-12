package com.ppoljanski.countries.view

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.PictureDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.google.android.gms.maps.CameraUpdateFactory
import com.ppoljanski.countries.R
import com.ppoljanski.countries.viewmodel.CountryDetailsViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ppoljanski.countries.model.CountryWithDetails
import com.ppoljanski.countries.util.svgloading.SvgSoftwareLayerSetter
import kotlinx.android.synthetic.main.activity_country_details.*
import kotlinx.android.synthetic.main.details_country.*

const val COUNTRY_NAME_EXTRA = "countryName"

private const val TAG = "[pp]DetailsActivity"

class CountryDetailsActivity : AppCompatActivity() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) { ViewModelProviders.of(this)[CountryDetailsViewModel::class.java] }

    private var dialogRef: DialogInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)

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

        val countryName = intent.getStringExtra(COUNTRY_NAME_EXTRA) ?: throw IllegalArgumentException("Country name must be provided to CountryDetailsActivity")
        Log.d(TAG, "countryName extra: $countryName")
        viewModel.loadCountryDetails(countryName)
    }

    override fun onDestroy() {
        // dismiss not dismissed dialog to prevent window leak
        dialogRef?.dismiss()
        dialogRef = null
        super.onDestroy()
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
