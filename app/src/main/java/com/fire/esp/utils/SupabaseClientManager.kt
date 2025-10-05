package com.fire.esp.utils

import io.supabase.supabase.SupabaseClient
import io.supabase.supabase.gotrue.Gotrue
import io.supabase.supabase.postgrest.PostgrestClient

object SupabaseClientManager {

    private const val SUPABASE_URL = "https://YOUR_PROJECT.supabase.co"
    private const val SUPABASE_KEY = "YOUR_ANON_KEY"

    val client = SupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    )

    val auth: Gotrue = client.gotrue

    suspend fun signInWithGoogle(idToken: String) {
        // implement Google sign-in logic
    }

    suspend fun sendPhoneOTP(phone: String, onResult: (Boolean) -> Unit) {
        // implement phone OTP send logic
    }

    suspend fun verifyPhoneOTP(phone: String, code: String, onResult: (Boolean) -> Unit) {
        // implement OTP verification
    }
}
