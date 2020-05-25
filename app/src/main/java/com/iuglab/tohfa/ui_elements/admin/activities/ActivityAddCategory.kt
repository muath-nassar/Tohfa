package com.iuglab.tohfa.ui_elements.admin.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Category
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_add_category.*
import kotlinx.android.synthetic.main.toolbar_admin.*
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.HashMap


class ActivityAddCategory : AppCompatActivity() {
  lateinit var dialog : ProgressDialog
    val TAG = "mzn"
    var imgUrI: Uri? = null
    val db = Firebase.firestore
    val storage = Firebase.storage
    val categoryFolder = storage.reference.child("category_images")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
        setSupportActionBar(toolbar)
        toolbar.title = "UPDATE CATEGORY" //customize title
        dialog = ProgressDialog(this)

    }

    override fun onResume() {
        super.onResume()
        btnAddCategory.setOnClickListener {

            val bitmap = (imgCategory.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val data = baos.toByteArray()
            dialog.setMessage("Uploading ...")
            dialog.setTitle("Wait")
            dialog.show()
            dialog.setCancelable(false)
            dialog.show()
            val generatedName = generateRandomeName()
            val childRef = categoryFolder.child(generatedName)
            val uploadTask = childRef.putBytes(data)
            uploadTask.addOnFailureListener { exception ->
                Log.e(TAG, exception.message)
                dialog.cancel()
            }.addOnSuccessListener {
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
                childRef.downloadUrl.addOnSuccessListener {
                    uri ->
                    val categoryName: String = etCategoryName.text.toString()
                    val categoryImage = uri.toString()
                    val category = hashMapOf(Category.NAME to categoryName, Category.IMAGE to categoryImage)
                    db.collection("categories").add(category).addOnSuccessListener {
                        Log.e(TAG,"category $categoryName is added to firestore")
                    }.addOnFailureListener { exception -> Log.e(TAG,exception.message) }
                }
                dialog.cancel()
            }

        }
        imgCategory.setOnClickListener {
            Dexter.withActivity(this)
                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        val i = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(i,100)

                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        finish()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        token!!.continuePermissionRequest();
                    }
                }).check()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK && requestCode == 100){
            imgUrI = data!!.data
            imgCategory.setImageURI(imgUrI)
        }
    }
    private fun generateRandomeName(): String {
        return System.currentTimeMillis().toString() + RandomString.getAlphaNumericString(5)
    }
}


// Java program generate a random AlphaNumeric String
// using Math.random() method

object RandomString {
    // function to generate a random string of length n
    fun getAlphaNumericString(n: Int): String {

        // chose a Character random from this String
        val AlphaNumericString = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz")

        // create StringBuffer size of AlphaNumericString
        val sb = StringBuilder(n)
        for (i in 0 until n) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            val index = (AlphaNumericString.length
                    * Math.random()).toInt()

            // add Character one by one in end of sb
            sb.append(
                AlphaNumericString[index]
            )
        }
        return sb.toString()
    }

/*    @JvmStatic
    fun main(args: Array<String>) {

        // Get the size n
        val n = 20

        // Get and display the alphanumeric string
        println(getAlphaNumericString(n))
    }*/
}
