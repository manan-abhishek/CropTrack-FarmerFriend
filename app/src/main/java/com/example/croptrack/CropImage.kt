import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.croptrack.R
import com.github.drjacky.imagepicker.ImagePicker
import kotlin.math.pow


class CropImage : Fragment() {
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private var selectedBitmap: Bitmap? = null
    private lateinit var referenceBitmap: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crop_image, container, false)

        val retakeBtn: Button = view.findViewById(R.id.retake)
        val submitBtn: Button = view.findViewById(R.id.submit)
        val img: ImageView = view.findViewById(R.id.img)

        // Load reference image
        referenceBitmap = BitmapFactory.decodeResource(resources, R.drawable.cropinfection)

        // Get passed image URI
        val imageUriString = arguments?.getString("image_uri")
        imageUriString?.let {
            val uri = Uri.parse(it)
            img.setImageURI(uri)
            selectedBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
        }

        // ImagePicker result handler
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                uri?.let { galleryUri ->
                    try {
                        img.setImageURI(galleryUri)
                        selectedBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, galleryUri)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Image load error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                val error = ImagePicker.getError(result.data)
                Toast.makeText(requireContext(), error ?: "Action cancelled", Toast.LENGTH_SHORT).show()
            }
        }

        // Retake or pick image
        retakeBtn.setOnClickListener {
            ImagePicker.with(requireActivity())
                .bothCameraGallery()
                .crop()
                .galleryMimeTypes(
                    mimeTypes = arrayOf("image/png", "image/jpg", "image/jpeg")
                )
                .setMultipleAllowed(false)
                .createIntentFromDialog { intent ->
                    imagePickerLauncher.launch(intent)
                }
        }

        submitBtn.setOnClickListener {
            if (selectedBitmap == null) {
                Toast.makeText(requireContext(), "Please select an image first", Toast.LENGTH_SHORT).show()
            } else {
                val userGray = resizeAndGrayscale(selectedBitmap!!)
                val refGray = resizeAndGrayscale(referenceBitmap)
                val ssim = calculateSSIM(userGray, refGray)
                loadDialog(ssim < 0.8)
            }
        }

        return view
    }

    // Helper methods (unchanged)
    private fun resizeAndGrayscale(bitmap: Bitmap): Bitmap {
        val size = 200
        val resized = Bitmap.createScaledBitmap(bitmap, size, size, true)
        val gray = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        for (x in 0 until size) {
            for (y in 0 until size) {
                val pixel = resized.getPixel(x, y)
                val r = (pixel shr 16) and 0xFF
                val g = (pixel shr 8) and 0xFF
                val b = pixel and 0xFF
                val grayVal = (0.3 * r + 0.59 * g + 0.11 * b).toInt()
                val grayPixel = 0xFF shl 24 or (grayVal shl 16) or (grayVal shl 8) or grayVal
                gray.setPixel(x, y, grayPixel)
            }
        }
        return gray
    }
    private fun calculateSSIM(img1: Bitmap, img2: Bitmap): Double {
        val width = img1.width
        val height = img1.height
        var meanX = 0.0
        var meanY = 0.0

        for (x in 0 until width) {
            for (y in 0 until height) {
                meanX += img1.getPixel(x, y) and 0xFF
                meanY += img2.getPixel(x, y) and 0xFF
            }
        }

        meanX /= (width * height)
        meanY /= (width * height)

        var varX = 0.0
        var varY = 0.0
        var covXY = 0.0

        for (x in 0 until width) {
            for (y in 0 until height) {
                val px1 = img1.getPixel(x, y) and 0xFF
                val px2 = img2.getPixel(x, y) and 0xFF
                varX += (px1 - meanX).pow(2)
                varY += (px2 - meanY).pow(2)
                covXY += (px1 - meanX) * (px2 - meanY)
            }
        }

        varX /= (width * height - 1)
        varY /= (width * height - 1)
        covXY /= (width * height - 1)

        val c1 = 6.5025
        val c2 = 58.5225

        return ((2 * meanX * meanY + c1) * (2 * covXY + c2)) /
                ((meanX.pow(2) + meanY.pow(2) + c1) * (varX + varY + c2))
    }
    private fun loadDialog(infect: Boolean) {
        val dialogView: View
        val info: TextView
        val sunInfo: TextView

        if (infect) {
            dialogView = layoutInflater.inflate(R.layout.dialog_suitable, null)
            info = dialogView.findViewById(R.id.info)
            sunInfo = dialogView.findViewById(R.id.sub_info)

            info.text = "Crop is infection free"
            sunInfo.text = "There is no need to do pesticides or other things"
        } else {
            dialogView = layoutInflater.inflate(R.layout.dialog_unsuitable, null)
            info = dialogView.findViewById(R.id.info)
            sunInfo = dialogView.findViewById(R.id.sub_info)

            info.text = "Crop is infected"
            sunInfo.text = "There is need to do pesticides or other things"
        }

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

}
