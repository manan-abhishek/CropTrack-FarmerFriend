package com.example.croptrack

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.croptrack.R
import java.util.Locale

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        setAppLang()

        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val th = Thread {
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            finally {
                val intent = Intent(this, LoginSignupMain::class.java)
                startActivity(intent)
                finish()
            }
        }
        th.start()
    }

    private fun setAppLang() {
        val prefs = getSharedPreferences("AppSetting", MODE_PRIVATE)
        val lang = prefs.getString("App_Lang", null)

        val languageCode = lang ?: "en"
        if (lang == null) {
            prefs.edit().putString("App_Lang", "en").apply()
        }

        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }
}
