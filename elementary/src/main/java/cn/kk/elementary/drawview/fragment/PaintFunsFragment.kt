package cn.kk.elementary.drawview.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.kk.base.adapter.ListAdapter
import cn.kk.base.bean.ListItemAction
import cn.kk.base.fragment.BaseFragment
import cn.kk.elementary.R

/**
 * Paint 常用函数
 */
class PaintFunsFragment: BaseFragment() {

    val funsArray: MutableList<ListItemAction> by lazy {
       getItemActionList(resources.getStringArray(R.array.paint_common_funs))
    }
    override fun getLayoutId(): Int = R.layout.fragment_paint_funs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val rv_funs = view?.findViewById<RecyclerView>(R.id.rv_funs)
        rv_funs?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_funs?.adapter = ListAdapter(funsArray)
    }
}