package me.jackwebb.goodbyevibration

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import timber.log.Timber

inline fun <T : Any, L : LiveData<T>> LifecycleOwner.observeNotNull(liveData: L, crossinline body: (T) -> Unit) {
    liveData.observe(this, {
        it?.let(body) ?: run { Timber.i("observeNonNull: Ignoring null value") }
    })
}