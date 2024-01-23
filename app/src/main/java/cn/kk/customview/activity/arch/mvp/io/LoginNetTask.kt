package cn.kk.customview.activity.arch.mvp.io

import cn.kk.customview.activity.arch.mvp.NetRequestTask
import cn.kk.customview.activity.arch.mvp.callback.HttpCallback
import cn.kk.customview.activity.arch.mvp.model.LoginInfoModel
import cn.kk.base.io.net.NetOkHttpHelper
import cn.kk.customview.io.HttpBaseCallback
import okhttp3.Request
import java.lang.Exception

/**
 * 登录任务
 */
class LoginNetTask: NetRequestTask<String, LoginInfoModel> {

    override fun execute(data: String, callback: HttpCallback<LoginInfoModel>) {
        // 执行网络请求操作

        callback.onStart()

        NetOkHttpHelper.getInstance().postAsyncForm(data, object : HttpBaseCallback() {
            override fun onResponse(data: String) {
               // 解析 data
//                val model = Gson().fromJson(data, LoginInfoModel::class.java)
                callback.onSuccess(LoginInfoModel().apply { info = data })
            }

            override fun onError(request: Request, e: Exception) {
               callback.onError(e.toString())
            }

        })
    }
}