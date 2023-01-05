package com.example.ecommerce.Adapter

import com.example.ecommerce.Model.modelCategory

import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.Adapter.CategoryAdapter.CategoryViewHolder
import android.widget.TextView
import com.example.ecommerce.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Filter

import android.widget.ImageView
import java.util.*
import kotlin.collections.ArrayList

class CategoryAdapter(
    exampleList: MutableList<modelCategory>,
    listener: RecyclerViewClickListener
) : RecyclerView.Adapter<CategoryViewHolder>() {
    private var exampleList: List<modelCategory> = ArrayList()
    private var exampleListFull: List<modelCategory> = ArrayList()
    private var listener: RecyclerViewClickListener

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var imageView: ImageView
        var textView1: TextView

        init {
            imageView = itemView.findViewById(R.id.category_image)
            textView1 = itemView.findViewById(R.id.category_name)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            listener.onClick(view, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.example_category, parent, false)
        return CategoryViewHolder(v)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem.text1
    }

    override fun getItemCount(): Int {
        return exampleList.size
    }

    val filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<modelCategory> = ArrayList()
            if (constraint.length == 0) {
                filteredList.addAll(exampleListFull)
            } else {
                val filterPattern =
                    constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in exampleListFull) {
                    if (item.text1.lowercase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            exampleList.clear()
            exampleList.addAll(results.values as List<modelCategory>)
        }
    }

    init {
        this.exampleList = exampleList
        exampleListFull = ArrayList(exampleList)
        this.listener = listener
    }

    interface RecyclerViewClickListener {
        fun onClick(v: View?, position: Int)
    }
}


