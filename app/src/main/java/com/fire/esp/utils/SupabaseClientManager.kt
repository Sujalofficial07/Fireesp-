package com.fire.esp.utils

import com.fire.esp.data.SupabaseClientProvider
import io.supabase.models.User
import io.supabase.gotrue.response.GoTrueSession
import io.supabase.gotrue.response.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SupabaseClientManager {

    private val client = SupabaseClientProvider.client

    // -------------------------
    // Authentication
    // -------------------------

    suspend fun signInWithGoogle(): User? = withContext(Dispatchers.IO) {
        try {
            val session: Session? = client.auth.signInWithProvider("google")
            session?.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun signInWithPhone(phone: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val otpResult = client.auth.signInWithOtp(phone)
            otpResult != null
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun verifyPhoneOTP(phone: String, code: String): User? = withContext(Dispatchers.IO) {
        try {
            val session: GoTrueSession? = client.auth.verifyOtp(phone, code)
            session?.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getCurrentUser(): User? = withContext(Dispatchers.IO) {
        client.auth.currentUser
    }

    suspend fun signOut(): Boolean = withContext(Dispatchers.IO) {
        try {
            client.auth.signOut()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // -------------------------
    // Database helpers
    // -------------------------

    suspend fun <T> fetchTable(table: String): List<T>? = withContext(Dispatchers.IO) {
        try {
            val response = client.from(table).select("*").execute()
            response.data as? List<T>
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
