package cn.kk.customview.io

import okhttp3.Request
import java.lang.Exception

/**
 * 网络请求结果回调
 */
abstract class HttpBaseCallback {

    abstract fun onResponse(data: String)

    abstract fun onError(request: Request, e: Exception)
}