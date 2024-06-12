package cn.kk.customview.ui.cool300.chapter8

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import java.io.File

/**
 * QQ 空间不支持原生分享，只能用 SDK 分享: https://wiki.connect.qq.com
 */
class SimpleIntentActivity: BaseActivity() {
    val WECHAT_PKG_NAME = "com.tencent.mm"
    val QQ_PKG_NAME = "com.tencent.mobileqq"
    val YINXIANG_PKG_NAME = "com.yinxiang"

    val TEST_PDF_PATH = "/sdcard/Download/2.pdf"

    companion object {
        val TYPE_OPEN_PDF = 1
    }
    override fun getLayout(): Int {
        return R.layout.activity_simple_intent
    }

    lateinit var et_input: EditText

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        val btn_share = findViewById<Button>(R.id.btn_share)
        et_input = findViewById<EditText>(R.id.et_input)
        if (isTypeOpenPDF()) {
            btn_share.text = "Share PDF"
            et_input.hint = "请输入 PDF 文件路径!"
            et_input.setText(TEST_PDF_PATH)
        }

        btn_share.setOnClickListener {
            if (isTypeOpenPDF()) {
                openPDF()
            } else {
                share2WechatV2()
            }
        }
    }

    private fun isTypeOpenPDF(): Boolean {
        return ui_type == TYPE_OPEN_PDF
    }

    /**
     * 分享文本到微信
     */
    private fun shareText2Wechat(){
        if (TextUtils.isEmpty(et_input.text)) {
            showToast("内容不能为空!")
            return
        }
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            setPackage("com.tencent.mm")
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, et_input.text.toString())
        }

        // 判断手机是否安装了微信
        try {
            startActivity(sendIntent)
        } catch (e: Exception) {
            showToast(e.toString())
        }
    }

    /**
     * 分享直接抵达微信
     * 参考: https://blog.huati365.com/3578179733077f16
     */
    private fun share2WechatV2(){
        if (TextUtils.isEmpty(et_input.text)) {
            showToast("内容不能为空!")
            return
        }
        val intent = Intent()
        val wxFriend = "com.tencent.mm.ui.tools.ShareImgUI" // 微信联系人
        val wxCircleUI = "com.tencent.mm.ui.tools.ShareToTimeLineUI" // 朋友圈
        val comp = ComponentName("com.tencent.mm", wxFriend)
        intent.component = comp
        intent.action = Intent.ACTION_SEND
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, et_input.text.toString())

        startActivity(intent)
    }

    private fun openPDF(){
        if (TextUtils.isEmpty(et_input.text)) {
            showToast("路径不能为空!")
            return
        }
        val uri = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(this@SimpleIntentActivity, application.packageName.plus(".file.provider"), File(et_input.text.toString()))
        } else {
            Uri.fromFile(File(et_input.text.toString()))
        }

        val intent = Intent().apply {
            setAction(Intent.ACTION_SEND)
            setType("application/pdf")
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            // Android 7.0 以上不能这么用了：https://www.jianshu.com/p/558f90275c7c
            putExtra(Intent.EXTRA_STREAM, uri)
            setComponent(getMComponentName(ShareTypeMedia.QQ)) // 微信没有登录，因此会报错：「获取资源失败」很坑
        }

        startActivity(intent)
    }

    // 不能使用 Uri.from() 或者 File().toUri()
    // 要用：FileProvider，参考：https://stackoverflow.com/questions/38200282/android-os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed
    private fun sharePic2QQ(){
       val file = File("").toUri()
    }

    fun getMComponentName(systemShareMedia: ShareTypeMedia?): ComponentName? {
        when (systemShareMedia) {
            ShareTypeMedia.WECHAT -> return ComponentName(
                WECHAT_PKG_NAME,
                "com.tencent.mm.ui.tools.ShareImgUI"
            )
            ShareTypeMedia.WECHAT_CIRCLE -> return ComponentName(
                WECHAT_PKG_NAME,
                "com.tencent.mm.ui.tools.ShareToTimeLineUI"
            )
            ShareTypeMedia.QQ -> return ComponentName(
                QQ_PKG_NAME,
                "com.tencent.mobileqq.activity.JumpActivity"
            )
            ShareTypeMedia.QQ_ZONE -> return ComponentName(
                QQ_PKG_NAME,
                "com.tencent.mobileqq.cooperation.qzone.share.QZoneShareActivity"
            )
            ShareTypeMedia.YINXIANG -> return ComponentName(
                YINXIANG_PKG_NAME,
                "com.evernote.note.composer.NewNoteAloneActivity"
            )
            else -> {
                return null
            }
        }
        return null
    }

}
    enum class ShareTypeMedia {
        WECHAT,
        WECHAT_CIRCLE,
        QQ, QQ_ZONE,
        YINXIANG
    }
