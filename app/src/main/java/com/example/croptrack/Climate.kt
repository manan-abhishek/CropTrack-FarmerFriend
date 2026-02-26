package com.example.croptrack

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.croptrack.databinding.FragmentClimateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Climate : Fragment() {

    private var _binding: FragmentClimateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClimateBinding.inflate(inflater, container, false)
        val view = binding.root


        (activity as? MainActivity)?.open(
            (activity as MainActivity).getClimateIcon(),
            (activity as MainActivity).getClimateText()
        )

        // Fetch default city weather
        fetchWeatherData("Jalandhar")

        // Enable city search
        setupSearchView()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })
    }

    private fun fetchWeatherData(cityName: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val call = retrofit.getCurrentWeatherData(
            cityName,
            "4fb99c42793edc9138bc74907628154f",
            "metric"
        )

        call.enqueue(object : Callback<WeatherApp> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val data = response.body()
                if (response.isSuccessful && data != null) {

                    val temp = data.main.temp.toString()
                    val humidity = data.main.humidity
                    val wind = data.wind.speed
                    val sea = data.main.pressure
                    val condition = data.weather.firstOrNull()?.main ?: "Unknown"
                    val maxT = data.main.temp_max
                    val minT = data.main.temp_min
                    val rise = data.sys.sunrise.toLong()
                    val set = data.sys.sunset.toLong()


                    binding.temp.text = "$temp °C"
                    binding.condition.text = condition
                    binding.maxTemp.text = "Max Temp: $maxT °C"
                    binding.minTemp.text = "Min Temp: $minT °C"
                    binding.humidity.text = "$humidity %"
                    binding.windspeed.text = "$wind m/s"
                    binding.sea.text = "$sea hPa"
                    binding.sunrise.text = formatTime(rise)
                    binding.sunset.text = formatTime(set)
                    binding.day.text = formatDay(System.currentTimeMillis())
                    binding.date.text = formatDate()
                    binding.cityName.text = cityName

                    applyWeatherAnimation(condition)
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to fetch weather", Toast.LENGTH_SHORT).show()
                Log.e("WeatherApp", "Failure: ${t.localizedMessage}")
            }
        })
    }

    private fun applyWeatherAnimation(condition: String) {
        when (condition) {
            "Haze" -> setAnimation(R.drawable.colud_background, R.raw.cloud)
            "Clear Sky", "Sunny", "Clear" -> setAnimation(R.drawable.sunny_background, R.raw.sun)
            "Partly Clouds", "Clouds", "Overcast", "Mist", "Foggy" -> setAnimation(R.drawable.colud_background, R.raw.cloud)
            "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain" -> setAnimation(R.drawable.rain_background, R.raw.rain)
            "Light Snow", "Moderate Snow", "Heavy Snow", "Blizzard" -> setAnimation(R.drawable.snow_background, R.raw.snow)
            else -> setAnimation(R.drawable.sunny_background, R.raw.sun)
        }
        binding.lottieAnimationView.playAnimation()
    }

    private fun setAnimation(bgRes: Int, animRes: Int) {
        binding.root.setBackgroundResource(bgRes)
        binding.lottieAnimationView.setAnimation(animRes)
    }

    private fun formatTime(timestamp: Long): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp * 1000))
    }

    private fun formatDay(timeInMillis: Long): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date(timeInMillis))
    }

    private fun formatDate(): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date())
    }
}
