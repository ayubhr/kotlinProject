package com.example.ecommerce.Customer

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
import com.example.ecommerce.Customer.customerMain

class customerAccountInfo : AppCompatActivity() {
    private var getUsername: TextView? = null
    private var getPhone: TextView? = null
    private var getEmail: TextView? = null
    private var getPassword: TextView? = null
    private var id: String? = null
    private var redirect: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customer_acc_info)
        val getUsername = findViewById<TextView>(R.id.get_username)
        val getPhone = findViewById<TextView>(R.id.get_mobile)
        val getEmail = findViewById<TextView>(R.id.get_email)
        val getPassword = findViewById<TextView>(R.id.get_password)
        val redirect = findViewById<Button>(R.id.redirect_main)
        id = intent.getStringExtra("cId")
        val Rootref = FirebaseDatabase.getInstance().getReference("Customers").child("Users").child(
            id!!
        )
        Rootref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                getUsername.setText(snapshot.child("name").value.toString())
                getPhone.setText(snapshot.child("phone").value.toString())
                getEmail.setText(snapshot.child("email").value.toString())
                getPassword.setText(snapshot.child("password").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        redirect.setOnClickListener(View.OnClickListener {
            val i = Intent(this@customerAccountInfo, customerMain::class.java)
            startActivity(i)
        })
    }
}