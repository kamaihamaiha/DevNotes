package com.example.hencoder.touch

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.example.hencoder.R
import com.example.hencoder.px
import kotlin.math.max
import kotlin.math.min

/**
 * 双向滑动
 * 1. 默认是填充满父 view: 因此要计算缩放比例，于是有了 smallScale 和 bitScale
 * 2. 双击缩放：双击 GestureDetectorCompat
 */
private val IMAGE_SIZE = 300f.px.toInt()
private val EXTRA_SCALA_FRACTION = 1.5f

class ScalableImageView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val bitmap = getAvatar(IMAGE_SIZE)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var smallScale = 0f
    private var bigScale = 0f
    private val henGestureListener = HenGestureListener()
    private val henScaleGestureListener = HenScaleGestureListener()


    private val henFlingRunner = HenFlingRunner()
    private val gestureDetectorCompat = GestureDetectorCompat(context, henGestureListener)
    private val scaleGestureDetector = ScaleGestureDetector(context, henScaleGestureListener)
    private var bigFlag = false
    private val scroller = OverScroller(context)
    private var scaleFraction = 0f //放缩比例
        set(value) {
            field = value
            invalidate()
        }

    //手指捏撑比例
    private var currentScale = 0f
        set(value) {
            field = value
            invalidate()
        }
    /*private val scalaAnimator: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
//        ObjectAnimator.ofFloat(this, "currentScale", 0f, 1f)
    }*/

    private val scalaAnimator = ObjectAnimator.ofFloat(
        this, "currentScale"
        , smallScale, bigScale
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (width - IMAGE_SIZE) / 2f
        originalOffsetY = (height - IMAGE_SIZE) / 2f

        if (bitmap.width / bitmap.height.toFloat() > width / height.toFloat()) {
            //如果图片比屏幕胖
            smallScale = width / bitmap.width.toFloat()
            bigScale = height / bitmap.height.toFloat() * EXTRA_SCALA_FRACTION
        } else {
            smallScale = height / bitmap.height.toFloat()
            bigScale = width / bitmap.width.toFloat() * EXTRA_SCALA_FRACTION
        }

        //初始化 currentScale
        currentScale = smallScale
        scalaAnimator.setFloatValues(smallScale, bigScale)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


//        val scale = if (bigFlag) bigScale else smallScale

        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        // val scale = smallScale + (bigScale - smallScale) * scaleFraction
        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 去掉原生功能，使用自己的手势外挂功能
        scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) { //如果没有正在缩放，那么也支持双击
            gestureDetectorCompat.onTouchEvent(event)
        }
        return true
    }

    fun getAvatar(width: Int): Bitmap {
        var options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }


    private fun fixOffsets() {
        offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)

        offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
        offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)
    }

    //整理实现的接口，统一管理
    inner class HenGestureListener : SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onScroll(
            downEvent: MotionEvent?,
            currentEvent: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            //手指移动时触发. distanceX 旧位置 - 新位置。返回值不影响结果
            if (bigFlag) {
                offsetX -= distanceX
                offsetY -= distanceY

                fixOffsets()
                invalidate()

                //这个安全，推荐用这个
//            ViewCompat.postOnAnimation(this, this)
            }
            return false
        }


        override fun onFling(
            downEvent: MotionEvent?,
            currentEvent: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            //快滑。velocityX / velocityY 滑动速度

            if (bigFlag) {

                scroller.fling(
                    offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    (-(bitmap.width * bigScale - width) / 2).toInt(),
                    ((bitmap.width * bigScale - width) / 2).toInt(),
                    (-(bitmap.height * bigScale - height) / 2).toInt(),
                    ((bitmap.height * bigScale - height) / 2).toInt(),
                    60f.px.toInt(), 60f.px.toInt()
                )

                //把后面的this 推到主线程执行，从下面的一帧开始
//                postOnAnimation(this)
                ViewCompat.postOnAnimation(this@ScalableImageView, henFlingRunner)
                //把后面 this 推到祝线程执行，立刻执行，更快一些，一帧会执行多次
                // post(this)
            }
            return false
        }


        override fun onDoubleTap(e: MotionEvent): Boolean {
            //双击
            bigFlag = !bigFlag
            if (bigFlag) {
                //应该从手指双击处为中心开始偏移，重新计算偏移
                offsetX = (e.x - width / 2) * (1 - bigScale / smallScale)
                offsetY = (e.y - height / 2) * (1 - bigScale / smallScale)

                //修正，超出图片范围，不算双击
                fixOffsets()

                scalaAnimator.start()
            } else {
                scalaAnimator.reverse()

            }
            //重新绘制
//        invalidate()
            return true
        }
    }

    inner class HenFlingRunner : Runnable {
        override fun run() {
            if (scroller.computeScrollOffset()) { //如果动画还在进行中
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
//            postOnAnimation(this)
                ViewCompat.postOnAnimation(this@ScalableImageView, this)
            }
        }

    }

    inner class HenScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            //应该从手指捏和处为中心开始偏移，重新计算偏移
            offsetX = (detector.focusX - width / 2) * (1 - bigScale / smallScale)
            offsetY = (detector.focusY - height / 2) * (1 - bigScale / smallScale)
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {

        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            //放缩系数
            currentScale *= detector.scaleFactor

            val tempCurrentScale = currentScale
            //限定范围
            currentScale = currentScale.coerceAtLeast(smallScale).coerceAtMost(bigScale)

            //当滑动没有超过范围，才返回 true，让 detector.scaleFactor 更新
            return tempCurrentScale === currentScale
        }

    }
}