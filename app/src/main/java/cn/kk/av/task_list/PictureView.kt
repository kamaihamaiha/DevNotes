package cn.kk.av.task_list

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import cn.kk.customview.R

/**
 * 自定义 View 绘制 bitmap
 */
class PictureView(context: Context, attributes: AttributeSet?): View(context, attributes) {

    constructor(context: Context): this(context, null)

    val paint: Paint
    val bitmap: Bitmap

    init {
        paint = Paint()
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.cat)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }
}