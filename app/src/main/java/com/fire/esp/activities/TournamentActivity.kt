package com.fire.esp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.fire.esp.R
import com.fire.esp.adapters.TournamentAdapter
import com.fire.esp.data.SupabaseClient
import com.fire.esp.data.Tournament

class TournamentActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TournamentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament)

        recyclerView = findViewById(R.id.recyclerTournaments)
        adapter = TournamentAdapter(emptyList()) // ready adapter
        recyclerView.adapter = adapter

        fetchTournaments()
    }

    private fun fetchTournaments() {
        // Fetch tournaments from Supabase
        // adapter.updateList(fetchedTournaments)
    }
}
