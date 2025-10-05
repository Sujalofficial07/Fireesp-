package com.fire.esp.utils

import com.fire.esp.data.LeaderboardUser
import io.supabase.SupabaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SupabaseClientManager {
    private val client = SupabaseClient("YOUR_SUPABASE_URL", "YOUR_SUPABASE_KEY")

    suspend fun fetchLeaderboard(): List<LeaderboardUser> = withContext(Dispatchers.IO) {
        val response = client.from("leaderboard").select("*").execute()
        if (response.error == null) {
            response.data?.map {
                LeaderboardUser(
                    id = it["id"] as? String ?: "",
                    display_name = it["display_name"] as? String ?: "",
                    total_wins = (it["total_wins"] as? Long)?.toInt() ?: 0,
                    total_points = (it["total_points"] as? Long)?.toInt() ?: 0,
                    kdr = it["kdr"] as? Double ?: 0.0
                )
            } ?: emptyList()
        } else {
            emptyList()
        }
    }
}
