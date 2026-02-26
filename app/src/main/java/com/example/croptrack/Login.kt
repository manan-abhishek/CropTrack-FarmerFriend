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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.croptrack.R
import com.example.croptrack.Signup
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

// Extension function to check if TextInputLayout's EditText is empty
private fun TextInputLayout.isEmptyField(): Boolean {
    return this.editText?.text.toString().trim().isEmpty()
}

class Login : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        if(sharedPreferences.contains("phone") || sharedPreferences.contains("email")){
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra("phone", sharedPreferences.getString("phone", "Please add your Phone number"))
            intent.putExtra("email", sharedPreferences.getString("email", "please add your email"))
            intent.putExtra("name", sharedPreferences.getString("name", ""))
            startActivity(intent)
            requireActivity().finish()
        }


        val loginErr = view.findViewById<TextView>(R.id.loginErr)
        val nameInputLayout = view.findViewById<TextInputLayout>(R.id.loginNameInputLayout)
        val phoneInputLayout = view.findViewById<TextInputLayout>(R.id.loginPhoneInputLayout)
        val emailInputLayout = view.findViewById<TextInputLayout>(R.id.loginEmailInputLayout)
        val passwordInputLayout = view.findViewById<TextInputLayout>(R.id.loginPasswordInputLayout)
        val loginButton = view.findViewById<MaterialButton>(R.id.loginButton)
        val redirectToSignUp = view.findViewById<TextView>(R.id.redirectToSignUp)

        val googleIconLayout = view.findViewById<LinearLayout>(R.id.googleIconLayout)
        val facebookIconLayout = view.findViewById<LinearLayout>(R.id.facebookIconLayout)

        val loginBtn = requireActivity().findViewById<TextView>(R.id.login)
        val signupBtn = requireActivity().findViewById<TextView>(R.id.signup)

        loginButton.setOnClickListener {
            val name = nameInputLayout.editText?.text.toString().trim()
            val phone = phoneInputLayout.editText?.text.toString().trim()
            val email = emailInputLayout.editText?.text.toString().trim()
            val password = passwordInputLayout.editText?.text.toString().trim()

            if (name.isNotEmpty() && ((phone.isNotEmpty() && phone.length == 10) || (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())) && password.isNotEmpty()) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("phone", phone)
                intent.putExtra("email", email)
                startActivity(intent)
                requireActivity().finish()
            } else {
                highLight(nameInputLayout, phoneInputLayout, emailInputLayout, passwordInputLayout)
                loginErr.text = getString(R.string.ipErr)
            }
        }

        redirectToSignUp.setOnClickListener {
            loginBtn.setTextColor(Color.parseColor("#888888"))
            loginBtn.background = null
            signupBtn.setTextColor(Color.parseColor("#ffffff"))
            signupBtn.background = ContextCompat.getDrawable(requireActivity(), R.drawable.colorbg)

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.loginSignup, Signup())
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

    private fun highLight(name: TextInputLayout, phone: TextInputLayout, email: TextInputLayout, pass: TextInputLayout) {
        if(name.isEmptyField()) {
            name.boxStrokeColor = Color.parseColor("#FF0000")
            name.boxStrokeWidth = 2
        }
        if(phone.isEmptyField()) {
            phone.boxStrokeColor = Color.parseColor("#FF0000")
            phone.boxStrokeWidth = 2
        }
        if(email.isEmptyField()){
            email.boxStrokeColor = Color.parseColor("#FF0000")
            email.boxStrokeWidth = 2
        }
        if(pass.isEmptyField()){
            pass.boxStrokeColor = Color.parseColor("#FF0000")
            pass.boxStrokeWidth = 2
        }
    }
}
