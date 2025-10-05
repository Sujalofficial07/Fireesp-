package com.fire.esp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fire.esp.data.Profile
import com.fire.esp.data.SupabaseClientManager
import com.fire.esp.databinding.ActivityProfileBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var binding: ActivityProfileBinding
    private val scope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityProfileBinding.inflate(inflater, container, false)

        loadProfile()
        return binding.root
    }

    private fun loadProfile() {
        scope.launch {
            val profile: Profile? = SupabaseClientManager.getCurrentUserProfile()
            profile?.let {
                binding.tvDisplayName.text = it.displayName
                binding.tvWins.text = "Wins: ${it.totalWins}"
                binding.tvPoints.text = "Points: ${it.totalPoints}"
                binding.tvKdr.text = "K/D: ${it.kdr}"
            }
        }
    }
}
