package com.fire.esp.data

import io.supabase.SupabaseClient

object SupabaseClientProvider {
    val client: SupabaseClient by lazy {
        SupabaseClient(
            supabaseUrl = SupabaseConfig.URL,
            supabaseKey = SupabaseConfig.ANON_KEY
        )
    }
}
