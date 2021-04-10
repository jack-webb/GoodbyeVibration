package me.jackwebb.goodbyevibration.repo

import kotlinx.coroutines.flow.collect
import me.jackwebb.goodbyevibration.VibrationController
import me.jackwebb.goodbyevibration.database.AppDao
import me.jackwebb.goodbyevibration.model.App
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val appDao: AppDao
) {

    suspend fun resetEverything() {
        getAppsWithVibrationDisabled().collect {
            it.forEach { app ->
                enableVibration(app.packageName)
                appDao.delete(app)
            }
        }
    }

    // region Vibration
    fun getAppsWithVibrationDisabled() = appDao.appsWithVibrationDisabled()

    suspend fun disableVibration(packageName: String) {
        VibrationController.disable(packageName)
        appDao.insert(App(packageName, true))
    }

    suspend fun enableVibration(packageName: String) {
        VibrationController.enable(packageName)
        appDao.insert(App(packageName, false))
    }

    suspend fun resetAllVibration() {
        getAppsWithVibrationDisabled().collect {
            it.forEach { app -> enableVibration(app.packageName) }
        }
    }
    // endregion Vibration
}