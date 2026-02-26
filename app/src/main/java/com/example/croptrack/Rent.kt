package com.example.croptrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.croptrack.R


class Rent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent)

        findViewById<LinearLayout>(R.id.itemTractor).setOnClickListener {
            navigateToDetail("Tractor", "A powerful tractor for heavy lifting.", R.drawable.img)
        }

        findViewById<LinearLayout>(R.id.itemCombine).setOnClickListener {
            navigateToDetail("Combine Harvester", "Efficient machine for harvesting various crops.", R.drawable.img_1)
        }
        findViewById<LinearLayout>(R.id.itemPlough).setOnClickListener {
            navigateToDetail("Plough", "Used to prepare soil for sowing by turning it over.", R.drawable.img_2)
        }

        findViewById<LinearLayout>(R.id.itemRotavator).setOnClickListener {
            navigateToDetail("Rotavator", "Rotary tiller ideal for breaking up soil.", R.drawable.img_3)
        }

        findViewById<LinearLayout>(R.id.itemSeeder).setOnClickListener {
            navigateToDetail("Seeder", "Sows seeds evenly and efficiently across the field.", R.drawable.img_4)
        }

        findViewById<LinearLayout>(R.id.itemSprayer).setOnClickListener {
            navigateToDetail("Sprayer", "Applies pesticides or fertilizers over crops.", R.drawable.img_5)
        }

        findViewById<LinearLayout>(R.id.itemBaler).setOnClickListener {
            navigateToDetail("Baler", "Compacts hay or straw into manageable bales.", R.drawable.img_6)
        }

        findViewById<LinearLayout>(R.id.itemIrrigation).setOnClickListener {
            navigateToDetail("Irrigation System", "Provides controlled water supply to crops.", R.drawable.img_7)
        }

        findViewById<LinearLayout>(R.id.itemMiniLoader).setOnClickListener {
            navigateToDetail("Mini Loader", "Versatile machine for moving soil and materials.", R.drawable.img_8)
        }

        findViewById<Button>(R.id.btnMyBookings).setOnClickListener {
            startActivity(Intent(this, MyBookingsActivity::class.java))
        }
    }

    private fun navigateToDetail(name: String, description: String, imageResId: Int) {
        val intent = Intent(this, EquipmentDetailsActivity::class.java).apply {
            putExtra("name", name)
            putExtra("description", description)
            putExtra("imageResId", imageResId)
        }
        startActivity(intent)
    }
}
