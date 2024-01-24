package cn.kk.customview

import android.app.Application
import cn.kk.base.BaseApp
import cn.kk.io.db.BookRepository

class MyApp: BaseApp() {

    companion object {
        lateinit var application: Application

        fun getInstance(): MyApp {
            return application as MyApp
        }
    }

    override fun onCreate() {
        super.onCreate()

        application = this

        // init Database
        BookRepository.init(this)
    }

}