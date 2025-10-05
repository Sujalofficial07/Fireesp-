package com.fire.esp.utils

import com.fire.esp.data.supabase
import io.supabase.gotrue.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import io.supabase.gotrue.auth.GoogleProvider
import io.supabase.gotrue.auth.PhoneAuthOptions

object SupabaseClientManager {

    suspend fun signInWithGoogle(): User? = withContext(Dispatchers.IO) {
        try {
            val result = supabase.auth.signInWithOAuth(GoogleProvider)
            result.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun sendPhoneOTP(phone: String): Boolean = withContext(Dispatchers.IO) {
        try {
            supabase.auth.signInWithPhone(phone, PhoneAuthOptions(redirectTo = "com.fire.esp://oauth-callback"))
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun verifyPhoneOTP(phone: String, otp: String): User? = withContext(Dispatchers.IO) {
        try {
            val result = supabase.auth.verifyOTP(phone, otp)
            result.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getUserProfile(userId: String) = withContext(Dispatchers.IO) {
        try {
            supabase.from("profiles")
                .select("*")
                .eq("id", userId)
                .single()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchTournaments() = withContext(Dispatchers.IO) {
        try {
            supabase.from("tournaments")
                .select("*")
                .order("startDate", ascending = true)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<Any>()
        }
    }

    suspend fun fetchLeaderboard() = withContext(Dispatchers.IO) {
        try {
            supabase.from("leaderboard")
                .select("*")
                .order("totalPoints", ascending = false)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<Any>()
        }
    }
}
