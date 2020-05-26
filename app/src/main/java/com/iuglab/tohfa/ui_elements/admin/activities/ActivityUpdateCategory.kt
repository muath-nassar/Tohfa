package com.iuglab.tohfa.ui_elements.admin.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
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
import kotlinx.android.synthetic.main.activity_update_category.*
import kotlinx.android.synthetic.main.toolbar_admin.*
import java.io.ByteArrayOutputStream

class ActivityUpdateCategory : AppCompatActivity() {
    lateinit var dialog : ProgressDialog
    val TAG = "mzn"
    var imgUrI: Uri? = null
    val db = Firebase.firestore
    val storage = Firebase.storage
    val categoryFolder = storage.reference.child("category_images")
    lateinit var categoryId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_category)
        setSupportActionBar(toolbar)
        dialog = ProgressDialog(this)

        toolbar.title = "UPDATE CATEGORY" //customize title
        categoryId = intent.getStringExtra("p_id")!!
        // put fields in layout
        db.collection("categories").document(categoryId).get().addOnSuccessListener {
            documentSnapshot ->
            etCategoryNameUpdate.setText(documentSnapshot.getString(Category.NAME))
            Glide.with(this).load(documentSnapshot.getString(Category.IMAGE)).into(imgCategoryUpdate)
            imgUrI = Uri.parse(documentSnapshot.getString(Category.IMAGE))
        }
    }

    override fun onResume() {
        super.onResume()
        imgCategoryUpdate.setOnClickListener {
            Dexter.withActivity(this)
                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(i,120)
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
        btnCategoryUpdate.setOnClickListener {
            if (imgUrI != null && etCategoryNameUpdate.text.toString().isNotEmpty()) {
                val bitmap = (imgCategoryUpdate.drawable as BitmapDrawable).bitmap
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
                    childRef.downloadUrl.addOnSuccessListener { uri ->

                        val categoryName: String = etCategoryNameUpdate.text.toString()
                        val categoryImage = uri.toString()

                        val myDocument = db.collection("categories").document(categoryId)
                            myDocument.update(Category.NAME,categoryName,Category.IMAGE,categoryImage)
                            .addOnSuccessListener {
                            Snackbar.make(
                                btnCategoryUpdate,
                                "Done, $categoryName is updated successfully",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            Log.e(TAG, "category $categoryName is updated to firestore")
                        }.addOnFailureListener { exception ->
                            Log.e(TAG, exception.message)
                            Snackbar.make(
                                btnCategoryUpdate,
                                "oobs!! smothering went wrong ",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                    dialog.cancel()
                }


            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK && requestCode == 120){
            imgUrI = data!!.data
            imgCategoryUpdate.setImageURI(imgUrI)
        }
    }
    private fun generateRandomeName(): String {
        return System.currentTimeMillis().toString() + RandomString.getAlphaNumericString(5)
    }
}
