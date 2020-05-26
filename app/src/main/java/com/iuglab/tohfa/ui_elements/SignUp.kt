package com.iuglab.tohfa.ui_elements

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.user.activity.CategoriesActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        tvSignup.setOnClickListener { startActivity(Intent(this,LoginActivity::class.java)) }

        auth = FirebaseAuth.getInstance()
    }

    override fun onResume() {
        super.onResume()

        val pd = ProgressDialog(this).apply {
            setIcon(R.drawable.ic_applogo)
            setTitle("Please wait a few seconds")
            setMessage("Data validation in progress ...")
            setCancelable(false)
        }

        btnSignup.setOnClickListener {
            if (signupUsername.text!!.isNotEmpty()) {
                if (signupEmail.text!!.isNotEmpty()  && Patterns.EMAIL_ADDRESS.matcher(signupEmail.text.toString()).matches()) {
                    if (signupPassword.text!!.isNotEmpty() && signupPassword.text.toString().length >= 8) {
                        if (signinPassword.text!!.isNotEmpty()) {
                            if (signinPassword.text.toString() == signupPassword.text.toString()) {
                                pd.show()

                                val task = auth.createUserWithEmailAndPassword(signupEmail.text.toString(), signupPassword.text.toString())
                                task.addOnCompleteListener {
                                    pd.dismiss()

                                    if (task.isSuccessful) {
                                        val sharedPref = getSharedPreferences("MyPref2", Context.MODE_PRIVATE)
                                        val editor = sharedPref.edit()
                                        editor.putBoolean("isLogin", true)
                                        editor.apply()

                                        val result = auth.currentUser
                                        val name = signupUsername.text.toString()
                                        val email = result!!.email
                                        val id = result.uid
//                        val name = result.displayName
//                        val phone = result.phoneNumber
//                        val img = result.photoUrl.toString()

                                        Log.e("user", "$email -$id -$name")
                                        val i = Intent(this, CategoriesActivity::class.java)
                                        i.putExtra("email", email)
                                        i.putExtra("name", name)
                                        i.putExtra("id", id)
                                        startActivity(i)

                                    } else {
                                        Snackbar.make(signupcontiner, "Registration Failure, Please Try Again", Snackbar.LENGTH_LONG).show()
                                    }
                                }
                                task.addOnCanceledListener {
                                    Snackbar.make(signupcontiner, "Registration Canceled!, Please Try Again", Snackbar.LENGTH_LONG).show()
                                    pd.dismiss()
                                }
                                task.addOnFailureListener {
                                    Snackbar.make(signupcontiner, "Registration Failure(${it.localizedMessage}),  Please Try Again", Snackbar.LENGTH_LONG).show()
                                    pd.dismiss()
                                }


                            } else {
                                signinPassword.error = "Please, This Field Must Match The Password Field"
                                pd.dismiss()
                            }
                        }else{
                            signinPassword.error = "Please, Enter Your Password Again Here"
                            pd.dismiss()
                        }
                    }else{
                        signupPassword.error = "Please, Password must be 8 or more characters "
                        pd.dismiss()
                    }
                }else{
                    signupEmail.error = "Please, Enter Your Email Here"
                    pd.dismiss()
                }
            }else{
                signupUsername.error = "Please, Enter Your Name Here"
                pd.dismiss()
            }


        }


    }



}
