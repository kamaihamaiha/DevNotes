package cn.kk.customview.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.*
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.base.utils.StringHelper
import cn.kk.base.utils.SystemHelper
import cn.kk.customview.R
import cn.kk.customview.activity.more.BaiduPanActivity

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
                    Log.d(TAG, "shouldOverrideUrlLoading: ${it}")
                    if (it.startsWith("https://openapi.baidu.com/oauth/2.0/login_success")) {
                        val token = it.substringAfter("access_token=")
                        // https://api.frdic.com/BDPan/oauth?code=1dd54d7725d01148c5dfc26ceb244756
                        // https://openapi.baidu.com/oauth/2.0/login_success#expires_in=2592000&access_token=123.4177eb716eec044b1278d92268391b91.Y3lc2VTqNsoWKgJ4c-8M4Sq1jqOQDt6XtqA0jfD.UjUzlw&session_secret=&session_key=&scope=basic+netdisk

                        val expiresInStr = StringHelper.parseParamsFromUrl(it)
                        val expiresIn = expiresInStr?.toLong()?:0
                        Log.d(TAG, "shouldOverrideUrlLoading: token=${token}")
                        setResult(Activity.RESULT_OK, Intent().apply {
                            putExtra(BaiduPanActivity.INTENT_ACCESS_TOKEN, token)
                            putExtra(BaiduPanActivity.INTENT_EXPIRES_IN, expiresIn)
                        })
                        finish()
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