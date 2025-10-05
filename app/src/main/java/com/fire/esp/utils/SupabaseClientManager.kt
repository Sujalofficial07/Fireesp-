package com.fire.esp.utils

import com.fire.esp.data.SupabaseClientProvider
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.sessions.SessionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SupabaseClientManager {

    private val client = SupabaseClientProvider.client
    private val auth = client.auth

    // -------------------------
    // Authentication
    // -------------------------

    suspend fun signInWithGoogle(): UserInfo? = withContext(Dispatchers.IO) {
        try {
            val result = auth.signInWithOAuth(GoTrue.OAuthProvider.Google)
            result.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun signInWithPhone(phone: String): Boolean = withContext(Dispatchers.IO) {
        try {
            auth.signInWithPhone(phone)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun verifyPhoneOTP(phone: String, code: String): UserInfo? = withContext(Dispatchers.IO) {
        try {
            val session = auth.verifyPhoneOtp(phone, code)
            session?.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getCurrentUser(): UserInfo? = withContext(Dispatchers.IO) {
        auth.currentSessionOrNull()?.user
    }

    suspend fun signOut(): Boolean = withContext(Dispatchers.IO) {
        try {
            auth.signOut()
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
            val result = client.from(table).select().decodeList<T>()
            result
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
