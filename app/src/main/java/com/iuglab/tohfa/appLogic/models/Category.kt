package com.iuglab.tohfa.appLogic.models

import java.net.URI

data class Category(val name: String, val img: String?) {
    companion object {
        val NAME = "name"
        val IMAGE = "image"
        val COLLECTION_NAME = "categories"
    }
}