package me.jackwebb.goodbyevibration.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.jackwebb.goodbyevibration.database.AppDao
import me.jackwebb.goodbyevibration.database.GoodbyeVibrationDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): GoodbyeVibrationDatabase {
        return Room.databaseBuilder(
            appContext,
            GoodbyeVibrationDatabase::class.java,
            "goodbye_vibration_db.db"
        ).build()
    }

    @Provides
    fun provideAppDao(database: GoodbyeVibrationDatabase):AppDao {
        return database.appDao()
    }
}