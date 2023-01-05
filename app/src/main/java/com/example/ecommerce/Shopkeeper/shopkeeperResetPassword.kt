package com.example.ecommerce.Shopkeeper

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
import android.widget.TextView


class shopkeeperResetPassword : AppCompatActivity() {
    private var shopkeeperPass1: EditText? = null
    private var shopkeeperPass2: EditText? = null
    private val parentDbName = "Users"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopkeeper_reset)
        val shopkeeperPass1 = findViewById<EditText>(R.id.input_new_pass)
        val shopkeeperPass2 = findViewById<EditText>(R.id.confirm_pass)
        val reset = findViewById<Button>(R.id.reset_btn_shopkeeper)
        reset.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                verifyInput()
            }

            private fun verifyInput() {
                val pass1 = shopkeeperPass1.text.toString()
                val pass2 = shopkeeperPass2.text.toString()
                if (TextUtils.isEmpty(pass1)) {
                    Toast.makeText(
                        this@shopkeeperResetPassword,
                        "Enter new password",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(pass2)) {
                    Toast.makeText(
                        this@shopkeeperResetPassword,
                        "Confirm password",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    ChangePassword(pass1, pass2)
                }
            }

            private fun ChangePassword(pass1: String, pass2: String) {
                val shopNo = intent.getStringExtra("EnteredShopNo")
                val Rootref: DatabaseReference
                Rootref = FirebaseDatabase.getInstance().getReference("Shopkeepers")
                if (pass1 != pass2) {
                    Toast.makeText(
                        this@shopkeeperResetPassword,
                        "Password entries don't match",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Rootref.child(parentDbName).child(shopNo!!).child("password").setValue(pass1)
                    Toast.makeText(
                        this@shopkeeperResetPassword,
                        "Password changed successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(applicationContext, shopkeeperMain::class.java)
                    intent.putExtra("Id", shopNo)
                    startActivity(intent)
                }
            }
        })
    }
}