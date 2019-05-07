package com.ppoljanski.countries.model

data class CountryWithDetails(val name: String, val capital: String, val latlng: List<Double>) {
    val lat
        get() = latlng[0]
    val lng
        get() = latlng[1]

    init {
        if (latlng.size != 2)
            throw IllegalArgumentException("latlng DoubleArray constructor argument must have 2 elements")
    }
}

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