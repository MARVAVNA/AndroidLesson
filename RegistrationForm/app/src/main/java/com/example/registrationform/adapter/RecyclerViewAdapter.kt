package com.example.registrationform.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationform.R

class RecyclerViewAdapter(private val objects: List<String>) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclingViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclingViewHolder =
        RecyclingViewHolder(
            LayoutInflater.from(parent.context.applicationContext)
                .inflate(R.layout.item_list_view, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclingViewHolder, position: Int) {
        holder.bind(objects[position])
    }

    override fun getItemCount(): Int = objects.size

    class RecyclingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String) {
            itemView.findViewById<TextView>(R.id.tv_list_element).text = item
        }
    }
}