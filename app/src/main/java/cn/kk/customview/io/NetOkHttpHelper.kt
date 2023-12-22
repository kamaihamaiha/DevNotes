package cn.kk.customview.io

import android.content.Context
import android.os.Handler
import android.text.TextUtils
import cn.kk.base.utils.StringHelper
import okhttp3.Cache
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

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
    //endregion
    val BAIDU_NET_DISK_AUTH_HOST: String? = "https://openapi.baidu.com"
    val BAIDU_NET_DISK_PAN_HOST: String? = "https://pan.baidu.com"
    val BAIDU_NET_DISK_AUTH_URL: String = BAIDU_NET_DISK_AUTH_HOST + "/oauth/2.0/token?grant_type=authorization_code&code=%s&client_id=%s&client_secret=%s&redirect_uri=%s"
    val BAIDU_NET_DISK_USER_INFO_URL = "$BAIDU_NET_DISK_PAN_HOST/rest/2.0/xpan/nas?method=uinfo&access_token=%s"
    val BAIDU_NET_DISK_FILE_LIST_URL = "$BAIDU_NET_DISK_PAN_HOST/rest/2.0/xpan/file?method=list&access_token=%s"
    val BAIDU_NET_DISK_FILE_META_URL = "$BAIDU_NET_DISK_PAN_HOST/rest/2.0/xpan/multimedia?method=filemetas&access_token=%s&fsids=%s&thumb=1"
    val BAIDU_NET_DISK_FILE_VIDEO_INFO_URL = "$BAIDU_NET_DISK_PAN_HOST/rest/2.0/xpan/file?method=streaming&access_token=%s&path=%s&type=%s"
    val VIDEO_FMT_TS_480 = "M3U8_AUTO_480" // 默认用这种

    val VIDEO_FMT_TS_720 = "M3U8_AUTO_720"
    val VIDEO_FMT_TS_1080 = "M3U8_AUTO_1080"
    val VIDEO_FMT_FLV_264_480 = "M3U8_FLV_264_480" // 私有协议，需播放器额外特殊支持，或使用网盘播放器

    fun getBaiduPanUserInfo(accessToken: String, callback: ResultCallback){
        val url = "https://pan.baidu.com/rest/2.0/xpan/nas?method=uinfo&access_token=${accessToken}"
        getInstance().getAsync(url, callback)
    }

    fun getBaiduPanCurPathFileListInfo(accessToken: String, path: String, callback: ResultCallback){
        var url: String = String.format(BAIDU_NET_DISK_FILE_LIST_URL, accessToken)
        if (!TextUtils.isEmpty(path)) {
            url += "&dir=" + StringHelper.urlEncode(path)
        }
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