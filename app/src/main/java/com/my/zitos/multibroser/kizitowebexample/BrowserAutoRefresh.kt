package com.my.zitos.multibroser.kizitowebexample

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.my.zitos.multibroser.kizitowebexample.sharedPref.setWebView1UrlPref
import com.my.zitos.multibroser.kizitowebexample.viewModelAndWebViewHolder.GenViewModel
import com.my.zitos.multibroser.kizitowebexample.viewModelAndWebViewHolder.GenViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun BrowserAutoRefresh(
    modifier: Modifier = Modifier,
    initUrl: String,
    isPcModeActiveInit: Boolean,
    isAdBlockerActiveInit: Boolean,
    isIncognitoActiveInit: Boolean,
    showSettings: (url:String, pcMode: Boolean,adMode: Boolean, incognitoMode: Boolean) -> Unit

) {



    val snowColor by remember { mutableStateOf(Color(0xffFFFBFE)) }

    val context = LocalContext.current

    val scope = rememberCoroutineScope()



    //  Remove or Stop Ad in web
    val adServers = StringBuilder()
    var line: String? = ""
    val inputStream = context.resources.openRawResource(R.raw.adblockerserverlist)
    val br = BufferedReader(InputStreamReader(inputStream))
    try {
        while (br.readLine().also { line = it } != null) {
            adServers.append(line)
            adServers.append("\n")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }



    val genViewModel: GenViewModel = viewModel(
        factory = GenViewModelFactory(context)
    )

    val flowWebViewHolder by genViewModel.webViewHolder1.collectAsStateWithLifecycle()







    LaunchedEffect(isPcModeActiveInit) {

        flowWebViewHolder.isPcModeActive = isPcModeActiveInit
    }
    LaunchedEffect(isAdBlockerActiveInit) {

        flowWebViewHolder.isAdBlockerActive = isAdBlockerActiveInit

    }
    LaunchedEffect(isIncognitoActiveInit) {

        flowWebViewHolder.isIncognitoActive = isIncognitoActiveInit

    }

    LaunchedEffect(initUrl) {
        delay(200)
        flowWebViewHolder.webViewUrl = initUrl

        if (flowWebViewHolder.webView.url.isNullOrBlank()) {
            if (initUrl.isNotBlank()) {
                flowWebViewHolder.webController = WebControllerType.LOAD_URL
            }
        }
    }



    val lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }





    Column(modifier = modifier
        .fillMaxSize()
    ) {



        AndroidView(factory = { webContext ->


            flowWebViewHolder.webView.apply {


            layoutParams = ViewGroup.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
            )

                setLayerType(View.LAYER_TYPE_HARDWARE, null)

                settings.apply {

                    javaScriptEnabled = true

                    displayZoomControls = false

                    // Enable smooth scrolling
                    isVerticalScrollBarEnabled = true
                    isHorizontalScrollBarEnabled = true
                   isScrollbarFadingEnabled = true


                    mediaPlaybackRequiresUserGesture = false // Allow auto-play of media
                    loadsImagesAutomatically = true
                    javaScriptCanOpenWindowsAutomatically = true


                    domStorageEnabled = false  // Enable DOM storage
                    databaseEnabled = false  // Enable database

                }



                // Enable private browsing mode
                if (flowWebViewHolder.isPcModeActive == true) {
                    this.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
                    this.settings.userAgentString  = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:70.0) Gecko/20100101 Firefox/70.0"
                    this.settings.useWideViewPort = true
                     this.settings.loadWithOverviewMode = true

                }else{

                    this.settings.userAgentString = WebSettings.getDefaultUserAgent(context)
                    this.settings.useWideViewPort = false
                    this.settings.loadWithOverviewMode = false
                }

                if (flowWebViewHolder.isIncognitoActive == true){

                    this.settings.cacheMode = WebSettings.LOAD_NO_CACHE

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        this.settings.safeBrowsingEnabled = true
                    }
                }else{
                    this.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        this.settings.safeBrowsingEnabled = false
                    }

                }




                val activity = context as? Activity
                webChromeClient = object : WebChromeClient(){

                    var customView: View? = null


                    override fun onHideCustomView() {

                        customView?.let {
                            (activity)?.window?.decorView?.let { decorView ->
                                (decorView as FrameLayout).removeView(it)
                                customView = null
                            }
                        }

                    }

                    override fun onShowCustomView(paramView: View, paramCustomViewCallback: CustomViewCallback) {

                        if (customView != null) {
                            onHideCustomView()
                            return
                        }
                        customView = paramView
                        ((activity)?.window?.decorView as? FrameLayout)?.addView(
                            customView,
                            FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT
                            )
                        )
                    }

                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        flowWebViewHolder.webProgressValue = newProgress

                    }

                }



                webViewClient = object : WebViewClient() {


                    override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {

                        scope.launch {
                            if (flowWebViewHolder.webViewUrl != view.url.toString()) {

                                // change the url in sharedPref
                                setWebView1UrlPref(
                                    url = view.url.toString(),
                                    dataStore = context.dataStoreAll
                                )
                                // change the url in viewModel
                                flowWebViewHolder.webViewUrl = view.url.toString()
                            }
                        }


                        // Ads Blocker
                        if (flowWebViewHolder.isAdBlockerActive == true) {

                            val empty = ByteArrayInputStream("".toByteArray())
                            val kk5 = adServers.toString()
                            //if (kk5.contains(":::::" + request.url.host))
                            if (kk5.contains("" + request.url.host)) {

                                return WebResourceResponse("text/plain", "utf-8", empty)
                            }
                        }
                        return super.shouldInterceptRequest(view, request)
                    }



                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {

                        if (view != null)
                        {
                            if (url != null && url!!.contains("intent://")) {
                                val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                                if (intent != null) {
                                    val fallbackUrl = intent.getStringExtra("browser_fallback_url")
                                    return if (fallbackUrl != null) {
                                        view.loadUrl(fallbackUrl)
                                        true
                                    } else {
                                        false
                                    }
                                }
                            } else if (url!!.startsWith("tel:")) {
                                val intent = Intent(Intent.ACTION_DIAL)
                                intent.data = Uri.parse(url)
                                // view?.context?.startActivity(intent)
                                context.startActivity(intent)
                                return true
                            } else if (url!!.startsWith("mailto:")) {
                                val intent = Intent(Intent.ACTION_VIEW)
                                val data = Uri.parse(
                                    url + Uri.encode("subject") + "&body=" + Uri.encode(
                                        "body"
                                    )
                                )
                                intent.data = data
                                context.startActivity(intent)
                                // view?.context?.startActivity(intent)
                                return true
                            }
                            return false
                        }else{
                            return super.shouldOverrideUrlLoading(view, request)
                        }

                    }

                    /*


                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {


                        return super.shouldOverrideUrlLoading(view, request)

                    }

                     */





                }


            }

        }, update = { webView ->



            // Enable private browsing mode
            if (flowWebViewHolder.isPcModeActive == true) {


              //  webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL


                webView.settings.userAgentString =   "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:70.0) Gecko/20100101 Firefox/70.0"

                webView.settings.useWideViewPort = true
                webView.settings.loadWithOverviewMode = true


                webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    webView.settings.safeBrowsingEnabled = true
                }

                webView.settings.domStorageEnabled = false  // Enable DOM storage
                webView.settings.databaseEnabled = false  // Enable database

            }else{


                webView.settings.userAgentString = WebSettings.getDefaultUserAgent(context)

                webView.settings.useWideViewPort = false
                webView.settings.loadWithOverviewMode = false



                webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    webView.settings.safeBrowsingEnabled = false
                }

                webView.settings.domStorageEnabled = false  // Enable DOM storage
                webView.settings.databaseEnabled = false  // Enable database


            }





            when(flowWebViewHolder.webController)
            {
                WebControllerType.LOAD_URL -> {
                    if (!flowWebViewHolder.webViewUrl.isNullOrBlank()) {

                        webView.loadUrl(flowWebViewHolder.webViewUrl!!)
                    }
                    flowWebViewHolder.webController= WebControllerType.DO_NOTING
                }

                WebControllerType.RELOAD_URL -> {
                    if (!webView.url.isNullOrBlank()) {
                        webView.reload()
                    }
                    flowWebViewHolder.webController= WebControllerType.DO_NOTING
                }

                WebControllerType.GO_BACK -> {
                    if (webView.canGoBack()){
                        webView.goBack()
                    }
                    flowWebViewHolder.webController= WebControllerType.DO_NOTING
                }

                WebControllerType.GO_FORWARD -> {
                    if (webView.canGoForward()){
                        webView.goForward()
                    }
                    flowWebViewHolder.webController= WebControllerType.DO_NOTING
                }

                WebControllerType.CLEAR_DATA_AND_RELOAD -> {

                    scope.launch {

                        webView.clearHistory()
                        webView.clearCache(true)
                        webView.clearSslPreferences()
                        webView.clearMatches()
                        webView.clearFormData()

                        CookieManager.getInstance().apply {
                            removeAllCookies(null)
                            //Makes sure no caching is done
                            flush()
                        }

                        WebStorage.getInstance().deleteAllData()

                        delay(3000)

                        if (!flowWebViewHolder.webViewUrl.isNullOrBlank()) {
                            webView.loadUrl(flowWebViewHolder.webViewUrl!!)
                        }

                        flowWebViewHolder.webController = WebControllerType.DO_NOTING

                    }

                }

                WebControllerType.DO_NOTING -> {


                }

            }


            when(lifecycle){
                Lifecycle.Event.ON_CREATE -> {}
                Lifecycle.Event.ON_START -> {}
                Lifecycle.Event.ON_RESUME -> {}
                Lifecycle.Event.ON_PAUSE -> {}
                Lifecycle.Event.ON_STOP -> {}
                Lifecycle.Event.ON_DESTROY -> {

                    webView.clearHistory()
                    webView.clearCache(true)
                    webView.clearSslPreferences()
                    webView.clearMatches()
                    webView.clearFormData()


                    CookieManager.getInstance().apply {
                        removeAllCookies(null)
                        //Makes sure no caching is done
                        flush()
                    }

                    WebStorage.getInstance().deleteAllData()

                }
                Lifecycle.Event.ON_ANY -> {}
            }


        },
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )





        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 2.dp, end = 2.dp)
                .height(3.dp),
            color = if (flowWebViewHolder.webProgressValue == 100){
                Color.Transparent
            }else{
                Color.Red
            },
            progress = { (flowWebViewHolder.webProgressValue / 100.0).toFloat() },
        )




        Row(modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(snowColor)
            .drawBehind {
                val strokeWidth = 3.dp.toPx()
                val x = size.width
                // Draw top border
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, 0f),
                    end = Offset(x, 0f),
                    strokeWidth = strokeWidth
                )
                // Draw bottom border
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height),
                    end = Offset(x, size.height),
                    strokeWidth = strokeWidth
                )
            }
            // .border(width = 0.5.dp, Color.Cyan)
        ) {

            Box(modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .weight(1f),
                contentAlignment = Alignment.Center
            ){

                IconButton(onClick = {


                    showSettings(
                        if ( flowWebViewHolder.webViewUrl.isNullOrBlank()){
                            ""
                        }else{
                            flowWebViewHolder.webViewUrl!!
                        },

                        if ( flowWebViewHolder.isPcModeActive == null){
                            false
                        }else{
                            flowWebViewHolder.isPcModeActive!!
                        },

                        if ( flowWebViewHolder.isAdBlockerActive == null){
                            false
                        }else{
                            flowWebViewHolder.isAdBlockerActive!!
                        },


                        if ( flowWebViewHolder.isIncognitoActive == null){
                            false
                        }else{
                            flowWebViewHolder.isIncognitoActive!!
                        }


                    )



                }) {
                    Icon(painter = painterResource(id = R.drawable.icon_settings),
                        contentDescription = "settings"
                    )
                }

            }




        }


    }





}

