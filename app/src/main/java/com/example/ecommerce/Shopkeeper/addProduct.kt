package com.example.ecommerce.Shopkeeper


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommerce.R

import com.google.firebase.storage.FirebaseStorage
import android.content.Intent
import android.text.TextUtils

import com.example.ecommerce.Model.imageUploader

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseError

import android.webkit.MimeTypeMap

import android.net.Uri
import android.view.View
import android.widget.*
import java.util.ArrayList
import java.util.HashMap

class addProduct() : AppCompatActivity() {
    private var spinner: Spinner? = null
    private var uploadIMG: ImageView? = null
    private var productName: TextView? = null
    private var productId: TextView? = null
    private var productPrice: TextView? = null
    private var productDiscount: TextView? = null
    private var phone: TextView? = null
    private var addBtn: Button? = null
    private var imageUri: Uri? = null
    private var text: String? = null
    private var sFlag = 0
    private var iFlag = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product)
        val spinner = findViewById<Spinner>(R.id.spinner_categories)
        val uploadIMG = findViewById<ImageView>(R.id.upload_pic)
        val addBtn = findViewById<Button>(R.id.add_product_btn)
        val productName = findViewById<TextView>(R.id.set_product_name)
        val productId = findViewById<TextView>(R.id.set_product_id)
        val productPrice = findViewById<TextView>(R.id.set_product_price)
        val productDiscount = findViewById<TextView>(R.id.set_product_discount)
        val phone = findViewById<TextView>(R.id.phone_number)
        val categories: MutableList<String> = ArrayList()
        categories.add(0, "Choose category")
        categories.add("Voitures")
        categories.add("Immobilier")
        categories.add("Telephones")
        categories.add("Vehicules & Pieces")
        categories.add("Informatique & Multimedia")
        categories.add("Mode & Beaute")
        categories.add("Maison & Jardin")
        categories.add("Matériaux & Equipement")
        var dataAdapter: ArrayAdapter<Any>
        dataAdapter = ArrayAdapter<Any>(applicationContext, android.R.layout.simple_spinner_item,
            categories as List<Any>
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(dataAdapter)
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                text = adapterView.getItemAtPosition(i).toString()
                if ((text == "Choose category")) {
                    sFlag = 1
                } else {
                    sFlag = 0
                    Toast.makeText(applicationContext, "Selected : $text", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
        val reference = FirebaseStorage.getInstance().getReference("Images")
        uploadIMG.setOnClickListener(View.OnClickListener {
            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, 2)
        })
        addBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                VerifyInput()
            }

            private fun VerifyInput() {
                val pName = productName.getText().toString()
                val pId = productId.getText().toString()
                val pPrice = productPrice.getText().toString()
                val pDisc = productDiscount.getText().toString()
                val pphone = phone.getText().toString()
                if (sFlag == 1) {
                    Toast.makeText(
                        applicationContext,
                        "Please choose a category first",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(pName)) {
                    Toast.makeText(
                        applicationContext,
                        "Product name can't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(pId)) {
                    Toast.makeText(
                        applicationContext,
                        "Product Id can't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(pPrice)) {
                    Toast.makeText(applicationContext, "Price can't be empty", Toast.LENGTH_SHORT)
                        .show()
                } else if (TextUtils.isEmpty(pDisc)) {
                    Toast.makeText(
                        applicationContext,
                        "Please set a discount value(Type 0 if discount not applicable)",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(pphone)) {
                    Toast.makeText(
                        applicationContext,
                        "Phone number can't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (iFlag == 0) {
                    Toast.makeText(
                        applicationContext,
                        "Please upload an image of the product",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    uploadImage(imageUri, pName, pId, pPrice, pDisc, pphone)
                    Toast.makeText(
                        applicationContext,
                        "Please hold on while we do something.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            private fun uploadImage(
                imageUri: Uri?,
                productName: String,
                productId: String,
                productPrice: String,
                productDisc: String,
                phone: String
            ) {
                val fileRef = reference.child(
                    System.currentTimeMillis().toString() + "." + getFileExtension(imageUri)
                )
                fileRef.putFile((imageUri)!!).addOnSuccessListener {
                    fileRef.downloadUrl.addOnSuccessListener { uri ->
                        val model = imageUploader(uri.toString())
                        uploadData(model, productName, productId, productPrice, productDisc, phone)
                        Toast.makeText(
                            applicationContext,
                            "Image uploaded successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                    .addOnProgressListener { }.addOnFailureListener {
                        Toast.makeText(
                            applicationContext,
                            "Uploading failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }

            private fun uploadData(
                model: imageUploader,
                productName: String,
                productId: String,
                productPrice: String,
                productDisc: String,
                phone: String
            ) {
                val Rootref = FirebaseDatabase.getInstance().getReference((text)!!)
                val shopId = intent.getStringExtra("sID")
                Rootref.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        assert(shopId != null)
                        if (!(snapshot.child((shopId)!!).child(productId).exists())) {
                            val userdataMap = HashMap<String, Any?>()
                            userdataMap["Product Id"] = productId
                            userdataMap["Product Name"] = productName
                            userdataMap["Product Price"] = productPrice
                            userdataMap["Product Discount"] = productDisc
                            userdataMap["IMG"] = model.imageUrl
                            userdataMap["Shop Name"] = intent.getStringExtra("sName")
                            userdataMap["Phone Number"] = phone
                            Rootref.child((shopId)).child(productId).updateChildren(userdataMap)
                                .addOnCompleteListener(
                                    OnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                applicationContext,
                                                "Product added successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                applicationContext,
                                                "Product wasn't added",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    })
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Product with ID $productId already exists.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }

            private fun getFileExtension(mUri: Uri?): String? {
                val cr = contentResolver
                val mime = MimeTypeMap.getSingleton()
                return mime.getExtensionFromMimeType(cr.getType((mUri)!!))
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == 2) && (resultCode == RESULT_OK) && (data != null)) {
            iFlag = 1
            imageUri = data.data
            uploadIMG!!.setImageURI(imageUri)
        }
    }
}