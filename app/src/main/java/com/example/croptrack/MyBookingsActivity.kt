package com.example.croptrack

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MyBookingsActivity : AppCompatActivity() {

    data class Booking(val userName: String, val equipmentName: String, val date: String, val image: Int)

    private fun getBookings(): List<Booking> {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Bookings", MODE_PRIVATE)
        val allEntries = sharedPreferences.all
        val bookings = mutableListOf<Booking>()

        // Find all booking keys based on userName suffix
        val keys = allEntries.keys.filter { it.endsWith("_userName") }

        for (key in keys) {
            val baseKey = key.removeSuffix("_userName")
            val userName = sharedPreferences.getString("${baseKey}_userName", "") ?: ""
            val equipmentName = sharedPreferences.getString("${baseKey}_equipmentName", "") ?: ""
            val date = sharedPreferences.getString("${baseKey}_date", "") ?: ""
            val image = sharedPreferences.getInt("${baseKey}_image", 0)

            if (userName.isNotBlank() && date.isNotBlank()) {
                bookings.add(Booking(userName, equipmentName, date, image))
            }
        }

        return bookings
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)

        val bookings = getBookings()
        val listView = findViewById<ListView>(R.id.lvBookings)

        val adapter = BookingAdapter(this, bookings)
        listView.adapter = adapter
    }
}
