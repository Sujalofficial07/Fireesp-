package com.fire.esp.utils

import com.fire.esp.data.SupabaseClientProvider
import io.supabase.SupabaseClient
import io.supabase.gotrue.User
import io.supabase.gotrue.providers.Provider
import io.supabase.realtime.Subscription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SupabaseClientManager {

    private val supabase: SupabaseClient = SupabaseClientProvider.client

    // ----------------- Auth -----------------

    // Google Sign-In (redirect based)
    suspend fun signInWithGoogle(): User? = withContext(Dispatchers.IO) {
        try {
            val response = supabase.auth.signInWithProvider(Provider.Google)
            response.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Phone login OTP
    suspend fun sendPhoneOTP(phone: String): Boolean = withContext(Dispatchers.IO) {
        try {
            supabase.auth.signInWithOtp(phone = phone)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun verifyPhoneOTP(phone: String, code: String): User? = withContext(Dispatchers.IO) {
        try {
            val response = supabase.auth.verifyOtp(phone = phone, token = code)
            response.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Get current logged in user
    fun getCurrentUser(): User? = supabase.auth.user

    // ----------------- Profile -----------------

    suspend fun getProfile(userId: String): Map<String, Any?>? = withContext(Dispatchers.IO) {
        try {
            val response = supabase.from("profiles").select("*").eq("id", userId).single().execute()
            response.data
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // ----------------- Tournaments -----------------

    suspend fun getTournaments(): List<Map<String, Any?>> = withContext(Dispatchers.IO) {
        try {
            val response = supabase.from("tournaments").select("*").execute()
            response.data as? List<Map<String, Any?>> ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // ----------------- Leaderboard -----------------

    suspend fun getLeaderboard(): List<Map<String, Any?>> = withContext(Dispatchers.IO) {
        try {
            val response = supabase.from("profiles").select("*").order("total_points", false).execute()
            response.data as? List<Map<String, Any?>> ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
