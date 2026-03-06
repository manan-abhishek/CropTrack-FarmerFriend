package com.example.croptrack

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.android.material.card.MaterialCardView

class CropProgress_1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crop_progress_1, container, false)

        val sharedPreferences = requireContext().getSharedPreferences("added_crops", Context.MODE_PRIVATE)
        val llAddedCrops = view.findViewById<LinearLayout>(R.id.added_crops)

        var id = 1
        val lastId = sharedPreferences.getString("id", "0")?:"0"
        while (id <= lastId.toInt()) {
            val id2 = id
            val cropData = sharedPreferences.getString(id.toString(), null)
            if (cropData == null) {
                Toast.makeText(requireContext(), "Something went wrong... with id: $id", Toast.LENGTH_SHORT).show()
            } else {
                val cropInfo = cropData.split(",")
                if (cropInfo.size >= 5) {
                    val cropName = cropInfo[0]
                    val cropArea = cropInfo[1]
                    val cropLocation = cropInfo[2]
                    val cropProgress = cropInfo[3]
                    val cropDate = cropInfo[4]

                    val cardView = com.google.android.material.card.MaterialCardView(requireContext()).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            setMargins(16, 16, 16, 16)
                        }
                        setCardBackgroundColor(Color.parseColor("#BEE3CB"))
                        radius = 16f
                        cardElevation = 8f
                        strokeWidth = 2
                        strokeColor = Color.parseColor("#4BA26A")
                        setContentPadding(24, 24, 24, 24)

                        setOnClickListener {
                            openCropProgress(id2.toString(), cropName, cropArea, cropLocation, cropProgress, cropDate)
                        }

                        val innerLayout = LinearLayout(requireContext()).apply {
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                        }

                        val textView = TextView(requireContext()).apply {
                            text = "Crop: $cropName\nCrop Id: $id\nArea: $cropArea\nLocation: $cropLocation\nStart Date: $cropDate"
                            setTextColor(Color.parseColor("#333333"))
                            textSize = 18f
                            setTypeface(null, Typeface.BOLD)
                            gravity = Gravity.CENTER
                            setPadding(8, 8, 8, 8)
                        }

                        innerLayout.addView(textView)
                        addView(innerLayout)
                    }


                    llAddedCrops.addView(cardView)
                } else {
                    Toast.makeText(requireContext(), "Invalid crop data format for id: $id", Toast.LENGTH_SHORT).show()
                }
            }
            ++id
        }

        return view
    }

    private fun openCropProgress(id: String, cropName: String, cropArea: String, cropLocation: String, cropProgress: String, cropDate: String){
        val frag = CropProgress_2()
        val bundle = Bundle()
        bundle.putString("id", id)
        bundle.putString("cropName", cropName)
        bundle.putString("cropArea", cropArea)
        bundle.putString("cropLocation", cropLocation)
        bundle.putString("cropProgress", cropProgress)
        bundle.putString("cropDate", cropDate)
        frag.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment, frag)
            .addToBackStack(null)
            .commit()
    }
}