package com.iuglab.tohfa.appLogic.extra_helpers

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.iuglab.tohfa.appLogic.models.Category


class AdminFirebaseHelper(val context: Context) {
    private val TAG = "mzn"
    private var flag = false
    private val db = Firebase.firestore;
    public fun getAllCategories(): ArrayList<Category> {
        val categories = ArrayList<Category>()

        db.collection("categories").get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    categories.add(
                        Category(
                            document.getString(Category.NAME)!!,

                            document.getString(Category.IMAGE)!!
                        )
                    )
                    Log.e(TAG, document.getString(Category.NAME))
                    flag = true

                }
            }.addOnFailureListener { exception ->
                Log.e(TAG, exception.localizedMessage)
                flag = true
            }
        var counter = 0
        while (!flag && counter<4){
            Thread.sleep(1000)
            counter++
        }
        return categories
    }
}