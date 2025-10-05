package com.fire.esp.data

import io.supabase.SupabaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SupabaseClientManager {
    val client = SupabaseClient(
        supabaseUrl = SupabaseConfig.URL,
        supabaseKey = SupabaseConfig.ANON_KEY
    )

    suspend fun getLeaderboardUsers(): List<LeaderboardUser> = withContext(Dispatchers.IO) {
        val response = client.from("profiles")
            .select("*")
            .order("total_points", false)
            .execute()

        if (response.data != null) {
            response.data.map {
                LeaderboardUser(
                    id = it["id"] as String,
                    display_name = it["display_name"] as String,
                    total_wins = (it["total_wins"] as? Int) ?: 0,
                    total_points = (it["total_points"] as? Int) ?: 0,
                    kdr = (it["kdr"] as? Double) ?: 0.0
                )
            }
        } else {
            emptyList()
        }
    }
}
