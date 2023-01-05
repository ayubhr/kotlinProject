package com.example.ecommerce.Others

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommerce.R
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.ecommerce.Customer.customerMain

class about : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)
        val redirect = findViewById<Button>(R.id.redirect_main)
        redirect.setOnClickListener(View.OnClickListener {
            val i = Intent(this@about, customerMain::class.java)
            startActivity(i)
        })
    }
}