package com.iuglab.tohfa.appLogic.models

class Customer(override val name: String, override val password: String) : User() {
    override fun login(username: String, password: String): Boolean {
        TODO("Not yet implemented")
    }
    fun addToCart(product: Product,quantity : Int): Boolean{
        TODO("Not yet implemented")
    }
    fun purchase(){

    }
    fun rateProduct(product: Product, rate: Int){

    }
}