package cn.kk.elementary.anim.property.value

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import cn.kk.base.*
import cn.kk.base.activity.BaseActivity
import cn.kk.elementary.R

/**
 * 属性动画
 *
 * 1. ValueAnimator 的简单使用
 * 2. 常用函数
 *      2.0 addUpdateListener
 *      2.1 addListener()
 *      2.2 cancel()
 *      2.3 repeatCount
 *      2.4 repeatMode
 *      2.5 reverse()
 *      2.6 doOnStart {  }
 *      2.7 doOnEnd {  }
 *      2.8 doOnCancel {  }
 *      2.9 doOnPause {  }
 *      2.10 doOnRepeat {  }
 *      2.11 doOnResume {  }
 *      2.12 startDelay
 *      2.13 clone()
 *
 *
 */
class ValueAnimationActivity: BaseActivity() {

    val viewBlockY by lazy {
        findViewById<View>(R.id.viewBlock).y
    }

    lateinit var valueAnimator: ValueAnimator
    lateinit var btn_play: Button

    // 媒体播放帮助类
    lateinit var mediaHelper: MediaHelper

    var milestone = 0
    // 动画播放状态
    var playState = false


    override fun getLayout(): Int = R.layout.activity_anim_property

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        btn_play = findViewById<Button>(R.id.btn_play)
        mediaHelper = MediaHelper(this)
        btn_play.setOnClickListener {
            if (!playState) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    valueAnimator = createAnim()
                }
                setAnimMoreOperation()
                valueAnimator.start()
            } else {
                // region 2.2 取消播放
                valueAnimator.cancel()

                // endregion 2.2 取消播放
            }
        }
    }

    // region 1. ValueAnimator 的简单使用
    @RequiresApi(Build.VERSION_CODES.R)
    private fun createAnim(): ValueAnimator {
        // region step1. 创建 ValueAnimator 实例. 只水平移动。
        val viewBlock = findViewById<View>(R.id.viewBlock)
        val maxHorizontalDistance = UIHelper.getScreenSize(this).x - viewBlock.width

        // 移动的转折点数组
        val turnPoints = intArrayOf(
            maxHorizontalDistance,
            maxHorizontalDistance * 1 / 5,
            maxHorizontalDistance * 4 / 5,
            maxHorizontalDistance * 2 / 5,
            maxHorizontalDistance * 3 / 5,
            maxHorizontalDistance / 2
        )
        val animator = ValueAnimator.ofInt(
            0,
            turnPoints[0],
            turnPoints[1],
            turnPoints[2],
            turnPoints[3],
            turnPoints[4],
            turnPoints[5]
        )
        animator.duration = 4000
        // endregion step1. 创建 ValueAnimator 实例


        // region step2. 添加监听
        animator.addUpdateListener {

            val curValue: Int = it.animatedValue as Int
            findViewById<TextView>(R.id.tv_value).text = curValue.toString()

            // 给控件 viewBlock 执行动画
            viewBlock.layout(
                curValue,
                viewBlockY.toInt(),
                viewBlock.width + curValue,
                viewBlockY.toInt() + viewBlock.height
            )

            // 当到达转折点时，播放声音
            if (milestone < turnPoints.size && ValueHelper.valueIsNearBy(curValue, turnPoints[milestone])) {

                // 第二轮播放，就没有生意了。不知道为什么
                mediaHelper.playBeep()
                printLog("milestone: $milestone")
                milestone++

            }
        }
        // endregion step2. 添加监听

        // region step3. 开启动画
//        animator.start()
        // endregion step3. 开启动画

        return animator
    }
    // endregion 2. ValueAnimator 的简单使用

    // region 2. 常用函数
    fun setAnimMoreOperation(){

        // region 2.1 监听动画变换的 4 个状态
        valueAnimator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator) {
               playState = true
                btn_play.text = "cancel"
            }

            override fun onAnimationEnd(animation: Animator) {
               playState = false
                btn_play.text = "play"
            }

            override fun onAnimationCancel(animation: Animator) {
               playState = false
                btn_play.text = "restart"
            }

            override fun onAnimationRepeat(animation: Animator) {
            }

        })
        // endregion 2.1 监听动画变换的 4 个状态

    }
    // endregion 2. 常用函数
}