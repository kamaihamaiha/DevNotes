package cn.kk.customview.activity.more

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.base.io.BooleanCallback
import cn.kk.base.io.net.NetDiskBaiduManager
import cn.kk.customview.MyApp
import cn.kk.customview.R
import cn.kk.customview.activity.NormalWebViewActivity
import cn.kk.base.io.net.NetOkHttpHelper
import cn.kk.base.media.MyPlayerActivity
import cn.kk.customview.io.HttpBaseCallback
import cn.kk.customview.io.model.BaiduPanUserInfo
import cn.kk.customview.io.model.FileListModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.gson.Gson
import okhttp3.Request
import java.lang.Exception

class BaiduPanActivity: BaseActivity() {
    companion object {
        val PAN_REQUEST_CODE = 100
        val INTENT_ACCESS_TOKEN = "access_token"
        val INTENT_EXPIRES_IN = "expires_in"
    }

    val INTENT_DIR_PATH_KEY = "path"
    val SP_PAN_ACCESS_TOKEN_KEY = "pan_access_token_key"
    val SP_PAN_EXPIRE_STAMP_KEY = "pan_expire_stamp_key"
    var panAccessToken = ""
    var mPanUserInfo: BaiduPanUserInfo ?= null
    var dirPath = ""

    val mEmptyView: View by lazy {
        layoutInflater.inflate(R.layout.common_empty_layout, null)
    }

    val mNetDiskFileList = mutableListOf<FileListModel.FileInfo>()
    val mPanFileAdapter by lazy {
        PanNetDiskAdapter(mNetDiskFileList).apply {
            setOnItemClickListener { adapter, view, position ->
                val fileItem = adapter.getItem(position) as FileListModel.FileInfo
                if (fileItem.isDirTag()) {
                    // 打开下一级目录, 用新的页面打开
                    startActivity(Intent(this@BaiduPanActivity, BaiduPanActivity::class.java).apply {
                        putExtra(INTENT_DIR_PATH_KEY, fileItem.path)
                    })
                } else {
                    // todo 处理文件
                    if (fileItem.isAudioType()) {

                    } else if (fileItem.isVideoType()) {
                        NetDiskBaiduManager.getNetDiskBaiduMediaStreamUrl(false, panAccessToken, fileItem.path, true, object: BooleanCallback {
                            override fun onResult(success: Boolean, msg: String?) {
                                if (!success) {
                                    UIHelper.toast(msg?:"", this@BaiduPanActivity)
                                    return
                                }
                                startActivity(Intent(this@BaiduPanActivity, MyPlayerActivity::class.java).apply {
                                    putExtra(INTENT_MEDIA_URL_KEY, msg)
                                })
                            }

                        })
                    }




                }
            }
        }
    }
    override fun getLayout(): Int {
        return R.layout.activity_baidu_pan
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()


        // file list
        val rvList = findViewById<RecyclerView>(R.id.rv_file)
        rvList?.apply {
            layoutManager = LinearLayoutManager(this@BaiduPanActivity)
            adapter = mPanFileAdapter
        }


        panAccessToken = MyApp.getInstance().prefs.getString(SP_PAN_ACCESS_TOKEN_KEY, "")?:""
        val validStampEnd = MyApp.getInstance().prefs.getLong(SP_PAN_EXPIRE_STAMP_KEY, 0)
        val validTime = validStampEnd > System.currentTimeMillis()
        if (panAccessToken.isNullOrEmpty() || !validTime) {
            // 未授权
            /**
             * 1. 打开百度授权页面
             * 2. 获取根目录下的文件
             */
            startActivityForResult(
                Intent(this@BaiduPanActivity, NormalWebViewActivity::class.java)
                    .apply {
                        putExtra(INTENT_WEB_URL_KEY, getAuthUrl())
                    }, PAN_REQUEST_CODE
            )
        } else {
            /**
             * 1. 获取用户信息
             * 2. 获取根目录下的文件
             */
            getUserInfo(panAccessToken)

            dirPath = intent.getStringExtra(INTENT_DIR_PATH_KEY) ?: ""
            getCurPathFileList(dirPath)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PAN_REQUEST_CODE) {
            // show a toast
            val token = data?.getStringExtra(INTENT_ACCESS_TOKEN)
            val expiresIn = data?.getLongExtra(INTENT_EXPIRES_IN, 0L)?:0L
            MyApp.getInstance().prefs.edit().putString(SP_PAN_ACCESS_TOKEN_KEY, token).apply()
            val expireStamp = System.currentTimeMillis() + expiresIn * 1000L
            MyApp.getInstance().prefs.edit().putLong(SP_PAN_EXPIRE_STAMP_KEY, expireStamp).apply()

            getUserInfo(token?:"")
            getCurPathFileList(dirPath)
        }
    }

