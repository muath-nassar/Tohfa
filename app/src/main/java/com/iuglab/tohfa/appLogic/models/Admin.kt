package com.iuglab.tohfa.appLogic.models

class Admin(override val name: String, override val password: String) : User() {
    override fun login(username: String, password: String): Boolean {
        TODO("Not yet implemented")
    }
    fun add(product: Product): Boolean{
        TODO("Not yet implemented")
    }
    fun add(category: Category): Boolean{
        TODO("Not yet implemented")
    }
    fun update(id :String, product: Product): Boolean{
        TODO("Not yet implemented")
    }
    fun update(categryName: String, category: Category): Boolean{
        TODO("Not yet implemented")
    }
    fun deleteProduct(id: String): Boolean{
        TODO("Not yet implemented")
    }
    fun deleteCategory(name: String): Boolean{
        TODO("Not yet implemented")
    }
}