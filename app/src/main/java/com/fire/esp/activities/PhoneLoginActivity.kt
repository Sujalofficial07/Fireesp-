package com.fire.esp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fire.esp.databinding.ActivityPhoneLoginBinding
import com.fire.esp.utils.SupabaseClientManager
import kotlinx.coroutines.launch

class PhoneLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Send OTP button
        binding.btnSendOTP.setOnClickListener {
            val phone = binding.etPhone.text.toString()
            if (phone.isNotEmpty()) sendOTP(phone)
            else Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show()
        }

        // Verify OTP button
        binding.btnVerifyOTP.setOnClickListener {
            val phone = binding.etPhone.text.toString()
            val code = binding.etOTP.text.toString()
            if (phone.isNotEmpty() && code.isNotEmpty()) verifyOTP(phone, code)
            else Toast.makeText(this, "Enter phone and OTP", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendOTP(phone: String) {
        lifecycleScope.launch {
            val success = SupabaseClientManager.sendPhoneOTP(phone)
            if (success) {
                Toast.makeText(this@PhoneLoginActivity, "OTP Sent!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@PhoneLoginActivity, "Failed to send OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verifyOTP(phone: String, code: String) {
        lifecycleScope.launch {
            val user = SupabaseClientManager.verifyPhoneOTP(phone, code)
            if (user != null) {
                startActivity(Intent(this@PhoneLoginActivity, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this@PhoneLoginActivity, "Invalid OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
