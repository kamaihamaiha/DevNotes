package cn.kk.base.io.net

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.text.TextUtils
import android.util.Log
import cn.kk.base.BaseApp
import cn.kk.base.io.BooleanCallback
import cn.kk.base.io.ObjectCallback
import cn.kk.base.io.model.netdisk.baidu.NetDisk_BaiduAccessTokenResponse
import cn.kk.base.io.model.netdisk.baidu.NetDisk_BaiduFileMetaInfo
import cn.kk.base.io.model.netdisk.baidu.NetDisk_BaiduFileMetaResponse
import cn.kk.base.utils.StringHelper
import cn.kk.base.utils.SystemHelper
import cn.kk.base.utils.ThreadHelper
import com.google.gson.Gson
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import kotlin.coroutines.resumeWithException

object NetDiskBaiduManager {

    val mOkHttpClient by lazy {
        NetOkHttpHelper.getInstance().getOkhttpClient()
    }



    // baidu pan

     object BaiduPanAppConfig {
         var appID = 45394028
         val appKey: String = "xIG6LSGvRvLGN43OvI9obxeZfEdCZQYQ"
         var secretKey = "l072dbnqsHQ4b6cWNSdnOlHIg8lHhAau"
    }

    const val APP_KEY: String = "xIG6LSGvRvLGN43OvI9obxeZfEdCZQYQ"

    // region url
    const val NetDisk_Baidu_AUTH_HOST = "https://openapi.baidu.com"
    const val NetDisk_Baidu_HOST = "https://pan.baidu.com"
    const val NetDisk_Baidu_GET_ACCESS_TOKEN_URL = "$NetDisk_Baidu_AUTH_HOST/oauth/2.0/token?grant_type=authorization_code&code=%s&client_id=%s&client_secret=%s&redirect_uri=%s"
    const val NetDisk_Baidu_REFRESH_ACCESS_TOKEN_URL = "$NetDisk_Baidu_AUTH_HOST/oauth/2.0/token?grant_type=refresh_token&refresh_token=%s&client_id=%s&client_secret=%s"
    const val NetDisk_Baidu_USER_INFO_URL = "$NetDisk_Baidu_HOST/rest/2.0/xpan/nas?method=uinfo&access_token=%s"
    const val NetDisk_Baidu_FILE_LIST_URL = "$NetDisk_Baidu_HOST/rest/2.0/xpan/file?method=list&access_token=%s"

    // 分类文件列表
    const val NetDisk_Baidu_CATEGORY_FILE_LIST_URL =
        "$NetDisk_Baidu_HOST/rest/2.0/xpan/multimedia?method=categorylist&show_dir=1&order=name&desc=0&access_token=%s&parent_path=%s&category=%s"
    const val NetDisk_Baidu_FILE_META_URL =
        "$NetDisk_Baidu_HOST/rest/2.0/xpan/multimedia?method=filemetas&access_token=%s&fsids=%s&thumb=1&needmedia=1&dlink=1"
    const val NetDisk_Baidu_FILE_MEDIA_INFO_URL = "$NetDisk_Baidu_HOST/rest/2.0/xpan/file?method=streaming&access_token=%s&path=%s&type=%s"
    val NetDisk_Baidu_FILE_DOWNLOAD_URL = "%s&access_token=%s"

    const val VIDEO_FMT_TS_480 = "M3U8_AUTO_480" // 默认用这种

    const val VIDEO_FMT_TS_720 = "M3U8_AUTO_720"
    const val VIDEO_FMT_TS_1080 = "M3U8_AUTO_1080"
    const val VIDEO_FMT_FLV_264_480 = "M3U8_FLV_264_480" // 私有协议，需播放器额外特殊支持，或使用网盘播放器

    const val AUDIO_FMT_MP3 = "M3U8_MP3_128" // 私有协议

    const val AUDIO_FMT_TS = "M3U8_HLS_MP3_128" // 通用hls协议

    const val VIDEO_SUBTITLE = "M3U8_SUBTITLE_SRT" // 视频字幕 type 固定值

    const val MEDIA_TYPE_VIDEO = "video"
    const val MEDIA_TYPE_AUDIO = "audio"

    // endregion

    var aiSubtitleTaskRunning = false
    val AI_SUBITILE_TASK_INTERVAL = 60 * 1000L
    val aiSubtitleFetchHandler = Handler(Looper.getMainLooper())
    var aiSubtitleTask: Runnable ?=null

