package cn.kk.elementary.anim.property.value.interpolation

import android.animation.ArgbEvaluator
import android.animation.TimeInterpolator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.animation.doOnEnd
import cn.kk.base.MediaHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.elementary.R

/**
 * 属性动画
 * 1. 自定义插值器
 * 2. 自定义 Evaluator
 * 3. ArgbEvaluator
 * 4.
 */
class InterpolationActivity : BaseActivity() {

    private lateinit var animator: ValueAnimator
    private lateinit var btn_play: Button
    private val viewBlock by lazy {
        findViewById<View>(R.id.viewBlock)
    }

    // region 颜色动画 ArgbEvaluator
    private  val animatorArgb: ValueAnimator by lazy {
        ValueAnimator.ofInt(0xffffff00.toInt(), 0xff0000ff.toInt()).apply {
            duration = 3000
            setEvaluator(ArgbEvaluator())
            addUpdateListener {
                viewBlock.setBackgroundColor(animatedValue as Int)
            }
        }
    }
    // endregion 颜色动画 ArgbEvaluator

    override fun getLayout(): Int = R.layout.activity_interpolation

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        btn_play = findViewById(R.id.btn_play)

        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
            // 声明动画，并设置
            animator = ValueAnimator.ofInt(0, btn_play.y.toInt() - viewBlock.height)
            animator.duration = 2000
            animator.addUpdateListener {
                val curValue = it.animatedValue as Int
                viewBlock.layout(viewBlock.left, curValue, viewBlock.right, curValue + viewBlock.height)
            }
            animator.interpolator = MyInterpolator()

            // 设置 Evaluator
//            animator.setEvaluator(MyEvaluator())
            animator.setEvaluator(ReverseEvaluator())

            animator.doOnEnd {
                MediaHelper(this).playBeep()
            }
        }


        // 点击按钮，开启动画
        btn_play.setOnClickListener {
            animator.start()
            animatorArgb.start()
        }
    }

    /**
     * 自定义插值器
     */
    class MyInterpolator : TimeInterpolator {
        override fun getInterpolation(input: Float): Float {
            return 1 - input
        }
    }

    /**
     * 自定义 Evaluator
     * 把 ofInt() 传递进来的数字范围，整体变大 200
     */
    class MyEvaluator : TypeEvaluator<Int> {
        override fun evaluate(fraction: Float, startValue: Int, endValue: Int): Int {
            return 500 + startValue + (fraction * (endValue - startValue)).toInt()
        }
    }

    /**
     * 自定义 Evaluator
     * 进度倒转
     */
    class ReverseEvaluator: TypeEvaluator<Int>{
        override fun evaluate(fraction: Float, startValue: Int, endValue: Int): Int {
           return (endValue - fraction * (endValue - startValue)).toInt()
        }

    }
}