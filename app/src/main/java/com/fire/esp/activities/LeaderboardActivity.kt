package com.fire.esp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.fire.esp.R
import com.fire.esp.adapters.LeaderboardAdapter
import com.fire.esp.data.LeaderboardUser

class LeaderboardActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LeaderboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        recyclerView = findViewById(R.id.recyclerLeaderboard)
        adapter = LeaderboardAdapter(emptyList())
        recyclerView.adapter = adapter

        fetchLeaderboard()
    }

    private fun fetchLeaderboard() {
        // Fetch leaderboard from Supabase
        // adapter.updateList(users)
    }
}
