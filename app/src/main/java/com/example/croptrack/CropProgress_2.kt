package com.example.croptrack

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView

class CropProgress_2 : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val cropStages = arrayOf("Germination", "Seedling Stage", "Vegetative Stage", "Flowering Stage", "Fruiting", "Maturity", "Harvesting", "Last Stage")
    private lateinit var sharedPreferences: SharedPreferences
    private var id: Int? = null

    lateinit var progressBar: ProgressBar
    lateinit var currentStateText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crop_progress_2, container, false)

        // Initialize SharedPreferences before using it
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Get data from arguments
        val id1 = arguments?.getInt("id")
        val cropName = arguments?.getString("cropName")
        val cropArea = arguments?.getString("cropArea")
        val cropLocation = arguments?.getString("cropLocation")
        // You may or may not get progress from arguments
        val progressArg = arguments?.getString("cropProgress")
        val cropDate = arguments?.getString("cropDate")

        // Get views
        val idText = view.findViewById<TextView>(R.id.id)
        val nameText = view.findViewById<TextView>(R.id.cropName)
        val areaText = view.findViewById<TextView>(R.id.cropArea)
        val locationText = view.findViewById<TextView>(R.id.cropLocation)
        val dateText = view.findViewById<TextView>(R.id.date)
        progressBar = view.findViewById(R.id.progressBar)
        currentStateText = view.findViewById(R.id.currentStateText)

        // Set static data
        id = id1
        idText.text = "Crop ID: ${id1.toString()}"
        nameText.text = "${cropName}"
        areaText.text = "Crop Area: ${cropArea}"
        locationText.text = "Location: ${cropLocation}"
        dateText.text = "Start Date: ${cropDate}"

        // Determine progress:
        val storedCropData = sharedPreferences.getString(id1.toString(), null)
        val pg: Float = if (storedCropData != null) {
            val parts = storedCropData.split(',')
            if (parts.size == 5) {
                parts[3].toFloatOrNull() ?: 0.0f
            } else {
                0.0f
            }
        } else {
            progressArg?.toFloatOrNull() ?: 0.0f
        }

        // Update UI
        progressBar.progress = pg.toInt()
        val index = (pg / 12.5f).toInt()
        if(progressBar.progress == 100){
            currentStateText.text = "Current Stage: Completed"
        }else {
            currentStateText.text = "Current Stage: ${cropStages.getOrElse(index) { " Unknown Stage" }}"
        }
        Log.d("CropProgress", "Progress saved/updated: $pg")

        return view
    }

    override fun onStart(){
        super.onStart()
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onStop() {
        super.onStop()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?){
        Log.d("CropProgress", "onSharedPreferenceChanged triggered, key: $key")
        val idKey = id?.toString() ?: return
        if (key == idKey) {
            val cropData = sharedPreferences.getString(idKey, null) ?: return
            val parts = cropData.split(',')
            if (parts.size == 5) {
                val pg = parts[3].toFloatOrNull() ?: 0.0f
                activity?.runOnUiThread {
                    progressBar.progress = pg.toInt()
                    val index = (pg / 12.5f).toInt()
                    currentStateText.text = "Current Stage: ${cropStages.getOrElse(index) { " Unknown Stage" }}"
                }
                Log.d("CropProgress", "Progress updated: $pg")
            }
        }
    }
}
