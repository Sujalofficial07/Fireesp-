package com.fire.esp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fire.esp.databinding.ActivityProfileBinding
import io.supabase.SupabaseClient
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding: ActivityProfileBinding
    private val scope = MainScope()
    private lateinit var supabase: SupabaseClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityProfileBinding.inflate(inflater, container, false)

        supabase = SupabaseClient(
            supabaseUrl = "YOUR_SUPABASE_URL",
            supabaseKey = "YOUR_SUPABASE_ANON_KEY"
        )

        loadProfile()
        return binding.root
    }

    private fun loadProfile() {
        scope.launch {
            val user = supabase.auth.getUser()
            val response = supabase.from("profiles").select("*").eq("id", user?.id).single().execute()
            if(response.data != null){
                val p = response.data
                binding.tvDisplayName.text = p["display_name"].toString()
                binding.tvWins.text = "Wins: ${p["total_wins"]}"
                binding.tvPoints.text = "Points: ${p["total_points"]}"
                binding.tvKdr.text = "K/D: ${p["kdr"]}"
            }
        }
    }
}
