package cn.kk.customview.activity.more

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.MyApp
import cn.kk.customview.R
import cn.kk.customview.activity.NormalWebViewActivity
import cn.kk.customview.io.NetOkHttpHelper
import cn.kk.customview.io.ResultCallback
import cn.kk.customview.io.bean.BaiduPanUserInfo
import cn.kk.customview.io.bean.FileListModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.gson.Gson
import okhttp3.Request
import org.json.JSONObject
import java.lang.Exception

class BaiduPanActivity: BaseActivity() {
    companion object {
        val PAN_REQUEST_CODE = 100
        val INTENT_ACCESS_TOKEN = "access_token"
    }

    val INTENT_DIR_PATH_KEY = "path"
    val SP_PAN_ACCESS_TOKEN_KEY = "pan_access_token_key"
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

            dirPath = intent.getStringExtra(INTENT_DIR_PATH_KEY) ?: ""
            getCurPathFileList(dirPath)

            val a: Short
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

    private fun getUserInfo(token: String){
        NetOkHttpHelper.getInstance().getBaiduPanUserInfo(token, object : ResultCallback(){
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
        NetOkHttpHelper.getInstance().getBaiduPanCurPathFileListInfo(panAccessToken, path, object : ResultCallback(){
            override fun onResponse(data: String) {
                val fileListModel = Gson().fromJson(data, FileListModel::class.java)
                fileListModel.list?.filter { it.supportType() }?.forEach { // 过滤文件类型
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
            file?.let {
                holder.setText(R.id.tv_title, file.server_filename)
                holder.setText(R.id.tv_file_size, if (file.isDirTag()) "" else file.getHumanSize())
                holder.setText(R.id.tv_time, file.getServerATime())
                holder.setImageResource(R.id.iv_icon, getFileTypeResId(it))
                holder.setGone(R.id.tv_file_size, !file.isDirTag())
            }
        }

        private fun getFileTypeResId(file: FileListModel.FileInfo): Int{
            if (file.isAudioType()) return R.drawable.icon_file_audio
            if (file.isVideoType()) return R.drawable.icon_file_video
            return R.drawable.favorite_album
        }
    }
}