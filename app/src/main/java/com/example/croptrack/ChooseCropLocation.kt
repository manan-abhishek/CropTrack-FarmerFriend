package com.example.croptrack

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class ChooseCropLocation : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choose_crop_location, container, false)

        val cropName = arguments?.getString("cropName")
        val cropNameTextView: TextView = view.findViewById(R.id.cropName)
        cropNameTextView.text = cropName

        val submitBtn: MaterialButton = view.findViewById(R.id.submitBtn)
        val location: TextInputLayout = view.findViewById(R.id.location)
        val fieldArea: TextInputLayout = view.findViewById(R.id.fieldArea)
        val err: TextView = view.findViewById(R.id.err)

        // New date field
        val dateField: TextInputLayout = view.findViewById(R.id.dateField)
        val dateInput: TextInputEditText = view.findViewById(R.id.dateInput)

        // Set up a click listener that shows a DatePickerDialog
        dateInput.setOnClickListener {
            // Use the current date as the default date in the picker
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                // Format the date as needed (e.g., "dd/MM/yyyy")
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                dateInput.setText(formattedDate)
            }, year, month, day)
            datePickerDialog.show()
        }

        submitBtn.setOnClickListener {
            val areaText = fieldArea.editText?.text
            val locText = location.editText?.text
            val dateText = dateInput.text?.toString() ?: ""

            val area = areaText?.toString() ?: ""
            val loc = locText?.toString() ?: ""

            // Check if required fields are filled (you can also check for valid date here)
            if(area.isNotEmpty() && loc.isNotEmpty() && dateText.isNotEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Your details: $area, $loc, on $dateText are saved",
                    Toast.LENGTH_SHORT
                ).show()

                val description = CropDescription().apply {
                    arguments = Bundle().apply {
                        putString("cropName", cropName)
                        putString("area", area)
                        putString("location", loc)
                        putString("date", dateText)  // Passing the selected date to the next fragment
                    }
                }

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment, description)
                    .addToBackStack(null)
                    .commit()
            } else {
                err.visibility = VISIBLE
                if(area.isEmpty()){
                    fieldArea.boxStrokeColor = Color.parseColor("#FF0000")
                    fieldArea.boxStrokeWidth = 2
                }
                if(loc.isEmpty()){
                    location.boxStrokeColor = Color.parseColor("#FF0000")
                    location.boxStrokeWidth = 2
                }
                if(dateText.isEmpty()){
                    dateField.boxStrokeColor = Color.parseColor("#FF0000")
                    dateField.boxStrokeWidth = 2
                }
            }
        }

        return view
    }
}
