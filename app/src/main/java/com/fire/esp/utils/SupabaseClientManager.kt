package com.fire.esp.utils

import io.supabase.SupabaseClient
import io.supabase.gotrue.GoTrueClient
import io.supabase.postgrest.PostgrestClient

object SupabaseClientManager {

    private const val SUPABASE_URL = "https://YOUR_PROJECT.supabase.co"
    private const val SUPABASE_KEY = "YOUR_ANON_KEY"

    // Main Supabase client
    val client = SupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    )

    val auth: GoTrueClient = client.gotrue
    val postgrest: PostgrestClient = client.postgrest

    // Google sign-in (suspend function)
    suspend fun signInWithGoogle(idToken: String, onResult: (Boolean) -> Unit) {
        try {
            val session = auth.signInWithProvider(
                provider = "google",
                idToken = idToken
            )
            onResult(session != null)
        } catch (e: Exception) {
            e.printStackTrace()
            onResult(false)
        }
    }

    // Send phone OTP
    suspend fun sendPhoneOTP(phone: String, onResult: (Boolean) -> Unit) {
        try {
            val response = auth.signInWithOTP(phone)
            onResult(response != null)
        } catch (e: Exception) {
            e.printStackTrace()
            onResult(false)
        }
    }

    // Verify phone OTP
    suspend fun verifyPhoneOTP(phone: String, code: String, onResult: (Boolean) -> Unit) {
        try {
            val session = auth.verifyOTP(phone, code)
            onResult(session != null)
        } catch (e: Exception) {
            e.printStackTrace()
            onResult(false)
        }
    }
}
