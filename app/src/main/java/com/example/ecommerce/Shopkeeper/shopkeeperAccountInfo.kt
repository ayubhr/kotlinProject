package com.example.ecommerce.Shopkeeper

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import com.example.ecommerce.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.ecommerce.Shopkeeper.shopkeeperMain

class shopkeeperAccountInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopkeeper_acc_info)
        val getUsername = findViewById<TextView>(R.id.get_username)
        val getPhone = findViewById<TextView>(R.id.get_mobile)
        val getShopName = findViewById<TextView>(R.id.get_shopName)
        val getShopNo = findViewById<TextView>(R.id.get_shopID)
        val redirect = findViewById<Button>(R.id.redirect_main)
        val id = intent.getStringExtra("Id")
        val Rootref =
            FirebaseDatabase.getInstance().getReference("Shopkeepers").child("Users").child(
                id!!
            )
        Rootref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                getUsername.setText(snapshot.child("Username").value.toString())
                getPhone.setText(snapshot.child("phone").value.toString())
                getShopName.setText(snapshot.child("Shopname").value.toString())
                getShopNo.setText(snapshot.child("shopID").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        redirect.setOnClickListener(View.OnClickListener {
            val i = Intent(this@shopkeeperAccountInfo, shopkeeperMain::class.java)
            startActivity(i)
        })
    }
}