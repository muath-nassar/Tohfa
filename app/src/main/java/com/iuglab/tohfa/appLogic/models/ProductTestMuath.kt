package com.iuglab.tohfa.appLogic.models

class ProductTestMuath    (
   var name: String,
//    var category: String,
//    var id: String?,
   var price: Double,
    var description: String
//    var img: URI?,
//    var rate: Int = 0,
//    var location: Location
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