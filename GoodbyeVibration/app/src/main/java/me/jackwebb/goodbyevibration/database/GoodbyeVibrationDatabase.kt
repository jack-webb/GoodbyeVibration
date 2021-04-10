package me.jackwebb.goodbyevibration.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.jackwebb.goodbyevibration.model.App

@Database(entities = [App::class], version = 1)
abstract class GoodbyeVibrationDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}