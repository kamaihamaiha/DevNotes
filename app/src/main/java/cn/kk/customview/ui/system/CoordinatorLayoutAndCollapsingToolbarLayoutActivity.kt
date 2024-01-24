package cn.kk.customview.ui.system

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cn.kk.base.adapter.CommentAdapter
import cn.kk.base.bean.CommentModel
import cn.kk.customview.R
import com.google.android.material.appbar.CollapsingToolbarLayout

/**
 * 1. CollapsingToolbarLayout 把 ImageView 和 Toolbar 包裹起来，作为一个可折叠的 Toolbar
 * 2. 再用 AppBarLayout 把 CollapsingToolbarLayout 包裹起来，作为一个 Appbar 的整体
 * 3. AppBarLayout 必须是第一个嵌套在 CoordinatorLayout 里面的子 view
 * 4. 需要定义 AppBarLayout 与滚动视图 RecyclerView 之间的联系，Design Support Library 包含了一个特殊的字符串资源 @string/appbar_scrolling_view_behavior
 *    它和 AppBarLayout.ScrollingViewBehavior 相匹配，用来通知 AppBarLayout 何时发生来滚动事件。这个 Behavior
 *    需要设置在触发事件的 View 之上，所以应该在 RecyclerView 或者任意支持嵌套滚动的 View 比如 NestedScrollView
 *    上添加 app:layout_behavior="@string/appbar_scrolling_view_behavior" 这个属性。当然 AppBarLayout 中
 *    子 View 需要设置 app:layout_scrollFlags 这个属性，否则就算接收到 RecyclerView 滚动事件，AppBarLayout 也
 *    不会有什么变化。
 *
 * CollapsingToolbarLayout 关键属性：
 * 1. app:contentScrim="" 用来设置 CollapsingToolbarLayout 收缩后最顶部的颜色
 * 2. app:expandedTitleGravity="left|bottom" 表示将此 CollapsingToolbarLayout 完全展开后，title 所处的位置，默认值为 left + bottom
 * 3. app:expandedTitleGravity="left|bottom" 表示当头部的衬图 ImageView 消失后，此 title 将回归到 Toolbar 的位置，默认为 left
 * 4. app:layout_scrollFlags="scroll|exitUntilCollapsed" 用来设置滚动事件
 *
 */
class CoordinatorLayoutAndCollapsingToolbarLayoutActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_collapsing_toolbar)

        setStatusBarTrans()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val collapsing_toolbar = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        val rv_list = findViewById<RecyclerView>(R.id.rv_list)

        collapsing_toolbar.title = "Dragon Ball"

        val names = mutableListOf("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S")
        val modelList = mutableListOf<CommentModel>()
        var index = 0
        for (name in names) {
            modelList.add(CommentModel(name, "", "content".plus(name), System.currentTimeMillis() + index * 7200, index / 2 == 0 ))
            index ++
        }
        rv_list.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            itemAnimator = DefaultItemAnimator()
            adapter = CommentAdapter(modelList, context)
        }

    }

    /**
     * 设置状态栏透明，且独自占用空间
     */
    private fun setStatusBarTrans(){
        // 表示让应用主题内容占据系统状态栏的空间
        val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.decorView.systemUiVisibility = option

        // 状态栏透明
        window.statusBarColor = Color.TRANSPARENT
    }
}