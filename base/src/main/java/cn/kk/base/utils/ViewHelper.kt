package cn.kk.base.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import cn.kk.base.UIHelper


/**
 * 视图帮助类
 */
object ViewHelper {

    /**
     * 给控件设置背景，效果是两边是半圆
     * @param target 目标 view
     * @param color 背景 shape 颜色
     */
    fun setShapeDualSemicircle(target: View, color: Int, alpha: Float){
        val correctAlpha = Math.min(Math.max(0f, alpha), 1.0f)
        target.post {
            target.background = GradientDrawable().apply {
                cornerRadius = target.measuredHeight / 2f
                val blue: Float = Color.blue(color).toFloat()
                val red: Float = Color.red(color).toFloat()
                val green: Float = Color.green(color).toFloat()
                var aColor = -1
                (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    Color.argb(correctAlpha, red, green, blue) else {
                    val alphaInt = (correctAlpha * 255.0f + 0.5f).toInt()
                    color and 0x00ffffff or (alphaInt shl 24)
                }).also { aColor = it }
                setColor(aColor)
            }

        }
    }

    /**
     * 显示图片
     * 1. 图片宽度充满屏幕
     * 2. 高度若不够，则上下留白
     * 3. 高度若超过控件高度，则从顶部开始显示，下面的则不再显示了
     */
    fun showImg(imgDrawableId: Int, activity: Activity, targetView: ImageView){
        val MAX_RADIO = 1.2f // 最大高宽比

        // 1. 获取图片尺寸
        val bitmap = BitmapFactory.decodeResource(activity.resources, imgDrawableId)
        val imageAspectRadio = bitmap.height * 1.0f / bitmap.width

        // 2. 计算把图片等比例缩放到宽度与控件宽度相同时，的尺寸

        // 3. 如果缩放后的高度超过控件高度，那么裁剪
        var finalBitmap = bitmap
        if (imageAspectRadio > MAX_RADIO) {
            // 计算裁剪部分大小
            val cropHeight = bitmap.width * MAX_RADIO
            finalBitmap =  Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, cropHeight.toInt())

            // 控件尺寸要调整
            (targetView.layoutParams as LinearLayout.LayoutParams).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    height = (UIHelper.getScreenSize(activity).x * MAX_RADIO).toInt()
                }
            }
        }

        // 4. 显示在控件上面
        targetView.setImageBitmap(finalBitmap)
    }

}