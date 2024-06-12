package cn.kk.elementary.chapter1.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

/**
 * Canvas
 * 屏幕显示与 Canvas 关系
 *
 * 1. Canvas 调用 drawXXX() 系列函数时，产生一个全新的 Canvas 透明涂层
 * 2. 如果在调用 drawXXX() 系列函数前，调用平移、旋转对 Canvas 进行了操作，这个操作是不可逆的
 * 3. 在 Canvas 涂层与屏幕合成时，超出屏幕范围的图像是不会显示出来的。
 */
class CanvasAndScreen(context: Context?) : View(context) {
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 声明 2 个画笔
        val paintRed = Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 5f
        }
        val paintGreen = Paint().apply {
            color = Color.GREEN
            style = Paint.Style.STROKE
            strokeWidth = 5f
        }

        // 声明一个矩形
        val rect = Rect(80, 80, 400, 400)

        // 用红色画笔绘制矩形
        canvas.drawRect(rect, paintRed)

        // 平移画布，然后再用绿色画笔绘制矩形
        canvas.translate(100f, 50f)
        canvas.drawRect(rect, paintGreen)
    }
}