    // region suspend funs
    suspend fun getNetDiskBaiduMediaStreamUrl(isSuperVip: Boolean, accessToken: String, path: String, videoType: Boolean): String? = suspendCancellableCoroutine { continuation ->
        getNetDiskBaiduMediaStreamUrl(isSuperVip, accessToken, path, videoType) { success, url ->
            if (success) {
                continuation.resume(url, null)
            } else {
                continuation.resumeWithException(RuntimeException("Failed to get media stream URL: ${url}"))
            }
        }.also {
            continuation.invokeOnCancellation {
                // 如果协程被取消，处理相关的中断逻辑
            }
        }
    }

     suspend fun getFileMetaInfo(accessToken: String, fsId: Long): NetDisk_BaiduFileMetaInfo? = suspendCancellableCoroutine { continuation ->
        getNetDisk_BaiduFileMetaInfo(accessToken, fsId) { success, result ->
            if (!success || TextUtils.isEmpty(result)) {
//                EuUtil.toastMsg(JniApi.getAppContext(), result)
//                UIHelper.toast(result, )
            } else {
                // 获取用户信息成功，显示用户信息
                val fileMetaResponse = Gson().fromJson(result, NetDisk_BaiduFileMetaResponse::class.java)
                fileMetaResponse.list?.let {
                    if (it.isNotEmpty()) {
                        continuation.resume(it[0], null)
                    } else {
                        continuation.resume(null, null)
                    }
                } ?: continuation.resume(null, null)
            }
        }
    }

    // endregion

    // region request
    private fun getRequest(url: String, forceRefresh: Boolean = false): Request.Builder {
      return  Request.Builder().url(url)
    }

    fun getNetDisk_BaiduAccessToken(authCode: String?, appKey: String, secretKey: String, redirectUri: String, callback: ObjectCallback<String>) {
        val url = String.format(NetDisk_Baidu_GET_ACCESS_TOKEN_URL, authCode, appKey, secretKey, redirectUri)
        getNetDisk_BaiduCommonInfo(url, callback)
    }

    fun refreshNetDisk_BaiduAccessToken(refreshToken: String, appKey: String, secretKey: String, callback: ObjectCallback<NetDisk_BaiduAccessTokenResponse>) {
        val url = String.format(NetDisk_Baidu_REFRESH_ACCESS_TOKEN_URL, refreshToken ,appKey, secretKey)
        getNetDisk_BaiduCommonInfo(url) { success, result ->
            if (success && !TextUtils.isEmpty(result)) {
                try {
                    val accessTokenResponse = Gson().fromJson(result, NetDisk_BaiduAccessTokenResponse::class.java)
                    callback.onResult(true, accessTokenResponse)
                } catch (e: Exception) {
                    e.printStackTrace()
                    callback.onResult(false, null)
                }
            } else {
                callback.onResult(false, null)
            }
        }
    }

    fun getNetDisk_BaiduUserInfo(accessToken: String?, callback: ObjectCallback<String>) {
        val url = String.format(NetDisk_Baidu_USER_INFO_URL, accessToken)
        getNetDisk_BaiduCommonInfo(url, callback)
    }

    fun getNetDisk_BaiduFileList(accessToken: String?, path: String?, callback: ObjectCallback<String>) {
        var url = String.format(NetDisk_Baidu_FILE_LIST_URL, accessToken)
        if (!TextUtils.isEmpty(path)) {
            url += "&dir=" + StringHelper.urlEncode(path)
        }
        getNetDisk_BaiduCommonInfo(url, callback)
    }

    /**
     * 获取分类文件列表
     * @param accessToken
     * @param path
     * @param callback
     */
    fun getNetDisk_BaiduCategoryFileList(accessToken: String?, path: String, forceRefresh: Boolean, callback: ObjectCallback<String>) {
        var path = path
        val category = "1,2,6" // 视频,音频,其他(字幕文件)
        if (path.isEmpty()) path = "/"
        val url = String.format(NetDisk_Baidu_CATEGORY_FILE_LIST_URL, accessToken, StringHelper.urlEncode(path), category)
        getNetDisk_BaiduCommonInfo(url, forceRefresh, callback)
    }

