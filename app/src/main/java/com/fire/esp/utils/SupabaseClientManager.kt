package com.fire.esp.utils

import io.supabase.supabase.SupabaseClient
import io.supabase.supabase.gotrue.Gotrue
import io.supabase.supabase.postgrest.PostgrestClient

object SupabaseClientManager {

    private const val SUPABASE_URL = "https://rqjsgmxnzqemjztvdomr.supabase.co"
    private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJxanNnbXhuenFlbWp6dHZkb21yIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTk1NjA1MzUsImV4cCI6MjA3NTEzNjUzNX0.xL5qWoqgwRdsrPTi3CRU-RGt3xfVmMMjwA99t8YyOU0"
    
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
