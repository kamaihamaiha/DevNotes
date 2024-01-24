package cn.kk.base.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.core.animation.addListener

object AnimHelper {

    fun hideView(view: View?, hide: Boolean) {
        if (view == null) return
        val transAnim = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            if (hide) 0f else 1f,
            Animation.RELATIVE_TO_SELF,
            if (hide) 1f else 0f
        ).apply {
            duration = 500
            fillAfter = true
        }
        view.startAnimation(transAnim)
    }

    fun hideViewByObjectAnim(view: View?, hide: Boolean){
        if (view == null) return
        val anim = ObjectAnimator.ofFloat(view, "translationY", if(hide) 0f else view.height.toFloat(), if (hide) view.height.toFloat() else 0f)
        anim.duration = 500
        anim.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator) {
                if (!hide) {
                    view.visibility = View.VISIBLE
                }
            }

            override fun onAnimationEnd(animation: Animator) {
                if (hide) {
                    view.visibility = View.GONE
                }
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }

        })
        anim.start()
    }
}