package com.fire.esp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.fire.esp.data.LeaderboardUser
import com.fire.esp.data.Profile
import com.fire.esp.data.Tournament
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.select

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
   fun signInWithGoogle(idToken: String, onResult: (Boolean, String?) -> Unit) {
        // Supabase GoTrue Google sign-in example
        client.auth.signInWithOAuth(
            provider = io.github.jan.supabase.gotrue.Provider.Google,
            idToken = idToken
        ).onSuccess {
            onResult(true, null)
        }.onFailure {
            onResult(false, it.message)
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
