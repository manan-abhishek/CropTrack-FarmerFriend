package com.example.croptrack

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.croptrack.MyBookingsActivity.Booking

class BookingAdapter(private val context: Context, private val bookings: List<Booking>) : BaseAdapter() {

    override fun getCount() = bookings.size
    override fun getItem(position: Int) = bookings[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_booking, parent, false)

        val imageView = view.findViewById<ImageView>(R.id.itemImage)
        val textView = view.findViewById<TextView>(R.id.itemBookingInfo)

        val booking = bookings[position]
        imageView.setImageResource(booking.image)
        textView.text = "${booking.userName} booked ${booking.equipmentName} on ${booking.date}"

        return view
    }
}
