package cn.kk.customview.activity

import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.View
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.base.receiver.ScreenChangeReceiver
import cn.kk.base.utils.IOUtils
import cn.kk.customview.R
import cn.kk.customview.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * 首页
 */
class HomeTabActivity: BaseActivity() {
    private val fragmentList = mutableMapOf<Int, Fragment>()
    private var lastTabId = -1
    val mScreenChangeReceiver by lazy {
        ScreenChangeReceiver()
    }

    override fun getLayout(): Int {
        return R.layout.activity_home_tab
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        // edge to edge
        val home_root_view = findViewById<View>(R.id.home_root_view)
        val bottom_navi = findViewById<BottomNavigationView>(R.id.bottom_navi)
        ViewCompat.setOnApplyWindowInsetsListener(home_root_view, object : OnApplyWindowInsetsListener {
            override fun onApplyWindowInsets(
                v: View,
                windowInsets: WindowInsetsCompat
            ): WindowInsetsCompat {
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.updatePadding(top = -1 * insets.top)
                bottom_navi.updatePadding(bottom = insets.bottom)
               return WindowInsetsCompat.CONSUMED
            }
        })

        bottom_navi.setOnItemSelectedListener {
            Log.d(TAG, "doWhenOnCreate: 0")
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            // 先隐藏上一个 fragment
            if (lastTabId != -1 && lastTabId != it.itemId) {
                val lastFragment = getFragment(lastTabId)
                fragmentTransaction.hide(lastFragment)
                fragmentTransaction.setMaxLifecycle(lastFragment, Lifecycle.State.STARTED)
            }

            val curFragment = getFragment(it.itemId)
            val fragmentTag = it.itemId.toString()
            if (!curFragment.isAdded || supportFragmentManager.findFragmentByTag(fragmentTag) == null) {
                fragmentTransaction.add(R.id.fragment_container ,curFragment, fragmentTag)
             } else {
                fragmentTransaction.show(curFragment)
            }
            lastTabId = it.itemId
            fragmentTransaction.setMaxLifecycle(curFragment, Lifecycle.State.RESUMED)
            fragmentTransaction.commitAllowingStateLoss()
            Log.d(TAG, "doWhenOnCreate: fragmentId: ${it.itemId}")
            return@setOnItemSelectedListener true
        }

        // 默认选中第一个 tab 页面
        bottom_navi.selectedItemId = R.id.navigation_tab_book

        IOUtils.write2SDCard("hello android!")

        // register screen on/off receiver
         val intentFilter = IntentFilter()
         intentFilter.addAction(Intent.ACTION_SCREEN_ON)
         intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
         registerReceiver(mScreenChangeReceiver, intentFilter)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mScreenChangeReceiver)
    }
   private fun getFragment(id: Int): Fragment {
        if (!fragmentList.containsKey(id)) {
            val existFragment = supportFragmentManager.findFragmentByTag(id.toString())
            if (existFragment != null) {
                fragmentList.put(id, existFragment)
                return existFragment
            }

            when (id) {
                R.id.navigation_tab_views -> fragmentList.put(id, ViewHomeFragment())
                R.id.navigation_tab_player -> fragmentList.put(id, PlayerFragment())
                R.id.navigation_tab_arch -> fragmentList.put(id, ArchFragment())
                R.id.navigation_tab_book -> fragmentList.put(id, BookListFragment())
                R.id.navigation_tab_more -> fragmentList.put(id, MoreFragment())
            }
        }
        if (id == R.id.navigation_tab_player) {
            UIHelper.setStatusBarDark(mSelf)
        } else {
            UIHelper.setStatusBarTrans(mSelf)
        }
       return fragmentList.get(id)!!
    }
}