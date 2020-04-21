package com.iuglab.tohfa.appLogic.extra_helpers

import android.app.Application
import com.iuglab.tohfa.appLogic.models.User

class ApplicationHelper: Application() {
    companion object{
        var currentUser: User? = null
        val notificationChannel = "com.iuglab.tohfa"
    }
}