package com.example.croptrack

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.croptrack.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Signup : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameInputLayout = view.findViewById<TextInputLayout>(R.id.signupNameInputLayout)
        val phoneInputLayout = view.findViewById<TextInputLayout>(R.id.signupPhoneInputLayout)
        val emailInputLayout = view.findViewById<TextInputLayout>(R.id.signupEmailInputLayout)
        val passwordInputLayout = view.findViewById<TextInputLayout>(R.id.signupPasswordInputLayout)
        val confirmPasswordInputLayout = view.findViewById<TextInputLayout>(R.id.signupConfirmPasswordInputLayout)

        val signUpName = view.findViewById<TextInputEditText>(R.id.signUpName)
        val signUpPhone = view.findViewById<TextInputEditText>(R.id.signUpPhone)
        val signUpEmail = view.findViewById<TextInputEditText>(R.id.signUpEmail)
        val signUpPassword = view.findViewById<TextInputEditText>(R.id.signUpPassword)
        val signUpConfirmPassword = view.findViewById<TextInputEditText>(R.id.signUpConfirmPassword)

        val ipErr = view.findViewById<TextView>(R.id.ipErr)
        val cnfErr = view.findViewById<TextView>(R.id.cnfErr)

        val signUpButton = view.findViewById<Button>(R.id.signUpButton)
        val redirectToLogin = view.findViewById<TextView>(R.id.redirectToLogin)

        val googleIconLayout = view.findViewById<LinearLayout>(R.id.googleIconLayout)
        val facebookIconLayout = view.findViewById<LinearLayout>(R.id.facebookIconLayout)

        val loginBtn = requireActivity().findViewById<TextView>(R.id.login)
        val signupBtn = requireActivity().findViewById<TextView>(R.id.signup)

        signUpButton.setOnClickListener {
            val name = signUpName.text.toString().trim()
            val phone = signUpPhone.text.toString().trim()
            val email = signUpEmail.text.toString().trim()
            val password = signUpPassword.text.toString().trim()
            val confirmPassword = signUpConfirmPassword.text.toString().trim()

            if (name.isNotEmpty() && ((phone.isNotEmpty() && phone.length == 10) || (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())) && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {

                    //PUT THE THINGS IN sharedpreference...
                    val sharedPrefernce = requireContext().getSharedPreferences("user_data",Context.MODE_PRIVATE)
                    val editor = sharedPrefernce.edit()
                    editor.putString("name", name)
                    editor.putString("phone", phone)
                    editor.putString("email", email)
                    editor.apply()

                    // Proceed to MainActivity...
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    cnfErr.text = getString(R.string.cnfErr)
                }
            } else {
                highLight(nameInputLayout, phoneInputLayout, emailInputLayout, passwordInputLayout, confirmPasswordInputLayout)
                ipErr.text = getString(R.string.ipErr)
            }
        }

        redirectToLogin.setOnClickListener {
            loginBtn.setTextColor(Color.parseColor("#ffffff"))
            loginBtn.background = ContextCompat.getDrawable(requireActivity(), R.drawable.colorbg)
            signupBtn.setTextColor(Color.parseColor("#888888"))
            signupBtn.background = null

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.loginSignup, Login())
                .addToBackStack(null)
                .commit()
        }

        googleIconLayout.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        facebookIconLayout.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun highLight(
        name: TextInputLayout,
        phone: TextInputLayout,
        email: TextInputLayout,
        password: TextInputLayout,
        confirmPassword: TextInputLayout
    ) {
        val defaultColor = Color.parseColor("#D3D3D3") // Light gray
        val defaultStrokeWidth = 1
        val errorColor = Color.parseColor("#FF0000") // Red
        val errorStrokeWidth = 2

        if(name.editText?.text.toString().trim().isEmpty()){
            name.boxStrokeColor = errorColor
            name.boxStrokeWidth = errorStrokeWidth
        } else {
            name.boxStrokeColor = defaultColor
            name.boxStrokeWidth = defaultStrokeWidth
        }

        if(phone.editText?.text.toString().trim().isEmpty()){
            phone.boxStrokeColor = errorColor
            phone.boxStrokeWidth = errorStrokeWidth
        } else {
            phone.boxStrokeColor = defaultColor
            phone.boxStrokeWidth = defaultStrokeWidth
        }

        if(email.editText?.text.toString().trim().isEmpty()){
            email.boxStrokeColor = errorColor
            email.boxStrokeWidth = errorStrokeWidth
        } else {
            email.boxStrokeColor = defaultColor
            email.boxStrokeWidth = defaultStrokeWidth
        }

        if(password.editText?.text.toString().trim().isEmpty()){
            password.boxStrokeColor = errorColor
            password.boxStrokeWidth = errorStrokeWidth
        } else {
            password.boxStrokeColor = defaultColor
            password.boxStrokeWidth = defaultStrokeWidth
        }

        if(confirmPassword.editText?.text.toString().trim().isEmpty()){
            confirmPassword.boxStrokeColor = errorColor
            confirmPassword.boxStrokeWidth = errorStrokeWidth
        } else {
            confirmPassword.boxStrokeColor = defaultColor
            confirmPassword.boxStrokeWidth = defaultStrokeWidth
        }
    }
}
