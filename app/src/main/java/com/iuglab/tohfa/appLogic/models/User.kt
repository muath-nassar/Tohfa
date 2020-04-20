package com.iuglab.tohfa.appLogic.models

abstract class User {
    abstract val name: String
    abstract val password : String
    abstract fun login(username: String, password: String ): Boolean

}
