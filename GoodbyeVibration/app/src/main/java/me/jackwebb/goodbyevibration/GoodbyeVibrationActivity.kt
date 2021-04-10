package me.jackwebb.goodbyevibration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import me.jackwebb.goodbyevibration.ui.main.AppsFragment

@AndroidEntryPoint
class GoodbyeVibrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, AppsFragment.newInstance())
                    .commitNow()
        }
    }
}