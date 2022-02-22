package cn.kk.customview.ui

import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import cn.kk.customview.ui.hencoder.GraphicLocationAndSizeMeasureActivity
import cn.kk.customview.ui.hencoder.touch.TouchFeedBackActivity
import kotlinx.android.synthetic.main.activity_system_ui.*

/**
 * HenCoder 课程学习整理
 */
class HenCoderActivity: BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_hencoder
    }

    override fun setListViewID(): Int {
        return R.id.rv_list
    }

    override fun getItemNameList(): MutableList<String> {
        return mutableListOf<String>().apply {
            add("图形的位置和尺寸测量")
            add("Xfermode 完全使用解析")
            add("文字的测量")
            add("范围裁剪和几何变换")
            add("属性动画和硬件加速")
            add("Bitmap 和 Drawable")
            add("手写 MaterialEditText")
            add("布局流程完全解析")
            add("尺寸的自定义")
            add("Layout 的自定义")
            add("绘制流程源码解析")
            add("触摸反馈系列")
        }
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        initAdapter()

    }
     override fun initAdapter() {
         super.initAdapter()
        listAdapter.setOnItemClickListener { adapter, view, position ->
            when (itemList[position]) {
                itemList[0] -> openNextUI(GraphicLocationAndSizeMeasureActivity::class.java, itemList[position])
                itemList[11] -> openNextUI(TouchFeedBackActivity::class.java, itemList[position])
            }
        }
        rv_list.adapter = listAdapter
    }
}