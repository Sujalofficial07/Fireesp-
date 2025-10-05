package com.fire.esp.utils

import com.fire.esp.data.SupabaseClientProvider
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.query.PostgrestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SupabaseClientManager {

    val client = SupabaseClientProvider.client

    // -------------------------
    // Authentication
    // -------------------------

    suspend fun signInWithGoogle() = withContext(Dispatchers.IO) {
        client.gotrue.loginWith(GoTrue.Provider.GOOGLE)
    }

    suspend fun signInWithPhone(phone: String) = withContext(Dispatchers.IO) {
        client.gotrue.signInWith(GoTrue.Phone(phone))
    }

    suspend fun verifyPhoneOTP(phone: String, code: String): UserInfo? = withContext(Dispatchers.IO) {
        val session = client.gotrue.verifyPhoneOtp(phone, code)
        session.user
    }

    suspend fun getCurrentUser(): UserInfo? = withContext(Dispatchers.IO) {
        client.gotrue.retrieveUserForCurrentSession()
    }

    suspend fun signOut() = withContext(Dispatchers.IO) {
        client.gotrue.logout()
    }

    // -------------------------
    // Database helpers
    // -------------------------

    suspend fun fetchTable(table: String): PostgrestResult? = withContext(Dispatchers.IO) {
        client.postgrest[table].select().decodeList()
    }
}
