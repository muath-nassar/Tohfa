package com.iuglab.tohfa.ui_elements

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.user.activity.CategoriesActivity
import kotlinx.android.synthetic.main.activity_login_by_face_book.*
import org.json.JSONException

class LoginByFacebook : AppCompatActivity(){

    lateinit var mCallBackManager : CallbackManager
    lateinit var mFirebaseAuth : FirebaseAuth
    lateinit var authStateListener : FirebaseAuth.AuthStateListener
    lateinit var accessTokenTracker : AccessTokenTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login_by_face_book)

        mFirebaseAuth = FirebaseAuth.getInstance()
        FacebookSdk.sdkInitialize(applicationContext)
        btnLoginByFacebook.setReadPermissions("email","public_profile")
        btnLoginByFacebook.callOnClick()

        mCallBackManager = CallbackManager.Factory.create()
        btnLoginByFacebook.registerCallback(mCallBackManager , object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                try {
                    Log.e("Abd","OnSuccess : ${result!!.accessToken}")

                    Log.e("Abd","OnSuccess : ${result.accessToken.source.name}")

                    handleFacebookToken(result.accessToken)

                }catch (e: JSONException){
                    Log.e("Abd","Success with Error : ${e.message}")
                }
            }

            override fun onCancel() {
                Log.e("Abd","Canceled")
            }

            override fun onError(error: FacebookException?) {
                Log.e("Abd","OnError : $error")
            }

        })

        authStateListener = FirebaseAuth.AuthStateListener {
            val user = it.currentUser
            if (user != null){
                updateUI(user)
            }else{
                updateUI(null)
            }
        }


        accessTokenTracker = object : AccessTokenTracker(){
            override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {
                if (currentAccessToken == null){
                    Log.e("Abd","AccessTokenTracker : SignOut")
                    mFirebaseAuth.signOut()
                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallBackManager.onActivityResult(requestCode,resultCode,data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookToken(accessToken: AccessToken?) {
        Log.e("Abd","Handle Access Token")

        val credential = FacebookAuthProvider.getCredential(accessToken!!.token)

        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener { result ->
            if(result.isSuccessful){
                Log.e("Abd","signInWithCredential : Success")
                val user = mFirebaseAuth.currentUser
                updateUI(user)

            }else{
                Log.e("Abd","signInWithCredential : Failed (${result.exception})")

            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null){

            //////   SharedPreferences For Facebook Login   ///////////
            val sharedPref = getSharedPreferences("MyPref4", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean("isLoginByFacebook", true)
            editor.apply()
            /////////////////////////////////////////////////////////

            val name = user.displayName
            val email = user.email
            var photo = ""
            if (user.photoUrl != null){
                var photoUri = user.photoUrl.toString()
                photoUri = photoUri + "?type=large"
                photo = photoUri
            }

            val intent = Intent(baseContext, CategoriesActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("photo", photo)
            startActivity(intent)
            finish()
        }else{
            Log.e("Abd","UpdateUI : Null User")
            Toast.makeText(applicationContext,"Update UI : User Null",Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        mFirebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        if (authStateListener != null){
            mFirebaseAuth.removeAuthStateListener(authStateListener)
        }
    }
}
