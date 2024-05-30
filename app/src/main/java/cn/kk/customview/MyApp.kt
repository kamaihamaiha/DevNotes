package cn.kk.customview

import android.app.Application
import cn.kk.base.BaseApp
import cn.kk.customview.io.NetOkHttpHelper
import cn.kk.io.db.BookRepository

class MyApp: BaseApp() {


    override fun onCreate() {
        super.onCreate()
        application = this
        // init Net okhttp helper
        NetOkHttpHelper.init(this)

        // init Database
        BookRepository.init(this)
    }

    companion object {
        lateinit var application: Application
    }
}