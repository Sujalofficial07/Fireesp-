package com.fire.esp.data

import io.supabase.SupabaseClient

object SupabaseClientProvider {
    val client: SupabaseClient by lazy {
        SupabaseClient(
          supabaseUrl = SUPABASE_URL,
          supabaseKey = SUPABASE_ANON_KEY
        )
    }
}
