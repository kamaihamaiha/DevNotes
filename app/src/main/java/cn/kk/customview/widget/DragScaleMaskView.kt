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
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import cn.kk.base.UIHelper

/**
 * 1. edit mode: show text tips and four corner rectangles
 * 2. drag: move the view
 */
class DragScaleMaskView(context: Context, attributeSet: AttributeSet?): AppCompatTextView(context, attributeSet) {

    constructor(context: Context): this(context, null)

    // start point
    val startPoint = Pair(0f, 0f)
    // range/size
    val MIN_WIDTH = UIHelper.dp2px(90f)
    val MIN_HEIGHT = UIHelper.dp2px(60f)
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
    var initLayout = true


    init {
        tipContext = "遮挡区域可拖动，拖拽四个角可以调整大小"
        setPadding(padding, padding, padding, padding)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed && initLayout) {
            val parentWidth = (parent as? View)?.width ?: 0
            val parentHeight = (parent as? View)?.height ?: 0

            // 计算 TextView 的宽度和高度
            val textViewWidth = right - left
            val textViewHeight = bottom - top

            // 计算新的位置：底部水平居中
            val newLeft = (parentWidth - textViewWidth) / 2
            val newTop = parentHeight - textViewHeight

            // 设置 TextView 的位置
            layout(newLeft, newTop, newLeft + textViewWidth, newTop + textViewHeight)
            (layoutParams as FrameLayout.LayoutParams).apply {
                leftMargin = newLeft
                topMargin = newTop
            }
            initLayout = false
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(bgColor)

        if (editMode) {
            drawCorner(canvas)
//            setText(tipContext)
        } else {
//            setText("")
        }

    }


    private var lastX = 0f
    private var lastY = 0f
    private val SCALE_MODE_NONE = -1
    private val SCALE_MODE_LT = 0
    private val SCALE_MODE_RT = 1
    private val SCALE_MODE_LB = 2
    private val SCALE_MODE_RB = 3

    private var scaleMode = SCALE_MODE_NONE
    private var upTimeStamp = 0L
    private val SHOW_EDIT_MODEL_DURATION = 1500L // 2s
    private val MIN_SCALE_DISTANCE = cornerSize.first * 1.5
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return super.onTouchEvent(event)

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX
                lastY = event.rawY

                // 判断是否点击到了四个角
                val leftTopPoint = intArrayOf(0,0)
                getLocationOnScreen(leftTopPoint)

                val rightTopPoint = intArrayOf(leftTopPoint[0] + width, leftTopPoint[1])
                val leftBottomPoint = intArrayOf(leftTopPoint[0], leftTopPoint[1] + height)
                val rightBottomPoint = intArrayOf(leftTopPoint[0] + width, leftTopPoint[1] + height)

                val distanceLT = Math.sqrt(Math.pow((lastX - leftTopPoint[0]).toDouble(), 2.0) + Math.pow((lastY - leftTopPoint[1]).toDouble(), 2.0))
                val distanceRT = Math.sqrt(Math.pow((lastX - rightTopPoint[0]).toDouble(), 2.0) + Math.pow((lastY - rightTopPoint[1]).toDouble(), 2.0))
                val distanceLB = Math.sqrt(Math.pow((lastX - leftBottomPoint[0]).toDouble(), 2.0) + Math.pow((lastY - leftBottomPoint[1]).toDouble(), 2.0))
                val distanceRB = Math.sqrt(Math.pow((lastX - rightBottomPoint[0]).toDouble(), 2.0) + Math.pow((lastY - rightBottomPoint[1]).toDouble(), 2.0))
                // find the min distance
                if (distanceLT < MIN_SCALE_DISTANCE) {
                    scaleMode = SCALE_MODE_LT
                } else if (distanceRT < MIN_SCALE_DISTANCE) {
                    scaleMode = SCALE_MODE_RT
                } else if (distanceLB < MIN_SCALE_DISTANCE) {
                    scaleMode = SCALE_MODE_LB
                } else if (distanceRB < MIN_SCALE_DISTANCE) {
                    scaleMode = SCALE_MODE_RB
                } else {
                    scaleMode = SCALE_MODE_NONE
                }


                editMode = true
                Log.d("DragScale--", "onTouchEvent: scaleMode: $scaleMode")

                setText(tipContext)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                var dx = event.rawX - lastX
                var dy = event.rawY - lastY

                // range limit
                if (left + dx < 0 ) {
                    dx = -left.toFloat() // 纠正变化量
                }
                if (top + dy < 0) {
                    dy = -top.toFloat() // 纠正变化量
                }
                if (right + dx > (parent as ViewGroup).width) {
                    dx = (parent as ViewGroup).width - right.toFloat() // 纠正变化量
                }
                if (bottom + dy > (parent as ViewGroup).height) {
                    dy = (parent as ViewGroup).height - bottom.toFloat() // 纠正变化量
                }

                // 更新 View 的位置
                val layoutParams = layoutParams as FrameLayout.LayoutParams
                if (scaleMode == SCALE_MODE_NONE) {
                    layoutParams.leftMargin += dx.toInt()
                    layoutParams.topMargin += dy.toInt()
                } else {
                    when(scaleMode) {
                        SCALE_MODE_LT -> {
                            layoutParams.width -= dx.toInt()
                            layoutParams.height -= dy.toInt()

                            layoutParams.leftMargin += dx.toInt()
                            layoutParams.topMargin += dy.toInt()

                        }
                        SCALE_MODE_RT -> {
                            layoutParams.width += dx.toInt()
                            layoutParams.height -= dy.toInt()
                            layoutParams.topMargin += dy.toInt()
                        }
                        SCALE_MODE_LB -> {
                            layoutParams.width -= dx.toInt()
                            layoutParams.height += dy.toInt()
                            layoutParams.leftMargin += dx.toInt()
                        }
                        SCALE_MODE_RB -> {
                            layoutParams.width += dx.toInt()
                            layoutParams.height += dy.toInt()
                        }
                    }

                    // size limit
                    layoutParams.width = Math.max(layoutParams.width, MIN_WIDTH.toInt())
                    layoutParams.height = Math.max(layoutParams.height, MIN_HEIGHT.toInt())
                }


                Log.d("DragScale--", "onTouchEvent: left: ${left}, top: ${top}, right: ${right}, bottom: ${bottom}")
                Log.d("DragScale--", "onTouchEvent: leftMargin: ${layoutParams.leftMargin}, topMargin: ${layoutParams.topMargin}, rightMargin: ${layoutParams.rightMargin}, bottomMargin: ${layoutParams.bottomMargin}")

                requestLayout()

                lastX = event.rawX
                lastY = event.rawY
            }
            MotionEvent.ACTION_UP -> {
                editMode = false
                upTimeStamp = System.currentTimeMillis()
                Handler(Looper.getMainLooper()).postDelayed({
                    if (editMode) return@postDelayed
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