package com.ssafy.jdqr

import android.annotation.SuppressLint
import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    private val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val requestPermissionLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allGranted = permissions.entries.all { it.value }
            if (allGranted) {
                // 권한이 모두 허용됨
                setupWebView()
            } else {
                // 권한이 거부됨
                Toast.makeText(this, "권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)

        if (arePermissionsGranted()) {
            // 권한이 이미 허용됨
            setupWebView()
        } else {
            // 권한 요청
            requestPermissionLauncher.launch(requiredPermissions)
        }
    }

    private fun arePermissionsGranted(): Boolean {
        return requiredPermissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }
    @JavascriptInterface
    public fun copyToClipboard(text: String) {
        val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager;
        val clip: ClipData = ClipData.newPlainText("demo", text);
        clipboard.setPrimaryClip(clip);
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true // 자바스크립트 사용 허용
        webSettings.domStorageEnabled = true // 로컬 저장소 사용 허용
        webSettings.setGeolocationEnabled(true);

        webView.webViewClient = WebViewClient() // 링크 클릭 시 웹 브라우저로 열지 않도록 설정

        webView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                runOnUiThread {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        request.grant(request.resources)
                    }
                }
            }
        }

        webView.loadUrl("https://jdqr608.duckdns.org/place") // 로드할 URL 설정
        //webView.loadUrl("https://codesandbox.io/s/react-webcam-demo-wrecn");
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack() // 웹뷰에서 뒤로가기 지원
        } else {
            super.onBackPressed()
        }
    }
}