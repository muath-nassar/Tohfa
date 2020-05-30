package com.iuglab.tohfa.ui_elements.user.model

class itemRate(var id:String , var userRate:Int) {

    companion object {
        var  COL_KEY = "key"
        var COL_RATE = "currentRate"
        val  TABLE_NAME = "Rate"

        val TABLE_CREATE = "create table $TABLE_NAME ($COL_KEY text not null ," +
                "$COL_RATE integer ) ;"
    }
}