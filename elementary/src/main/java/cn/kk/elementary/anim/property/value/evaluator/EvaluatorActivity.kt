package cn.kk.elementary.anim.property.value.evaluator

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.Point
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import cn.kk.base.activity.BaseActivity
import cn.kk.elementary.R
import cn.kk.elementary.anim.evaluator.FallBallEvaluator

/**
 * 自定义 Evaluator
 * 1. 字母变化
 * 2. 抛物动画
 */
class EvaluatorActivity: BaseActivity() {

    lateinit var btnPlay: Button

    val circle: ImageView by lazy {
        findViewById<ImageView>(R.id.circle)
    }
    val initCircleY: Int by lazy {
        circle.y.toInt()
    }

    override fun getLayout(): Int = R.layout.activity_evaluator

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        btnPlay = findViewById(R.id.btn_play)

        // 1. 字母变化动画
        val animLetter = ValueAnimator.ofObject(LetterEvaluator(), 'A', 'Z').apply {
            duration = 5000
            interpolator = LinearInterpolator()
            addUpdateListener {
                findViewById<TextView>(R.id.tvLetter).text = animatedValue.toString()
            }
        }

        // 2. 抛物线动画
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
            circle.y
        }
        val animFall = ValueAnimator.ofObject(FallBallEvaluator(), Point(0, 0), Point(800, 800))
        animFall.duration = 5000
        animFall.addUpdateListener {
            val curP = it.animatedValue as Point

            // 圆球初始化时有个高度，要加上这个高度
            circle.layout(curP.x, curP.y + initCircleY, curP.x + circle.width, curP.y + circle.height + initCircleY)
        }

        btnPlay.setOnClickListener {
            animLetter.start()
            animFall.start()
        }
    }

    /**
     * 字母 LetterEvaluator
     * 实际上是 ascii 码的字符
     */
    class LetterEvaluator: TypeEvaluator<Char>{
       override fun evaluate(fraction: Float, startValue: Char, endValue: Char): Char {
           val startValueInt = startValue.toInt()
           val endValueInt = endValue.toInt()
           val curChar = startValueInt + fraction * (endValueInt - startValueInt)
           return curChar.toChar()
       }
   }
}