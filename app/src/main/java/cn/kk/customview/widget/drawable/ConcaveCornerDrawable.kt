package cn.kk.customview.widget.drawable

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import cn.kk.base.UIHelper


class ConcaveCornerDrawable: Drawable() {

    lateinit var paint: Paint
    private val path = Path()

    init {
        paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        paint.isAntiAlias = true
    }


    override fun draw(canvas: Canvas) {
        val startX = 0f // 起点X坐标
        val startY = 0f // 起点Y坐标
        val width = 900f
        val height = 1200f
        val rNormal = UIHelper.dp2px(30f) // 普通圆角半径
        val rConcave1 = UIHelper.dp2px(25f)
        val rConcave2 = UIHelper.dp2px(35f) // 第二段凹形圆角半径
        val rConcave3 = rNormal // 第三段凹形圆角半径
        val widthOffset = rNormal * 4 + 30 // 缺角宽，不包含左边sub圆弧宽度
        val heightOffset = rNormal * 2 // 缺角高

        // 左上角 (普通圆角)
        path.arcTo(startX, startY, startX + 2 * rNormal, startY + 2 * rNormal, 180f, 90f, true)

        // 顶部直线
        path.lineTo(startX + width - widthOffset - 1 * rConcave1, startY)

        // 右上角 (普通圆角)
//        path.arcTo(startX + width - 2 * rNormal, startY, startX + width, startY + 2 * rNormal, 270f, 90f, true)
        // 右上角凹形arc1
        path.arcTo(startX + width - widthOffset - 2 * rConcave1, startY, startX + width - widthOffset, startY + 2 * rConcave1, 270f, 80f, true)

        // 右上角凹形arc2: 逆时针绘制
        path.arcTo(startX + width - widthOffset, startY + heightOffset - 2 * rConcave2, startX + width - widthOffset + 2 * rConcave2, startY + heightOffset, 170f, -80f, false)
        // arc2 的终点
        canvas.drawPoint(startX + width - widthOffset +  rNormal, startY + 2 * rNormal, Paint().apply {
            color = Color.GREEN
            strokeWidth = 15f
        })
        // 右上角凹形arc3
        path.arcTo(startX + width - 2 * rNormal, startY + heightOffset, startX + width, startY + heightOffset + 2 * rNormal, 270f, 90f, false)


        // right line
        path.moveTo(startX + width, startY + heightOffset + rNormal)
        path.lineTo(startX + width, startY + height - rNormal)

        // 右下角 (普通圆角)
        path.arcTo(startX + width - 2 * rNormal, startY + height - 2 * rNormal, startX + width, startY + height, 0f, 90f, true)

        // bottom line
        path.lineTo(startX + rNormal, startY + height)

        // 左下角 (普通圆角)
        path.arcTo(startX, startY + height - 2 * rNormal, startX + 2 * rNormal, startY + height, 90f, 90f, true)

        // left line
        path.moveTo(startX, startY + height - rNormal)
        path.lineTo(startX, startY + rNormal)
        path.close()

        // 绘制路径
        canvas.drawPath(path, paint)

    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.setColorFilter(colorFilter)
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }
}