    fun isRootDir(): Boolean {
        dirPath = intent.getStringExtra(INTENT_DIR_PATH_KEY) ?: ""
        return TextUtils.isEmpty(dirPath)
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

    private fun refreshPanAccessToken() {
        val appKey = NetDiskBaiduManager.BaiduPanAppConfig.appKey
        val secretKey = NetDiskBaiduManager.BaiduPanAppConfig.secretKey

        NetDiskBaiduManager.refreshNetDisk_BaiduAccessToken("", appKey, secretKey) { success, result ->
            result?.let {
                // save token to sp file
                panAccessToken = it.access_token
                MyApp.getInstance().prefs.edit().putString(SP_PAN_ACCESS_TOKEN_KEY, it.access_token).apply()
                val expiresStamp = System.currentTimeMillis() + it.expires_in * 1000L
                MyApp.getInstance().prefs.edit().putLong(SP_PAN_EXPIRE_STAMP_KEY, expiresStamp)
                    .apply()
                getUserInfo(panAccessToken)
            }
        }

    }

    private fun getUserInfo(token: String){
        NetOkHttpHelper.getInstance().getBaiduPanUserInfo(token, object : HttpBaseCallback(){
            override fun onResponse(data: String) {
                mPanUserInfo = Gson().fromJson(data, BaiduPanUserInfo::class.java)
                setTitle(String.format("%s的网盘", mPanUserInfo!!.baidu_name))
            }

            override fun onError(request: Request, e: Exception) {
                UIHelper.toast("error: ${e.toString()}", this@BaiduPanActivity)
            }

        })
    }

    private fun setTitle(title: String){
        val dirName = dirPath.substring(dirPath.lastIndexOf("/") + 1)
        updateTitle(if (isRootDir()) title else dirName)
    }
    private fun getCurPathFileList(path: String){
        NetOkHttpHelper.getInstance().getBaiduPanCurPathFileListInfo(panAccessToken, path, object : HttpBaseCallback(){
            override fun onResponse(data: String) {
                val fileListModel = Gson().fromJson(data, FileListModel::class.java)
                fileListModel.list?.forEach { // 过滤文件类型
                    mNetDiskFileList.add(it)
                }
                mPanFileAdapter.apply {
                    setEmptyView(mEmptyView)
                    notifyDataSetChanged()
                }
            }

            override fun onError(request: Request, e: Exception) {

            }

        })
    }


    class PanNetDiskAdapter(fileList: MutableList<FileListModel.FileInfo>): BaseQuickAdapter<FileListModel.FileInfo, BaseViewHolder>(R.layout.item_net_disk_file , fileList) {
        override fun convert(holder: BaseViewHolder, file: FileListModel.FileInfo) {
            file.let {
                holder.setText(R.id.tv_title, file.server_filename)
                holder.setText(R.id.tv_file_size, if (file.isDirTag()) "" else file.getHumanSize())
                holder.setText(R.id.tv_time, file.getServerATime())
                holder.setImageResource(R.id.iv_icon, getFileTypeResId(it))
                holder.setGone(R.id.tv_file_size, file.isDirTag())
            }
        }

        private fun getFileTypeResId(file: FileListModel.FileInfo): Int{
            if (file.isDirTag()) return R.drawable.favorite_album
            if (file.isAudioType()) return R.drawable.icon_file_audio
            if (file.isVideoType()) return R.drawable.icon_file_video
            if (file.isDocumentFileType()) return R.drawable.icon_document
            return R.drawable.icon_file_unknown
        }
    }
}