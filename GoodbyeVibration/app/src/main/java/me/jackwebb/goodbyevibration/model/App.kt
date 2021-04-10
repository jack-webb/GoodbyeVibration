package me.jackwebb.goodbyevibration.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apps")
data class App(
    @PrimaryKey
    val packageName: String,
    val vibrationDisabled: Boolean
)
