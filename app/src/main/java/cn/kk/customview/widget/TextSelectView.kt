package cn.kk.customview.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ActionMode
import android.view.ContextMenu
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView

/**
 * 相关回答
 * https://stackoverflow.com/questions/62102838/textview-how-to-prevent-displaying-the-action-menu-when-selecting-text-in-textv
 */
class TextSelectView(context: Context, attributeSet: AttributeSet?): AppCompatTextView(context, attributeSet) {
    private val gestureDetector: GestureDetector

    init {
        setTextIsSelectable(true)
        isClickable = true
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent) {
                // 处理长按事件
                performLongClick()
            }
        })
    }

    override fun onCreateContextMenu(menu: ContextMenu?) {
        menu?.clear()
    }

    override fun onCheckIsTextEditor(): Boolean {
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
        /*if (event?.actionMasked == MotionEvent.ACTION_DOWN && isTextSelectable) {
            performLongClick()
            // 消耗事件，防止显示默认操作菜单
            return true
        }
        return super.onTouchEvent(event)*/
    }

    override fun performLongClick(): Boolean {
        // 移除默认的操作菜单
        return super.performLongClick().also { removeCallbacks { showContextMenu() } }
    }
}