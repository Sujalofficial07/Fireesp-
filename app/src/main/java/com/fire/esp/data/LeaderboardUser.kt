package com.fire.esp.data

data class LeaderboardUser(
    val id: String = "",
    val display_name: String = "",
    val total_wins: Int = 0,
    val total_points: Int = 0,
    val kdr: Double = 0.0
)
