package me.jackwebb.goodbyevibration

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import me.jackwebb.goodbyevibration.ui.apps.Constants.DISABLED_DATA_WORK_KEY

class VibrationPersistenceWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val disabled = inputData.getStringArray(DISABLED_DATA_WORK_KEY) ?: return Result.failure()
        VibrationController.disable(disabled.toList())
        return Result.success()
    }
}
