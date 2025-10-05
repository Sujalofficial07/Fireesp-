package com.fire.esp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fire.esp.databinding.ActivityPhoneLoginBinding
import com.fire.esp.utils.SupabaseClientManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import io.supabase.SupabaseClient

class PhoneLoginActivity : AppCompatActivity() {

private lateinit var binding: ActivityPhoneLoginBinding
private val scope = MainScope()

override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
binding = ActivityPhoneLoginBinding.inflate(layoutInflater)
setContentView(binding.root)

binding.btnSendOTP.setOnClickListener {
val phone = binding.etPhone.text.toString()
if (phone.isNotEmpty()) sendOTP(phone)
}

binding.btnVerifyOTP.setOnClickListener {
val phone = binding.etPhone.text.toString()
val code = binding.etOTP.text.toString()
if (phone.isNotEmpty() && code.isNotEmpty()) verifyOTP(phone, code)
}
}

private fun sendOTP(phone: String) {
scope.launch {
val success = SupabaseClientManager.sendPhoneOTP(phone)
if (success) Toast.makeText(this@PhoneLoginActivity, "OTP Sent!", Toast.LENGTH_SHORT).show()
else Toast.makeText(this@PhoneLoginActivity, "Failed to send OTP", Toast.LENGTH_SHORT).show()
}
}

private fun verifyOTP(phone: String, code: String) {
scope.launch {
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