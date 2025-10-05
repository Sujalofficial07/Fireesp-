package com.fire.esp.data
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import io.supabase.SupabaseClient

data class Profile(
    val id: String,
    val displayName: String,
    val avatarUrl: String?,
    val isAdmin: Boolean,
    val totalWins: Int,
    val totalPoints: Int,
    val kdr: Double
)

data class Tournament(
    val id: String,
    val name: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val maxParticipants: Int,
    val prizePool: String,
    val status: String
)

data class LeaderboardUser(
    val displayName: String,
    val totalPoints: Int,
    val kdr: Double
)
