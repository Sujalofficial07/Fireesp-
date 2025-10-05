package com.fire.esp.data

data class Tournament(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val start_date: String = "",
    val end_date: String = "",
    val max_participants: Int = 0,
    val prize_pool: String = "",
    val status: String = "upcoming",
    val created_by: String? = null
)
