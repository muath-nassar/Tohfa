package com.iuglab.tohfa.appLogic.models

import android.widget.Toast
import com.iuglab.tohfa.appLogic.extra_helpers.ApplicationHelper

class Cart {

    private constructor()



    companion object {
        val g : HashMap<String,String>? = null

        private val products = mutableMapOf<Product, Int>()
        private var instance: Cart? = null
        fun getInstance(): Cart {
            if (instance == null) return Cart()
            else return instance!!
        }
    }

    fun addProductToCart(product: Product, quantity: Int) {
        products.put(product, quantity)
    }

    fun clearCart() {
        products.clear()
    }

    fun calcTotalPrice(): Double{
        var total: Double = 0.0
        if (products.size>0){
            for (i in products){
                //total+= i.key.price * i.value
            }
            return total
        }else return 0.0
    }
    fun buyCart(){
        if (products.isEmpty()) {
            Toast.makeText(ApplicationHelper(),"oops!! the cart is empty. \n please add products to cart",Toast.LENGTH_SHORT).show()
        }else{
            val price = calcTotalPrice()
            Toast.makeText(ApplicationHelper(),"You have successfully purchased with $price",Toast.LENGTH_SHORT).show()
        }
    }

}