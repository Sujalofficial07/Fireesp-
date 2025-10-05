package com.fire.esp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fire.esp.data.LeaderboardUser
import com.fire.esp.databinding.ItemLeaderboardBinding

class LeaderboardAdapter(private var users: List<LeaderboardUser>) :
    RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    inner class LeaderboardViewHolder(val binding: ItemLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val binding = ItemLeaderboardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LeaderboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val user = users[position]
        holder.binding.tvName.text = user.display_name
        holder.binding.tvPoints.text = "Points: ${user.total_points}"
        holder.binding.tvKdr.text = "K/D: ${user.kdr}"
    }

    override fun getItemCount(): Int = users.size

    /** Update the list dynamically from Supabase response */
    fun updateList(newUsers: List<LeaderboardUser>) {
        users = newUsers
        notifyDataSetChanged()
    }
    }
    
