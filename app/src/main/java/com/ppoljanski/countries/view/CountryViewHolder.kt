package com.ppoljanski.countries.view

import com.ppoljanski.countries.model.Country
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.viewholder_country.view.*

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(country: Country) = with(itemView) {
        txtCountryName.text = country.name
    }
}