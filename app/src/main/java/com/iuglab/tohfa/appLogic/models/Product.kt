package com.iuglab.tohfa.appLogic.models

import com.google.android.gms.maps.model.LatLng
import java.net.URI

 class Product
    (
   var name: String,
    var category: String,
    var id: String?,
    var price: Double,
    var description: String,
    var img: String?,
    var rate: Int = 0,
    var location: LatLng
)
{
    companion object {
        //for database names
        val NAME = "name"
        val CATEGORY = "category"
        val ID = "id"
        val DECRIPTION = "description"
        val PRICE = "price"
        val IMAGE = "image"
        val RATE = "rate"
        val LOCATION = "location"
    }
}