package cn.kk.elementary.anim.property.`object`

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.widget.Button
import android.widget.ImageView
import cn.kk.base.activity.BaseActivity
import cn.kk.elementary.R

/**
 * 3.5.2 AnimatorSet.Builder
 * play()
 * with()
 * before()
 * after()
 */
class AnimatorSetBuilderActivity: BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_animator_set_listener

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        val circle1 = findViewById<ImageView>(R.id.circle1)
        val circle2 = findViewById<ImageView>(R.id.circle2)
        val circle3 = findViewById<ImageView>(R.id.circle3)
        // region 1. 定义多个动画
        val animCircle1Trans = ObjectAnimator.ofFloat(circle1, "translationY", 0f, 1200f)
        val animCircle2Trans = ObjectAnimator.ofFloat(circle2, "translationY", 0f, 1200f)
        val animCircle3Trans = ObjectAnimator.ofFloat(circle3, "translationY", 0f, 1200f)


        findViewById<Button>(R.id.btn_play).setOnClickListener {
            val animatorSet = AnimatorSet()

            // animCircle1Trans 和 animCircle3Trans 一起执行，然后再执行 animCircle2Trans
            animatorSet.
            play(animCircle1Trans)
                .with(animCircle3Trans)
                .before(animCircle2Trans)

            animatorSet.start()
        }
    }
}