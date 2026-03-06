package com.example.croptrack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.card.MaterialCardView

class SoilTest : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_soil_test, container, false)

        val soilTest: MaterialCardView = view.findViewById(R.id.testSoil)
        val soilReport: MaterialCardView = view.findViewById(R.id.soilReport)

        soilTest.setOnClickListener {
            val soilData = SoilData()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, soilData)
                .addToBackStack(null)
                .commit()
        }

        soilReport.setOnClickListener {
            val soilReportFragment = SoilReport()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, soilReportFragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}
