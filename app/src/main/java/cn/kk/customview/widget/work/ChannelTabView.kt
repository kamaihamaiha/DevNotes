package cn.kk.customview.widget.work

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import cn.kk.base.UIHelper
import cn.kk.customview.R
import com.example.hencoder.dp

/**
 * 流式布局
 * todo
 * 1. 可指定行数 ok
 * 2. 指定最大行数 ok
 *    换行有两种效果：
 *      1. 第一行和第二行摆放的 tab 数量大致相当(手机、pad 分屏时)
 *      2. 第一行摆满，剩下的摆第二行(宽度足够大时，也就是 pad（全屏） 下)
 * 3. 分状态：选中、非选中 ok
 * 4. 点击后刷新选中 item ok
 * 5. 选中变化监听 ok
 * 6. 根据宽度重新排布
 */
class ChannelTabView(ctx: Context, attrs: AttributeSet): ViewGroup(ctx, attrs) {
    private val TAG = "ChannelTabView--"

    val LINE_MODE_PHONE = 0 // 第一种换行效果
    val LINE_MODE_PAD = 1 // 第二种换行效果

    val tab_count = 5
    var maxLineCount = 2 // 默认2行
    // 多行模式，目前有两种效果
    var multiLineMode = LINE_MODE_PHONE
    var curSelectedTabIndex = 0

    val TAB_VIEW_HEIGHT = dp2pxInt(30f)
    val TEXT_SZIE_OF_NORMAL_WORD = 15f
    val TAB_INTERVAL_MARGIN = dp2px(12f).toInt() // tab item view 左右间距
    val TAB_INTERVAL_MARGIN_VERTICAL = dp2px(10f).toInt() // tab item view 上下间距

    var visibleWidth: Int

    val tabNames = arrayListOf<String>()
    var mTabItemSelectedChangeListener : TabItemSelectedChangeListener ?= null

    init {
        // 可见区域的宽度，这里默认用屏幕宽度。根据实际情况定
        visibleWidth = UIHelper.getScreenWidth(context as Activity)
        for (i in 1 until tab_count + 1) {
            tabNames.add(String.format("星期 · %d", i))
        }

//        createTabViews()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var containerWidth = paddingLeft // 父容器宽
        var containerHeight = paddingTop // 父容器高

        var lineHeight = 0  // 记录每一行高度
        var lineWidth = 0  // 记录每一行宽度

        var totalTabViewWidth = 0 // 一行摆开，tab item views 总宽度，包括间隔
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility != View.GONE) {
                measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            } else {
                continue
            }

            // 拿到测量的宽和高
            val childW = childView.measuredWidth
            val childH = childView.measuredHeight

            totalTabViewWidth += childW
            if (i != childCount - 1) { // 不是最后一个，后面再加上 tab item view 间距
                totalTabViewWidth += TAB_INTERVAL_MARGIN
            }
        }


        Log.d(TAG, "onMeasure: totalTabViewWidth: ${totalTabViewWidth}, visibleWidth: ${visibleWidth}")
        if (totalTabViewWidth <= visibleWidth) { // 一行
            maxLineCount = 1
            containerWidth = totalTabViewWidth + paddingStart + paddingEnd
        } else { // 2行,
            // 如果 2行的 visibleWidth 都摆不开，则只能支持 LINE_MODE_PHONE 模式
            maxLineCount = 2
            if (totalTabViewWidth > maxLineCount * visibleWidth) {
                multiLineMode = LINE_MODE_PHONE
            }
            if (multiLineMode == LINE_MODE_PHONE) { // 粗略计算一行的大致宽度，也就是父容器的宽度
                containerWidth = totalTabViewWidth / maxLineCount
                // 计算实际需要的一行宽度，也就是容器总宽度
                var tempWidth = 0
                for(i in 0 until childCount) {
                    val curChildWidth = getChildAt(i).measuredWidth
                    tempWidth += curChildWidth
                    if (tempWidth >= containerWidth) { // 满足条件后，containerWidth 就是刚好超过总 tabs 宽度一半的 width
                        containerWidth = tempWidth + paddingStart + paddingEnd
                        break
                    }
                }
            } else if (multiLineMode == LINE_MODE_PAD) {
                containerWidth = visibleWidth
            }


        }

        // 单行高度 * 行数 + 行间距 + paddingTop + paddingBottom
        containerHeight = getChildAt(0).measuredHeight * maxLineCount + (maxLineCount - 1) * TAB_INTERVAL_MARGIN_VERTICAL + paddingTop + paddingBottom
        // 测量完成后，设置给系统
        setMeasuredDimension(containerWidth, containerHeight)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val containerWidth = r - l
        var lineHeight = 0
        var childLeft = paddingLeft
        var childTop = paddingTop

        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility == View.GONE) {
                continue
            }
            val childWidth = childView.measuredWidth
            val childHeight = childView.measuredHeight

            lineHeight = Math.max(childHeight, lineHeight)
            if (childLeft + childWidth + paddingRight > containerWidth) { // 换行
                childLeft = paddingLeft
                childTop += TAB_INTERVAL_MARGIN_VERTICAL + lineHeight
                lineHeight = childHeight
            }

            // 摆放 tab item view
            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)

            // 更新 childLeft
            childLeft += (childWidth + TAB_INTERVAL_MARGIN)
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    // region create tab item view
    fun createTabViews(){

        var tabIndex = 0
        tabNames.forEach {
            val tab = TextView(context).apply {
                height = TAB_VIEW_HEIGHT
                textSize = TEXT_SZIE_OF_NORMAL_WORD
                text = it
                gravity = Gravity.CENTER
                tag = tabIndex
                isSelected = tabIndex == curSelectedTabIndex
                setTextColor(ContextCompat.getColor(context, if(tabIndex == curSelectedTabIndex) R.color.colorPrimary else R.color.grey_2))
                setBackgroundResource(R.drawable.bg_channel_tab_selector)
                setPadding(10.dp.toInt(), 0, 10.dp.toInt(), 0)
                setOnClickListener {
                    // 点击后刷新状态
                    if (isSelected) return@setOnClickListener
                    isSelected = true
                    setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))

                    // 取消之前选中的 tab view
                    cancelSelectedTabView(tag as Int)

                    mTabItemSelectedChangeListener?.onSelectedChange(tag as Int)
                }
            }

            addView(tab)
            tabIndex++
        }

    }

    private fun cancelSelectedTabView(tabIndex: Int) {
        if (getChildAt(curSelectedTabIndex)  == null) {
            UIHelper.toast("tab view at ${curSelectedTabIndex} is null", context)
            return
        }
        (getChildAt(curSelectedTabIndex) as TextView).apply {
            isSelected = false
            setTextColor(ContextCompat.getColor(context, R.color.grey_2))
        }

        curSelectedTabIndex = tabIndex
    }

    fun changeWidth(width: Int) {
        visibleWidth = width
        requestLayout()
    }


    // endregion

    // region util method
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
    }
    // endregion

    interface TabItemSelectedChangeListener {

        fun onSelectedChange(selectedIndex: Int)
    }
}