package cn.kk.elementary.chapter1.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import cn.kk.elementary.R

/**
 * Canvas 变换
 * translate(dx: Float, dy: Float): 平移后，坐标系（绘制的区域）跟随画布变换。
 */
class Trans(context: Context?) : View(context) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint().apply {
            color = Color.RED
        }

        // 绘制矩形
        val left = 0f
        val top = 0f
        val right = 300f
        val bottom = 200f
        canvas.drawRect(left, top, right, bottom, paint)

        // 平移画布，再绘制.
        canvas.translate(400f, 200f)
        canvas.drawRect(left, top, right, bottom, paint)

    }
}