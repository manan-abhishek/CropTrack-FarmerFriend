package com.example.croptrack

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Locale

class MainActivity : AppCompatActivity() {


    private var openImg: ImageView? = null
    private var openTxt: TextView? = null
    lateinit var notificationCount: TextView
    private lateinit var viewPager: ViewPager2

    fun open(newImg: ImageView, newTxt: TextView) {
        openImg?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
        openTxt?.setTextColor(Color.WHITE)

        newImg.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
        newTxt.setTextColor(ContextCompat.getColor(this, R.color.black))

        openImg = newImg
        openTxt = newTxt
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        val logo: ImageView = findViewById(R.id.logo)
        val homeBtn: LinearLayout = findViewById(R.id.btnHome)
        val climateBtn: LinearLayout = findViewById(R.id.btnClimate)
        val reelBtn: LinearLayout = findViewById(R.id.btnReel)
        val rentBtn: LinearLayout = findViewById(R.id.btnRent)
        val userPhoto: ImageView = findViewById(R.id.userPhoto)

        val homeIcon: ImageView = findViewById(R.id.homeIcon)
        val climateIcon: ImageView = findViewById(R.id.climateIcon)
        val reelIcon: ImageView = findViewById(R.id.reelIcon)
        val rentIcon: ImageView = findViewById(R.id.rentIcon)

        val home: TextView = findViewById(R.id.home)
        val climate: TextView = findViewById(R.id.climate)
        val reel: TextView = findViewById(R.id.reel)
        val rent: TextView = findViewById(R.id.rent)

        val langChanger: ImageView = findViewById(R.id.langChanger)
        val notification: ImageView = findViewById(R.id.notification)
        notificationCount = findViewById(R.id.notificationCount)

        val sharedPref = this.getSharedPreferences("notify", Context.MODE_PRIVATE)
        val notifications = sharedPref.getStringSet("notifications", setOf())
        val size = notifications?.size ?: 0
        notificationCount.text = size.toString()

        val drawable = ContextCompat.getDrawable(this, R.drawable.circle_bg)?.mutate()
        if (drawable is GradientDrawable) {
            drawable.setColor(Color.parseColor("#FF5722"))
        }
        notificationCount.background = drawable

        logo.setOnClickListener {
            viewPager.currentItem = 0
            open(homeIcon, home)
        }

        userPhoto.setOnClickListener {
            val sp = this.getSharedPreferences("user_data", Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(this, LoginSignupMain::class.java)
            startActivity(intent)
            finish()
        }

        langChanger.setOnClickListener {
            showLangMenu()
        }

        notification.setOnClickListener {
            val notifyFrag = Notifications()
            supportFragmentManager.beginTransaction()
                .replace(R.id.viewPager, notifyFrag)
                .addToBackStack("Home")
                .commit()
        }

        openImg = homeIcon
        openTxt = home
        open(homeIcon, home)

        homeBtn.setOnClickListener {
            viewPager.currentItem = 0
            open(homeIcon, home)
        }

        climateBtn.setOnClickListener {
            viewPager.currentItem = 1
            open(climateIcon, climate)
        }

        reelBtn.setOnClickListener {
            viewPager.currentItem = 2
            open(reelIcon, reel)
        }

        rentBtn.setOnClickListener {
            viewPager.currentItem = 3
            open(rentIcon, rent)

        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> open(homeIcon, home)
                    1 -> open(climateIcon, climate)
                    2 -> open(reelIcon, reel)
                    3 -> open(rentIcon, rent)
                }
            }
        })
    }

    private fun showLangMenu() {
        val languages = arrayOf("English", "हिन्दी", "ਪੰਜਾਬੀ")

        MaterialAlertDialogBuilder(this)
            .setTitle("Choose Language")
            .setItems(languages) { _, which ->
                when (which) {
                    0 -> setLocale("en")
                    1 -> setLocale("hi")
                    2 -> setLocale("pa")
                }
            }
            .show()

        val currentLang = Locale.getDefault().language
        Toast.makeText(this, "Now your app language: $currentLang", Toast.LENGTH_SHORT).show()
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        val preferedLang = getSharedPreferences("AppSetting", MODE_PRIVATE)
        preferedLang.edit().putString("App_Lang", languageCode).apply()

        resources.updateConfiguration(config, resources.displayMetrics)
        recreate()
    }

    fun getHomeIcon(): ImageView = findViewById(R.id.homeIcon)
    fun getHomeText(): TextView = findViewById(R.id.home)

    fun getClimateIcon(): ImageView = findViewById(R.id.climateIcon)
    fun getClimateText(): TextView = findViewById(R.id.climate)

    fun getReelIcon(): ImageView = findViewById(R.id.reelIcon)
    fun getReelText(): TextView = findViewById(R.id.reel)

    fun getRentIcon(): ImageView = findViewById(R.id.rentIcon)
    fun getRentText(): TextView = findViewById(R.id.rent)


}
