package cn.kk.customview.ui.work

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import kotlinx.android.synthetic.main.activity_flicker.*

/**
 * 闪烁动画
 */
class FlickerActivity: BaseActivity() {
    override fun getLayout(): Int {
      return  R.layout.activity_flicker
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        val alphaAnim = AlphaAnimation(1.0f, 0.3f).apply {
            duration = 500
            repeatMode = Animation.REVERSE
            repeatCount = Int.MAX_VALUE
        }


        btn_play.setOnClickListener {
            if (btn_play.animation == null) {
                btn_play.startAnimation(alphaAnim)
            } else {
                if (btn_play.animation.hasStarted()) {
                    btn_play.clearAnimation()
                } else {
                    btn_play.startAnimation(alphaAnim)
                }
            }

        }
    }
}