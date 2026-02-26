package com.example.croptrack

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class Notifications : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        val containerLayout: LinearLayout = view.findViewById(R.id.ll)

        val sp = requireContext().getSharedPreferences("notify", Context.MODE_PRIVATE)
        val set = sp.getStringSet("notification_keys", emptySet()) ?: emptySet()

        for (entry in set) {
            val parts = entry.split(",")
            if (parts.size == 3) {
                val title = parts[1]
                val subtitle = parts[2]

                val cardView = CardView(requireContext()).apply {
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(32, 24, 32, 0)
                    }
                    layoutParams = params
                    radius = 24f
                    cardElevation = 12f
                    setCardBackgroundColor(Color.parseColor("#E8F5E9"))
                    useCompatPadding = true
                    preventCornerOverlap = true
                    setContentPadding(32, 32, 32, 32)

                    val innerLayout = LinearLayout(requireContext()).apply {
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.START
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                    }

                    val titleText = TextView(requireContext()).apply {
                        text = title
                        textSize = 20f
                        setTypeface(null, Typeface.BOLD)
                        setTextColor(Color.parseColor("#2E7D32"))
                    }

                    val subtitleText = TextView(requireContext()).apply {
                        text = subtitle
                        textSize = 16f
                        setTextColor(Color.parseColor("#555555"))
                        setPadding(0, 8, 0, 0)
                    }

                    setOnClickListener {
                        val updatedSet = set.toMutableSet()
                        updatedSet.remove(entry)

                        val sp = requireContext().getSharedPreferences("notify", Context.MODE_PRIVATE)
                        val editor = sp.edit()
                        editor.putStringSet("notification_keys", updatedSet)
                        editor.apply()

                        Toast.makeText(requireContext(), "Notification with ID: ${id} is marked as seen", Toast.LENGTH_SHORT).show()
                        containerLayout.removeView(this)

                        val MA = activity as MainActivity
                        MA.notificationCount.text = updatedSet.size.toString()
                    }

                    innerLayout.addView(titleText)
                    innerLayout.addView(subtitleText)
                    addView(innerLayout)
                }

                containerLayout.addView(cardView)
            }
        }

        return view
    }

}