package com.example.registrationform

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationform.adapter.ListViewAdapter
import com.example.registrationform.adapter.RecyclerViewAdapter
import java.time.Year
import java.util.*

class RegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerDayView = view.findViewById<Spinner>(R.id.spinner_day_view)
        val days = mutableListOf<String>()
        for (i in 1..31) {
            days.add(i.toString())
        }
        spinnerDayView.adapter = context?.let { createListViewAdapter(days, it) }

        val spinnerMonthView = view.findViewById<Spinner>(R.id.spinner_month_view)
        val months = listOf(
            "January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December"
        )
        spinnerMonthView.adapter = context?.let { createListViewAdapter(months, it) }

        val spinnerYearView = view.findViewById<Spinner>(R.id.spinner_year_view)
        val years = mutableListOf<String>()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        for (i in 1900..currentYear) {
            years.add(i.toString())
        }
        spinnerYearView.adapter = context?.let { createListViewAdapter(years, it) }

        spinnerDayView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerChanged(spinnerDayView, spinnerMonthView, spinnerYearView)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        spinnerMonthView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerChanged(spinnerDayView, spinnerMonthView, spinnerYearView)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        spinnerYearView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerChanged(spinnerDayView, spinnerMonthView, spinnerYearView)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

    private fun spinnerChanged(day: Spinner, month: Spinner, year: Spinner) {
        Toast.makeText(
            context,
            "${day.selectedItem}/${month.selectedItem}/${year.selectedItem}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun createListViewAdapter(objects: List<String>, context: Context): ListViewAdapter =
        ListViewAdapter(
            context = context,
            objects = objects
        )

}