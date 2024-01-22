package cn.kk.base.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ScreenChangeReceiver: BroadcastReceiver() {
    val TAG = "ScreenChangeReceiver"
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_SCREEN_ON) {
            // 屏幕亮起
            Log.d(TAG, "onReceive: screen on")
        } else if (intent?.action == Intent.ACTION_SCREEN_OFF) {
            Log.d(TAG, "onReceive: screen off")
            // 屏幕关闭
        }
    }
}