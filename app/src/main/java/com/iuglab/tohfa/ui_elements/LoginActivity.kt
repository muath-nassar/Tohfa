package com.iuglab.tohfa.ui_elements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.iuglab.tohfa.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_login)
    }
}
