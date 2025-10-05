package com.fire.esp.data

import io.supabase.SupabaseClient

object SupabaseClientProvider {
    val client: SupabaseClient by lazy {
        SupabaseClient(
          supabase_url = "https://rqjsgmxnzqemjztvdomr.supabase.co"
          supabase_anon_key = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJxanNnbXhuenFlbWp6dHZkb21yIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTk1NjA1MzUsImV4cCI6MjA3NTEzNjUzNX0.xL5qWoqgwRdsrPTi3CRU-RGt3xfVmMMjwA99t8YyOU0"

        )
    }
}
