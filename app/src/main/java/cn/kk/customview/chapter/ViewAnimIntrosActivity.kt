package cn.kk.customview.chapter

import android.view.animation.*
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import kotlinx.android.synthetic.main.activity_view_anim_intros.*

/**
 * 动画的一些例子
 * 1. 视图动画，xml 方式
 * 2. 视图动画，代码方式
 */
class ViewAnimIntrosActivity: BaseActivity() {

    val animList = arrayListOf<AnimBean>()
    var count = 0

    override fun getLayout(): Int = R.layout.activity_view_anim_intros

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        // 1. 视图动画，xml 方式
        // 播放伸缩动画
        val animScale = AnimationUtils.loadAnimation(this, R.anim.scale_anim)
        val animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha_anim)
        val animTrans = AnimationUtils.loadAnimation(this, R.anim.trans_anim)
        val animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate_anim)
        val animSet = AnimationUtils.loadAnimation(this, R.anim.set_anim)

        // 2. 视图动画，代码方式
        // 2.1. 伸缩动画
        val scaleAnimation1 = ScaleAnimation(0.1f, 1.3f,0.1f, 1.3f).apply { duration = 2000 }
        val scaleAnimation2 = ScaleAnimation(0.1f, 1.3f,0.1f, 1.3f, 0.5f, 0.5f).apply { duration = 2000 }
        val scaleAnimation3 = ScaleAnimation(0.1f, 1.3f,0.1f,1.3f, Animation.RELATIVE_TO_SELF,
            0.5f,Animation.RELATIVE_TO_SELF, 0.5f).apply { duration = 2000 }

        // 2.2 渐变动画
        val alphaAnim = AlphaAnimation(0.3f, 1.0f).apply {
            duration = 2500
            fillAfter = true
        }

        // 2.3 旋转动画
        val rotateAnim = RotateAnimation(10f, 80f, Animation.RELATIVE_TO_SELF, 0.6f, Animation.RELATIVE_TO_SELF, 0.4f).apply {
            duration = 1500
        }

        // 2.4 平移动画
        val transAnim = TranslateAnimation(0.03f, 0.9f, 0.2f, 0.8f).apply {
            duration = 1500
        }

        // 2.5 动画集合
        val setAnim = AnimationSet(true)
        setAnim.addAnimation(scaleAnimation1)
        setAnim.addAnimation(alphaAnim)
        setAnim.addAnimation(rotateAnim)
        setAnim.addAnimation(transAnim)
        setAnim.duration = 2500
        setAnim.fillAfter = true

        // 添加动画
        animList.add(AnimBean(animScale, "scale anim xml"))
        animList.add(AnimBean(scaleAnimation1, "scale anim code 1"))
        animList.add(AnimBean(scaleAnimation2, "scale anim code 2"))
        animList.add(AnimBean(scaleAnimation3, "scale anim code 3"))
        animList.add(AnimBean(animAlpha, "alpha anim xml"))
        animList.add(AnimBean(alphaAnim, "alpha anim code"))
        animList.add(AnimBean(animTrans, "trans anim xml"))
        animList.add(AnimBean(transAnim, "trans anim code"))
        animList.add(AnimBean(animRotate, "rotate anim xml"))
        animList.add(AnimBean(rotateAnim, "rotate anim code"))
        animList.add(AnimBean(animSet, "set anim xml"))
        animList.add(AnimBean(setAnim, "set anim code"))

        btn_scale_play.setOnClickListener {

            val animBean = animList[count % animList.size]
            tv_scale_anim.startAnimation(animBean.anim)

            animBean.anim.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {
                    btn_scale_play.text = animBean.name
                    tv_scale_anim.text = animBean.name
                }

                override fun onAnimationEnd(animation: Animation?) {
                    btn_scale_play.text = "over..."
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }

            })

            count++
        }
    }

    class AnimBean(val anim: Animation, val name: String)
}