    fun getNetDisk_BaiduFileMetaInfo(accessToken: String?, fileId: Long?, callback: ObjectCallback<String>) {
        val fsids = JSONArray()
        fsids.put(fileId)
        val url = String.format(NetDisk_Baidu_FILE_META_URL, accessToken, fsids.toString())
        getNetDisk_BaiduCommonInfo(url, callback)
    }

    var TAG_PAN = "NetDisk_Baidu--"

    fun getNetDiskBaiduMediaStreamUrl(sVip: Boolean, accessToken: String?, path: String?, isVideo: Boolean, callback: BooleanCallback?) {
        val url = String.format(
            NetDisk_Baidu_FILE_MEDIA_INFO_URL,
            accessToken,
            StringHelper.urlEncode(path),
            if (isVideo) VIDEO_FMT_TS_480 else AUDIO_FMT_TS
        )
        if (sVip && callback != null) {
            callback.onResult(true, url)
            return
        }
        getNetDiskBaiduMediaStreamUrl(callback, url, false)
    }

    fun getNetDiskBaiduAudioStreamUrl(accessToken: String?, panFilepath: String?, callback: BooleanCallback?) {
        val url = String.format(NetDisk_Baidu_FILE_MEDIA_INFO_URL, accessToken, StringHelper.urlEncode(panFilepath), AUDIO_FMT_TS)
        getNetDiskBaiduMediaStreamUrl(callback, url, true)
    }

