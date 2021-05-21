package me.jackwebb.goodbyevibration.ui.apps

import android.graphics.drawable.Drawable

data class AppInfo(
    val name: String,
    val packageName: String,
    val icon: Drawable,
    val checked: Boolean
)