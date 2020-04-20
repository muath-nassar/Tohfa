package com.iuglab.tohfa.appLogic.models

data class Location(val latitude: Int, val longitude: Int){
    companion object{
        val LATITUDE = "latitude"
        val LONGITUDE = "longitude"
    }

}