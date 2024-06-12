package cn.kk.customview.chapter.c1

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import cn.kk.base.activity.BaseActivity
import cn.kk.base.bean.HtmlWikiModel
import cn.kk.base.utils.AssetsHelper
import cn.kk.customview.R
import cn.kk.customview.adapter.BasicViewViewPagerAdapter
import cn.kk.elementary.chapter1.*
import cn.kk.elementary.chapter1.canvas.CanvasAndScreen
import cn.kk.elementary.chapter1.canvas.CanvasClipView
import cn.kk.elementary.chapter1.canvas.CanvasStateView
import cn.kk.elementary.chapter1.canvas.Trans
import cn.kk.elementary.chapter1.path.BasicArcView
import cn.kk.elementary.chapter1.region.BasicRegion1View
import cn.kk.elementary.chapter1.region.BasicRegion2View
import cn.kk.elementary.chapter1.region.BasicRegion3View

/**
 * 第一章，
 * 显示第一章的自定义 View 视图
 */
class DrawBasicActivity: BaseActivity() {

    override fun getLayout(): Int = R.layout.activity_one

    private var pageIndex = 0

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
//        val tvTitle = findViewById<TextView>(R.id.tv_page_title)
//        tvTitle.text = intent.getStringExtra(INTENT_TITLE_KEY)

        val viewList = mutableListOf<View>()
        viewList.add(BasicBackgroundView(this))
        viewList.add(BasicLineView(this))
        viewList.add(BasicRectView(this))
        viewList.add(BasicCircleView(this))
        viewList.add(BasicCircleRingView(this))
        viewList.add(BasicPointView(this))
        viewList.add(BasicArcView(this))
        viewList.add(BasicRegion1View(this))
        viewList.add(BasicRegion2View(this))
        viewList.add(BasicRegion3View(this))
        viewList.add(Trans(this))
        viewList.add(CanvasAndScreen(this))
        viewList.add(CanvasClipView(this))
        viewList.add(CanvasStateView(this))

        val one_viewpager = findViewById<ViewPager2>(R.id.one_viewpager)
        one_viewpager.apply {
            adapter = BasicViewViewPagerAdapter(viewList)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
        one_viewpager.setCurrentItem(0, true)

        one_viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                pageIndex = position
            }
        })
    }

    override fun showTitleMoreBtn(): Boolean {
        return true
    }

    override fun onTitleMoreClick() {

        when (pageIndex) {
            1 -> showWikiHtmlDialog(mSelf, HtmlWikiModel(AssetsHelper.getHtmlFilePath("basic_line_view.html")).apply { title = "BasicLineView" })
        }
    }
}