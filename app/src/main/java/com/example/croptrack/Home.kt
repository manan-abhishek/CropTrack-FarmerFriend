package com.example.croptrack

import CropImage
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.croptrack.AddCrop
import com.example.croptrack.Climate
import com.example.croptrack.R
import com.example.croptrack.SoilTest
import com.github.drjacky.imagepicker.ImagePicker
import java.net.URI

class Home : Fragment() {

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val mainActivity = activity as? MainActivity
        mainActivity?.open(
            mainActivity.getHomeIcon(),
            mainActivity.getHomeText()
        )

        val card1: View = view.findViewById(R.id.card1)
        val card2: View = view.findViewById(R.id.card2)
        val card3: View = view.findViewById(R.id.card3)
        val card4: View = view.findViewById(R.id.card4)
        val weatherCard: View = view.findViewById(R.id.weather_card)

        val govtYojnas: LinearLayout = view.findViewById(R.id.govtyojnas)

        weatherCard.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, Climate())
                .addToBackStack(null)
                .commit()
        }
        card1.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, AddCrop())
                .addToBackStack(null)
                .commit()
        }
        card2.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, CropProgress_1())
                .addToBackStack(null)
                .commit()
        }
        card3.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, SoilTest())
                .addToBackStack(null)
                .commit()
        }

        // Register your activity result launcher once
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                uri?.let { galleryUri ->
                    val bundle = Bundle()
                    bundle.putString("image_uri", galleryUri.toString())

                    val cropFragment = CropImage()
                    cropFragment.arguments = bundle

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment, cropFragment)
                        .addToBackStack("Home")
                        .commit()
                }
            } else {
                Toast.makeText(requireContext(),"Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
        card4.setOnClickListener {
            ImagePicker.with(requireActivity())
                .bothCameraGallery()
                .crop()
                .setMultipleAllowed(false)
                .createIntentFromDialog { intent ->
                    imagePickerLauncher.launch(intent)
                }
        }


        for (i in 0 until govtYojnas.childCount) {
            val child = govtYojnas.getChildAt(i)
            child?.setOnClickListener {
                Log.d("ClickTest", "Child $i clicked")
                openGovtYojnaFragment()
            }
        }

        return view
    }
    private fun openGovtYojnaFragment() {
        val fragment = GovtYojna()
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
