package cn.kk.customview.activity

import androidx.fragment.app.Fragment
import cn.kk.customview.R
import cn.kk.customview.config.UIConfig
import cn.kk.customview.ui.fragment.NormalListFragment
import cn.kk.customview.ui.fragment.NormalTabFragment
import cn.kk.elementary.anim.adapter.BaseFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

open class NormalTabActivity: BaseTabActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_normal_tab
    }

    override fun MutableList<Fragment>.addFragments() {
        when (tabType) {
            // region 动画篇
            TabType.ANIM_TYPE -> {
                var items = resources.getStringArray(R.array.view_anim_types).toMutableList()
                add(NormalListFragment().apply {
                    partType = UIConfig.SubConfigSystem.Anim.ANIM_VIEW
                    addItems(items)
                })
                items = resources.getStringArray(R.array.property_anim_types).toMutableList()
                add(NormalListFragment().apply {
                    partType = UIConfig.SubConfigSystem.Anim.ANIM_PROPERTY
                    addItems(items)
                })
                items = resources.getStringArray(R.array.anim_advance_sections).toMutableList()
                add(NormalListFragment().apply {
                    partType = UIConfig.SubConfigSystem.Anim.ANIM_ADVANCE
                    addItems(items)
                })
            }
            // endregion

            // region 绘图篇
            TabType.DRAW_TYPE -> {
                var items = resources.getStringArray(R.array.view_anim_types).toMutableList()
                // region Paint 基本使用
                add(NormalTabFragment().apply {
                    partType = UIConfig.SubConfigSystem.Draw.PAINT_TYPE
                })
                // endregion

                // region 绘图进阶
                add(NormalTabFragment().apply {
                    partType = UIConfig.SubConfigSystem.Draw.ADVANCE_TYPE
                })
                // endregion
                add(NormalListFragment().apply {
                    partType = UIConfig.SubConfigSystem.Draw.XFERMODE_TYPE
                })
                add(NormalListFragment().apply {
                    partType = UIConfig.SubConfigSystem.Draw.CANVAS_TYPE
                })
                add(NormalListFragment().apply {
                    partType = UIConfig.SubConfigSystem.Draw.ANDROID_CANVAS_TYPE
                })
                add(NormalListFragment().apply {
                    partType = UIConfig.SubConfigSystem.Draw.MATRIX_TYPE
                })
            }

            // endregion

            // region 视图篇
            TabType.VIEW_TYPE -> {
                var items = resources.getStringArray(R.array.view_sections).toMutableList()
                add(NormalListFragment().apply {
                    partType = UIConfig.SubConfigSystem.View.BOX_VIEW
                })
                add(NormalListFragment().apply {
                    partType = UIConfig.SubConfigSystem.View.VIEW_ADVANCE_PROPERTY
                })
            }

            // endregion
        }
    }

}