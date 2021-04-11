package me.jackwebb.goodbyevibration.ui.apps

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.jackwebb.goodbyevibration.AppInfo
import me.jackwebb.goodbyevibration.VibrationPersistenceWorker
import me.jackwebb.goodbyevibration.repo.AppRepository
import me.jackwebb.goodbyevibration.ui.apps.Constants.DISABLED_DATA_WORK_KEY
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(
    application: Application,
    private val appRepository: AppRepository,
) : AndroidViewModel(application) {
    // We're pretty tied to the Android system here, so having application/context refs is prob okay
    private val packageManager = getApplication<Application>().packageManager

    val apps = MutableLiveData<List<AppInfo>>()
    val showSystemApps = MutableLiveData(false)
    val showGoogleApps = MutableLiveData(true)

    @SuppressLint("QueryPermissionsNeeded")
    var applicationInfos: List<ApplicationInfo> =
        packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

    init {
        viewModelScope.launch {
            appRepository.getAppsWithVibrationDisabled().collect { disabledPackages ->
                apps.value = applicationInfos.map { app ->
                    AppInfo(
                        packageManager.getApplicationLabel(app).toString(),
                        app.packageName,
                        packageManager.getApplicationIcon(app),
                        app.packageName in disabledPackages.map { it.packageName }
                    )
                }.sortedBy { it.name }
            }
        }
    }

    fun onAppChecked(packageName: String, checked: Boolean) {
        viewModelScope.launch {
            if (checked) {
                appRepository.disableVibration(packageName)
            } else {
                appRepository.enableVibration(packageName)
            }

            refreshWorker()
        }
    }

    fun showSystemApps(checked: Boolean) {
        showSystemApps.value = checked
    }

    fun showGoogleApps(checked: Boolean) {
        showGoogleApps.value = checked
    }

    fun resetAll() {
        viewModelScope.launch {
            appRepository.resetAllVibration()
        }
    }

    private fun refreshWorker() {
        val disabledPackages = apps.value?.map { it.packageName }?.toTypedArray() ?: run {
            Timber.w("List of apps was null, refreshing worker with empty package array")
            return@run arrayOf<String>()
        }

        val workManager = WorkManager.getInstance(getApplication<Application>().applicationContext)

        val workRequest =
            PeriodicWorkRequestBuilder<VibrationPersistenceWorker>(12, TimeUnit.HOURS)
                .addTag(WORK_REQUEST_TAG)
                .setInitialDelay(12, TimeUnit.HOURS)
                .setInputData(workDataOf(DISABLED_DATA_WORK_KEY to disabledPackages))
                .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_REQUEST_TAG,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    companion object {
        private const val WORK_REQUEST_TAG = "GoodbyeVibration_WorkRequest"
    }
}