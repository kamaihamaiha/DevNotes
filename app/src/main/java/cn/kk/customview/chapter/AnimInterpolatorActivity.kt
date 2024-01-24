package cn.kk.customview.chapter

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R

/**
 * 动画插值器
 *
 * 两种使用方式：
 * 1. 在 xml 文件中引用
 * 2. 用 setInterpolator() 方法
 */
class AnimInterpolatorActivity: BaseActivity() {

    val animList = arrayListOf<Animation>()
    var count = 0

    override fun getLayout(): Int = R.layout.activity_anim_interpolator

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        // 1.
        val alphaInterpolatorAnim = AnimationUtils.loadAnimation(this, R.anim.alpha_linear_interpolator_anim)
        val transInterpolatorAnim = AnimationUtils.loadAnimation(this, R.anim.trans_interpolator_anim)

        val transInterpolatorAnim2 = TranslateAnimation(0f, 900f, 0f, 0f).apply {
            duration = 1000
            fillAfter = true
            // 2.
            interpolator = AccelerateDecelerateInterpolator()
        }

        animList.add(alphaInterpolatorAnim)
        animList.add(transInterpolatorAnim)
        animList.add(transInterpolatorAnim2)
        val btn_play = findViewById<Button>(R.id.btn_play)
        val viewBlock = findViewById<View>(R.id.viewBlock)
        btn_play.setOnClickListener {

            val curAnim = animList[(count++) % animList.size]
            viewBlock.startAnimation(curAnim)
        }
    }
}