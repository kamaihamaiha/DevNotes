package cn.kk.customview

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import cn.kk.customview.widget.WeekView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var myEditTextViewLayout = R.layout.activity_main
        var myTextViewAndRectViewLayout = R.layout.activity_test
        var myTitleBarLayout = R.layout.my_titlebar
        var myImageViewLayout = R.layout.activity_imageview
        var mySurfaceViewLayout = R.layout.activity_surfaceview
        var myHorizontalViewLayout = R.layout.activity_horizontalview
        var weekViewLayout = R.layout.activity_weekview
        setContentView(weekViewLayout)

        findViewById<Button>(R.id.btn_checkin).setOnClickListener {
            findViewById<WeekView>(R.id.weekView).playAnim(1,findViewById<ImageView>(R.id.iv_flag_out))
        }
    }
}