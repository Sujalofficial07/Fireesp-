package com.fire.esp.utils

import android.view.View
import android.widget.Button

object UIHelper {
    fun applyGlowingAquaButton(button: Button) {
        button.setBackgroundResource(android.R.drawable.btn_default)
        button.setTextColor(0xFF00F5FF.toInt())
        button.elevation = 8f
    }
}
