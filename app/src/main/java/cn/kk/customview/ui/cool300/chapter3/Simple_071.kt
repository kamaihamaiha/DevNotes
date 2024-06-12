package cn.kk.customview.ui.cool300.chapter3

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R

/**
 * 弹出底部菜单，主窗口立刻变暗
 * 设置 WindowManger.LayoutParams 的 alpha 值
 */
class Simple_071: BaseActivity() {
    lateinit var myPopup: PopupWindow

    override fun getLayout(): Int {
        return R.layout.simple_071
    }
    lateinit var iv_bg: ImageView

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        iv_bg = findViewById<ImageView>(R.id.iv_bg)
        iv_bg.setOnClickListener {
            myPopup = showPopupFromBottom()
        }
    }

    private fun showPopupFromBottom(): PopupWindow {
        val myWindow = LayoutInflater.from(this).inflate(R.layout.my_pop_bottom_layout, null, false)
        val myMenuWindow = PopupWindow().apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            setContentView(myWindow)
        }

        val menuUp = myWindow.findViewById<TextView>(R.id.tv_up)
        val menuDown = myWindow.findViewById<TextView>(R.id.tv_down)

        menuUp.setOnClickListener {
            myMenuWindow.dismiss()
            resetWindow()
        }
        menuDown.setOnClickListener {
            myMenuWindow.dismiss()
            resetWindow()
        }

        // 菜单从窗口底部滑出
        myMenuWindow.showAtLocation(iv_bg, Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
        // 窗口变暗
        darkWindow()

        return myMenuWindow
    }

    fun darkWindow(){
        val myParams = window.attributes
        myParams.alpha = 0.4f
        window.attributes = myParams
    }

    fun resetWindow(){
        val myParams = window.attributes
        myParams.alpha = 1f
        window.attributes = myParams
    }
}