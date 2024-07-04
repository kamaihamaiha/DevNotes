package cn.kk.customview.activity.work

import android.view.View
import android.view.ViewGroup
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 * 参考: https://medium.com/@traviswkim/show-an-activity-with-bottomsheet-behavior-1ca2f77285b8
 */
class BottomSheetDialogActivity: BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_bottom_sheet_dialog
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        UIHelper.setStatusBarTextColorDark(this)
        initBottomSheetBehavior()

        // Adjust the height of the BottomSheetDialogActivity
        /*val window = window
        window.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (resources.displayMetrics.heightPixels * 0.75).toInt()
        )*/

        // Set the window background to transparent to see the underlying activ
    }


    private fun initBottomSheetBehavior() {
        val detailContainer = findViewById<View>(R.id.detail_container)
        detailContainer.layoutParams.apply {
//            height = (resources.displayMetrics.heightPixels * 0.75).toInt()
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(detailContainer)
        // Expanded by default
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.skipCollapsed = true
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    finish()
                    //Cancels animation on finish()
                    overridePendingTransition(0, 0)
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }
}