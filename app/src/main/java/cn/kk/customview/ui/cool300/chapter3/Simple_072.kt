package cn.kk.customview.ui.cool300.chapter3

import android.view.*
import android.view.Menu
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R

/**
 * 长时间按住控件时弹出上下文菜单
 */
class Simple_072: BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.simple_072
    }


    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        val iv_bg = findViewById<ImageView>(R.id.iv_bg)
        registerForContextMenu(iv_bg)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        // 用 add 或者扩充一个定义在 XML 中的菜单资源，用 getMenuInflater() 获取
        menu?.add(0, 1, 0, "one")
        menu?.add(0, 2, 0, "two")
        menu?.add(0, 3, 0, "three")
        menu?.add(0, 4, 0, "four")
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            1 -> UIHelper.toast("one", this)
            2 -> UIHelper.toast("two", this)
            3 -> UIHelper.toast("three", this)
            4 -> UIHelper.toast("four", this)
        }
        return super.onContextItemSelected(item)
    }

    override fun onContextMenuClosed(menu: Menu) {
        super.onContextMenuClosed(menu)
    }

}