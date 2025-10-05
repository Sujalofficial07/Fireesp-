import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import io.supabase.SupabaseClient

val supabase = SupabaseClient(
    supabaseUrl = "https://rqjsgmxnzqemjztvdomr.supabase.co",
    supabaseKey = "<prefer publishable key instead of anon key for mobile apps>"
  ) 