package me.jackwebb.goodbyevibration

import android.graphics.drawable.Drawable

data class AppInfo(
    val name: String,
    val packageName: String,
    val icon: Drawable,
    val checked: Boolean
)