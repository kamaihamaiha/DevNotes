package cn.kk.base

import android.app.Application
import android.preference.PreferenceManager
import cn.kk.base.io.net.NetOkHttpHelper

open class BaseApp: Application() {

    companion object {
        lateinit var application: Application

        fun getInstance(): BaseApp {
            return application as BaseApp
        }
    }

    val prefs by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onCreate() {
        super.onCreate()

        application = this

        // init Net okhttp helper
        NetOkHttpHelper.init(this)

    }

}