package com.iuglab.tohfa.ui_elements

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.user.activity.CategoriesActivity
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.InputStream
import java.net.URL

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //////////  dark Theme ////////////////
        val sharedPref5 = getSharedPreferences("isDark", Context.MODE_PRIVATE)
        val isDark = sharedPref5.getBoolean("isDark",false)
        if (isDark){
            switchDarkTheme.isChecked = true
        }
        switchDarkTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            //////   SharedPreferences For Dark Mode ///////////
            val sharedPref = getSharedPreferences("isDark", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()

            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("isDark", true).apply()
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.remove("isDark").apply()
            }
        }
        /////////////////////////////////////



        ///////  Set Name,Email,Photo //////
        val user = FirebaseAuth.getInstance().currentUser
        // To Get Account Image

        if (user != null){
            if (user.photoUrl != null){
                //GetImageTask(settingUserImg).execute(user.photoUrl.toString())
                Glide
                    .with(this)
                    .load(user.photoUrl)
                    .placeholder(R.drawable.ic_user)
                    .into(settingUserImg)
            }
        }
        tvSettingsName.text = user!!.displayName
        tvSettingsEmail.text = user.email
        ////////////////////////////////////



        btnDeleteAccount.setOnClickListener {
            if (user != null) {
//                LoginActivity().updateUI(null)
                user.delete()
                removeSharedPrefs("MyPref2","isLogin")
                removeSharedPrefs("MyPref4","isLoginByFacebook")
                startActivity(Intent(applicationContext,LoginActivity::class.java))
                finish()

            } else {
                Toast.makeText(applicationContext,FirebaseAuth.getInstance().currentUser!!.email+" IS NOT SIGNIN",Toast.LENGTH_LONG).show()
                finish()
            }
        }

        btnSigout.setOnClickListener {
            if (user != null) {
//                LoginActivity().updateUI(null)
                FirebaseAuth.getInstance().signOut()
                removeSharedPrefs("MyPref2","isLogin")
                removeSharedPrefs("MyPref4","isLoginByFacebook")
                startActivity(Intent(applicationContext,LoginActivity::class.java))
                finish()

            } else {
                Toast.makeText(applicationContext,FirebaseAuth.getInstance().currentUser!!.email+" IS NOT SIGNIN",Toast.LENGTH_LONG).show()
            }

        }
    }

    fun removeSharedPrefs(filename : String , key :String){
        ///////////////// isLogin SharedPreferences ////////////////////
        val sharedPref = getSharedPreferences(filename, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove(key).apply()
        ////////////////////////////////////////////////////////////
    }

    override fun onBackPressed() {
        startActivity(Intent(this,CategoriesActivity::class.java))
        finish()
    }

}
