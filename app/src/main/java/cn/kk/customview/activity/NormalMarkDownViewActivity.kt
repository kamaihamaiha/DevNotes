package cn.kk.customview.activity

import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.base.utils.SystemHelper
import cn.kk.customview.R
import com.mukesh.MarkdownView
import kotlinx.android.synthetic.main.activity_normal_markdown_view.*

/**
 * 通用显示 Markdown 文件
 */
class NormalMarkDownViewActivity: BaseActivity() {
    lateinit var markDownView: MarkdownView
    val mUrl by lazy {
        intent.getStringExtra(INTENT_MARKDOWN_PATH_KEY).toString()
    }
    override fun getLayout(): Int {
       return R.layout.activity_normal_markdown_view
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        setMyToolBar()

        val local = intent.getBooleanExtra(INTENT_MARKDOWN_LOCAL_KEY, true)

        markDownView = findViewById(R.id.markdown_view)

        markDownView.settings.javaScriptEnabled = true
        markDownView.webChromeClient = WebChromeClient()

        if (local) {
            markDownView.loadMarkdownFromAssets(mUrl)
        } else {
            markDownView.webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    refresh_view?.isRefreshing = false
                }
            }
            markDownView.loadUrl(mUrl)
            // 延时show 加载，否则显示不出来
            markDownView.postDelayed({ refresh_view.isRefreshing = true }, 50)
        }

        // pull refresh
        refresh_view.setOnRefreshListener {
            if (!local) {
                markDownView.loadUrl(mUrl)
                refresh_view.isRefreshing = true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_copy_url, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.copy_url) {
            SystemHelper.setClipboardText(mUrl, this@NormalMarkDownViewActivity)
            UIHelper.toast("复制链接", this@NormalMarkDownViewActivity)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (markDownView.canGoBack()) { // 处理网页返回
            markDownView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}