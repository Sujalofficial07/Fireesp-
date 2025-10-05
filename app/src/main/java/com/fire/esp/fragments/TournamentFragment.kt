package com.fire.esp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fire.esp.adapters.TournamentAdapter
import com.fire.esp.data.SupabaseClientManager
import com.fire.esp.data.Tournament
import com.fire.esp.databinding.ActivityTournamentBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class TournamentFragment : Fragment() {

    private lateinit var binding: ActivityTournamentBinding
    private lateinit var adapter: TournamentAdapter
    private val scope = MainScope()
    private var tournaments = mutableListOf<Tournament>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityTournamentBinding.inflate(inflater, container, false)

        adapter = TournamentAdapter(tournaments)
        binding.recyclerTournaments.layoutManager = LinearLayoutManager(context)
        binding.recyclerTournaments.adapter = adapter

        loadTournaments()
        return binding.root
    }

    private fun loadTournaments() {
        scope.launch {
            val fetchedTournaments: List<Tournament> = SupabaseClientManager.getAllTournaments()
            tournaments.clear()
            tournaments.addAll(fetchedTournaments)
            adapter.notifyDataSetChanged()
        }
    }
}
