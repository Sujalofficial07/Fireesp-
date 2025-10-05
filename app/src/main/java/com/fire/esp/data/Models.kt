package com.fire.esp.data

data class Profile(
    val id: String = "",
    val displayName: String = "",
    val avatarUrl: String? = null,
    val isAdmin: Boolean = false,
    val totalWins: Int = 0,
    val totalPoints: Int = 0,
    val kdr: Double = 0.0
)

data class Tournament(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val maxParticipants: Int = 0,
    val prizePool: String = "",
    val status: String = ""
)

data class LeaderboardUser(
    val displayName: String = "",
    val totalPoints: Int = 0,
    val kdr: Double = 0.0
)
