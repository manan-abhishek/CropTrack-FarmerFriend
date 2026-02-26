package com.example.croptrack

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.croptrack.R

class LoginSignupMain : AppCompatActivity() {
    private lateinit var login: TextView
    private lateinit var signup: TextView
    private var selectedTab: TextView? = null  // Track selected tab

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_signup_main)

        login = findViewById(R.id.login)
        signup = findViewById(R.id.signup)

        login.text = getString(R.string.Login)
        signup.text = getString(R.string.Signup)

        // Set the initial selected tab and fragment
        selectedTab = login
        loadFragment(Login())  // Initially load the Login Fragment

        login.setOnClickListener {
            if (selectedTab != login) {
                updateTabSelection(login)
                loadFragmentWithLogging(Login())
            }
        }

        signup.setOnClickListener {
            if (selectedTab != signup) {
                updateTabSelection(signup)
                loadFragmentWithLogging(Signup())
            }
        }
    }

    // This method logs the time it takes to load the fragment on the main thread
    private fun loadFragmentWithLogging(fragment: Fragment) {
        val startTime = System.currentTimeMillis()

        // Perform the fragment transaction
        loadFragment(fragment)

        val endTime = System.currentTimeMillis()
        Log.d("FragmentLoadTime", "Fragment loading took ${endTime - startTime} ms")
    }

    // This method performs the fragment transaction on the main thread
    private fun loadFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.loginSignup, fragment)
        transaction.commit()  // Avoiding commitAllowingStateLoss for better safety
    }

    // Method to update tab selection and UI state
    private fun updateTabSelection(selected: TextView) {
        // Reset previous selection
        selectedTab?.apply {
            setTextColor(Color.parseColor("#888888"))
            background = null
        }

        // Set new selection
        selected.setTextColor(Color.parseColor("#ffffff"))
        selected.background = ContextCompat.getDrawable(this, R.drawable.colorbg)

        // Update the selected tab reference
        selectedTab = selected
    }
}
