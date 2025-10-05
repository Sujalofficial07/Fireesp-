package com.fire.esp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fire.esp.databinding.ActivityLoginBinding
import com.fire.esp.utils.SupabaseClientManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import io.supabase.SupabaseClient
import com.fire.esp.databinding.ActivityHomeBinding


class LoginActivity : AppCompatActivity() {

private lateinit var binding: ActivityLoginBinding
private val scope = MainScope()

override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
binding = ActivityLoginBinding.inflate(layoutInflater)
setContentView(binding.root)

// Google login
binding.btnGoogleLogin.setOnClickListener {
scope.launch {
val user = SupabaseClientManager.signInWithGoogle()
if (user != null) openHome()
}
}

// Phone login
binding.btnPhoneLogin.setOnClickListener {
startActivity(Intent(this, PhoneLoginActivity::class.java))
}
}

private fun openHome() {
startActivity(Intent(this, HomeActivity::class.java))
finish()
}
}