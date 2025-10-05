package com.fire.esp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.fire.esp.R
import com.fire.esp.adapters.TournamentAdapter
import com.fire.esp.data.Tournament
import com.fire.esp.utils.SupabaseClientManager
import kotlinx.coroutines.launch

class TournamentActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TournamentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament)

        recyclerView = findViewById(R.id.recyclerTournaments)
        adapter = TournamentAdapter(emptyList())
        recyclerView.adapter = adapter

        fetchTournaments()
    }

    private fun fetchTournaments() {
        lifecycleScope.launch {
            try {
                val response = SupabaseClientManager.fetchTournaments()
                adapter.updateList(response)
            } catch (e: Exception) {
                Toast.makeText(
                    this@TournamentActivity,
                    "Failed to fetch tournaments: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
