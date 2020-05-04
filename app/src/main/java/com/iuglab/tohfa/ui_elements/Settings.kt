package com.iuglab.tohfa.ui_elements

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.iuglab.tohfa.R
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val user = FirebaseAuth.getInstance().currentUser

        btnDeleteAccount.setOnClickListener {
            val isSignIn = FirebaseAuth.getInstance().currentUser
            if (isSignIn != null) {
                user!!.delete()
                startActivity(Intent(applicationContext,LoginActivity::class.java))
                removeSharedPrefs("MyPref2","isLogin")
            } else {
                Toast.makeText(applicationContext,FirebaseAuth.getInstance().currentUser!!.email+" IS NOT SIGNIN",Toast.LENGTH_LONG).show()
            }
        }

        btnSigout.setOnClickListener {
            if (user != null) {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(applicationContext,LoginActivity::class.java))
                removeSharedPrefs("MyPref2","isLogin")
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
}
