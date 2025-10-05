package com.fire.esp.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClientProvider {

    private const val SUPABASE_URL = "https://rqjsgmxnzqemjztvdomr.supabase.co"
    private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJxanNnbXhuenFlbWp6dHZkb21yIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTk1NjA1MzUsImV4cCI6MjA3NTEzNjUzNX0.xL5qWoqgwRdsrPTi3CRU-RGt3xfVmMMjwA99t8YyOU0"

    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_ANON_KEY
        ) {
            install(GoTrue)
            install(Postgrest)
        }
    }
}
