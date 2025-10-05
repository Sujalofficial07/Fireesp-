package com.fire.esp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fire.esp.data.Tournament
import com.fire.esp.databinding.ItemTournamentBinding

class TournamentAdapter(private var tournaments: List<Tournament>) :
    RecyclerView.Adapter<TournamentAdapter.TournamentViewHolder>() {

    inner class TournamentViewHolder(val binding: ItemTournamentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        val binding = ItemTournamentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TournamentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        val tournament = tournaments[position]
        holder.binding.tvName.text = tournament.name
        holder.binding.tvStatus.text = "Status: ${tournament.status}"

        holder.binding.btnJoinTournament.setOnClickListener {
            // Handle join tournament action, e.g., call Supabase API
        }
    }

    override fun getItemCount(): Int = tournaments.size

    /** Update the list dynamically from Supabase */
    fun updateList(newTournaments: List<Tournament>) {
        tournaments = newTournaments
        notifyDataSetChanged()
    }
    }
    
