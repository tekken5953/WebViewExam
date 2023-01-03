package com.example.webviewexam

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class WebViewActivity : AppCompatActivity() {
    lateinit var webView: WebView
    lateinit var urlTitle: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url: String = intent.getStringExtra("url").toString()  // 화면 전환으로 불러온 값
        Log.d("TAG_WebView", "Initial Url is $url")
        webView = findViewById(R.id.webView)
        val webSettings: WebSettings = webView.settings

        urlTitle = findViewById(R.id.webViewURLTv)
        urlTitle.apply {
            setTextIsSelectable(true)   // 드래그로 선택 가능
            text = url
        }

        val webViewClient: WebViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                Log.d("TAG_WebView", "shouldOverrideUrlLoading")
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                super.doUpdateVisitedHistory(view, url, isReload)
                Log.d("TAG_WebView", "doUpdateVisitedHistory")
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                // 페이지를 불러오는 도중 에러가 발생하면 기록삭제 후 빈 화면 로드
                Log.e("TAG_WebView", "onReceivedError")
                view?.loadUrl("about:blank")
                view?.clearHistory()
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                urlTitle.text = url
                Log.d("TAG_WebView", "onPageStarted")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("TAG_WebView", "onPageFinished")
            }
        }

        webView.webViewClient = webViewClient

        webSettings.javaScriptEnabled = true // 자바스크립트 허용

        webSettings.loadWithOverviewMode = true // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정

        webView.loadUrl(url)

        val back: ImageView = findViewById(R.id.webViewBackIv)
        back.setOnClickListener() {
            if (webView.canGoBack())
                webView.goBack()    // 이전 페이지 로드
            else
                finish()
        }

        val forward: ImageView = findViewById(R.id.webViewForwardIv)
        forward.setOnClickListener() {
            if (webView.canGoForward()) {
                webView.goForward()
            }
        }

        val upScroll: ImageView = findViewById(R.id.webViewUpIv)
        upScroll.setOnClickListener() {
            webView.scrollTo(0, 0)
        }

        val refresh: ImageView = findViewById(R.id.webViewRefreshIv)
        refresh.setOnClickListener() {
            webView.reload()
        }
    }

    // 키코드 입력 이벤트 처리
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 백버튼을 클릭하거나 이전 페이지의 기록이 있는 경우
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()    // 이전 페이지 로드
            return true
        } else
            finish()
        return super.onKeyDown(keyCode, event)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}
