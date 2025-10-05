package com.fire.esp.data

data class Tournament(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val maxParticipants: Int = 0,
    val prizePool: String = "",
    val status: String = "upcoming",
    val createdBy: String? = null
)
