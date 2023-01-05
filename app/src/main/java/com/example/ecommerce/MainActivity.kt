package com.example.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommerce.R
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.ecommerce.Others.userTabLogin
import com.example.ecommerce.Others.userTabRegister

class MainActivity : AppCompatActivity() {
    private var joinNowBtn: Button? = null
    private var loginBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        joinNowBtn = findViewById<View>(R.id.join_now) as Button
        loginBtn = findViewById<View>(R.id.login_main) as Button
        loginBtn!!.setOnClickListener {
            val loginIntent = Intent(this@MainActivity, userTabLogin::class.java)
            startActivity(loginIntent)
        }
        joinNowBtn!!.setOnClickListener {
            val registerIntent = Intent(this@MainActivity, userTabRegister::class.java)
            startActivity(registerIntent)
        }
    }
}