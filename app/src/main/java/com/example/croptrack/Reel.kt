package com.example.croptrack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class Reel : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reel, container, false)

        // Call MainActivity's open method to highlight the Reel icon and text
        val mainActivity = activity as? MainActivity
        mainActivity?.open(
            mainActivity.getReelIcon(),
            mainActivity.getReelText()
        )

        return view
    }
}
