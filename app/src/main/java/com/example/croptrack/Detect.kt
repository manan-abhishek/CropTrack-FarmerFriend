package com.croptrack.farmerfriend

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class Detect : Fragment() {

    private val PICK_IMAGE = 1
    private val CAMERA_REQUEST = 2

    private lateinit var plantImage: ImageView
    private lateinit var uploadBtn: MaterialButton
    private lateinit var cameraBtn: MaterialButton
    private lateinit var detectBtn: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_detect, container, false)

        // Highlight bottom navigation icon
        val mainActivity = activity as? MainActivity
        mainActivity?.open(
            mainActivity.getReelIcon(),
            mainActivity.getReelText()
        )

        // Initialize views
        plantImage = view.findViewById(R.id.plantImage)
        uploadBtn = view.findViewById(R.id.btnUploadImage)
        cameraBtn = view.findViewById(R.id.btnOpenCamera)
        detectBtn = view.findViewById(R.id.btnDetectDisease)

        // Upload image from gallery
        uploadBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

        // Capture image using camera
        cameraBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST)
        }

        // Detect disease button
        detectBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Detecting Disease...", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == PICK_IMAGE && data != null) {
                val imageUri = data.data
                plantImage.setImageURI(imageUri)
            }

            if (requestCode == CAMERA_REQUEST && data != null) {
                val bitmap = data.extras?.get("data") as Bitmap
                plantImage.setImageBitmap(bitmap)
            }
        }
    }
}
