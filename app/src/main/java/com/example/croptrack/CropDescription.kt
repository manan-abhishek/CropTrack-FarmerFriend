package com.example.croptrack

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

//data class Crops{
//    val name: String,
//    val description: String,
//    val image: Int,
//    val location: String,
//    val area: String,
//}
class CropDescription : Fragment() {
    lateinit var notificationData: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crop_description, container, false)

        val cropName = arguments?.getString("cropName")
        val area = arguments?.getString("area")
        val location = arguments?.getString("location")
        val date = arguments?.getString("date")

        val cropImg: ImageView = view.findViewById(R.id.cropImg)
        val cropDes: TextView = view.findViewById(R.id.cropDes)



        if(cropName == "Wheat"){
            cropImg.setImageResource(R.drawable.wheat_image)
            cropImg.tag = "wheatImage"

            cropDes.text = "1. Wheat (Triticum aestivum)\n" +
                    "Type:\n" +
                    "Wheat is a cereal grain and a staple crop primarily grown in temperate regions. It belongs to the grass family (Poaceae) and is widely cultivated for its high nutritional value, especially for carbohydrate and protein content.\n" +
                    "\n" +
                    "Planting Season:\n" +
                    "Sowing Time: October–December\n" +
                    "\n" +
                    "Harvesting Time: March–May\n" +
                    "\n" +
                    "Optimal Temperature: 10–25°C with cool, dry weather during ripening\n" +
                    "\n" +
                    "Soil Requirement: Well-drained loamy or clayey soil with good moisture retention\n" +
                    "\n" +
                    "Minimum Area Required:\n" +
                    "Small-scale farming: 1 acre\n" +
                    "\n" +
                    "Large-scale production: 10+ acres for commercial viability\n" +
                    "\n" +
                    "Pros & Cons:\n" +
                    "✅ Pros:\n" +
                    "\n" +
                    "High global demand and market stability\n" +
                    "\n" +
                    "Long shelf life compared to other crops\n" +
                    "\n" +
                    "Good source of carbohydrates and dietary fiber\n" +
                    "\n" +
                    "Mechanized farming available for large-scale production\n" +
                    "\n" +
                    "❌ Cons:\n" +
                    "\n" +
                    "High water requirement for irrigation\n" +
                    "\n" +
                    "Susceptibility to pests such as wheat rust and aphids\n" +
                    "\n" +
                    "Price fluctuations due to international market trends\n" +
                    "\n" +
                    "Common Pesticides Used:\n" +
                    "For Fungal Diseases: Propiconazole, Mancozeb\n" +
                    "\n" +
                    "For Insect Control: Imidacloprid, Chlorpyrifos\n" +
                    "\n" +
                    "For Weed Control: Glyphosate, Metribuzin\n" +
                    "\n" +
                    "Market Demand & Scope:\n" +
                    "Major markets: India, China, USA, Russia, European Union\n" +
                    "\n" +
                    "Used in flour production, bread, pasta, biscuits, and animal feed\n" +
                    "\n" +
                    "Wheat exports contribute significantly to India’s agricultural GDP\n" +
                    "\n" +
                    "Government Schemes (Yojanas):\n" +
                    "Pradhan Mantri Kisan Samman Nidhi (PM-KISAN): ₹6,000 per year for farmers\n" +
                    "\n" +
                    "Rashtriya Krishi Vikas Yojana (RKVY): Funding for farm development\n" +
                    "\n" +
                    "Minimum Support Price (MSP): Government ensures a fixed price for wheat"
        }
        else if(cropName == "Pea"){
            cropImg.setImageResource(R.drawable.pea_image)
            cropImg.tag = "peaImage"

            cropDes.text = "Pea (Pisum sativum)\n" +
                    "Type:\n" +
                    "Pea is a cool-season legume crop grown for its edible seeds, used both fresh and dried. It belongs to the Fabaceae family and is rich in protein, vitamins, and fiber.\n" +
                    "\n" +
                    "Planting Season:\n" +
                    "Sowing Time: October–November\n" +
                    "\n" +
                    "Harvesting Time: February–March\n" +
                    "\n" +
                    "Optimal Temperature: 10–20°C\n" +
                    "\n" +
                    "Soil Requirement: Well-drained sandy loam soil with a pH of 6.0–7.5\n" +
                    "\n" +
                    "Minimum Area Required:\n" +
                    "Commercial farming: 1 acre minimum\n" +
                    "\n" +
                    "Home gardening: 200 sq. ft. for small-scale production\n" +
                    "\n" +
                    "Pros & Cons:\n" +
                    "✅ Pros:\n" +
                    "\n" +
                    "Enriches soil fertility due to nitrogen fixation\n" +
                    "\n" +
                    "Requires less irrigation compared to wheat and rice\n" +
                    "\n" +
                    "High market demand, especially for frozen and processed peas\n" +
                    "\n" +
                    "Low input cost and good yield in a short period\n" +
                    "\n" +
                    "❌ Cons:\n" +
                    "\n" +
                    "Sensitive to excess moisture leading to root rot\n" +
                    "\n" +
                    "Susceptible to pests like aphids and pea moths\n" +
                    "\n" +
                    "Labor-intensive during harvesting\n" +
                    "\n" +
                    "Common Pesticides Used:\n" +
                    "For Fungal Control: Carbendazim, Mancozeb\n" +
                    "\n" +
                    "For Insect Control: Lambda-Cyhalothrin, Neem-based biopesticides\n" +
                    "\n" +
                    "For Weed Control: Pendimethalin, Imazethapyr\n" +
                    "\n" +
                    "Market Demand & Scope:\n" +
                    "Used in canning, frozen food, protein isolates, soups, and animal feed\n" +
                    "\n" +
                    "Export potential to Europe and North America\n" +
                    "\n" +
                    "Growing demand due to plant-based protein diets\n" +
                    "\n" +
                    "Government Schemes (Yojanas):\n" +
                    "National Food Security Mission (NFSM) - Pulses: Support for pulse production\n" +
                    "\n" +
                    "Paramparagat Krishi Vikas Yojana (PKVY): Organic farming assistance\n" +
                    "\n" +
                    "MSP for Pulses: Ensures price protection for farmers"
        }
        else if(cropName == "Onion"){
            cropImg.setImageResource(R.drawable.onion_image)
            cropImg.tag = "OnionImage"

            cropDes.text = "Onion (Allium cepa)\n" +
                    "Type:\n" +
                    "Onion is a bulb vegetable belonging to the Amaryllidaceae family, widely used in cooking, condiments, and medicinal applications.\n" +
                    "\n" +
                    "Planting Season:\n" +
                    "Rabi Crop (Winter Onion): Sown in October–December, harvested in March–May\n" +
                    "\n" +
                    "Kharif Crop (Summer Onion): Sown in June–July, harvested in October–November\n" +
                    "\n" +
                    "Optimal Temperature: 15–25°C\n" +
                    "\n" +
                    "Soil Requirement: Well-drained sandy loam with high organic matter\n" +
                    "\n" +
                    "Minimum Area Required:\n" +
                    "Small-scale farming: 0.5–1 acre\n" +
                    "\n" +
                    "Large-scale onion farming: 5+ acres for export and storage benefits\n" +
                    "\n" +
                    "Pros & Cons:\n" +
                    "✅ Pros:\n" +
                    "\n" +
                    "High market demand year-round\n" +
                    "\n" +
                    "Long storage life (up to 6 months under proper conditions)\n" +
                    "\n" +
                    "Good export potential, especially to Gulf countries\n" +
                    "\n" +
                    "❌ Cons:\n" +
                    "\n" +
                    "Price fluctuations based on seasonal supply\n" +
                    "\n" +
                    "High labor costs for sowing, harvesting, and peeling\n" +
                    "\n" +
                    "Susceptible to storage rot and fungal diseases\n" +
                    "\n" +
                    "Common Pesticides Used:\n" +
                    "For Fungal Diseases: Difenoconazole, Propiconazole\n" +
                    "\n" +
                    "For Insect Control: Malathion, Spinosad\n" +
                    "\n" +
                    "For Weed Control: Oxyfluorfen, Pendimethalin\n" +
                    "\n" +
                    "Market Demand & Scope:\n" +
                    "Used in culinary products, dehydrated onion powder, and medicinal formulations\n" +
                    "\n" +
                    "Export potential to Bangladesh, UAE, Malaysia, and Sri Lanka\n" +
                    "\n" +
                    "India is the 2nd largest onion producer globally\n" +
                    "\n" +
                    "Government Schemes (Yojanas):\n" +
                    "Operation Greens: ₹500 crore scheme to stabilize onion prices\n" +
                    "\n" +
                    "Rashtriya Krishi Vikas Yojana (RKVY): Support for better storage and marketing\n" +
                    "\n" +
                    "MSP for Onion Farmers: Ensures stable pricing and prevents losses"
        }
        else{
            Toast.makeText(requireContext(), "Buddy how you get there with name: $cropName, $area and $location", Toast.LENGTH_SHORT).show()
        }

        val addCrop: Button = view.findViewById(R.id.addCrop)
        addCrop.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("added_crops", Context.MODE_PRIVATE)
            val id1 = sharedPreferences.getString("id", "0")?: "0"
            var id = id1.toInt()
            ++id
            val cropData = "$cropName,$area,$location,0,$date" //name, area, location, progress, date
            val editor = sharedPreferences.edit()
            editor.putString("id", id.toString()) // for last id
            editor.putString(id.toString(), cropData)// for data with id
            editor.apply()

            Toast.makeText(requireContext(), "Your crop added successfully with id: ${id}", Toast.LENGTH_SHORT).show()
            addProgressThread(id)

            val addedCrop = CropProgress_1()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, addedCrop)
                .addToBackStack("Home")
                .commit()
        }

        return view
    }

    private fun addProgressThread(cropId: Int){
        val sharedPreferences = requireContext().getSharedPreferences("added_crops", Context.MODE_PRIVATE)

        Thread {
            var progress = 0.0f

            while (progress < 100f) {
                try {
                    Thread.sleep(20000) // Sleep for 20 seconds
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "errorCatched", Toast.LENGTH_SHORT).show()
                }

                val cropData = sharedPreferences.getString(cropId.toString(), null)
                if (cropData == null) {
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), "Something went wrong...with id: $cropId", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val cropInfo = cropData.split(",")
                    if (cropInfo.size != 5) {
                        activity?.runOnUiThread {
                            Toast.makeText(requireContext(), "Crop data format is wrong in id: $cropId", Toast.LENGTH_SHORT).show()
                        }
                        break
                    }

                    var pg = cropInfo[3].toFloatOrNull() ?: 0.0f
                    pg += 12.5f
                    progress = pg
                    val updated = "${cropInfo[0]},${cropInfo[1]},${cropInfo[2]},$progress,${cropInfo[4]}"
                    sharedPreferences.edit().putString(cropId.toString(), updated).commit()
                    Log.d("FromCropDes", "Progress saved: $progress")

                    val sp = requireContext().getSharedPreferences("notify", Context.MODE_PRIVATE)
                    var id = sp.getInt("nId", 0)
                    val set = sp.getStringSet("notification_keys", emptySet())?.toMutableSet() ?: mutableSetOf()
                    id += 1

                    if(pg.toInt() == 100){
                        notificationData = "${id},Your crop ${cropId} Fully growth,Click to know more"
                    }else {
                        notificationData = "${id},Your crop ${cropId} Going to next stage,Click to know more"
                    }
                    set.add(notificationData)
                    sp.edit().putInt("nId", id).apply()
                    sp.edit().putStringSet("notification_keys", set).apply()

//                  NOTIFICATIONS:
                    val notificationManager =
                        requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                    val notifySound = Uri.parse("android.resource://${requireContext().packageName}/raw/notify_sound")

                    // Create channel if required
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(
                            "crop_notify",
                            "cropNotification",
                            NotificationManager.IMPORTANCE_DEFAULT
                        )
                        notificationManager.createNotificationChannel(channel)
                    }

                    val cirImg = BitmapFactory.decodeResource(resources, R.drawable.logo)
                    val bgImage = BitmapFactory.decodeResource(resources, R.drawable.gov_yojna)

                    // Intent to open MainActivity with extras (for navigating to fragment)
                    val intent = Intent(requireContext(), MainActivity::class.java).apply {
                        putExtra("open_crop_progress", "cropNotify") // You’ll use this to detect in MainActivity
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }

                    val pendingIntent = PendingIntent.getActivity(
                        requireContext(),
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    val notification = NotificationCompat.Builder(requireContext(), "crop_notify")
                        .setSmallIcon(R.drawable.onion)
                        .setContentTitle(if (pg.toInt() == 100) "Your crop with ID:$cropId is fully grown" else "Your crop $cropId Going to next stage")
                        .setContentText("Click to know more")
                        .setLargeIcon(cirImg)
                        .setStyle(
                            NotificationCompat.BigPictureStyle()
                                .bigPicture(bgImage)
                                .setSummaryText("Now check your crop is any infected or not...")
                        )
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setSound(notifySound)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build()

                    notificationManager.notify(id, notification)
                }

                val MA = activity as MainActivity
                MA.notificationCount.text = (MA.notificationCount.text.toString().toInt() + 1).toString()
            }
        }.start()
    }
}