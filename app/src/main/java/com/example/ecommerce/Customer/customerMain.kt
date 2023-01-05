package com.example.ecommerce.Customer


import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce.Adapter.CategoryAdapter
import com.example.ecommerce.Model.modelCategory
import com.example.ecommerce.Adapter.CategoryAdapter.RecyclerViewClickListener
import android.os.Bundle
import com.example.ecommerce.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.Intent
import android.view.Menu

import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo

import com.example.ecommerce.Others.about
import com.example.ecommerce.MainActivity
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import java.util.ArrayList

class customerMain : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var adapter: CategoryAdapter? = null
    private var exampleList: MutableList<modelCategory>? = null
    private var listener: RecyclerViewClickListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customer_main)
        val toolbar = findViewById<Toolbar>(R.id.custom_toolbar)
        setSupportActionBar(toolbar)
        fillExampleList()
        setUpRecyclerView()
    }

    private fun fillExampleList() {
        var exampleList = ArrayList<modelCategory>()
        exampleList.add(modelCategory(R.drawable.voiture, "Voitures"))
        exampleList.add(modelCategory(R.drawable.location, "Immobilier"))
        exampleList.add(modelCategory(R.drawable.telephone, "Telephones"))
        exampleList.add(modelCategory(R.drawable.rechange, "Vehicules & Pieces"))
        exampleList.add(modelCategory(R.drawable.informatique, "Informatique & Multimedia"))
        exampleList.add(modelCategory(R.drawable.beaute, "Mode & Beaute"))
        exampleList.add(modelCategory(R.drawable.jardin, "Maison & Jardin"))
        exampleList.add(modelCategory(R.drawable.construction, "Matériaux & Equipement"))
    }

    private fun setUpRecyclerView() {
        setOnClickListener()
        val recyclerView = findViewById<RecyclerView>(R.id.category_recycler_view)
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        adapter = CategoryAdapter(exampleList!!, listener!!)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun setOnClickListener() {
        listener = object : RecyclerViewClickListener {
            override fun onClick(v: View?, position: Int) {
                val myIntent = Intent(applicationContext, categorySpecific::class.java)
                myIntent.putExtra("cName", exampleList!![position].text1)
                startActivity(myIntent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_customer, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView?
        searchView!!.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter!!.filter.filter(newText)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.account_info) {
            val newIntent = Intent(applicationContext, customerAccountInfo::class.java)
            newIntent.putExtra("cId", intent.getStringExtra("cId"))
            startActivity(newIntent)
            return true
        } else if (id == R.id.about) {
            val newIntent = Intent(applicationContext, about::class.java)
            startActivity(newIntent)
            return true
        } else if (id == R.id.search) {
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