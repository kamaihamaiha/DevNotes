package cn.kk.base.utils

import android.app.Activity
import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Environment
import android.os.Vibrator
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat

object SystemHelper {

    private var clipboard: ClipboardManager? = null

    fun getAppInfo(context: Context){
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    }

    /**
     * 震动
     * @param duration 震动时长，单位：ms
     */
    fun vibrate(ctx: Context, duration: Long){
        try {
            val vibrator = ctx.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(duration)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSdcardPath(): String{
        return Environment.getExternalStorageDirectory().path
    }


    fun openSysNotificationSetting(ctx: Context){
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(Settings.EXTRA_APP_PACKAGE, ctx.packageName)
        }
        ctx.startActivity(intent)
    }


    fun isNotificationEnable(ctx: Context): Boolean {
        return try {
            NotificationManagerCompat.from(ctx).areNotificationsEnabled()
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 设置屏幕竖屏
     */
    fun setScreenOrientationPortrait(activity: Activity){
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * 设置屏幕横屏
     */
    fun setScreenOrientationLandscape(activity: Activity){
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    /**
     * 判断外接键盘连接状态
     * 注意：网上都说用 BluetoothDevice, BluetoothProfile, BluetoothAdapter 复杂且不管用
     */
    fun getExternalKeyboardStatus(ctx: Context): Boolean{
        return ctx.resources.configuration.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO
    }

    fun setClipboardText(text: String?, ctx: Context) {
        if (clipboard == null) {
            clipboard = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        }
        if (clipboard != null) {
            val clip = ClipData.newPlainText("kk_search", text)
            clipboard?.setPrimaryClip(clip)

        }
    }
}