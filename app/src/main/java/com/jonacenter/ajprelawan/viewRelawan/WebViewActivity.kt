package com.jonacenter.ajprelawan.viewRelawan

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.jonacenter.ajprelawan.R
import com.jonacenter.ajprelawan.api.Config

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        webView = findViewById(R.id.webView)

        // Set the title for the Toolbar
        supportActionBar?.title = "Kebijakan Privasi"

        // Enable JavaScript (optional)
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Optional: Enable zoom controls
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false

        // Set up the WebViewClient to handle redirects within your WebView
        webView.webChromeClient = WebChromeClient()

        // Load the URL in the WebView
        val url = Config.webView
        webView.loadUrl(url)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_webview, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                // Handle refresh action
                webView.reload()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
