package cn.kk.elementary.anim.property.`object`

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.widget.Button
import android.widget.ImageView
import cn.kk.base.activity.BaseActivity
import cn.kk.elementary.R

/**
 * 3.5.3 AnimatorSet 监听器
 *  addListener(object : Animator.AnimatorListener)
 *  1. 监听函数只是用来监听 AnimatorSet 的状态，与其中包含的动画无关
 *  2. AnimatorSet 没有设置循环的函数，所以动画执行一次就结束了，永远无法执行函数 onAnimationRepeat()
 */
class AnimatorSetListenerActivity: BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_animator_set_listener
    lateinit var animSet: AnimatorSet
    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        val circle1 = findViewById<ImageView>(R.id.circle1)
        val circle2 = findViewById<ImageView>(R.id.circle2)
        val circle3 = findViewById<ImageView>(R.id.circle3)
        // region 1. 定义多个动画
        val animCircle1Trans = ObjectAnimator.ofFloat(circle1, "translationY", 0f, 1200f)
        val animCircle2Trans = ObjectAnimator.ofFloat(circle2, "translationY", 0f, 1200f).apply {
            repeatCount = ValueAnimator.INFINITE
        }
        val animCircle3Trans = ObjectAnimator.ofFloat(circle3, "translationY", 0f, 1200f)


        findViewById<Button>(R.id.btn_play).setOnClickListener {
            animSet = AnimatorSet().apply {
                play(animCircle1Trans).with(animCircle2Trans)
                duration = 2000
                // 添加动画监听
                addListener(object : Animator.AnimatorListener{
                    override fun onAnimationStart(animation: Animator) {
                        showToast("start...")
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        showToast("end...")
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        showToast("cancel...")
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        showToast("repeat...")
                    }

                })
            }

            animSet.start()
        }

        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            animSet.cancel()
        }
    }
}