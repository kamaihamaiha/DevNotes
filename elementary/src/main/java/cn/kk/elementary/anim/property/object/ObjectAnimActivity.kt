package cn.kk.elementary.anim.property.`object`

import android.animation.ObjectAnimator
import android.graphics.Point
import android.widget.Button
import android.widget.TextView
import cn.kk.base.activity.BaseActivity
import cn.kk.elementary.R
import cn.kk.elementary.anim.evaluator.FallBallEvaluator
import cn.kk.elementary.anim.property.`object`.widget.FallingBallImageView

/**
 * ObjectAnimator
 * 1. 使用 "alpha"
 * 2. 自定义 ObjectAnimator 属性
 */
class ObjectAnimActivity: BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_object_anim

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        // region 1. 使用 alpha 动画
        val tvLetter = findViewById<TextView>(R.id.tvLetter)
        val anim = ObjectAnimator.ofFloat(tvLetter, "alpha", 1f, 0f, 1f)
        anim.duration = 2000

        // endregion

        // region 2. 自定义 ObjectAnimator 属性
        val circle = findViewById<FallingBallImageView>(R.id.circle)
        val animFall = ObjectAnimator.ofObject(circle, "fallingPos", FallBallEvaluator() ,Point(0, 0), Point(600, 600))
        animFall.duration = 3000
        // endregion

        // region 播放动画
        findViewById<Button>(R.id.btn_play).setOnClickListener {
            anim.start()
            animFall.start()
        }
        // endregion
    }
}