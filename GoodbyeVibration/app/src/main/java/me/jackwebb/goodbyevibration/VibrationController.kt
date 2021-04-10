package me.jackwebb.goodbyevibration

import com.topjohnwu.superuser.Shell

object VibrationController {
    fun disable(packageNames: Collection<String>) =
        Shell.su(*packageNames.map { "cmd appops set $it VIBRATE ignore" }.toTypedArray())
            .exec()

    fun disable(packageName: String) =
        Shell.su("cmd appops set $packageName VIBRATE ignore").exec()

    fun enable(packageNames: Collection<String>) =
        Shell.su(*packageNames.map { "cmd appops set $it VIBRATE allow" }.toTypedArray())
            .exec()

    fun enable(packageName: String) =
        Shell.su("cmd appops set $packageName VIBRATE allow").exec()
}