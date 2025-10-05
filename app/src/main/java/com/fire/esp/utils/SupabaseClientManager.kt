package com.fire.esp.utils

import io.supabase.gotrue.GoTrueClient
import io.supabase.gotrue.User
import io.supabase.postgrest.PostgrestClient
import io.supabase.postgrest.PostgrestResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SupabaseClientManager {

    private const val SUPABASE_URL = "https://rqjsgmxnzqemjztvdomr.supabase.co"
    private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJxanNnbXhuenFlbWp6dHZkb21yIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTk1NjA1MzUsImV4cCI6MjA3NTEzNjUzNX0.xL5qWoqgwRdsrPTi3CRU-RGt3xfVmMMjwA99t8YyOU0"
    
    // Auth client
    val auth: GoTrueClient = GoTrueClient(
        url = "$SUPABASE_URL/auth/v1",
        headers = mapOf("apikey" to SUPABASE_ANON_KEY)
    )

    // PostgREST client for database operations
    val client: PostgrestClient = PostgrestClient(
        url = "$SUPABASE_URL/rest/v1",
        headers = mapOf(
            "apikey" to SUPABASE_ANON_KEY,
            "Authorization" to "Bearer ${auth.currentUser?.accessToken ?: ""}"
        )
    )

    // Sign in with Google ID token
    suspend fun signInWithGoogle(idToken: String): User? = withContext(Dispatchers.IO) {
        val response = auth.signIn(
            provider = "google",
            idToken = idToken
        )
        response.user
    }

    // Send phone OTP
    suspend fun sendPhoneOTP(phone: String): Boolean = withContext(Dispatchers.IO) {
        val response = auth.signIn(phone = phone)
        response.user != null
    }

    // Verify phone OTP
    suspend fun verifyPhoneOTP(phone: String, code: String): User? = withContext(Dispatchers.IO) {
        val response = auth.verifyOTP(phone = phone, token = code)
        response.user
    }

    // Fetch table data from Supabase
    suspend fun <T> fetchTable(table: String): List<T> = withContext(Dispatchers.IO) {
        val response: PostgrestResponse<T> = client.from<T>(table).select().execute()
        response.data ?: emptyList()
    }
}
