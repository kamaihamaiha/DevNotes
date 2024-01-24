package cn.kk.customview.ui.cool300.chapter1

import android.widget.Button
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R

/**
 * 使用资源创建自定义背景的椭圆按钮
 */
class Simple_009: BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.simple_009
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        val btn_oval = findViewById<Button>(R.id.btn_oval)
        btn_oval.setOnClickListener { UIHelper.toast("click oval but!", this) }
    }
}