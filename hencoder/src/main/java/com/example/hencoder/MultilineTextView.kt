package com.example.hencoder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

class MultilineTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    constructor(context: Context) : this(context, null)

    val avatarWidth = 150f.px
    val avatarPadding = 70f.px
    val text =
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 21f.px
    }
    private val textPaint2 = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 23f.px
    }
    private val avatarPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(avatarWidth.toInt())
    private val fontMetrics = Paint.FontMetrics()


    override fun onDraw(canvas: Canvas?) {

        /*val staticLayout =
            StaticLayout(
                text, textPaint, width, Layout.Alignment.ALIGN_NORMAL,
                1f, 0f, false
            )

        staticLayout.draw(canvas)*/
        canvas?.drawBitmap(bitmap, width - avatarWidth, avatarPadding, avatarPaint)

        textPaint2.getFontMetrics(fontMetrics)
        val measureWidth = floatArrayOf(0f)
        /*//第二个参数：是否往前测量
        var count = textPaint2.breakText(text, true, width.toFloat(), measureWidth)
        canvas?.drawText(text, 0, count, 0f, -fontMetrics.top, textPaint2)

        var oldCount = count;
        count = textPaint2.breakText(text, count, text.length, true, width.toFloat(), measureWidth)
        canvas?.drawText(
            text, oldCount, oldCount + count, 0f,
            -fontMetrics.top + textPaint2.fontSpacing, textPaint2
        )*/

        var start = 0
        var count = 0
        var vertialOffset = -fontMetrics.top
        var maxWidth: Float

        while (start < text.length) {

            //要躲过图片。判断文字的 bottom 碰到了图片的顶部，文字的 top 碰到了图片的底部
            if (vertialOffset + fontMetrics.bottom < avatarPadding ||
                vertialOffset + fontMetrics.top > avatarPadding + avatarWidth
            ) {
                maxWidth = width.toFloat()
            } else {
                maxWidth = width.toFloat() - avatarWidth - 10f.px
            }

            count = textPaint2.breakText(
                text, start, text.length, true,
                maxWidth, measureWidth
            )
            canvas?.drawText(
                text, start, start + count, 0f,
                vertialOffset, textPaint2
            )
            start += count
            vertialOffset += textPaint2.fontSpacing
        }
    }

    private fun getAvatar(width: Int): Bitmap {
        var options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }
}