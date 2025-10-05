package com.fire.esp.utils

import com.fire.esp.data.LeaderboardUser
import com.fire.esp.data.Profile
import com.fire.esp.data.Tournament
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.select
import io.github.jan.supabase.postgrest.insert
import io.github.jan.supabase.postgrest.update
import io.github.jan.supabase.postgrest.delete
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SupabaseClientManager {

    private const val SUPABASE_URL = "https://rqjsgmxnzqemjztvdomr.supabase.co"
    private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJxanNnbXhuenFlbWp6dHZkb21yIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTk1NjA1MzUsImV4cCI6MjA3NTEzNjUzNX0.xL5qWoqgwRdsrPTi3CRU-RGt3xfVmMMjwA99t8YyOU0"

    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_ANON_KEY
        ) {
            install(GoTrue)
            install(Postgrest)
        }
    }

    val auth get() = client.gotrue

    // -----------------------------------
    // Authentication
    // -----------------------------------

    suspend fun signInWithGoogle(idToken: String) = withContext(Dispatchers.IO) {
        try {
            val session = auth.signInWithOAuth("google", idToken = idToken)
            session.user
        } catch (e: Exception) {
            null
        }
    }

    suspend fun sendPhoneOTP(phone: String, onResult: (Boolean) -> Unit) = withContext(Dispatchers.IO) {
        try {
            auth.signInWithOtp(phone = phone)
            onResult(true)
        } catch (e: Exception) {
            onResult(false)
        }
    }

    suspend fun verifyPhoneOTP(phone: String, otp: String, onResult: (Boolean) -> Unit) = withContext(Dispatchers.IO) {
        try {
            val session = auth.verifyOtp(phone = phone, token = otp)
            onResult(session.user != null)
        } catch (e: Exception) {
            onResult(false)
        }
    }

    fun signOut() = auth.signOut()

    // -----------------------------------
    // Profiles
    // -----------------------------------

    suspend fun fetchCurrentProfile(): Profile? = withContext(Dispatchers.IO) {
        auth.currentUser?.let {
            Profile(
                id = it.id,
                displayName = it.userMetadata["full_name"] as? String ?: "",
                avatarUrl = it.userMetadata["avatar_url"] as? String,
                email = it.email
            )
        }
    }

    suspend fun updateProfile(profile: Profile): Boolean = withContext(Dispatchers.IO) {
        try {
            client.from("profiles")
                .update(mapOf(
                    "display_name" to profile.displayName,
                    "avatar_url" to profile.avatarUrl
                ))
                .eq("id", profile.id)
                .execute()
            true
        } catch (e: Exception) {
            false
        }
    }

    // -----------------------------------
    // Leaderboard
    // -----------------------------------

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

    suspend fun addLeaderboardUser(user: LeaderboardUser): Boolean = withContext(Dispatchers.IO) {
        try {
            client.from("leaderboard")
                .insert(
                    mapOf(
                        "id" to user.id,
                        "display_name" to user.displayName,
                        "total_wins" to user.totalWins,
                        "total_points" to user.totalPoints,
                        "kdr" to user.kdr
                    )
                )
                .execute()
            true
        } catch (e: Exception) {
            false
        }
    }

    // -----------------------------------
    // Tournaments
    // -----------------------------------

    suspend fun fetchTournaments(): List<Tournament> = withContext(Dispatchers.IO) {
        val response = client.from("tournaments").select("*").execute()
        if (response.error == null) {
            response.data?.map {
                Tournament(
                    id = it["id"] as? String ?: "",
                    name = it["name"] as? String ?: "",
                    description = it["description"] as? String ?: "",
                    startDate = it["start_date"] as? String ?: "",
                    endDate = it["end_date"] as? String ?: "",
                    maxParticipants = (it["max_participants"] as? Long)?.toInt() ?: 0,
                    prizePool = it["prize_pool"] as? String ?: "",
                    status = it["status"] as? String ?: "upcoming",
                    createdBy = it["created_by"] as? String
                )
            } ?: emptyList()
        } else emptyList()
    }

    suspend fun addTournament(tournament: Tournament): Boolean = withContext(Dispatchers.IO) {
        try {
            client.from("tournaments")
                .insert(
                    mapOf(
                        "name" to tournament.name,
                        "description" to tournament.description,
                        "start_date" to tournament.startDate,
                        "end_date" to tournament.endDate,
                        "max_participants" to tournament.maxParticipants,
                        "prize_pool" to tournament.prizePool,
                        "status" to tournament.status,
                        "created_by" to tournament.createdBy
                    )
                )
                .execute()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateTournament(tournament: Tournament): Boolean = withContext(Dispatchers.IO) {
        try {
            client.from("tournaments")
                .update(
                    mapOf(
                        "name" to tournament.name,
                        "description" to tournament.description,
                        "start_date" to tournament.startDate,
                        "end_date" to tournament.endDate,
                        "max_participants" to tournament.maxParticipants,
                        "prize_pool" to tournament.prizePool,
                        "status" to tournament.status
                    )
                )
                .eq("id", tournament.id)
                .execute()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteTournament(tournamentId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            client.from("tournaments")
                .delete()
                .eq("id", tournamentId)
                .execute()
            true
        } catch (e: Exception) {
            false
        }
    }
}
