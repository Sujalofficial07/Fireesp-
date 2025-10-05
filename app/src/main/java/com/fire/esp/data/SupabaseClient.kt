package com.fire.esp.data

import io.supabase.SupabaseClient

object SupabaseClientProvider {
    // Replace with your Supabase project URL & anon/public key
    private const val SUPABASE_URL = "https://rqjsgmxnzqemjztvdomr.supabase.co"
    private const val SUPABASE_KEY = "<YOUR_PUBLISHABLE_KEY>"

    val client: SupabaseClient by lazy {
        SupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_KEY
        )
    }
}
