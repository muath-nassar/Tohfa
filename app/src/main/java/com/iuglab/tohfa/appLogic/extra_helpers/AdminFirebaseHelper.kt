package com.iuglab.tohfa.appLogic.extra_helpers

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.iuglab.tohfa.appLogic.models.Product

class AdminFirebaseHelper(val context: Context) {
    private val db = FirebaseFirestore.getInstance();
    public fun getAllProducts(): ArrayList<Product>{
        val products = ArrayList<Product>()

        return products
    }
}