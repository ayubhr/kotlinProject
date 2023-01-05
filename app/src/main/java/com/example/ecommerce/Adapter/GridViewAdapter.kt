package com.example.ecommerce.Adapter

import android.content.Context
import android.widget.BaseAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.ecommerce.R
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.ArrayList

class GridViewAdapter(
    private val productName: ArrayList<String>,
    private val productPrice: ArrayList<String>,
    private val productImgURL: ArrayList<String>,
    private val context: Context
) : BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return productImgURL.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(position: Int, view: View, viewGroup: ViewGroup): View {
        var view = view
        if (view == null) {
            view = layoutInflater.inflate(R.layout.example_grid_item, viewGroup, false)
            val imageProd = view.findViewById<View>(R.id.product_image) as ImageView
            val nameProd = view.findViewById<View>(R.id.product_name) as TextView
            val priceProd = view.findViewById<View>(R.id.product_price) as TextView
            nameProd.text = productName[position]
            priceProd.text = productPrice[position]
            Glide.with(context).load(productImgURL[position]).into(imageProd)
        }
        return view
    }
}