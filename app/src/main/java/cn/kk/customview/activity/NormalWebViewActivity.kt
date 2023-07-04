package cn.kk.customview.activity

import android.util.Log
import android.webkit.*
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R

/**
 * 通用显示网页
 */
class NormalWebViewActivity: BaseActivity() {
    override fun getLayout(): Int {
       return R.layout.activity_normal_webview
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        val webUrl = intent.getStringExtra(INTENT_WEB_URL_KEY).toString()
        val webView = findViewById<WebView>(R.id.web_view)

        WebView.setWebContentsDebuggingEnabled(true)
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()

        showProgressDialog("Loading...")
        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                hideProgressDialog()
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                request?.url.toString().let {
                    Log.d(TAG, "shouldOverrideUrlLoading: ${it}")
                    if (it.startsWith("xhsdiscover://")) {

                        return true
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }

        }
        webView.loadUrl(webUrl)
    }

}