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
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Category
import com.iuglab.tohfa.appLogic.models.Product
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.toolbar_admin.*
import java.io.ByteArrayOutputStream
import java.lang.Exception


class ActivityAddProduct : AppCompatActivity() {
    var imageUri: Uri? = null
    var hasImage = false
    val TAG = "mzn"
    val db = Firebase.firestore
    var lat = "latitude"
    var lng = "longitude"
    lateinit var dialog: ProgressDialog
    val storage = Firebase.storage
    val categoryFolder = storage.reference.child("product_images")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        //get category names start
        toolbar.title = "Add product"
        val categories = mutableListOf<String>()
        db.collection("categories").get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                categories.add(document.getString(Category.NAME)!!)
                val adapter = ArrayAdapter(this, R.layout.list_item_material, categories)
                (spCategory.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            }
        }.addOnFailureListener { exception ->
            Log.e(TAG, exception.message)
        }
        //get category names end
        // set dialog
        dialog = ProgressDialog(this)

        if (intent != null) {
            try {
                lat = intent.getStringExtra("lat")
                lng = intent.getStringExtra("lng")
                etProductLocation.setText("$lat,$lng")
                val name = intent.getStringExtra("pName")
                val price = intent.getStringExtra("pPrice")
                val desc = intent.getStringExtra("pDescription")
                val cat = intent.getStringExtra("pCategory")
                val img = intent.getParcelableExtra<Uri>("pImage")

                etCategory.setText(cat)
                etProductName.setText(name)
                etProductPrice.setText(price)
                etDescription.setText(desc)
                if (img!= null ){
                    imgProduct.setImageURI(img)
                    hasImage = true
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }


        }


        etProductLocation.setOnClickListener {
            val i = Intent(this, adminMapsActivity::class.java)
            i.putExtra("pName",etProductName.text.toString())
            i.putExtra("pPrice",etProductPrice.text.toString())
            i.putExtra("pCategory",etCategory.text.toString())
            i.putExtra("pDescription",etDescription.text.toString())
            i.putExtra("pImage",imageUri)
            i.putExtra("id","add")
            startActivity(i)

        }


    }

    override fun onResume() {
        super.onResume()

        btnAddProduct.setOnClickListener {
            if (
                hasImage &&
                etProductName.text.toString().isNotEmpty() &&
                etCategory.text.toString().isNotEmpty() &&
                etDescription.text.toString().isNotEmpty() &&
                etProductLocation.text.toString().isNotEmpty() &&
                etProductPrice.text.toString().isNotEmpty()
            ) {// check validation of all
                val name = etProductName.text.toString()
                val price = etProductPrice.text.toString().toDouble()
                val category = etCategory.text.toString()
                val location = etProductLocation.text.toString()
                lat = location.substringBefore(",")
                lng = location.substringAfter(",")
                val geoPoint = GeoPoint(lat.toDouble(), lng.toDouble())
                val description = etDescription.text.toString()
                // now upload and save
                val bitmap = (imgProduct.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos)
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
                        val product =
                            hashMapOf(
                                Product.NAME to name,
                                Product.CATEGORY to category,
                                Product.PRICE to price,
                                Product.DECRIPTION to description,
                                Product.LOCATION to geoPoint,
                                Product.IMAGE to uri.toString(),
                                Product.RATE to 0,
                                Product.PURCHASE_TIMES to 0

                            )

                        db.collection("products").add(product).addOnSuccessListener {
                            Snackbar.make(
                                btnAddProduct, "Done, $name is added successfully",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            Log.e(TAG, "category $name is added to firestore")
                            startActivity(Intent(this,ActivityShowProductForAdmin::class.java))
                            finish()
                        }.addOnFailureListener { exception ->
                            Log.e(TAG, exception.message)
                            Snackbar.make(
                                btnAddProduct,
                                "oobs!! smothering went wrong ",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                    dialog.cancel()
                }


            }

        }
        imgProduct.setOnClickListener { _ ->
            Dexter.withContext(this)
                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        val i =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(i, 110)
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                        p1!!.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        finish()
                    }

                }).check()

        }


    }

    private fun generateRandomeName(): String {
        return System.currentTimeMillis().toString() + RandomString.getAlphaNumericString(5)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 110) {

            imgProduct.setImageURI(data!!.data)
            hasImage = true
            imageUri = data.data
        }
    }
}

