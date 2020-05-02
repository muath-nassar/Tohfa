package com.iuglab.tohfa.ui_elements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.iuglab.tohfa.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        tvSignup.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}
