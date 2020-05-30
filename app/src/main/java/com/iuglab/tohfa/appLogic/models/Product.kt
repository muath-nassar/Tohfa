package com.iuglab.tohfa.appLogic.models

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng


 class Product(
   var name: String,
    var category: String,
    var id: String?,
    var price: Double,
    var description: String,
    var img: String?,
    var rate: Int = 0,
   var purchaseTimes : Int?,
    var location: LatLng) {

    companion object {
        //for database names
        val NAME = "name"
        val CATEGORY = "category"
        val ID = "id"
        val DECRIPTION = "description"
        val FAVORITE = "favorite"
        val PRICE = "price"
        val IMAGE = "image"
        val RATE = "rate"
        val LOCATION = "location"
        val PURCHASE_TIMES = "purchaseTimes"

        //for database favorite
        val COL_ID ="id"
        val COL_USER_KEY = "key"
        val  TABLE_NAME = "Favorite"
        val TABLE_CREATE = "create table $TABLE_NAME ($COL_ID integer primary key autoincrement ," +
                "$COL_USER_KEY text not null ) ;"

    }
 }