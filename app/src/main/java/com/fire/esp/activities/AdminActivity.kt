package com.fire.esp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fire.esp.R

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        // Admin can add/edit/delete tournaments
    }
}
