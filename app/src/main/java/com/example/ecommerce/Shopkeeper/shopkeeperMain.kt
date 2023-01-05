package com.example.ecommerce.Shopkeeper

import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.os.Bundle
import com.example.ecommerce.R
import android.content.Intent
import android.view.Menu
import com.example.ecommerce.Shopkeeper.addProduct
import android.view.MenuInflater
import android.view.MenuItem
import com.example.ecommerce.Shopkeeper.shopkeeperAccountInfo
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.ecommerce.Others.about
import com.example.ecommerce.MainActivity

class shopkeeperMain : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var cardView1: CardView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopkeeper_main)
        toolbar = findViewById(R.id.custom_toolbar)
        setSupportActionBar(toolbar)
        cardView1 = findViewById(R.id.card1)
        val cardView2 = findViewById<CardView>(R.id.card2)
        cardView2.setOnClickListener {
            val name = intent.getStringExtra("shopName")
            val next = Intent(applicationContext, addProduct::class.java)
            next.putExtra("sName", name)
            next.putExtra("sID", intent.getStringExtra("Id"))
            startActivity(next)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_shopkeeper, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.account_info) {
            val newIntent = Intent(applicationContext, shopkeeperAccountInfo::class.java)
            newIntent.putExtra("Id", intent.getStringExtra("Id"))
            startActivity(newIntent)
            Toast.makeText(applicationContext, "Account info", Toast.LENGTH_SHORT).show()
            return true
        } else if (id == R.id.help) {
            startActivity(Intent(applicationContext, about::class.java))
            Toast.makeText(applicationContext, "Contact us", Toast.LENGTH_SHORT).show()
            return true
        } else {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(applicationContext, "Succesfully Logout", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}