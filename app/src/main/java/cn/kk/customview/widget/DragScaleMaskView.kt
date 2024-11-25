package cn.kk.customview.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import cn.kk.base.UIHelper
import kotlin.math.log

/**
 * 1. edit mode: show text tips and four corner rectangles
 * 2. drag: move the view
 */
class DragScaleMaskView(context: Context, attributeSet: AttributeSet?): AppCompatTextView(context, attributeSet) {

    constructor(context: Context): this(context, null)

    // start point
    val startPoint = Pair(0f, 0f)
    // range/size
    val MIN_WIDTH = UIHelper.dp2px(80f)
    val MIN_HEIGHT = UIHelper.dp2px(50f)
    val padding = UIHelper.dp2px(10f).toInt()

    // 默认的遮挡区域大小
    val range = Pair(UIHelper.dp2px(200f), UIHelper.dp2px(60f))
    val cornerSize = Pair(UIHelper.dp2px(15f), UIHelper.dp2px(15f))
    var editMode = false
    var tipContext = ""
    var bgColor = Color.parseColor("#80000000")
    val bgPaint = Paint().apply {
        color = bgColor
    }
    val cornerPaint = Paint().apply {
        color = Color.parseColor("#FF0000")
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 10f
    }


    init {
        tipContext = "遮挡区域可拖动，拖拽四个角可以调整大小"
        setPadding(padding, padding, padding, padding)
        /*val layoutParams = layoutParams as FrameLayout.LayoutParams
        layoutParams.width = range.first.toInt()
        layoutParams.height = range.second.toInt()
        setLayoutParams(layoutParams)*/
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(bgColor)

        if (isDragging) {
            drawCorner(canvas)
//            setText(tipContext)
        } else {
//            setText("")
        }

    }


    private var lastX = 0f
    private var lastY = 0f
    private var isDragging = false
    private var upTimeStamp = 0L
    private val SHOW_EDIT_MODEL_DURATION = 1500L // 2s
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return super.onTouchEvent(event)

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX
                lastY = event.rawY
                isDragging = true
                setText(tipContext)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                if (isDragging) {
                    val dx = event.rawX - lastX
                    val dy = event.rawY - lastY

                    if (left + dx < 0) return true
                    if (top + dy < 0) return true
                    if (right + dx > (parent as ViewGroup).width) return true
                    if (bottom + dy > (parent as ViewGroup).height) return true

                    // 更新 View 的位置
                    val layoutParams = layoutParams as FrameLayout.LayoutParams
                    layoutParams.leftMargin += dx.toInt()
                    layoutParams.topMargin += dy.toInt()

                    Log.d("DragScale--", "onTouchEvent: left: ${left}, top: ${top}, right: ${right}, bottom: ${bottom}")
                    Log.d("DragScale--", "onTouchEvent: leftMargin: ${layoutParams.leftMargin}, topMargin: ${layoutParams.topMargin}, rightMargin: ${layoutParams.rightMargin}, bottomMargin: ${layoutParams.bottomMargin}")

                    requestLayout()

                    lastX = event.rawX
                    lastY = event.rawY

                }
            }
            MotionEvent.ACTION_UP -> {
                isDragging = false
                upTimeStamp = System.currentTimeMillis()
                Handler(Looper.getMainLooper()).postDelayed({
                    if (isDragging) return@postDelayed
                    if (System.currentTimeMillis() - upTimeStamp < SHOW_EDIT_MODEL_DURATION) return@postDelayed
                    setText("")
                    invalidate()
                }, SHOW_EDIT_MODEL_DURATION)
            }
        }
        return true
    }

    fun drawCorner(canvas: Canvas) {
        // left top
        val leftTopX = startPoint.first
        val leftTopY = startPoint.second
        canvas.drawLine(leftTopX, leftTopY, leftTopX + cornerSize.first, leftTopY, cornerPaint)
        canvas.drawLine(leftTopX, leftTopY, leftTopX, leftTopY + cornerSize.second, cornerPaint)

        // right top
        val rightTopX = startPoint.first + width
        val rightTopY = startPoint.second
        canvas.drawLine(rightTopX - cornerSize.first, rightTopY, rightTopX, rightTopY, cornerPaint)
        canvas.drawLine(rightTopX, rightTopY, rightTopX, rightTopY + cornerSize.second, cornerPaint)

        // left bottom
        val leftBottomX = startPoint.first
        val leftBottomY = startPoint.second + height
        canvas.drawLine(leftBottomX, leftBottomY - cornerSize.second, leftBottomX, leftBottomY, cornerPaint)
        canvas.drawLine(leftBottomX, leftBottomY, leftBottomX + cornerSize.first, leftBottomY, cornerPaint)

        // right bottom
        val rightBottomX = startPoint.first + width
        val rightBottomY = startPoint.second + height
        canvas.drawLine(rightBottomX - cornerSize.first, rightBottomY, rightBottomX, rightBottomY, cornerPaint)
        canvas.drawLine(rightBottomX, rightBottomY - cornerSize.second, rightBottomX, rightBottomY, cornerPaint)
    }
}