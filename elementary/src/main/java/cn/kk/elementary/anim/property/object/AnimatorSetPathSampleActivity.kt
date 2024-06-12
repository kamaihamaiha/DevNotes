package cn.kk.elementary.anim.property.`object`

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.widget.Button
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import cn.kk.base.activity.BaseActivity
import cn.kk.elementary.R
import kotlin.math.cos
import kotlin.math.sin

/**
 * 3.5.5 示例：路径动画
 *
 * 1. 计算角度
 * 2. 计算新的位置坐标
 * 3. 计算动画
 */
class AnimatorSetPathSampleActivity: BaseActivity() {

    var btnItemIsOPen = false
    val radius = 600
    lateinit var btnItem1: Button
    lateinit var btnItem2: Button
    lateinit var btnItem3: Button

    override fun getLayout(): Int = R.layout.activity_animator_set_path_sample

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        // region 1. 计算角度
        // endregion

        // region 2. 计算新的位置坐标
        // endregion

        // region 3. 计算动画

        // endregion

         btnItem1 = findViewById<Button>(R.id.btn_item1)
         btnItem2 = findViewById<Button>(R.id.btn_item2)
         btnItem3 = findViewById<Button>(R.id.btn_item3)



        findViewById<Button>(R.id.btn_menu).setOnClickListener {
            if (btnItemIsOPen){
                closeMenu()
            } else {
                openMenu()
            }
        }

        btnItem1.setOnClickListener {
            showToast("ok")
            if (btnItemIsOPen){
                closeMenu()
            }
        }
        btnItem2.setOnClickListener {
            showToast("add")
            if (btnItemIsOPen){
                closeMenu()
            }
        }
        btnItem3.setOnClickListener {
            showToast("alert")
            if (btnItemIsOPen){
                closeMenu()
            }
        }

    }

    fun openMenu(){
        openAnim(btnItem1, 0, 3, radius)
        openAnim(btnItem2, 1, 3, radius)
        openAnim(btnItem3, 2, 3, radius)
        btnItemIsOPen = true
    }

    fun closeMenu(){
        closeAnim(btnItem1, 0, 3, radius)
        closeAnim(btnItem2, 1, 3, radius)
        closeAnim(btnItem3, 2, 3, radius)

        btnItemIsOPen = false
    }

    /**
     * 获取角度
     */
    fun getDegree(index: Int, total: Int): Double{
        return (90 - 90 / (total - 1) * index).toDouble()
    }

    /**
     * 计算新的位置坐标 x 轴
     */
    fun getOpenPointX(degree: Double, radius: Int): Float{
      return (sin(Math.toRadians(degree)) * radius).toFloat() * -1
    }

    /**
     * 计算新的位置坐标 y 轴
     */
    fun getOpenPointY(degree: Double, radius: Int): Float{
       return (cos(Math.toRadians(degree)) * radius).toFloat() * -1
    }

    fun openAnim(target: View, index: Int, total: Int, radius: Int){
        target.visibility = View.VISIBLE
        // 动画：渐变 + 平移 + 缩放
        val transX = getOpenPointX(getDegree(index, total), radius)
        val transY = getOpenPointY(getDegree(index, total), radius)

        val alphaAnim = ObjectAnimator.ofFloat(target, "alpha", 0f, 1.0f)
        val transXAnim = ObjectAnimator.ofFloat(target, "translationX", 0f, transX)
        val transYAnim = ObjectAnimator.ofFloat(target, "translationY", 0f, transY)
        val scaleXAnim = ObjectAnimator.ofFloat(target, "scaleX", 0f, 1f)
        val scaleYAnim = ObjectAnimator.ofFloat(target, "scaleY", 0f, 1f)

        val animSet = AnimatorSet().apply {
            playTogether(alphaAnim, transXAnim, transYAnim, scaleXAnim, scaleYAnim)
            duration = 800
        }

        animSet.start()
    }

    fun closeAnim(target: View, index: Int, total: Int, radius: Int){
        // 动画：渐变 + 平移 + 缩放
        val transX = getOpenPointX(getDegree(index, total), radius)
        val transY = getOpenPointY(getDegree(index, total), radius)

        val alphaAnim = ObjectAnimator.ofFloat(target, "alpha", 1.0f, 0f)
        val transXAnim = ObjectAnimator.ofFloat(target, "translationX",  transX, 0f)
        val transYAnim = ObjectAnimator.ofFloat(target, "translationY",  transY, 0f)
        val scaleXAnim = ObjectAnimator.ofFloat(target, "scaleX", 1f, 0f)
        val scaleYAnim = ObjectAnimator.ofFloat(target, "scaleY", 1f, 0f)

        val animSet = AnimatorSet().apply {
            playTogether(alphaAnim, transXAnim, transYAnim, scaleXAnim, scaleYAnim)
            duration = 300
            addListener {
                doOnEnd {
                    target.visibility = View.GONE
                }
            }
        }

        animSet.start()
    }
}