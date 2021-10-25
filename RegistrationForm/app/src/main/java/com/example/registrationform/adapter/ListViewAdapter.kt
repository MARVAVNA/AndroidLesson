package com.example.registrationform.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.registrationform.R

class ListViewAdapter(context: Context, private val objects: List<String>) :
    ArrayAdapter<String>(context, R.layout.item_list_view, R.id.tv_list_element, objects) {

    override fun getCount(): Int = objects.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_list_view, parent, false)
        view?.findViewById<TextView>(R.id.tv_list_element)?.text = objects[position]
        return view
    }
}