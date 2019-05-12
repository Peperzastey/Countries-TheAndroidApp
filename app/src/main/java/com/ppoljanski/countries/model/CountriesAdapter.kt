package com.ppoljanski.countries.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ppoljanski.countries.R
import com.ppoljanski.countries.view.CountryViewHolder

class CountriesAdapter : RecyclerView.Adapter<CountryViewHolder>() {

    var items = emptyList<Country>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size
}