package cn.kk.customview.ui.system

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import cn.kk.base.UIHelper
import cn.kk.customview.R

/**
 * 沉浸式模式
 * [郭霖博客文章](https://mp.weixin.qq.com/s/CjFUKHIz6bQZaKw46Lj7VQ)
 */
class ImmersiveModeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_immersive_mode)
        // 两个 Flag 结合使用，表示让应用主题内容占用系统状态栏的空间
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        // 设置系统状态栏颜色为透明
        window.statusBarColor = Color.TRANSPARENT
        val view_status_bar = findViewById<View>(R.id.view_status_bar)
        view_status_bar.setOnApplyWindowInsetsListener { v, insets ->
            val barHeight = insets.systemWindowInsetTop // status bar height
            view_status_bar.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
                height = barHeight + 44 *3
            }
            view_status_bar.setPadding(0, barHeight, 0, 0)


            insets
        }

        val iv_avatar = findViewById<ImageView>(R.id.iv_avatar)
        val anim1 = ObjectAnimator.ofFloat(iv_avatar, "rotationY", -10f, 10f)
        val anim2 = ObjectAnimator.ofFloat(iv_avatar, "rotationY", 10f, -10f)

        val animSet = AnimatorSet().apply {
            playSequentially(anim1, anim2)
            duration = 1500
        }

        animSet.addListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                animSet.start()
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }

        })
        animSet.start()

    }
}