package cn.kk.customview.activity

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.*
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.base.utils.SystemHelper
import cn.kk.customview.R

/**
 * 通用显示网页
 */
class NormalWebViewActivity: BaseActivity() {
    override fun getLayout(): Int {
       return R.layout.activity_normal_webview
    }

    lateinit var mWebView: WebView
    val mWebUrl by lazy {
        intent.getStringExtra(INTENT_WEB_URL_KEY).toString()
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        setMyToolBar()
        mWebView = findViewById<WebView>(R.id.web_view)

        WebView.setWebContentsDebuggingEnabled(true)
        mWebView.settings.javaScriptEnabled = true
        mWebView.webChromeClient = WebChromeClient()

        showProgressDialog("Loading...")
        mWebView.webViewClient = object : WebViewClient(){
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
        mWebView.loadUrl(mWebUrl)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_webview, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.copy_url) {
            SystemHelper.setClipboardText(mWebUrl, this@NormalWebViewActivity)
            UIHelper.toast("复制链接", this@NormalWebViewActivity)
            return true
        } else if (item.itemId == R.id.export) {
            UIHelper.exportPdf(this, mWebView, mTitle,
                { success, result ->
                    result?.let {
                        showProgressDialog(it)
                    }
                }
            ) { success, result -> hideProgressDialog() }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}