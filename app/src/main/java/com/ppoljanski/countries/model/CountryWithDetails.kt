package com.ppoljanski.countries.model

data class CountryWithDetails(
    val name: String,
    val subregion: String,
    val capital: String,
    val area: Float,
    val population: Long,   //TODO try String
    val languages: List<Language>,    //try List<String> ?
    val latlng: List<Double>) {

    val lat
        get() = latlng[0]
    val lng
        get() = latlng[1]

    init {
        if (latlng.size != 2)
            throw IllegalArgumentException("latlng DoubleArray constructor argument must have 2 elements")
    }
}

data class Language(
    val iso639_1: String,
    val iso639_2: String,
    val name: String,
    val nativeName: String
)

//class LatLng(latlng: List<Double>) {
//    val lat: Double
//    val lng: Double
//
//    init {
//        if (latlng.size != 2)
//            throw IllegalArgumentException("latlng DoubleArray constructor argument must have 2 elements")
//        lat = latlng[0]
//        lng = latlng[1]
//    }
//}

//class LatLng(private val latlng: List<Double>) {
//    val lat
//        get() = latlng[0]
//    val lng
//        get() = latlng[1]
//
//    init {
//        if (latlng.size != 2)
//            throw IllegalArgumentException("latlng DoubleArray constructor argument must have 2 elements")
//    }
//}

//class CountryWithDetails(val name: String, val capital: String, val latlng: List<Double>) {
//    //val capital = capital
//    val latlng2 = LatLng(latlng)
//}
//TODO? toCountry()

/*class CountryWithDetails(val name: String, val capital: String, val latlng2: LatLng) {
    constructor(name: String, capital: String, latlng: List<Double>) : this(name, capital, LatLng(latlng)) {
        println("""CountryWithDetails ctor called with:
            |name:      $name
            |capital:   $capital
            |latlng:    $latlng
        """.trimMargin())
    }

    init {
        println("ctor init")
    }
}*/

/*class CountryWithDetails(name: String, capital: String, latlng: List<Double>) {
    val name = name
    val capital = capital
    val dupa = LatLng(latlng)
}*/