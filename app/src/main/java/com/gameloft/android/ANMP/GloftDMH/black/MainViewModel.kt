package com.gameloft.android.ANMP.GloftDMH.black

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import com.appsflyer.AppsFlyerConversionListener
import com.facebook.applinks.AppLinkData
import com.gameloft.android.ANMP.GloftDMH.black.utils.CNST
import com.gameloft.android.ANMP.GloftDMH.black.utils.CNST.D2
import com.gameloft.android.ANMP.GloftDMH.black.utils.CNST.D3
import com.orhanobut.hawk.Hawk
import io.sentry.Sentry
import java.util.*
import javax.inject.Inject

class MainViewModel  @Inject constructor(): ViewModel() {

    val conversionDataListener = object : AppsFlyerConversionListener {
        override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {

            val dataGotten = data?.get("media_source").toString()


            Hawk.put(CNST.C1, dataGotten)
            Log.d("devTEST", data.toString())
        }

        override fun onConversionDataFail(p0: String?) {
            Sentry.captureMessage("Loss of attribution")

        }

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
            TODO("Not yet implemented")
        }

        override fun onAttributionFailure(p0: String?) {
            TODO("Not yet implemented")
        }
    }


    fun deePP(context: Context) {
        AppLinkData.fetchDeferredAppLinkData(
            context
        ) { appLinkData: AppLinkData? ->
            appLinkData?.let {
                val params =
                    appLinkData.targetUri.pathSegments
                Log.d("D11PL", "$params")
                Sentry.captureMessage("Params FB: $params")
                val conjoined = TextUtils.join("/", params)
                val tokenizer = StringTokenizer(conjoined, "/")
                val firstLink = tokenizer.nextToken()
                val secondLink = tokenizer.nextToken()
                val thirdLink = tokenizer.nextToken()
                Hawk.put(CNST.D1, firstLink)
                Hawk.put(D2, secondLink)
                Hawk.put(D3, thirdLink)

            }
            if (appLinkData == null) {
                Sentry.captureMessage("FB data is null")
                Log.d("FB_TEST:", "Params = null")

            }
        }
    }
}