    /**
     * 获取视频流/音频流
     * 普通分两次请求：
     * 第一次请求广告逻辑后得到 adToken
     * 第二次请求把 adToken 加到参数中(需要encode)
     * 超级VIP 直接使用地址播放
     * @param audioM3u8  是否获取音频M3u8 分片信息
     * @param callback
     */
    private fun getNetDiskBaiduMediaStreamUrl(callback: BooleanCallback?, url: String, audioM3u8: Boolean) {
        ThreadHelper.runTask() {
            try {
                val adTokenRequest = getNetDisk_BaiduMediaRequest(url)
                // 第一次请求（普通用户）：获取 adToken
                var adTokenresponse = mOkHttpClient.newCall(adTokenRequest).execute()
                if (adTokenresponse.code == 400) {
                    SystemClock.sleep(2000) // 等2秒后再次请求
                    adTokenresponse = mOkHttpClient.newCall(adTokenRequest).execute()
                }
                if (!adTokenresponse.isSuccessful || adTokenresponse.body == null) {
                    Log.d(TAG_PAN, "onAdTokenResponse: ret: " + adTokenresponse.code + ", " + adTokenresponse.message)
                    ThreadHelper.runOnUIThread() {
                        callback?.onResult(false, adTokenresponse.code.toString() + ": " + adTokenresponse.message)
                    }
                    return@runTask
                }
                val adTokenRet = adTokenresponse.body!!.string()
                Log.d(TAG_PAN, "onResponse: ret: $adTokenRet")
                if (adTokenRet.startsWith("#EXTM3U")) {
                    val mediaInfo = if (audioM3u8) adTokenRet else url
                    ThreadHelper.runOnUIThread() {
                        callback?.onResult(true, mediaInfo)
                    }
                    return@runTask
                }
                val `object` = JSONObject(adTokenRet)
                val adToken = `object`.getString("adToken")
                val errcode = `object`.getInt("errno")
                val ltime = `object`.getInt("ltime") // 第二次请求应该在第一次响应后，过ltime秒后再发起
                Log.d(TAG_PAN, "getBaiduNetDiskVideoFileInfo success errno: " + getNetDisk_BaiduErrorMsg(errcode))


                // 第二次请求：
                val mediaUrl = url + "&adToken=" + StringHelper.urlEncode(adToken)
                val videoRequest = getNetDisk_BaiduMediaRequest(url)
                val videoResponse = mOkHttpClient.newCall(videoRequest).execute()
                // 本阶段可能出现两种情况的响应: 1. 响应 httpcode 为4xx，body为json，错误码为31341; 2. 响应 httpcode 200 + m3u8
                if (videoResponse.isSuccessful) {
                    val mediaInfo = if (audioM3u8) videoResponse.body!!.string() else mediaUrl
                    ThreadHelper.runOnUIThread() {
                        callback?.onResult(true, mediaInfo)
                    }
                } else {
                    val code = videoResponse.code
                    if (code >= 400 && code < 500) { // todo 此情况表示视频需要实时的转码，遇到这类情况，需要继续帮助用户轮询请求
                        val errorObj = JSONObject(videoResponse.body!!.string())
                        val errno = errorObj.getInt("errno")
                        Log.d(TAG_PAN, "getBaiduNetDiskVideoFileInfo failed: errno: " + getNetDisk_BaiduErrorMsg(errno))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG_PAN, "onError: ret: " + e.message)
                ThreadHelper.runOnUIThread() {
                    callback?.onResult(false, e.message)
                }
            }
        }
    }

    fun checkMediaUrl(url: String, bCallback: BooleanCallback?){
        mOkHttpClient.newCall(Request.Builder().url(url).head().build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                ThreadHelper.runOnUIThread() { bCallback?.onResult(false, e.toString()) } }

            override fun onResponse(call: Call, response: Response) {
                ThreadHelper.runOnUIThread() {
                    if (response.isSuccessful) {
                        bCallback?.onResult(true, "")
                    } else {
                        bCallback?.onResult(false, getErrorMsg(response))
                    }
                }
            }
        })

    }


    /**
     * 百度网盘部分接口需要的 User-Agent
     * @return
     */
    private fun getNetDisk_BaiduUserAgent(): String {
        val userAgent = String.format(
            "xpanvideo$%s;$%s;$%s;$%s;ts", "速查手册",
            SystemHelper.getVersionName(BaseApp.getInstance()), "Android", Build.VERSION.RELEASE
        )
        return StringHelper.urlEncode(userAgent)
    }

    fun getNetDisk_BaiduMediaRequest(url: String?): Request {
        val userAgent = getNetDisk_BaiduUserAgent()
        return Request.Builder()
            .addHeader("Host", "pan.baidu.com")
            .addHeader("User-Agent", userAgent)
            .url(url!!).build()
    }

    fun getNetDisk_BaiduDownloadFileRequest(url: String?): Request {
        return Request.Builder()
            .addHeader("User-Agent", "pan.baidu.com")
            .url(url!!).build()
    }

    fun getErrorMsg(response: Response): String {
       return when(response.code) {
            400 -> "该资源不存在"
            else -> {
                response.message
            }
        }
    }
    fun getNetDisk_BaiduErrorMsg(errno: Int): String {
        var msg = ""
        when (errno) {
            // public err code
            0 -> msg = "请求成功"
            2, 31023 -> msg = "参数错误"
            6 -> msg = "不允许接入用户数据"
            10 -> msg = "转存文件已经存在"
            11 -> msg = "自己发送的分享"
            12 -> msg = "批量转存出错"
            111 -> msg = "access token 失效"
            255 -> msg = "转存数量太多"
            2131 -> msg = "该分享不存在"
            31034 -> msg = "命中接口频控"
            -1 -> msg = "权益已过期"
            -3, -31066 -> msg = "文件不存在"
            -6 -> msg = "身份验证失败"

            //
            133 -> msg = "播放广告"
            31341 -> msg = "视频正在转码，收到此错误码，可重试"
            31346 -> msg = "视频转码失败"
            31024 -> msg = "没有访问权限"
            31062 -> msg = "文件名无效，检查是否包含特殊字符"
            31066 -> msg = "文件不存在"
            31339 -> msg = "视频非法"
        }
        return msg
    }

    fun getNetDisk_BaiduCommonInfo(url: String, callback: ObjectCallback<String>) {
        getNetDisk_BaiduCommonInfo(url, false, callback)
    }

    fun getNetDisk_BaiduCommonInfo(url: String, forceRefresh: Boolean, callback: ObjectCallback<String>) {
        var request: Request? = null
        try {
            request = getRequest(url, forceRefresh).build()
            mOkHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    callback.onResult(false, e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful && response.body != null) {
                        try {
                            val json = response.body!!.string()
                            if (!TextUtils.isEmpty(json)) {
                                ThreadHelper.runOnUIThread() { callback.onResult(true, json) }
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                            ThreadHelper.runOnUIThread() { callback.onResult(false, e.toString()) }
                        }
                    } else {
                        ThreadHelper.runOnUIThread() {
                            callback.onResult(
                                false,
                                response.code.toString() + ": " + response.message
                            )
                        }
                    }
                }
            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            ThreadHelper.runOnUIThread() { callback.onResult(false, ex.toString()) }
        }
    }

    fun post(url: String, requestBody: RequestBody, callback: ObjectCallback<String>){
//        TingHttpApi.ShareInstance().POST(url, requestBody, callback)
    }

    // endregion

}