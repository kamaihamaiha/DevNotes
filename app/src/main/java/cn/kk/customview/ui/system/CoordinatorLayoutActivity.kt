package cn.kk.customview.ui.system

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.*
import androidx.viewpager.widget.ViewPager
import cn.kk.customview.R
import cn.kk.customview.ui.fragment.ThirdFragment
import com.google.android.material.tabs.TabLayout

/**
 * Toolbar:
 *  app:layout_scrollFlags="scroll|enterAlways" 这个很关键，设置这个才能隐藏。设置滚动事件，属性里面必须至少启用 scroll 这个 flag
 *  这样 View 才会滚动出屏幕
 */
class CoordinatorLayoutActivity: AppCompatActivity() {
    lateinit var tabs: TabLayout
    lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener { finish() }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViewPager()
    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.viewPager)
        tabs = findViewById(R.id.tabs)
        val titles = listOf<String>("精选","体育","巴萨","购物","明星","视频","健康","励志","图文","本地")
        titles.forEach {
            tabs.addTab(tabs.newTab().setText(it))
        }
        val fragmentList = mutableListOf<Fragment>()
        for (i in titles.indices){
            fragmentList.add(ThirdFragment())
        }

        val fragmentAdapter = FragmentAdapter(fragmentList, titles, supportFragmentManager)

        viewPager.adapter = fragmentAdapter

        // TabLayout 和 ViewPager 关联起来
        tabs.setupWithViewPager(viewPager)

        // 给 TabLayout 设置适配器
        tabs.setTabsFromPagerAdapter(fragmentAdapter)

    }

    class FragmentAdapter(val fragments: List<Fragment>, val titles: List<String>,
                          fm: FragmentManager
    ): FragmentStatePagerAdapter(fm) {

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

        override fun getCount(): Int {
            return titles.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

    }
}