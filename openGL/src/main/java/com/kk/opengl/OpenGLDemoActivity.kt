package com.kk.opengl

import android.widget.TextView
import cn.kk.base.activity.BaseActivity
import com.kk.open_gl_lib.R

class OpenGLDemoActivity: BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_open_gl
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        val nativeLib = NativeLib()

        findViewById<TextView>(R.id.tv_open_GL).text = nativeLib.stringFromJNI()
    }
}