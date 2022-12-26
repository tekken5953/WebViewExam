package com.example.webviewexam

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class WebViewActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url: String = intent.getStringExtra("url").toString()  // 화면 전환으로 불러온 값
        val webView = findViewById<WebView>(R.id.webView)
        val webSettings: WebSettings = webView.settings

        webSettings.javaScriptEnabled = true // 자바스크립트 허용

        webSettings.loadWithOverviewMode = true // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

        webView.webViewClient = WebViewClient() // 새창 뜨지 않게 하기위해서

        webView.loadUrl(url)  // 페이지 로드

    }
}