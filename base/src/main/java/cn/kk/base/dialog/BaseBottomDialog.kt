package cn.kk.base.dialog

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import cn.kk.base.UIHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

abstract class BaseBottomDialog(context: Context, theme: Int): BottomSheetDialog(context, theme) {

    constructor(context: Activity):this(context, 0){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        expand()

        addBehaviorStateListener()
    }

    /**
     * 展开 dialog
     */
    private fun expand() {
        if (UIHelper.isLandscape(context)) {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            // 横屏时，peekHeight 高度设置大点，为了下滑容易收起来
            behavior.peekHeight = 800
        }
    }

    /**
     * 防止出现往下滑动时，出现折叠状态
     */
    private fun addBehaviorStateListener() {
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })


    }
}