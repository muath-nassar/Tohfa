package com.iuglab.tohfa.ui_elements.user.databse

import android.app.Activity
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.iuglab.tohfa.appLogic.models.Product

class UserDatabase(activity: Activity): SQLiteOpenHelper(activity , DATABASE_NAME , null ,DATABASE_VERSION) {

    companion object{
        val DATABASE_NAME = "UserDatabase"
        val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(Product.TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("Drop table if exists ${Product.TABLE_NAME}")
        onCreate(db)
    }

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

    fun deleteProducts(id:String) : Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        //return db.delete(Student.TABLE_NAME,"${Student.COL_ID} = ?", arrayOf(id.toString()))>0
       // return db.delete(Product.TABLE_NAME,"${Product.COL_USER_KEY} = ?", ) > 0
        return db.delete(Product.TABLE_NAME,"${Product.COL_USER_KEY} = " + "\'${id} \'",null)>0
//        var query = "DELETE * FROM " + Product.TABLE_NAME + " WHERE " + Product.COL_USER_KEY + " = " + "\'${id} \'"
//        return db.rawQuery(query,null,null)
    }
}