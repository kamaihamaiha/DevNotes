package cn.kk.customview.activity.work

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import cn.kk.base.activity.BaseActivity
import cn.kk.base.utils.IOUtils
import cn.kk.customview.R

class WebViewScrollCapActivity: BaseActivity() {
    val TEST_URL = "http://beta_dict.eudic.net/ting/articlepdf?mediaId=e81d9146-3b99-11ed-80da-005056863753"
    var capState = false
    override fun getLayout(): Int {
        /**
         * 5.0以上可能会显示保存不全，5.0以上进行了优化先渲染一部分，滚动再渲染导致，
         * 解决方案 Activity  setContent()之前设置渲染整个页面.
         *
         * 这么处理是最简单的
         */
        WebView.enableSlowWholeDocumentDraw()
        return R.layout.activity_webview_scroll_cap
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                hideProgressDialog()
            }
        }

        showProgressDialog("loading...")
        webView.loadUrl(TEST_URL)
        val btn_cap = findViewById<Button>(R.id.btn_cap)
        val btn_save = findViewById<Button>(R.id.btn_save)
        val btn_save_pictures = findViewById<Button>(R.id.btn_save_pictures)
        val iv_cap = findViewById<ImageView>(R.id.iv_cap)
        btn_cap.setOnClickListener {
            if (capState) {
                iv_cap.visibility = View.INVISIBLE
                btn_cap.text = "截图"
            } else {
                btn_cap.text = "重置"
                val bitmap = captureWebView(webView)
                iv_cap.visibility = View.VISIBLE
                iv_cap.setImageBitmap(bitmap)
            }
            capState = !capState
        }

        // save bitmap to local
        btn_save.setOnClickListener {
            captureWebView(webView)?.let {
                IOUtils.saveBitmap2File(it)?.let {
                    showToast("图片已保存至存储卡: ${it}")
                }

            }
        }

        btn_save_pictures.setOnClickListener {
            captureWebView(webView)?.let {
                IOUtils.saveBitmap2Pictures(this@WebViewScrollCapActivity, it)?.let {
                    showToast("图片已保存至相册: ${it}")
                }

            }
        }
    }

    private fun captureWebView(webView: WebView): Bitmap? {
        val snapShot: Picture = webView.capturePicture()
        val bitmap = Bitmap.createBitmap(snapShot.width, snapShot.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        snapShot.draw(canvas)
        Log.d(TAG, "captureWebView: bitmap size: ${bitmap.width} * ${bitmap.height}")
        return bitmap
    }

}