package com.iuglab.tohfa.ui_elements.user.model

class itemBascket(var id : String,var img:String , var name:String ,var price:Double , var contity:Int , var purchaseTimes:Int) {

    companion object {
        var  COL_ID = "id"
        var  COL_IMG = "img"
        var COL_NAME = "name"
        var COL_PRICE = "price"
        var COL_CONTITY ="contity"
        var COL_PURCHASE_TIME="purchaseTimes"
        val  TABLE_NAME = "Basket"

        val TABLE_CREATE = "create table $TABLE_NAME ($COL_ID text not null ," +
                "$COL_IMG text not null ," +
                "$COL_NAME text not null ," +
                "$COL_PRICE double ," +
                "$COL_CONTITY integer ," +
                "$COL_PURCHASE_TIME integer ) ;"
    }
}