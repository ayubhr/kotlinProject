package com.example.ecommerce.Customer

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import com.example.ecommerce.R
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.ecommerce.Customer.customerMain

class customerResetPassword : AppCompatActivity() {
    private val parentDbName = "Users"
    private var customerPass1: EditText? = null
    private var customerPass2: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customer_reset)
        val customerPass1 = findViewById<EditText>(R.id.input_new_pass)
        val customerPass2 = findViewById<EditText>(R.id.confirm_pass)
        val reset = findViewById<View>(R.id.reset_btn_customer) as Button
        reset.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                VerifyInput()
            }

            private fun VerifyInput() {
                val pass1 = customerPass1.text.toString()
                val pass2 = customerPass2.text.toString()
                if (TextUtils.isEmpty(pass1)) {
                    Toast.makeText(
                        this@customerResetPassword,
                        "Enter new password",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(pass2)) {
                    Toast.makeText(
                        this@customerResetPassword,
                        "Confirm password",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    ChangePassword(pass1, pass2)
                }
            }

            private fun ChangePassword(pass1: String, pass2: String) {
                val phoneNo = intent.getStringExtra("EnteredNo")
                val Rootref: DatabaseReference
                Rootref = FirebaseDatabase.getInstance().getReference("Customers")
                if (pass1 != pass2) {
                    Toast.makeText(
                        this@customerResetPassword,
                        "Password entries don't match!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    assert(phoneNo != null)
                    Rootref.child(parentDbName).child(phoneNo!!).child("password").setValue(pass1)
                    Toast.makeText(
                        this@customerResetPassword,
                        "Password changed successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(applicationContext, customerMain::class.java)
                    intent.putExtra("cId", phoneNo)
                    startActivity(intent)
                }
            }
        })
    }
}