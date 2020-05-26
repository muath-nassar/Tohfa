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
import com.bumptech.glide.Glide
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
import kotlinx.android.synthetic.main.activity_update_category.*
import kotlinx.android.synthetic.main.activity_update_product.*
import kotlinx.android.synthetic.main.toolbar_admin.*
import java.io.ByteArrayOutputStream
import java.lang.Exception

class ActivityUpdateProduct : AppCompatActivity() {
    var selectedProductId: String = ""
    var imageUri: Uri? = null
    var imgFomeFirebase = ""
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
        setContentView(R.layout.activity_update_product)
        toolbar.title = "Update product"
        val categories = mutableListOf<String>()
        db.collection("categories").get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                categories.add(document.getString(Category.NAME)!!)
                val adapter = ArrayAdapter(this, R.layout.list_item_material, categories)
                (spCategoryUpdate.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            }
        }.addOnFailureListener { exception ->
            Log.e(TAG, exception.message)
        }
        //fill the product information in inputs

        if (intent.getStringExtra("id") == "first open") {
            try {
                selectedProductId = intent.getStringExtra("p_id")!!
                //code to fill blanks on first open
                db.collection("products").document(selectedProductId).get()
                    .addOnSuccessListener { document ->
                        val name = document.getString(Product.NAME)
                        val price = document.getDouble(Product.PRICE)
                        val category = document.getString(Product.CATEGORY)
                        val location = document.getGeoPoint(Product.LOCATION)
                        val img = document.getString(Product.IMAGE)
                        val desc = document.getString(Product.DECRIPTION)
                        imgFomeFirebase = img!!

                        Glide.with(this).load(img).into(imgProductUpdate)
                        etProductNameUpdate.setText(name)
                        etProductPriceUpdate.setText(price.toString())
                        etCategoryUpdate.setText(category)
                        etDescriptionUpdate.setText(desc)
                        val latlngText = "${location!!.latitude},${location.longitude}"
                        etProductLocationUpdate.setText(latlngText)

                    }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (intent.getStringExtra("id") == "map"){
            selectedProductId = intent.getStringExtra("p_id")
        }

        //get category names end
        // set dialog
        dialog = ProgressDialog(this)

        if (intent != null) {
            try {
                lat = intent.getStringExtra("lat")
                lng = intent.getStringExtra("lng")
                etProductLocationUpdate.setText("$lat,$lng")
                val name = intent.getStringExtra("pName")
                val price = intent.getStringExtra("pPrice")
                val desc = intent.getStringExtra("pDescription")
                val cat = intent.getStringExtra("pCategory")
                val img = intent.getParcelableExtra<Uri>("pImage")
                imgFomeFirebase = intent.getStringExtra("fbImage")!!
                etCategoryUpdate.setText(cat)
                etProductNameUpdate.setText(name)
                etProductPriceUpdate.setText(price)
                etDescriptionUpdate.setText(desc)
                if (img != null) {
                    imgProductUpdate.setImageURI(img)
                }else{
                    Glide.with(this).load(imgFomeFirebase).into(imgProductUpdate)
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }


        }


        etProductLocationUpdate.setOnClickListener {
            val i = Intent(this, adminMapsActivity::class.java)
            i.putExtra("pName", etProductNameUpdate.text.toString())
            i.putExtra("pPrice", etProductPriceUpdate.text.toString())
            i.putExtra("pCategory", etCategoryUpdate.text.toString())
            i.putExtra("pDescription", etDescriptionUpdate.text.toString())
            i.putExtra("pImage", imageUri)
            i.putExtra("p_id",selectedProductId)
            i.putExtra("id", "update")
            i.putExtra("fbImage",imgFomeFirebase)
            startActivity(i)
            finish()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 220) {

            imgProductUpdate.setImageURI(data!!.data)
            hasImage = true
            imageUri = data.data
        }
    }

    override fun onResume() {
        super.onResume()
        btnAddProductUpdate.setOnClickListener {
            if (

                etProductNameUpdate.text.toString().isNotEmpty() &&
                etCategoryUpdate.text.toString().isNotEmpty() &&
                etDescriptionUpdate.text.toString().isNotEmpty() &&
                etProductLocationUpdate.text.toString().isNotEmpty() &&
                etProductPriceUpdate.text.toString().isNotEmpty()
            ) {// check validation of all
                val name = etProductNameUpdate.text.toString()
                val price = etProductPriceUpdate.text.toString().toDouble()
                val category = etCategoryUpdate.text.toString()
                val location = etProductLocationUpdate.text.toString()
                lat = location.substringBefore(",")
                lng = location.substringAfter(",")
                val geoPoint = GeoPoint(lat.toDouble(), lng.toDouble())
                val description = etDescriptionUpdate.text.toString()
                // now upload and save
                val bitmap = (imgProductUpdate.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                val data = baos.toByteArray()
                dialog.setMessage("Updating ...")
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
                        val name = etProductNameUpdate.text.toString()
                        val category = etCategoryUpdate.text.toString()
                        val description = etDescriptionUpdate.text.toString()
                        val geoPointText = etProductLocationUpdate.text.toString()
                        lat= geoPointText.substringBefore(",")
                        lng = geoPointText.substringAfter(",")
                        val price = etProductPriceUpdate.text.toString().toDouble()
                        val img = uri.toString()



                        db.collection("products").document(selectedProductId).update(
                            Product.NAME,name,
                            Product.PRICE,price,
                            Product.IMAGE,img,
                            Product.LOCATION,GeoPoint(lat.toDouble(),lng.toDouble()),
                            Product.DECRIPTION , description,
                            Product.CATEGORY, category
                            )
                            .addOnSuccessListener {
                                Snackbar.make(
                                    btnAddProductUpdate, "Done, $name is updated successfully",
                                    Snackbar.LENGTH_LONG
                                ).show()
                                Log.e(TAG, "category $name is updated to FireStore")
                                startActivity(Intent(this,ActivityShowProductForAdmin::class.java))
                                finish()
                            }.addOnFailureListener { exception ->
                                Log.e(TAG, exception.message)
                                Snackbar.make(
                                    btnAddProductUpdate,
                                    "oobs!! smothering went wrong ",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                    }
                    dialog.cancel()
                }


            }

        }
        imgProductUpdate.setOnClickListener { _ ->
            Dexter.withContext(this)
                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        val i =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(i, 220)
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
}
