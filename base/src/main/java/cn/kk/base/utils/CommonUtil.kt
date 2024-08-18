package cn.kk.base.utils

import android.R
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import cn.kk.base.UIHelper


object CommonUtil {
    const val URL_APP_EVERNOTE_DOWNLOAD = "https://sj.qq.com/appdetail/com.yinxiang?&from_wxz=1"

    const val WECHAT_PKG_NAME = "com.tencent.mm"
    const val QQ_PKG_NAME = "com.tencent.mobileqq"
    const val EVERNOTE_PKG_NAME = "com.yinxiang"
    const val XHS_PKG_NAME = "com.xingin.xhs"
    const val SINA_PKG_NAME = "com.sina.weibo"

    const val URL_DEEPLINK_TING_EN_HOME_XHS = "xhsdiscover://user/668defbf00000000030323a4"
    // 小红书 deeplink: https://pages.xiaohongshu.com/activity/deeplink#toc_14
    const val URL_DEEPLINK_MY_HOME_XHS = "xhsdiscover://user/5b16a87ee8ac2b2b2bbd55f1"


    fun hasInstallXhs(ctx: Context?): Boolean { // 是否安装了小红书
        return hasInstallSpecialApp(ctx, ShareTypeMedia.XINGIN_XHS)
    }
    fun hasInstallSpecialApp(ctx: Context?, shareTypeMedia: ShareTypeMedia?): Boolean {
        var pkgName: String = WECHAT_PKG_NAME
        when (shareTypeMedia) {
            ShareTypeMedia.QQ, ShareTypeMedia.QQ_ZONE -> pkgName = QQ_PKG_NAME
            ShareTypeMedia.WECHAT, ShareTypeMedia.WECHAT_CIRCLE -> pkgName = WECHAT_PKG_NAME
            ShareTypeMedia.EVERNOTE -> pkgName = EVERNOTE_PKG_NAME
            ShareTypeMedia.XINGIN_XHS -> pkgName = XHS_PKG_NAME
            else -> return false
        }
        return hasInstallSpecialApp(ctx, pkgName)
    }

    fun hasInstallSpecialApp(ctx: Context?, pkgName: String): Boolean {
        if (TextUtils.isEmpty(pkgName) || ctx == null) return false
        val packageManager = ctx.packageManager
        val info: PackageInfo? = getPackageInfoCompat(packageManager, pkgName, 0)
        return info != null
    }


    fun getPackageInfoCompat(packageManager: PackageManager, packageName: String, flags: Int): PackageInfo? {
        try {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val flagsLong = flags.toLong()
                 val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(flagsLong))
                 return packageInfo
             } else {
                 val packageInfo = packageManager.getPackageInfo(packageName, flags)
                 return packageInfo
             }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    fun gotoMarketDownload(activity: Activity, targetAppPkg: String): Boolean {
        val uri = Uri.parse("market://details?id=$targetAppPkg")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            activity.startActivity(goToMarket)
            return true
        } catch (e: ActivityNotFoundException) {
            if (EVERNOTE_PKG_NAME.equals(targetAppPkg)) {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(URL_APP_EVERNOTE_DOWNLOAD)
                    )
                )
            } else {
                UIHelper.toast("no_action_view",activity)
            }
        }
        return false
    }

    fun openSpecialApp(ctx: Context, appDeepLink: String) {
        val intentXHS = Intent(Intent.ACTION_VIEW, Uri.parse(appDeepLink))
        ctx.startActivity(intentXHS)
    }

    enum class ShareTypeMedia {
        WECHAT, WECHAT_CIRCLE, QQ, QQ_ZONE, EVERNOTE,  // 印象笔记
        XINGIN_XHS,  // 小红书
        SINA
    }
}