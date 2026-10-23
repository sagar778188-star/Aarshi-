package com.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      OriginOSLauncherScreen()
    }
  }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun OriginOSLauncherScreen() {
  var webViewRef: WebView? = remember { null }

  BackHandler {
    webViewRef?.evaluateJavascript("if (typeof goHome === 'function') { goHome(); }", null)
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFF030712))
  ) {
    AndroidView(
      modifier = Modifier.fillMaxSize(),
      factory = { context ->
        WebView(context).apply {
          layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
          )
          setBackgroundColor(0xFF030712.toInt())

          settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            allowFileAccess = true
            cacheMode = WebSettings.LOAD_DEFAULT
            useWideViewPort = true
            loadWithOverviewMode = true
            displayZoomControls = false
            builtInZoomControls = false
            mediaPlaybackRequiresUserGesture = false
          }

          webViewClient = object : WebViewClient() {}
          webChromeClient = object : WebChromeClient() {}

          loadUrl("file:///android_asset/index.html")
          webViewRef = this
        }
      },
      update = { view ->
        webViewRef = view
      }
    )
  }
}
