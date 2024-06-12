package cn.kk.customview.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import cn.kk.customview.utils.px

/**
 * 画线
 * 画圆形
 *
 */
class TestView(context: Context?, attributeSet: AttributeSet?) : View(context, attributeSet) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paint2 = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    val RADIUS = 30f.px
    val RADIUS_MINI = 20f.px

    init {
        paint2.color = Color.RED
        paint2.strokeWidth = 4f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        path.addCircle(300f, 500f, RADIUS_MINI, Path.Direction.CCW)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //draw line
        canvas.drawLine(10f, 100f, 500f, 100f, paint)

        //draw circle
        canvas.drawCircle(300f, 300f, RADIUS, paint2)

        //draw path
        canvas.drawPath(path, paint2)

    }
}