package com.example.musicapp

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Objects

class video : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        val webView: WebView = findViewById(R.id.web)

        // Enable JavaScript
        webView.settings.javaScriptEnabled = true

        // Allow mixed content if necessary
        webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE

        // Set WebViewClient to handle page navigation
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                // Handle URL navigation here
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }

        }
        webView.webChromeClient=object :WebChromeClient(){
            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

                // Set the custom view as the content view
                setContentView(view)
            }
            override fun onHideCustomView() {
                // Handle hiding the full screen view here if needed
                super.onHideCustomView()
                setContentView(R.layout.activity_video)

                // Reset to portrait orientation when exiting video
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                // Add code to return from full-screen here if required
            }
        }
        val h=intent.getStringExtra("songvideo")
        // Load the URL (note the https://)
        webView.loadUrl(h.toString())
    }


}