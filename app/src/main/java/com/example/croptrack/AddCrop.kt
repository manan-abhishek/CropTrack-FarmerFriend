package com.example.croptrack

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView

class AddCrop : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_crop, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            } else {
                notifyFunction()
            }
        } else {
            notifyFunction()
        }



        val cropsContainer: ConstraintLayout = view.findViewById(R.id.cropsContainer)

        for (i in 0 until cropsContainer.childCount) {
            val child = cropsContainer.getChildAt(i)
            if (child is MaterialCardView) {
                child.setOnClickListener {
                    val name = child.tag?.toString() ?: "NULL"

                    val locationFrag = ChooseCropLocation().apply {
                        arguments = Bundle().apply {
                            putString("cropName", name)
                        }
                    }

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment, locationFrag)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }

        return view
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                notifyFunction()
            } else {
                Toast.makeText(requireContext(), "User permission denied", Toast.LENGTH_SHORT).show()
            }
        }


    private fun notifyFunction() {
        val context = requireContext()

        Thread {
            val sharedPreferences = context.getSharedPreferences("notify", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            var nId = sharedPreferences.getInt("nId", 0)
            val currentSet = sharedPreferences.getStringSet("notification_keys", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

            ++nId
            editor.putInt("nId", nId)
            val notificationData = "$nId,A new Government yojna in your state,Now a farmer can get a lowest rate of income on its crop..."
            currentSet.add(notificationData)
            editor.putStringSet("notification_keys", currentSet)
            editor.apply()

            // Switch to main thread for UI/Notification operations
            android.os.Handler(android.os.Looper.getMainLooper()).post {
                val notifySound = Uri.parse("android.resource://${context.packageName}/raw/notify_sound")

                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            android.Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(context, "User permission denied", Toast.LENGTH_SHORT).show()
                        return@post
                    } else {
                        val channel = NotificationChannel(
                            "Govt_Yojna",
                            "yojna1",
                            NotificationManager.IMPORTANCE_DEFAULT
                        )
                        notificationManager.createNotificationChannel(channel)
                    }
                }

                val cirImg = BitmapFactory.decodeResource(context.resources, R.drawable.logo)
                val bgImage = BitmapFactory.decodeResource(context.resources, R.drawable.gov_yojna)

                val intent = Intent(context, MainActivity::class.java).apply {
                    putExtra("open_fragment", "yojna_fragment")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                val pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                val notification = NotificationCompat.Builder(context, "Govt_Yojna")
                    .setSmallIcon(R.drawable.onion)
                    .setContentTitle("A new Government yojna in your state")
                    .setContentText("Click to know more")
                    .setLargeIcon(cirImg)
                    .setStyle(
                        NotificationCompat.BigPictureStyle()
                            .bigPicture(bgImage)
                            .setSummaryText("Now a farmer can get a lowest rate of income on its crop...")
                    )
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSound(notifySound)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()

                notificationManager.notify(1001, notification)
            }

            val MA = activity as MainActivity
            MA.notificationCount.text = (MA.notificationCount.text.toString().toInt() + 1).toString()
        }.start()
    }


}
