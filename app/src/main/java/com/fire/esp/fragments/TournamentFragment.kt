package com.fire.esp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fire.esp.adapters.TournamentAdapter
import com.fire.esp.data.Tournament
import com.fire.esp.databinding.ActivityTournamentBinding
import io.supabase.SupabaseClient
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TournamentFragment : Fragment() {
    private lateinit var binding: ActivityTournamentBinding
    private lateinit var adapter: TournamentAdapter
    private val scope = MainScope()
    private lateinit var supabase: SupabaseClient
    private var tournaments = mutableListOf<Tournament>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityTournamentBinding.inflate(inflater, container, false)

        supabase = SupabaseClient(
            supabaseUrl = "YOUR_SUPABASE_URL",
            supabaseKey = "YOUR_SUPABASE_ANON_KEY"
        )

        adapter = TournamentAdapter(tournaments)
        binding.recyclerTournaments.layoutManager = LinearLayoutManager(context)
        binding.recyclerTournaments.adapter = adapter

        loadTournaments()
        return binding.root
    }

    private fun loadTournaments() {
        scope.launch {
            val response = supabase.from("tournaments").select("*").execute()
            if(response.data != null){
                tournaments.clear()
                tournaments.addAll(response.data as List<Tournament>)
                adapter.notifyDataSetChanged()
            }
        }
    }
}
