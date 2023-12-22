package cn.kk.customview.activity.more

import android.app.Activity
import android.content.Intent
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.MyApp
import cn.kk.customview.R
import cn.kk.customview.activity.NormalWebViewActivity
import cn.kk.customview.io.NetOkHttpHelper
import cn.kk.customview.io.ResultCallback
import okhttp3.Request
import org.json.JSONObject
import java.lang.Exception

class BaiduPanActivity: BaseActivity() {
    companion object {
        val PAN_REQUEST_CODE = 100
        val INTENT_ACCESS_TOKEN = "access_token"
    }

    val SP_PAN_ACCESS_TOKEN_KEY = "pan_access_token_key"
    var panAccessToken = ""
    override fun getLayout(): Int {
        return R.layout.activity_baidu_pan
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()


        panAccessToken = MyApp.getInstance().prefs.getString(SP_PAN_ACCESS_TOKEN_KEY, "")?:""
        if (panAccessToken.isEmpty()) { // todo 还要判断有效期
            // 未授权
            /**
             * 1. 打开百度授权页面
             * 2. 获取根目录下的文件
             */

            startActivityForResult(
                Intent(this@BaiduPanActivity, NormalWebViewActivity::class.java).apply {
                    putExtra(INTENT_WEB_URL_KEY, getAuthUrl())
                }, PAN_REQUEST_CODE
            )
        } else {
            // 已授权
            /**
             * 1. 获取用户信息
             * 2. 获取根目录下的文件
             */
            getUserInfo(panAccessToken)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PAN_REQUEST_CODE) {
            // show a toast
            val token = data?.getStringExtra(INTENT_ACCESS_TOKEN)
            MyApp.getInstance().prefs.edit().putString(SP_PAN_ACCESS_TOKEN_KEY, token).apply()

            getUserInfo(token?:"")
        }
    }

    fun getAuthUrl(): String{
        val PAN_AUTH_BASEURL = "http://openapi.baidu.com/oauth/2.0/authorize?"
        val response_type = "token"
        val client_id = "xIG6LSGvRvLGN43OvI9obxeZfEdCZQYQ" // appKey
        val redirect_uri = "oob" // 回调后会返回一个平台提供默认回调地址：http://openapi.baidu.com/oauth/2.0/login_success
        val scope = "basic,netdisk"
        val display = "mobile"

        val url = String.format("%sresponse_type=%s&client_id=%s&redirect_uri=%s&scope=%s&display=%s",
            PAN_AUTH_BASEURL, response_type, client_id, redirect_uri, scope, display)
        return url
    }

    fun getUserInfo(token: String){
        NetOkHttpHelper.getInstance().getBaiduPanUserInfo(token, object : ResultCallback(){
            override fun onResponse(data: String) {
                val obj = JSONObject(data)
                val name = obj.getString("baidu_name")
                UIHelper.toast( "欢迎您，$name", this@BaiduPanActivity)
            }

            override fun onError(request: Request, e: Exception) {
                TODO("Not yet implemented")
            }

        })
    }
}