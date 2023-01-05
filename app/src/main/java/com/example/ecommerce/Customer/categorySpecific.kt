package com.example.ecommerce.Customer

import androidx.appcompat.app.AppCompatActivity
import android.widget.GridView
import android.widget.TextView
import com.example.ecommerce.Adapter.GridViewAdapter
import com.google.firebase.database.DatabaseReference
import android.os.Bundle
import com.example.ecommerce.R
import com.google.firebase.database.FirebaseDatabase
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import android.content.Intent
import android.view.View
import com.example.ecommerce.Customer.productCard
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import java.util.ArrayList
import java.util.HashMap

class categorySpecific : AppCompatActivity() {
    private var grid: GridView? = null
    private var cText: TextView? = null
    private val productName = ArrayList<String>()
    private val productPrice = ArrayList<String>()
    private val productImgURL = ArrayList<String>()
    private val shopId = ArrayList<String>()
    private val productId = ArrayList<String>()
    private var adapter: GridViewAdapter? = null
    private var category: String? = null
    private var Rootref: DatabaseReference? = null
    var map = HashMap<String, String?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_gridview)
        grid = findViewById<View>(R.id.gridview) as GridView
        category = intent.getStringExtra("cName")
        val cText = findViewById<TextView>(R.id.category_text)
        cText.setText(category)
        Rootref = FirebaseDatabase.getInstance().getReference(category!!)
        LoadDataFromFirebase()
        adapter = GridViewAdapter(productName, productPrice, productImgURL, applicationContext)
        grid!!.adapter = adapter
        grid!!.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(applicationContext, productCard::class.java)
            intent.putExtra("catg", category)
            intent.putExtra("pName", productName[i])
            intent.putExtra("pPrice", productPrice[i])
            intent.putExtra("pimg", productImgURL[i])
            intent.putExtra("Id", map)
            startActivity(intent)
        }
    }

    private fun LoadDataFromFirebase() {
        Rootref!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (product in snapshot.children) {
                    map["shopId"] = product.key
                    for (pd in product.children) {
                        productName.add(pd.child("Product Name").value.toString())
                        productPrice.add(pd.child("Product Price").value.toString())
                        productImgURL.add(pd.child("IMG").value.toString())
                    }
                }
                adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}