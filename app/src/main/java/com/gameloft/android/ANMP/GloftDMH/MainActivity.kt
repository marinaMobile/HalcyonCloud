package com.gameloft.android.ANMP.GloftDMH

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.appsflyer.AppsFlyerLib
import com.gameloft.android.ANMP.GloftDMH.black.Filter
import com.gameloft.android.ANMP.GloftDMH.black.MainViewModel
import com.gameloft.android.ANMP.GloftDMH.black.utils.CNST
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.AndroidEntryPoint
import io.sentry.Sentry
import kotlinx.coroutines.*


@InternalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val prefs = getSharedPreferences("ActivityPREF", MODE_PRIVATE)

        if (prefs.getBoolean("activity_exec", false)) {
            Intent(this, Filter::class.java).also { startActivity(it) }
            finish()
        } else {
            val exec = prefs.edit()
            exec.putBoolean("activity_exec", true)
            exec.apply()
            Sentry.captureMessage("First install")
        }

        viewModel.deePP(this)
        AppsFlyerLib.getInstance().init(CNST.AF_DEV_KEY, viewModel.conversionDataListener, applicationContext)
        AppsFlyerLib.getInstance().start(this)
        afNullRecordedOrNotChecker(1000)

    }

    private fun toTestGrounds() {
        Intent(this, Filter::class.java)
            .also { startActivity(it) }
        finish()

    }

    private fun afNullRecordedOrNotChecker(timeInterval: Long): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            while (NonCancellable.isActive) {
                val hawk1: String? = Hawk.get(CNST.C1)
                if (hawk1 != null) {
                    Log.d("TestInUIHawk", hawk1.toString())
                    toTestGrounds()
                    break
                } else {

                    val hawk1: String? = Hawk.get(CNST.C1)
                    Sentry.captureMessage("Constant 1: $hawk1")
                    Log.d("TestInUIHawkNulled", hawk1.toString())
                    delay(timeInterval)
                }
            }
        }
    }
}