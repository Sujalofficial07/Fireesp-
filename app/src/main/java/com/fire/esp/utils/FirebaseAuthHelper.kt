package com.fire.esp.utils

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

object FirebaseAuthHelper {
    private val auth = FirebaseAuth.getInstance()

    fun signInWithGoogle(activity: Activity, callback: (Boolean) -> Unit) {
        // Implement Google Sign-In
        // On success: callback(true)
    }

    fun signInWithPhone(activity: Activity, callback: (Boolean) -> Unit) {
        // Implement Phone OTP
        // On success: callback(true)
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser
}
