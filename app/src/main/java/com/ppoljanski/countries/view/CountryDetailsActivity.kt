package com.ppoljanski.countries.view

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.common.ConnectionResult.SUCCESS
import com.google.android.gms.common.GoogleApiAvailability
import com.ppoljanski.countries.R
import com.ppoljanski.countries.viewmodel.CountryDetailsViewModel
import kotlinx.android.synthetic.main.activity_country_details.*

const val COUNTRY_NAME_EXTRA = "countryName"

//private const val GOOGLE_PLAY_SERVICES_ERROR_REQUEST_CODE = 1

private const val TAG = "[pp]DetailsActivity"

class CountryDetailsActivity : AppCompatActivity() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) { ViewModelProviders.of(this)[CountryDetailsViewModel::class.java] }

    private var dialogRef: DialogInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)

        /*val googleApiAvailability = GoogleApiAvailability.getInstance()
        val statusCode = googleApiAvailability.isGooglePlayServicesAvailable(this)
        if (statusCode != SUCCESS) {
            dialogRef = googleApiAvailability.getErrorDialog(this, statusCode, GOOGLE_PLAY_SERVICES_ERROR_REQUEST_CODE)
                .also { it.show() }
        }*/
        //TODO onActivityResult <- GOOGLE_PLAY_SERVICES_ERROR_REQUEST_CODE

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
}
