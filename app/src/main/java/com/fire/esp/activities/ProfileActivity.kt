package com.fire.esp.activities

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fire.esp.R
import com.fire.esp.utils.SupabaseClientManager
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvEmail: TextView
    private lateinit var tvUserId: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvEmail = findViewById(R.id.tvEmail)
        tvUserId = findViewById(R.id.tvUserId)

        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        lifecycleScope.launch {
            try {
                val user = SupabaseClientManager.client.auth.currentUser
                if (user != null) {
                    tvEmail.text = user.email ?: "No email"
                    tvUserId.text = user.id
                } else {
                    Toast.makeText(this@ProfileActivity, "User not logged in", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "Failed to fetch profile: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
