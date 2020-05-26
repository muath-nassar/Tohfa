package com.iuglab.tohfa.ui_elements

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.admin.activities.AdminHome
import com.iuglab.tohfa.ui_elements.user.activity.CategoriesActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.system.exitProcess


class LoginActivity : AppCompatActivity() , View.OnClickListener{

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //////   SharedPreferences For Welcome Screen  ///////////
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isActive", true)
        editor.apply()
        /////////////////////////////////////////////////////////

        tvForgetPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPassword::class.java))
        }

        tvSignup.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
            finish()
        }

        tvForgetPassword.setOnClickListener {
            startActivity(Intent(this, LoginByFacebook::class.java))
        }

        signinByFacebook.setOnClickListener {
            startActivity(Intent(this, LoginByFacebook::class.java))
        }


        signInButton.setOnClickListener(this)

        val pd = ProgressDialog(this).apply {
            setIcon(R.drawable.b_applogo)
            setTitle("Please wait a few seconds")
            setMessage("Data validation in progress ...")
            setCancelable(false)
        }

        btnLogin.setOnClickListener { it ->
            if (signinEmail.text!!.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(signinEmail.text.toString()).matches()) {
                if (signinPassword.text!!.isNotEmpty()) {
                    pd.show()

                    auth = FirebaseAuth.getInstance()
                    val loginresult = auth.signInWithEmailAndPassword(signinEmail.text.toString(), signinPassword.text.toString())

                    loginresult.addOnCompleteListener {
                        pd.dismiss()

                        if (loginresult.isSuccessful) {

                            val result = auth.currentUser
                            val email = result!!.email
                            val uid = result.uid
                            Log.e("user", "$email - $uid ")

                            if (email == getString(R.string.AdminEmail) && signinPassword.text.toString() == getString(R.string.AdminPass) && uid == getString(R.string.AdminKey)){
                                createSharedPrefs("MyPref3","isAdmin")
                                val i = Intent(this, AdminHome::class.java)
                                i.putExtra("email", email)
                                i.putExtra("id", uid)
                                startActivity(i)
                            }else{
                                createSharedPrefs("MyPref2","isLogin")
                                val i = Intent(this, CategoriesActivity::class.java)
                                i.putExtra("email", email)
                                i.putExtra("id", uid)
                                startActivity(i)
                            }
                            updateUI(result)
                        } else {
                            Snackbar.make(logincontiner, "Login Failure ,  Please Try Again", Snackbar.LENGTH_LONG).show()
                        }

                    }
                    loginresult.addOnCanceledListener {
                        Snackbar.make(logincontiner, "Login Canceled", Snackbar.LENGTH_LONG).show()
                        pd.dismiss()
                    }
                    loginresult.addOnFailureListener {
                        Snackbar.make(logincontiner, "Registration Failure(${it.localizedMessage}),  Please Try Again", Snackbar.LENGTH_LONG).show()
                        pd.dismiss()
                    }

                }else{
                    signinPassword.error = "Please Enter Your Password Here"
                }
            }else{
                signinEmail.error = "Please Enter Your Email Here"
            }
        }


        ///////////////////SignIn By Google ////////////
        // Button listeners
        signInButton.setOnClickListener(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()
    /////////////////////////////////////////////////////

    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }


    // [START onactivityresult]
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java) // get Account Informations
                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                updateUI(null)
            }
        }
    }

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        // [START_EXCLUDE silent]
        if (!googleProgressBar.isShown){
            googleProgressBar.visibility = View.VISIBLE
        }

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                val user = auth.currentUser
                createSharedPrefs("MyPref2","isLogin")
                updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.exception)
                Snackbar.make(logincontiner, "Authentication Failed. ${task.exception}", Snackbar.LENGTH_LONG).show()
                Toast.makeText(applicationContext,"${task.exception}",Toast.LENGTH_LONG).show()
                updateUI(null)
            }

            // [START_EXCLUDE]
            if (googleProgressBar.isShown){
                googleProgressBar.visibility = View.GONE
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.signInButton -> {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
                if (!googleProgressBar.isShown) googleProgressBar.visibility = View.VISIBLE
            }
        }
    }

    fun updateUI(user: FirebaseUser?) {
        if (googleProgressBar.isShown) googleProgressBar.visibility = View.GONE
        if (facebookProgressBar.isShown) facebookProgressBar.visibility = View.GONE

        val sharedPref2 = getSharedPreferences("MyPref2", Context.MODE_PRIVATE)
        val isLogin = sharedPref2.getBoolean("isLogin",false)
        if (isLogin){
            if (user != null) {
                Toast.makeText(applicationContext,"${user.email}",Toast.LENGTH_SHORT).show()
                Toast.makeText(applicationContext,"${user.displayName}",Toast.LENGTH_LONG).show()
                Toast.makeText(applicationContext,"${user.photoUrl}",Toast.LENGTH_LONG).show()

                val i = Intent(this, CategoriesActivity::class.java)
                i.putExtra("email", user.email)
                i.putExtra("name", user.displayName)
                i.putExtra("id", user.uid)
                i.putExtra("photo", user.phoneNumber)
                startActivity(i)

                if (signInButton.isShown){
                    signInButton.visibility = View.GONE
                }

                finish()
            } else {
                if (!signInButton.isShown){
                    signInButton.visibility = View.VISIBLE
                }
                Snackbar.make(logincontiner,
                    "Please Create Account OR SignIn By Google or Facebook",
                    Snackbar.ANIMATION_MODE_SLIDE.and(Snackbar.LENGTH_LONG))
                    .setAction("Create Account") {startActivity(Intent(applicationContext,SignUp::class.java))}
                    .show()
            }

            }
    }

    fun createSharedPrefs(filename: String , key: String){
        //////   SharedPreferences For Welcome Screen  ///////////
        val sharedPref = getSharedPreferences(filename, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(key, true)
        editor.apply()
        /////////////////////////////////////////////////////////
    }

    override fun onBackPressed() {}
}


