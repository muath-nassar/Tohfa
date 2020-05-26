package com.iuglab.tohfa.ui_elements

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.admin.activities.AdminHome
import com.iuglab.tohfa.ui_elements.user.activity.CategoriesActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.splash_screen)

        val sharedPref5 = getSharedPreferences("isDark", Context.MODE_PRIVATE)
        val isDark = sharedPref5.getBoolean("isDark",false)
        if (isDark){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        Handler().postDelayed({
            val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val isActive = sharedPref.getBoolean("isActive",false)
            if (isActive){

                val sharedPref2 = getSharedPreferences("MyPref2", Context.MODE_PRIVATE)
                val isLogin = sharedPref2.getBoolean("isLogin",false)

                val sharedPref3 = getSharedPreferences("MyPref3", Context.MODE_PRIVATE)
                val isAdmin = sharedPref3.getBoolean("isAdmin",false)

                val sharedPref4 = getSharedPreferences("MyPref4", Context.MODE_PRIVATE)
                val isLoginByFacebook = sharedPref4.getBoolean("isLoginByFacebook",false)



                when {
                    isLogin -> {
                        startActivity(Intent(applicationContext, CategoriesActivity::class.java))
                    }
                    isLoginByFacebook -> {
                        startActivity(Intent(applicationContext, CategoriesActivity::class.java))
                    }
                    isAdmin -> {
                        startActivity(Intent(applicationContext,AdminHome::class.java))
                    }
                    else -> {
                        startActivity(Intent(applicationContext,LoginActivity::class.java))
                    }
                }

            }else{
                startActivity(Intent(applicationContext,Welcome::class.java))
            }

            finish()
        }, 5000)


    }
}
