package me.jackwebb.goodbyevibration.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.jackwebb.goodbyevibration.model.App

@Dao
interface AppDao {
    @Query("SELECT * FROM apps")
    fun getAllApps(): Flow<List<App>>

    @Query("SELECT * FROM apps WHERE vibrationDisabled = 1")
    fun appsWithVibrationDisabled(): Flow<List<App>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(app: App)

    @Query("DELETE FROM apps")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(app: App)
}