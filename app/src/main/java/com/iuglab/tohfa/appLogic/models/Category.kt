package com.iuglab.tohfa.appLogic.models

import java.net.URI

data class Category(val name: String, val img: URI?) {
    companion object {
        val NAME = "name"
        val IMAGE = "image"
    }
}