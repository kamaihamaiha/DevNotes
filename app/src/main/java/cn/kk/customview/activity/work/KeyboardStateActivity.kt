package cn.kk.customview.activity.work

import android.content.res.Configuration
import android.widget.TextView
import cn.kk.base.activity.BaseActivity
import cn.kk.base.utils.SystemHelper
import cn.kk.customview.R

class KeyboardStateActivity: BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_keyboard_state
    }
    lateinit var tv_status: TextView
    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        tv_status = findViewById<TextView>(R.id.tv_status)
        tv_status.setText(if(SystemHelper.getExternalKeyboardStatus(this)) "连接" else "未连接")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        tv_status.setText(if(SystemHelper.getExternalKeyboardStatus(this)) "连接" else "未连接")
    }
}