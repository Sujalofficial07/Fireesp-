package com.fire.esp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fire.esp.databinding.ActivityAdminBinding
import io.supabase.SupabaseClient
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AdminFragment : Fragment() {
    private lateinit var binding: ActivityAdminBinding
    private val scope = MainScope()
    private lateinit var supabase: SupabaseClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityAdminBinding.inflate(inflater, container, false)

        supabase = SupabaseClient(
            supabaseUrl = "YOUR_SUPABASE_URL",
            supabaseKey = "YOUR_SUPABASE_ANON_KEY"
        )

        binding.btnAddTournament.setOnClickListener { addTournament() }
        binding.btnEditTournament.setOnClickListener { editTournament() }
        binding.btnDeleteTournament.setOnClickListener { deleteTournament() }

        return binding.root
    }

    private fun addTournament() {
        // Launch dialog/form and insert into Supabase
    }

    private fun editTournament() {
        // Select tournament and update
    }

    private fun deleteTournament() {
        // Select tournament and delete
    }
}
