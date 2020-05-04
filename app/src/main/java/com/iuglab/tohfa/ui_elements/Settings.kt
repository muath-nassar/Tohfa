package com.iuglab.tohfa.ui_elements

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.iuglab.tohfa.R
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val user = FirebaseAuth.getInstance().currentUser
        Toast.makeText(applicationContext,"${user!!.uid} - ${user.email} - ${user.displayName}",Toast.LENGTH_LONG).show()

        btnDeleteAccount.setOnClickListener {
            if (user != null) {
//                LoginActivity().updateUI(null)
                user.delete()
                removeSharedPrefs("MyPref2","isLogin")
                startActivity(Intent(applicationContext,LoginActivity::class.java))
            } else {
                Toast.makeText(applicationContext,FirebaseAuth.getInstance().currentUser!!.email+" IS NOT SIGNIN",Toast.LENGTH_LONG).show()
            }
        }

        btnSigout.setOnClickListener {
            if (user != null) {
//                LoginActivity().updateUI(null)
                FirebaseAuth.getInstance().signOut()
                removeSharedPrefs("MyPref2","isLogin")
                startActivity(Intent(applicationContext,LoginActivity::class.java))
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
