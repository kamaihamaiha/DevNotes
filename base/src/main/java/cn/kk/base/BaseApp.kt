package cn.kk.base

import android.app.Application

open class BaseApp: Application() {
    companion object {
        lateinit var application: Application
    }
    override fun onCreate() {
        super.onCreate()
        application = this
    }
}