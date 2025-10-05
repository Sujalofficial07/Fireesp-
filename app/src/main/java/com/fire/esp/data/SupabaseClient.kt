package com.fire.esp.data

import io.supabase.SupabaseClient
import io.supabase.gotrue.GoTrueUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SupabaseClientManager {

    // Initialize the Supabase client once
    val client = SupabaseClient(
        supabaseUrl = SupabaseConfig.URL,
        supabaseKey = SupabaseConfig.ANON_KEY
    )

    /** ------------------ AUTH ------------------ **/

    // Sign in with Google (redirect-based)
    suspend fun signInWithGoogle(): GoTrueUser? = withContext(Dispatchers.IO) {
        val response = client.auth.signInWithOAuth("google")
        response.user
    }

    // Send OTP for phone login
    suspend fun sendPhoneOTP(phone: String): Boolean = withContext(Dispatchers.IO) {
        val response = client.auth.signInWithPhone(phone)
        response.user != null
    }

    // Verify OTP
    suspend fun verifyPhoneOTP(phone: String, code: String): GoTrueUser? = withContext(Dispatchers.IO) {
        val response = client.auth.verifyOTP(phone, code)
        response.user
    }

    /** ------------------ PROFILE ------------------ **/

    // Get current logged in user's profile
    suspend fun getProfile(): Profile? = withContext(Dispatchers.IO) {
        val user = client.auth.getUser() ?: return@withContext null
        val response = client.from("profiles").select("*").eq("id", user.id).single().execute()
        val data = response.data ?: return@withContext null

        Profile(
            id = data["id"].toString(),
            displayName = data["display_name"].toString(),
            avatarUrl = data["avatar_url"] as? String,
            isAdmin = data["is_admin"] as? Boolean ?: false,
            totalWins = (data["total_wins"] as? Int) ?: 0,
            totalPoints = (data["total_points"] as? Int) ?: 0,
            kdr = (data["kdr"] as? Double) ?: 0.0
        )
    }

    /** ------------------ TOURNAMENTS ------------------ **/

    // Fetch all tournaments
    suspend fun getAllTournaments(): List<Tournament> = withContext(Dispatchers.IO) {
        val response = client.from("tournaments").select("*").order("start_date", ascending = true).execute()
        val list = response.data as? List<Map<String, Any>> ?: return@withContext emptyList()

        list.map { t ->
            Tournament(
                id = t["id"] as String,
                name = t["name"] as String,
                description = t["description"] as String,
                start_date = t["start_date"] as String,
                end_date = t["end_date"] as String,
                max_participants = (t["max_participants"] as? Int) ?: 0,
                prize_pool = t["prize_pool"] as String,
                status = t["status"] as String,
                created_by = t["created_by"] as? String
            )
        }
    }

    /** ------------------ LEADERBOARD ------------------ **/

    // Fetch leaderboard users ordered by points
    suspend fun getLeaderboard(): List<LeaderboardUser> = withContext(Dispatchers.IO) {
        val response = client.from("profiles").select("*").order("total_points", false).execute()
        val list = response.data as? List<Map<String, Any>> ?: return@withContext emptyList()

        list.map { u ->
            LeaderboardUser(
                displayName = u["display_name"].toString(),
                totalPoints = (u["total_points"] as? Int) ?: 0,
                kdr = (u["kdr"] as? Double) ?: 0.0
            )
        }
    }
}
