package com.example.ecommerce.Customer

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import android.os.Bundle
import com.example.ecommerce.R
import com.google.firebase.database.FirebaseDatabase
import com.bumptech.glide.Glide
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.ecommerce.Customer.customerMain
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import java.util.*

class productCard : AppCompatActivity() {
    private var imUrl: String? = null
    private var cat: String? = null
    private val id: Array<String> = TODO()
    private var prodImg: ImageView? = null
    private var prodPrice: TextView? = null
    private var prodName: TextView? = null
    private var shopName: TextView? = null
    private var prodId: TextView? = null
    private var shopId: TextView? = null
    private val quantity: TextView? = null
    private var phone: TextView? = null
    private var Rootref: DatabaseReference? = null

    //private String id;
    private var buy: Button? = null
    private var cart: Button? = null
    private val plus: Button? = null
    private val minus: Button? = null
    var buyState = false
    var cartState = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_card)
        cat = intent.getStringExtra("catg")
        imUrl = intent.getStringExtra("pimg")
        val hashMap = (intent.getSerializableExtra("Id") as HashMap<String, String>?)!!
        Rootref = Objects.requireNonNull(
            hashMap["shopId"]
        )?.let {
            FirebaseDatabase.getInstance().getReference(cat!!).child(
                it
            )
        }
        prodImg = findViewById<View>(R.id.prod_card_img) as ImageView
        prodName = findViewById<View>(R.id.prod_card_name) as TextView
        prodPrice = findViewById<View>(R.id.prod_card_price) as TextView
        shopName = findViewById<View>(R.id.prod_shop_name) as TextView
        prodId = findViewById<View>(R.id.prod_id) as TextView
        shopId = findViewById<View>(R.id.shop_id) as TextView
        phone = findViewById<View>(R.id.phoneN) as TextView
        buy = findViewById<View>(R.id.prod_card_buy) as Button
        cart = findViewById<View>(R.id.prod_card_cart) as Button
        SearchFirebase()
        Glide.with(applicationContext).load(imUrl).into(prodImg!!)

        buy!!.setOnClickListener { //Toast.makeText(getApplicationContext(),phone.getText().toString(),Toast.LENGTH_SHORT).show();
            val number = phone!!.text.toString().trim { it <= ' ' }
            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(number)))
            startActivity(callIntent)
        }
        cart!!.setOnClickListener {
            val intent = Intent(applicationContext, customerMain::class.java)
            startActivity(intent)
        }

    }

    private fun SearchFirebase() {
        Rootref!!.orderByChild("IMG").equalTo(imUrl)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (product in snapshot.children) {
                        //price = product.child("Product Name").getValue().toString();
                        //name = product.child("Product Price").getValue().toString();
                        prodName!!.text =
                            Objects.requireNonNull(product.child("Product Name").value).toString()
                        prodPrice!!.text =
                            Objects.requireNonNull(product.child("Product Price").value).toString()
                        shopName!!.text =
                            Objects.requireNonNull(product.child("Shop Name").value).toString()
                        prodId!!.text =
                            Objects.requireNonNull(product.child("Product Id").value).toString()
                        shopId!!.text =
                            Objects.requireNonNull(product.child("Product Discount").value)
                                .toString()
                        phone!!.text =
                            Objects.requireNonNull(product.child("Phone Number").value).toString()
                        //id = product.getKey();
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }
}