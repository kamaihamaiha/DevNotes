package com.example.hencoder.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.hencoder.px


/**
 * 知识点
 * 1. Float 扩展
 * 2. dp to px 转换
 * 3. onDraw()
 * 4. canvas.drawLine()
 * 5. canvas.drawCircle()
 * 6. canvas.drawPath()
 *      1. path 对象需要初始化，在 view 尺寸变化时候操作：重写 onSizeChanged() 方法
 * 7. path
 *      1. addCircle()
 *      2. addRect()
 *      3. path.fillType
 * 8. pathMeasure
 *      1. pathMeasure.length: 获取 path 长度
 *      2. pathMeasure.getPosTan(): 获取指定位置的切角（也就是正切值）
 *
 * ===================== 以下是重点 ===================================
 *
 * ===================== 图形的位置和尺寸测量 ===========================
 * ====== 1. 仪表盘 ======
 *
 */
class SimpleView(context: Context?, attrs: AttributeSet?): View(context) {
    val RADIIUS = 100f.px
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintForPath = Paint()
    private val path = Path()
    lateinit var pathMeasure:PathMeasure

    init {
        paintForPath.color = Color.BLUE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        // 圆心在 view 中心位置，
        // 绘制方向：控制 相交部分的区域是否应该填充，配合 path.fillType 使用(视频 40min 左右)
        // 绘制方向：Path.Direction.CCW（顺时针 counter-clockwise）
        // 绘制方向：Path.Direction.CW（顺时针 clockwise）

        // add circle path
        path.addCircle(width / 2f, height / 2f, RADIIUS, Path.Direction.CCW)

        // add rect path
        path.addRect(width / 2f - RADIIUS, height / 2f, width / 2f + RADIIUS, height / 2f + 2 * RADIIUS, Path.Direction.CW)

        // 默认是 WINDING 规则。类似楼梯螺旋上升
        // Path.FillType.EVEN_ODD 不管方向，根据相交的点是奇数还是偶数，决定是否内内部/外部
        path.fillType = Path.FillType.EVEN_ODD

        // pathMeasure
        pathMeasure = PathMeasure(path, false)
    }

    override fun onDraw(canvas: Canvas) {

        // region 绘制 line
        canvas.drawLine(100f, 100f, 500f, 100f, paint)
        // endregion

        // region 绘制圆
//        canvas.drawCircle(width / 2f, height / 2f , RADIIUS, paint)
        // endregion

        // region 绘制圆，用 drawPath()
        canvas.drawPath(path, paintForPath)
        // endregion

    }
}