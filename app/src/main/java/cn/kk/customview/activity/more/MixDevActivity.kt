package cn.kk.customview.activity.more

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import cn.kk.base.activity.BaseActivity
import cn.kk.base.bean.BaseMoreItem
import cn.kk.customview.R
import cn.kk.customview.dialog.MoreInfoListBottomDialog
import kotlinx.android.synthetic.main.activity_mix_dev.*

/**
 * 混合开发 总结
 */

const val web_url = "http://kamaihamaiha.usa3v.vip"
class MixDevActivity: BaseActivity() {

    override fun getLayout(): Int {
       return R.layout.activity_mix_dev
    }

    override fun showTitleMoreBtn(): Boolean = true

    override fun onTitleMoreClick() {
        val dialog = MoreInfoListBottomDialog(this, mutableListOf<BaseMoreItem>().apply {
            add(BaseMoreItem("前端开发", "https://gitee.com/kamaihamaiha/h5/blob/master/README.md"))
        }, 0) // todo 暂时用0，后面需要时在改成别的值
        dialog.show()
    }

    lateinit var webView: WebView
    private val nativeSDK = NativeSDK(this)

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        webView = findViewById(R.id.webview)

        webView.loadUrl(web_url)
        webView.settings.javaScriptEnabled = true


        // 第一种实现：拦截 JSBridge schema： android 端在这里拦截 js schema
        /*webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(
                view: WebView,
                url: String,
                message: String,
                result: JsResult
            ): Boolean {
                if (!message.startsWith("jsbridge://")) {
                    return super.onJsAlert(view, url, message, result)
                }

                // 拦截
                val text = message.substring(message.indexOf("=") + 1)
                showNativeDialog(text)
                result.confirm()
                return true
            }
        }*/


        // 第二种实现：注入API
        webView.webChromeClient = WebChromeClient()
        webView.addJavascriptInterface(NativeBridge(this), "NativeBridge")


        // native 调用 webview 接口
        findViewById<Button>(R.id.btn_show_web_dialog).setOnClickListener {
            val nativeInputStr = et_input.text.toString()
            showWebDialog(nativeInputStr)
        }

        // 获取 web 输入
        btn_get_web_input.setOnClickListener {
            nativeSDK.getWebEditTextValue(object : Callback{
                override fun invoke(value: String) {
                    showNativeDialog("web输入值：$value")
                }

            })
        }

        // 刷新
        findViewById<Button>(R.id.btn_refresh_web_dialog).setOnClickListener { webView.loadUrl(web_url) }
    }


    fun showWebDialog(msg: String){
        // web 的方法 需要 web 端实现
        val jsCode = String.format("window.showWebDialog('%s')", msg)
        webView.evaluateJavascript(jsCode, null)
    }

    fun showNativeDialog(msg: String){
        val alertDialog = AlertDialog.Builder(this).setMessage(msg)
        alertDialog.show()
    }

    interface Callback{

        fun invoke(value: String)
    }

    class NativeSDK(val ctx: Context) {
        private var id = 1
        private var map = HashMap<Int, Callback>()

        fun getWebEditTextValue(callback: Callback){
            val callbackId = id++
            map.put(callbackId, callback)
            val jsCode = String.format("window.JSSDK.getWebEditTextValue(%s)", callbackId)

            (ctx as MixDevActivity).runOnUiThread {
                ctx.webView.evaluateJavascript(jsCode, null)
            }
        }

        fun receiveMessage( callbackId: Int, value: String){
            if (map.containsKey(callbackId)){
                map.get(callbackId)?.invoke(value)
            }
        }
    }

    // 定义 NativeBridge 类

    class NativeBridge(val context: Context){

        @JavascriptInterface
        fun showNativeDialog(msg: String){
            AlertDialog.Builder(context).setMessage(msg).show()
        }

        @JavascriptInterface
        fun getNativeEditTextValue(callbackId: Int){
            val mainActivity = context as MixDevActivity
            val value = mainActivity.et_input.text.toString()

            val jsCode = String.format("window.JSSDK.receiveMessage(%s,'%s')", callbackId, value)
            mainActivity.runOnUiThread {
                mainActivity.webView.evaluateJavascript(jsCode, null)
            }
        }

        @JavascriptInterface
        fun receiveMessage(callbackId: Int, value: String){
            (context as MixDevActivity).nativeSDK.receiveMessage(callbackId, value)
        }
    }
}