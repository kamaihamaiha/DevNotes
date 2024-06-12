package cn.kk.customview.ui.system

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import cn.kk.base.UIHelper
import cn.kk.customview.R

/**
 * 1. 隐藏状态栏/显示状态栏
 * 2. 隐藏状态栏图标/显示状态栏图标
 * 3. 半透明状态栏(使用窗口标志实现的)
 * 4. 动态设置状态栏背景色\
 * 5. 将应用的背景图像扩展至状态栏
 * 6. 改变状态栏字体颜色
 *
 * View.SYSTEM_UI_FLAG_LOW_PROFILE 用于隐藏通知栏的部分图标，并变暗，主要是为了降低电量消耗。
 */
class StatusBarActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_bar)

        // 隐藏状态栏
        findViewById<Button>(R.id.btn_hide_status_bar).setOnClickListener {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        // 显示状态栏
        findViewById<Button>(R.id.btn_show_status_bar).setOnClickListener {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }

        val btn_hide_status_bar_icons = findViewById<Button>(R.id.btn_hide_status_bar_icons)
        val btn_status_bar_trans = findViewById<Button>(R.id.btn_status_bar_trans)
        val btn_status_bar_color_change = findViewById<Button>(R.id.btn_status_bar_color_change)
        val btn_extent_img_status_bar = findViewById<Button>(R.id.btn_extent_img_status_bar)
        val btn_status_bar_text_color_change = findViewById<Button>(R.id.btn_status_bar_text_color_change)
        // 隐藏状态栏部分图标
        btn_hide_status_bar_icons.setOnClickListener { window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE }

        // 显示状态栏全部图标
        btn_hide_status_bar_icons.setOnClickListener { window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE }

        // 半透明状态栏
        btn_status_bar_trans.setOnClickListener { window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) }

        // 设置状态栏背景色
        var count = 0
        btn_status_bar_color_change.setOnClickListener {
            when(count % 5){
                0 -> window.statusBarColor = Color.RED
                1 -> window.statusBarColor = Color.BLUE
                2 -> window.statusBarColor = Color.GREEN
                3 -> window.statusBarColor = Color.BLACK
                4 -> window.statusBarColor = Color.WHITE
            }
            count++
        }

        // 5. 将应用的背景图像扩展至状态栏
        btn_extent_img_status_bar.setOnClickListener {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

            // 设置状态栏背景透明
            window.statusBarColor = Color.TRANSPARENT

            // 动态设置根布局 padding top 值
            findViewById<LinearLayout>(R.id.rootView).setPadding(0, 44 * 3, 0, 0)
        }

        // 6.
        var cout_6 = 0
        btn_status_bar_text_color_change.setOnClickListener {
            if (cout_6 % 2 == 0){
                UIHelper.setStatusBarTextColorDark(this)
            } else {
                UIHelper.setStatusBarTextColorLight(this)
            }
            cout_6++
        }
    }
}