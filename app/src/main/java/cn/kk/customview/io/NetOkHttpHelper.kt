package cn.kk.customview.io

import android.content.Context
import android.os.Handler
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.Exception

/**
 * 网络帮助类
 * OkHttp 封装
 * 1. OkHttpClient 只实例化一次
 * 2. 请求回调在 UI 线程返回
 */
class NetOkHttpHelper(context: Context) {
    private  var okHttpClient: OkHttpClient
    private  var handler: Handler

    init {
        val cacheFile = context.externalCacheDir
        val cacheSize = 10L * 1024 * 1024
        okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .cache(Cache(cacheFile!!.absoluteFile, cacheSize))
            .build()

        handler = Handler()
    }

    companion object {
        val TAOBAO_TEL_URL =  "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=15063379937"
        @Volatile
        private var  mInstance: NetOkHttpHelper?= null
        fun init(context: Context): NetOkHttpHelper {
            if (mInstance == null){
                synchronized(this){
                    if (mInstance == null){
                     mInstance = NetOkHttpHelper(context)
                    }
                }
            }
            return mInstance!!
        }

        fun getInstance() = mInstance!!
    }

    // region net request

    fun getBaiduPanUserInfo(accessToken: String, callback: ResultCallback){
        val url = "https://pan.baidu.com/rest/2.0/xpan/nas?method=uinfo&access_token=${accessToken}"
        getInstance().getAsync(url, callback)
    }

    /**
     * 异步 GET 请求
     */
    fun getAsync(url: String, callback: ResultCallback){
        val request = Request.Builder().url(url).build()
        val call = okHttpClient.newCall(request)
        requestAndDealResult(call, callback)
    }

    /**
     * 异步 POST 表单请求
     */
    fun postAsyncForm(url: String, callback: ResultCallback){
        val formBody = FormBody.Builder().add("tel", "15063379937").build()
        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()
        val call = okHttpClient.newCall(request)
        requestAndDealResult(call, callback)
    }

    /**
     * 发送请求并且处理结果
     */
    fun requestAndDealResult(call: Call, callback: ResultCallback){
        call.enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                sendFailedCallback(call.request(), e, callback)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.body == null){
                    sendFailedCallback(call.request(), Exception(response.message), callback)
                    return
                }
                sendSuccessCallback(response.body!!.string(), callback)
            }

        })
    }

    fun sendFailedCallback(request: Request,e: Exception, callback: ResultCallback){
        handler.post { callback.onError(request, e) }
    }

    fun sendSuccessCallback(data: String, callback: ResultCallback){
        handler.post { callback.onResponse(data) }
    }

    // endregion
}