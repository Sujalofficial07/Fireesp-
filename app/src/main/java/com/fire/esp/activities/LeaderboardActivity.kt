package com.fire.esp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fire.esp.adapters.LeaderboardAdapter
import com.fire.esp.data.LeaderboardUser
import com.fire.esp.databinding.ActivityLeaderboardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaderboardBinding
    private lateinit var adapter: LeaderboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = LeaderboardAdapter(emptyList())
        binding.recyclerLeaderboard.layoutManager = LinearLayoutManager(this)
        binding.recyclerLeaderboard.adapter = adapter

        fetchLeaderboard()
    }

    private fun fetchLeaderboard() {
        CoroutineScope(Dispatchers.Main).launch {
            val users: List<LeaderboardUser> = SupabaseClientManager.fetchLeaderboard()
            adapter.updateList(users)
        }
    }
}
