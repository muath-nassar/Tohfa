package com.iuglab.tohfa.ui_elements.user.databse

import android.app.Activity
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.model.itemBascket

class UserDatabase(activity: Activity): SQLiteOpenHelper(activity , DATABASE_NAME , null ,DATABASE_VERSION) {

    companion object{
        val DATABASE_NAME = "UserDatabase"
        val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(Product.TABLE_CREATE)
        db.execSQL(itemBascket.TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("Drop table if exists ${Product.TABLE_NAME}")
        db.execSQL("Drop table if exists ${itemBascket.TABLE_NAME}")
        onCreate(db)
    }


    ///         FAVORITE        ///

    fun insertProduct(key:String): Boolean{
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(Product.COL_USER_KEY,key)
        return db.insert(Product.TABLE_NAME,null,cv) > 0
    }

    fun getAllKeyProduct(): ArrayList<String> {
        val db: SQLiteDatabase = this.writableDatabase
        val data = ArrayList<String>()
        val c =
            db.rawQuery("select * from ${Product.TABLE_NAME} order by ${Product.COL_ID} desc", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            data.add(c.getString(1).toString())
            c.moveToNext()
        }
        c.close()
        return data
    }

    fun  searchAboutProduct(key : String) : ArrayList<String>{
        val db: SQLiteDatabase = this.writableDatabase
        var keys = ArrayList<String>()
        val cursor = db.rawQuery("SELECT * FROM ${Product.TABLE_NAME} WHERE ${Product.COL_USER_KEY} = \'$key\' ",null)
        if(cursor.moveToFirst()){
            do {
                var key = cursor.getString(cursor.getColumnIndex(Product.COL_USER_KEY))
                keys.add(key)

            }while (cursor.moveToNext());
        }
        return keys
    }

    fun deleteProducts(id:String) : Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        //return db.delete(Student.TABLE_NAME,"${Student.COL_ID} = ?", arrayOf(id.toString()))>0
       // return db.delete(Product.TABLE_NAME,"${Product.COL_USER_KEY} = ?", ) > 0
        return db.delete(Product.TABLE_NAME,"${Product.COL_USER_KEY} = " + "\'$id\'",null)>0
//        var query = "DELETE * FROM " + Product.TABLE_NAME + " WHERE " + Product.COL_USER_KEY + " = " + "\'${id} \'"
//        return db.rawQuery(query,null,null)
    }






    ///         BASKET        ///

    fun insertBasket(basket:itemBascket): Boolean{
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(itemBascket.COL_ID,basket.id)
        cv.put(itemBascket.COL_IMG,basket.img)
        cv.put(itemBascket.COL_NAME,basket.name)
        cv.put(itemBascket.COL_PRICE,basket.price)
        cv.put(itemBascket.COL_CONTITY,basket.contity)
        cv.put(itemBascket.COL_PURCHASE_TIME,basket.purchaseTimes)
        return db.insert(itemBascket.TABLE_NAME,null,cv) > 0
    }

    fun getAllBasketProducts(): ArrayList<itemBascket> {
        val db: SQLiteDatabase = this.writableDatabase
        val basckets = ArrayList<itemBascket>()
        val c =
            db.rawQuery("select * from ${itemBascket.TABLE_NAME} order by ${itemBascket.COL_ID} desc", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val bascket = itemBascket(c.getString(0),c.getString(1),c.getString(2),c.getDouble(3),c.getInt(4),c.getInt(5))
            basckets.add(bascket)
            c.moveToNext()
        }
        c.close()
        return basckets
    }

    fun  searchAboutBasket(id : String) : ArrayList<itemBascket>{
        val db: SQLiteDatabase = this.writableDatabase
        var baskets = ArrayList<itemBascket>()
        val cursor = db.rawQuery("SELECT * FROM ${itemBascket.TABLE_NAME} WHERE ${itemBascket.COL_ID} = \'$id\' ",null)
        if(cursor.moveToFirst()){
            do {
                var id = cursor.getString(cursor.getColumnIndex(itemBascket.COL_ID))
                var img = cursor.getString(cursor.getColumnIndex(itemBascket.COL_IMG))
                var name =  cursor.getString(cursor.getColumnIndex(itemBascket.COL_NAME))
                var price =  cursor.getDouble(cursor.getColumnIndex(itemBascket.COL_PRICE))
                var contity =  cursor.getInt(cursor.getColumnIndex(itemBascket.COL_CONTITY))
                var purchaseTimes =  cursor.getInt(cursor.getColumnIndex(itemBascket.COL_PURCHASE_TIME))

                var basket = itemBascket(id,img,name,price,contity,purchaseTimes)

                baskets.add(basket)

            }while (cursor.moveToNext());
        }
        return baskets

    }

    fun deleteItemBascket(id:String) : Boolean{
        val db: SQLiteDatabase = this.writableDatabase
        return db.delete(itemBascket.TABLE_NAME,"${itemBascket.COL_ID} = \'$id\'",null)>0
    }

    fun deleteBasckets() : Boolean{
        val db: SQLiteDatabase = this.writableDatabase
//        var query = "DELETE * FROM " + itemBascket.TABLE_NAME + " "
//       return db.rawQuery(query,null,null)
        return db.delete(itemBascket.TABLE_NAME,null,null) > 0
    }


}