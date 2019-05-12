package com.ppoljanski.countries.model

data class CountryWithDetails(
    val name: String,
    val subregion: String,
    val capital: String,
    val area: Float,
    val population: Long,
    val languages: List<Language>,
    val flag: String,
    val latlng: List<Double>) {

    val lat
        get() = if (latlng.size == 2) latlng[0] else Double.NaN
    val lng
        get() = if (latlng.size == 2) latlng[1] else Double.NaN

    // convert to string convenience getters for use in data binding
    fun areaString() = area.toString()
    fun populationString() = population.toString()
    fun languagesString() = languages.map { it.name }.joinToString()
}

data class Language(
    val iso639_1: String,
    val iso639_2: String,
    val name: String,
    val nativeName: String
)
