package cn.kk.customview.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import cn.kk.customview.R


open class ExpandView : ViewGroup {

    // 展开状态，默认不展开
    var explainState = false
    val MAXLINES_BEFORE_EXPLAIN = 3
    val textView by lazy {
        TextView(context).apply {
            maxLines = maxLinesBeforeExplain
            setBackgroundColor(bgColor)
            setTextColor(textViewColor)
            textSize = textViewSize.toFloat()
        }
    }
    val imageButton by lazy {
        ImageButton(context).apply {
            setImageResource(R.drawable.icon_expand)

            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            params.height = dp2pxInt(16f)
            layoutParams = params
            setPadding(dp2pxInt(15f), 0, 0, 0)

            setBackgroundColor(bgColor)
        }
    }

    private var textViewSize: Int = 0
    private var textViewColor = Color.BLACK
    private var bgColor = Color.WHITE
    private var maxLinesBeforeExplain = 0

    constructor(context: Context?) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ExplainView)

        maxLinesBeforeExplain = typedArray.getInteger(
            R.styleable.ExplainView_max_collapse_lines,
            MAXLINES_BEFORE_EXPLAIN
        )
        val textViewSizePx = typedArray.getInteger(R.styleable.ExplainView_text_size, dp2pxInt(14f))
        textViewColor = typedArray.getColor(R.styleable.ExplainView_text_color, Color.BLACK)
        bgColor = typedArray.getColor(R.styleable.ExplainView_bg_color, Color.WHITE)


        textViewSize = px2sp(textViewSizePx.toFloat())
        typedArray.recycle()

        addView(textView)
        addView(imageButton.apply {
//            background = null
            setOnClickListener {
                // 点击事件

                if (explainState) {
                    textView.maxLines = maxLinesBeforeExplain
                    setImageResource(R.drawable.icon_expand)
                } else {
                    textView.maxLines = textView.lineCount
                    setImageResource(R.drawable.icon_collapse)
                }
                explainState = !explainState

            }
        })
        addView(View(context).apply {
            setBackgroundColor(Color.RED)
        })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measureWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeightSize = MeasureSpec.getSize(heightMeasureSpec)
        val measureWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val measureHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        // 这句很重要
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        var containerWidth = 0
        var containerHeight = 0

        for (index in 0 until childCount) {
            val childView = getChildAt(index)
            if (childView is TextView) {
                // 如果是 TextView，那么父控件的尺寸就和该子 view 尺寸一样
                containerWidth = childView.measuredWidth + paddingLeft + paddingRight
                containerHeight = childView.measuredHeight + paddingTop + paddingBottom

            } else {

            }
        }

        val finalWidth =
            if (measureWidthMode == MeasureSpec.EXACTLY) measureWidthSize else containerWidth
        val finalHeight =
            if (measureHeightMode == MeasureSpec.EXACTLY) measureHeightSize else containerHeight

        // 行数不超过限制，就不限时
        if (textView.lineCount <= maxLinesBeforeExplain) {
            imageButton.visibility = View.GONE
        } else {

        }
        setMeasuredDimension(finalWidth, finalHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        for (index in 0 until childCount) {
            val childView = getChildAt(index)
            if (childView is TextView) {
                childView.layout(
                    l + paddingLeft ,
                    t + paddingTop ,
                    r - paddingRight ,
                    b - paddingBottom
                )
            } else  if (childView is ImageButton){
                val left = r - childView.measuredWidth - paddingRight - childView.paddingLeft
                val top = b - childView.measuredHeight - paddingBottom
                childView.layout(
                    left,
                    top,
                    r - paddingRight,
                    b - paddingBottom
                )
            } else if (childView is View){
                childView.layout(
                    left,
                    top,
                    50,
                    50
                )
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        explainState = textView.lineCount <= textView.maxLines

        if (!explainState) {
            val lineEnd = textView.layout.getLineEnd(maxLinesBeforeExplain - 1)

            val subSequence = textView.text.toString().subSequence(0, lineEnd)
        }
    }

    fun setData(data: String) {
        textView.text = data
    }

    companion object {
        fun dp2px(value: Float): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                Resources.getSystem().displayMetrics
            )
        }

        fun dp2pxInt(value: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                Resources.getSystem().displayMetrics
            ).toInt()
        }

        fun px2sp(value: Float): Int {
            return (value / Resources.getSystem().displayMetrics.scaledDensity).toInt()
        }
    }
}