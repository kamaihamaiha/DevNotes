package cn.kk.customview.ui.cool300.chapter5

import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.widget.ImageView
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R

class Simple_143: BaseActivity() {
    override fun getLayout(): Int {
      return  R.layout.activity_simple_143
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        val iv_apple = findViewById<ImageView>(R.id.iv_apple)
        iv_apple.setOnClickListener {
            val myColorStateList = createColorStateList(0xffffffff.toInt(),
                0xffffff00.toInt(), 0xff0000ff.toInt(), 0xffff0000.toInt()
            )

            iv_apple.background = RippleDrawable(myColorStateList, null, null)
        }


    }

    private fun createColorStateList(normal: Int, pressed: Int, focused: Int, unable: Int) : ColorStateList {
        val myColors = intArrayOf(pressed, focused, normal, focused, unable, normal)

        // 二维数组
        val myStatus = arrayOfNulls<IntArray>(6)

        myStatus[0] = intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled)
        myStatus[1] = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_focused)
        myStatus[2] = intArrayOf(android.R.attr.state_enabled)
        myStatus[3] = intArrayOf(android.R.attr.state_focused)
        myStatus[4] = intArrayOf(android.R.attr.state_window_focused)
        myStatus[5] = intArrayOf()

        return ColorStateList(myStatus, myColors)
    }
}