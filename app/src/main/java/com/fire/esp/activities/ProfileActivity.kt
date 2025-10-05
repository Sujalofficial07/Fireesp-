package com.fire.esp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fire.esp.databinding.ActivityProfileBinding
import com.fire.esp.utils.SupabaseClientManager

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fetch current user from Supabase Auth
        val currentUser = SupabaseClientManager.getCurrentUser()

        binding.tvEmail.text = currentUser?.email ?: "No Email"
        binding.tvUserId.text = currentUser?.id ?: "No ID"
        binding.tvDisplayName.text =
            currentUser?.userMetadata?.get("full_name")?.toString() ?: "Unknown"
    }
}
