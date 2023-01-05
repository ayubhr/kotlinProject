package com.example.ecommerce.Shopkeeper


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
import com.example.ecommerce.Model.usersShopkeepers
import android.content.Intent
import android.view.View
import android.widget.Button
import com.google.firebase.database.DatabaseError

class shopkeeperVerify : AppCompatActivity() {
    private var phoneShopkeeper: EditText? = null
    private var shopIdShopkeeper: EditText? = null
    var Rootref: DatabaseReference? = null
    private val parentDbName = "Users"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopkeeper_verify)
        shopIdShopkeeper = findViewById<View>(R.id.reg_shopNo_shopkeeper) as EditText
        phoneShopkeeper = findViewById<View>(R.id.reg_phone_shopkeeper) as EditText
        val verify = findViewById<View>(R.id.verify_shopkeeper) as Button
        verify.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                VerifyInput()
            }

            private fun VerifyInput() {
                val shopNo = shopIdShopkeeper!!.text.toString()
                val phone = phoneShopkeeper!!.text.toString()
                if (TextUtils.isEmpty(shopNo)) {
                    Toast.makeText(
                        this@shopkeeperVerify,
                        "Please enter 5-digit registered shop no",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(
                        this@shopkeeperVerify,
                        "Please enter registered mobile number",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this@shopkeeperVerify, "Please wait", Toast.LENGTH_SHORT).show()
                    VerifyData(shopNo, phone)
                }
            }

            private fun VerifyData(shopNo: String, phone: String) {
                Rootref = FirebaseDatabase.getInstance().getReference("Shopkeepers")
                Rootref!!.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(parentDbName).child(shopNo).exists()) {
                            val usersdata = snapshot.child(parentDbName).child(shopNo).getValue(
                                usersShopkeepers::class.java
                            )!!
                            if (usersdata.shopID == shopNo) {
                                if (usersdata.phone == phone) {
                                    val intent = Intent(
                                        applicationContext,
                                        shopkeeperResetPassword::class.java
                                    )
                                    intent.putExtra("EnteredShopNo", shopNo)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        this@shopkeeperVerify,
                                        "Sorry! Phone No and ShopId don't match",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                this@shopkeeperVerify,
                                "Shop with Id $shopNo doesn't exist.",
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