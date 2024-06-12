package cn.kk.customview.activity

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import cn.kk.elementary.anim.adapter.BaseFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

abstract class BaseTabActivity: BaseActivity() {

    protected var tabType = -1

    protected val tabsName by lazy {
        getItemNamesByType()
    }
    override fun getItemNamesByType(): MutableList<String> {
        when (tabType) {
            TabType.ANIM_TYPE -> {
              return resources.getStringArray(R.array.anim_items).toMutableList()
            }
            TabType.DRAW_TYPE -> {
                return resources.getStringArray(R.array.draw_sections).toMutableList()
            }
            TabType.VIEW_TYPE -> {
                return resources.getStringArray(R.array.view_sections).toMutableList()
            }

            // region SystemUI Type
            TabType.SystemUI.RECYCLER_VIEW_TYPE -> return resources.getStringArray(R.array.recycler_view_types).toMutableList()
            TabType.SystemUI.MENU_TYPE -> return resources.getStringArray(R.array.menu_types).toMutableList()
            // endregion

            // region audio
            TabType.Audio.AUDIO_BASIC_TYPE -> return resources.getStringArray(R.array.audio_basic_types).toMutableList()
            // endregion
            else -> {
            }
        }
        return super.getItemNamesByType()
    }



    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        tabType = intent.getIntExtra(INTENT_TYPE_KEY, -1)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabs = findViewById<TabLayout>(R.id.tabs)
        viewPager.adapter = BaseFragmentAdapter(this, mutableListOf<Fragment>().apply {
            addFragments()
        })

        TabLayoutMediator(tabs, viewPager, true, object: TabLayoutMediator.TabConfigurationStrategy {
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = tabsName[position]
            }

        }).attach()
    }

    override fun initAdapter() {
        // nothing...
    }

    abstract fun MutableList<Fragment>.addFragments()

    /**
     * 用于 TabActivity
     */
  open class TabType {
        companion object {
            // 动画篇
            val ANIM_TYPE = 1
            // 绘图篇
            val DRAW_TYPE = 2
            // 视图篇
            val VIEW_TYPE = 3
        }

         class SystemUI {
            companion object {

                val RECYCLER_VIEW_TYPE = 10
                val MENU_TYPE = 11

                // sub menu type
                val menu_option = 111
                val menu_bottom = 112
                val menu_context = 113
                val menu_popup_window = 114
                val menu_action_more = 115


            }
        }

        class Audio {
            companion object {
                val AUDIO_TYPE = 17

                // sub audio type
                val AUDIO_BASIC_TYPE = 171
            }
        }
    }
}