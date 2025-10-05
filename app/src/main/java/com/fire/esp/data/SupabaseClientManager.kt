package com.fire.esp.utils

import com.fire.esp.data.LeaderboardUser
import com.fire.esp.data.Profile
import com.fire.esp.data.Tournament
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.select
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SupabaseClientManager {

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

    // Fetch leaderboard users
    suspend fun fetchLeaderboard(): List<LeaderboardUser> = withContext(Dispatchers.IO) {
        val response = client.from("leaderboard").select("*").execute()
        if (response.error == null) {
            response.data?.map {
                LeaderboardUser(
                    id = it["id"] as? String ?: "",
                    displayName = it["display_name"] as? String ?: "",
                    totalWins = (it["total_wins"] as? Long)?.toInt() ?: 0,
                    totalPoints = (it["total_points"] as? Long)?.toInt() ?: 0,
                    kdr = it["kdr"] as? Double ?: 0.0
                )
            } ?: emptyList()
        } else emptyList()
    }

    // TODO: Add your Google sign-in, phone OTP, and other auth functions here
}
