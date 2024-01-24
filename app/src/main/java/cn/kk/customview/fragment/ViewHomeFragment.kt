package cn.kk.customview.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import cn.kk.base.fragment.BaseFragment
import cn.kk.customview.R
import cn.kk.customview.config.UIConfig
import cn.kk.customview.ui.cool300.Cool300HomeFragment
import cn.kk.customview.ui.fragment.NormalListFragment
import cn.kk.elementary.anim.adapter.BaseFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewHomeFragment: BaseFragment() {
    val tabs_name = arrayOf("系统学习","系统UI","炫酷应用 300例","第三方UI","Hencoder", "work" )

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_view
    }

    lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.viewPager)
        viewPager.adapter = BaseFragmentAdapter(requireActivity(), mutableListOf<Fragment>().apply {
            // add fragment ui
            add(NormalListFragment().apply {
                partType = UIConfig.PART_SYSTEM_STUDY
                addItem("绘图基础", true)
                addItem("动画篇", true)
                addItem("绘图篇", true)
                addItem("视图篇")
            })

            // region 系统UI
            add(NormalListFragment().apply {
                partType = UIConfig.PART_SYSTEM_UI
                addItem("ImageView", true)
                addItem("Dialog", true)
                addItem("ImmersiveMode", true)
                addItem("CoordinatorLayout", true)
                addItem("CoordinatorLayout & CollapsingToolbarLayout", true)
                addItem("Custom Behavior")
                addItem("Status Bar", true)
                addItem("Line Height", true)
                addItem("Material Design", true)
                addItem("约束布局- TextView 长度可变", true)
                addItem("TextView with drawable", true)
                addItem("忽略系统大字体", true)
                addItem("Html Text", true)
                addItem("桌面小组件", true)
                addItem("Drawable", true)
                addItem("RecyclerView 用法", true)
                addItem("EditText 用法", true)
            })
            // endregion

            // region cool-300
//            add((NormalTabFragment().apply { partType = UIConfig.PART_COOL_300 }))
            add(Cool300HomeFragment())
            // endregion

            add(NormalListFragment().apply {
                partType = UIConfig.PART_THIRD
                addItem("LottieAnim", true)
            })

            // region Hencoder
            add(NormalListFragment().apply {
                partType = UIConfig.PART_HENCODER
                addItem("图形的位置和尺寸测量 136min", true)
                addItem("Xfermode 完全使用解析 43min", true)
                addItem("文字的测量 99min", true)
                addItem("范围裁剪和几何变换 63min", true)
                addItem("属性动画和硬件加速 127min")
                addItem("Bitmap 和 Drawable 54min", true)
                addItem("手写 MaterialEditText 76min")
                addItem("布局流程完全解析 35min")
                addItem("尺寸的自定义 40min")
                addItem("Layout 的自定义 65min")
                addItem("绘制流程源码解析 81min")
                addItem("触摸反馈系列", true)
            })
            // endregion

            // region work
            add(NormalListFragment().apply {
                partType = UIConfig.PART_WORK
                addItem("时间水平进度条", true)
                addItem("闪烁按钮", true)
                addItem("渐变图片", true)
                addItem("区间拖拽条", true)
                addItem("拼写组件", false)
                addItem("日历打卡组件", true)
                addItem("RecyclerView 用法", true)
                addItem("EditText/TextView 高亮文本", true)
                addItem("TextView 跑马灯效果", true)
                addItem("EditText 滚动时焦点不跳", true)
                addItem("TextView 日期+斜线", true)
                addItem("channel tab view", true)
                addItem("webView opera logs to client", true)
                addItem("save log to internal storage tab view", true)
                addItem("WebView 滚动截图", true)
                addItem("打开系统通知设置", true)
                addItem("蓝牙键盘状态", true)
                addItem("修改 App 图标", true)
            })
            // endregion
        })

        // 默认定位到最后一个 tab 页
        viewPager.currentItem = (viewPager.adapter as BaseFragmentAdapter).itemCount - 1

        // bind tabLayout and viewPager
        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        TabLayoutMediator(tabs, viewPager, true, object: TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = tabs_name[position]
            }

        }).attach()
    }
}