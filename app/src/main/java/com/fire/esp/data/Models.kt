package com.fire.esp.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

// ---------------- Supabase Client ----------------
object SupabaseClientProvider {

    private const val SUPABASE_URL = "https://rqjsgmxnzqemjztvdomr.supabase.co"
    private const val SUPABASE_ANON_KEY = "YOUR_SUPABASE_KEY"

    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_ANON_KEY
        ) {
            install(GoTrue)
            install(Postgrest)
        }
    }
}

// ---------------- Data Models ----------------
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
    val status: String = "upcoming",
    val createdBy: String? = null
)

data class LeaderboardUser(
    val id: String = "",
    val displayName: String = "",
    val totalWins: Int = 0,
    val totalPoints: Int = 0,
    val kdr: Double = 0.0
)
