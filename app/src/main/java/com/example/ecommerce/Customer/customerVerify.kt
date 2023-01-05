package com.example.ecommerce.Customer

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import android.os.Bundle
import com.example.ecommerce.R
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.example.ecommerce.Model.usersCustomers
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.ecommerce.Customer.customerResetPassword
import com.google.firebase.database.DatabaseError

class customerVerify : AppCompatActivity() {
    private var phoneCustomer: EditText? = null
    var Rootref: DatabaseReference? = null
    private val parentDbName = "Users"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customer_verify)
        phoneCustomer = findViewById<View>(R.id.reg_phone_customer) as EditText
        val verifyPhone = findViewById<View>(R.id.generate_OTP_customer) as Button
        verifyPhone.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                VerifyInput()
            }

            private fun VerifyInput() {
                val phone = phoneCustomer!!.text.toString()
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(
                        this@customerVerify,
                        "Please enter a phone number",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this@customerVerify, "Please wait ", Toast.LENGTH_SHORT).show()
                    VerifyPhone(phone)
                }
            }

            private fun VerifyPhone(phone: String) {
                Rootref = FirebaseDatabase.getInstance().getReference("Customers")
                Rootref!!.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(parentDbName).child(phone).exists()) {
                            val usersData = snapshot.child(parentDbName).child(phone).getValue(
                                usersCustomers::class.java
                            )!!
                            if (usersData.phone == phone) {
                                val intent =
                                    Intent(applicationContext, customerResetPassword::class.java)
                                intent.putExtra("EnteredNo", phone)
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(
                                this@customerVerify,
                                "Sorry! No account with $phone exists.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        })
    }
}