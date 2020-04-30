package com.iuglab.tohfa.ui_elements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.iuglab.tohfa.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_login)

        tvForgetPassword.setOnClickListener {
            startActivity(Intent(this,ForgetPassword::class.java))
        }

        tvSignup.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
        }

    }
}
