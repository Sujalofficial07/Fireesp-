package com.fire.esp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fire.esp.adapters.LeaderboardAdapter
import com.fire.esp.data.LeaderboardUser
import com.fire.esp.data.SupabaseClientManager
import com.fire.esp.databinding.ActivityLeaderboardBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LeaderboardFragment : Fragment() {

    private lateinit var binding: ActivityLeaderboardBinding
    private lateinit var adapter: LeaderboardAdapter
    private val scope = MainScope()
    private var users = mutableListOf<LeaderboardUser>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityLeaderboardBinding.inflate(inflater, container, false)

        adapter = LeaderboardAdapter(users)
        binding.recyclerLeaderboard.layoutManager = LinearLayoutManager(context)
        binding.recyclerLeaderboard.adapter = adapter

        loadLeaderboard()
        return binding.root
    }

    private fun loadLeaderboard() {
        scope.launch {
            val fetchedUsers = SupabaseClientManager.getLeaderboardUsers()
            users.clear()
            users.addAll(fetchedUsers)
            adapter.notifyDataSetChanged()
        }
    }
}
