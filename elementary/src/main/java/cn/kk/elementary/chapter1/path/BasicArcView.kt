package cn.kk.elementary.chapter1.path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 绘制弧线
 * arcTo()
 */
class BasicArcView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    constructor(context: Context?): this(context,null)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // region 绘制第一种
        val path = Path()
        path.moveTo(100f,100f)

        //矩形，用来限定弧的范围
        val rectF = RectF(200f,200f,400f,400f)

        // 弧：0 -> 90, 默认将 path move 的起点也链接上了
        path.arcTo(rectF,0f,90f)

        // 绘制 rectF 矩形区域范围，用来参考画的弧
        canvas.drawRect(rectF,Paint().apply {
            color = Color.GRAY
            style = Paint.Style.STROKE
            strokeWidth = 4f
        })

        // 绘制弧，
        canvas.drawPath(path, Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 5f
        })


        // region 绘制第二种
        val arc2StartX = 700f
        val arc2StartY = 200f
        val arc2Radius = 100f
        canvas.drawPoint(arc2StartX, arc2StartY, Paint().apply {
            color = Color.GREEN
            strokeWidth = 15f
        })
        val path2 = Path()
        path2.moveTo(arc2StartX,arc2StartY)
        val rectF2 = RectF(arc2StartX,arc2StartY,arc2StartX + arc2Radius*2,arc2StartY + arc2Radius*2)

        // forceMoveTo = true，不连接 path 的起点和弧的起点
        path2.arcTo(rectF2,0f,90f,true)

        // 绘制 rectF 矩形区域范围，用来参考画的弧
        canvas.drawRect(rectF2,Paint().apply {
            color = Color.GRAY
            style = Paint.Style.STROKE
            strokeWidth = 4f
        })

        // 绘制含有弧线的 path2
        canvas.drawPath(path2, Paint().apply {
            color = Color.GREEN
            style = Paint.Style.STROKE
            strokeWidth = 5f
        })
    }
}