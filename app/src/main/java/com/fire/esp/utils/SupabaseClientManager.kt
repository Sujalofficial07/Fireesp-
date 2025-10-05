package com.fire.esp.utils

import android.util.Log
import io.supabase.gotrue.GoTrueClient
import io.supabase.gotrue.User
import io.supabase.postgrest.PostgrestClient
import io.supabase.supabase.SupabaseClient

object SupabaseClientManager {

    // Replace with your Supabase URL and anon key
    private const val SUPABASE_URL = "https://rqjsgmxnzqemjztvdomr.supabase.co"
    private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJxanNnbXhuenFlbWp6dHZkb21yIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTk1NjA1MzUsImV4cCI6MjA3NTEzNjUzNX0.xL5qWoqgwRdsrPTi3CRU-RGt3xfVmMMjwA99t8YyOU0"
    
    // Supabase client
    val client: SupabaseClient by lazy {
        SupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_KEY
        )
    }

    // GoTrue auth client
    val auth: GoTrueClient by lazy {
        client.auth
    }

    // Sign in with Google
    suspend fun signInWithGoogle(idToken: String): User? {
        return try {
            auth.signInWithIdToken(provider = "google", idToken = idToken)
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Google sign-in failed: ${e.message}")
            null
        }
    }

    // Send OTP to phone
    suspend fun sendPhoneOTP(phone: String): Boolean {
        return try {
            auth.signInWithPhone(phone)
            true
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Send OTP failed: ${e.message}")
            false
        }
    }

    // Verify OTP
    suspend fun verifyPhoneOTP(phone: String, code: String): User? {
        return try {
            auth.verifyOTP(phone, code)
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Verify OTP failed: ${e.message}")
            null
        }
    }

    // Fetch data from a table
    suspend fun <T> fetchTable(table: String, mapper: (Map<String, Any>) -> T): List<T> {
        return try {
            val response = client.from(table).select("*").execute()
            if (response.error != null) {
                Log.e("SupabaseClient", "Fetch table $table error: ${response.error.message}")
                emptyList()
            } else {
                (response.data as? List<Map<String, Any>>)?.map { mapper(it) } ?: emptyList()
            }
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Fetch table $table exception: ${e.message}")
            emptyList()
        }
    }

    // Insert data
    suspend fun insertTable(table: String, data: Map<String, Any>): Boolean {
        return try {
            val response = client.from(table).insert(data).execute()
            response.error == null
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Insert table $table failed: ${e.message}")
            false
        }
    }

    // Update data
    suspend fun updateTable(table: String, data: Map<String, Any>, match: Map<String, Any>): Boolean {
        return try {
            val response = client.from(table).update(data).eq(match.keys.first(), match.values.first()).execute()
            response.error == null
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Update table $table failed: ${e.message}")
            false
        }
    }

    // Delete data
    suspend fun deleteTable(table: String, match: Map<String, Any>): Boolean {
        return try {
            val response = client.from(table).delete().eq(match.keys.first(), match.values.first()).execute()
            response.error == null
        } catch (e: Exception) {
            Log.e("SupabaseClient", "Delete table $table failed: ${e.message}")
            false
        }
    }
}
