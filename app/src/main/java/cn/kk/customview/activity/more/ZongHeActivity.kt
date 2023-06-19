package cn.kk.customview.activity.more

import cn.kk.customview.R
import cn.kk.customview.activity.BaseTabActivity
import cn.kk.customview.activity.NormalCardListActivity
import cn.kk.customview.activity.NormalWebViewActivity
import cn.kk.base.bean.BaseItem
import cn.kk.customview.bean.ItemSimpleCard

/**
 * 综合技术 总结
 */
class ZongHeActivity: NormalCardListActivity() {

    override fun getLayout() = R.layout.activity_normal_list

    override fun getListSpanCount() = 2

    override fun getItemCardList(): MutableList<ItemSimpleCard>{
        return mutableListOf<ItemSimpleCard>().apply {
            add(ItemSimpleCard("嵌套滑动，NestedScrollView", true).apply {
                item_action = BaseItem.ACTION_MORE_ZONGHE_NESTEDSCROLLVIEW
                web_url = "https://mp.weixin.qq.com/s/bPrbyt4Fr-S5XTXhk5QO9A"
            })
            add(ItemSimpleCard("Android 插桩之美", true).apply {
                item_action = BaseItem.ACTION_MORE_ZONGHE_chazhuang
                web_url = "https://mp.weixin.qq.com/s/dbseDMO3tqNPtSvBB5UL3Q"
            })
            add(ItemSimpleCard("Mock 神器", true).apply {
                item_action = BaseItem.ACTION_MORE_ZONGHE_mock
                web_url = "https://mp.weixin.qq.com/s/FmGMZ8PMIzA002vF26Terw"
            })
            add(ItemSimpleCard("Compose 和 MVI 结合", true).apply {
                web_url = "https://mp.weixin.qq.com/s/sa8vLJ_3Osh-IqKRnUgfQw"
            })
            add(ItemSimpleCard("子线程更新 UI 全解", true).apply {
                web_url = "https://mp.weixin.qq.com/s/Y9Dqs7eBmoewgwf2e60uOw"
            })
            add(ItemSimpleCard("架构案例学习", true).apply {
                web_url = "https://mp.weixin.qq.com/s/wCoNtt9SWu1pHsWkCMBSxQ"
            })
            add(ItemSimpleCard("Android原生TabLayout使用全解析", true).apply {
                web_url = "https://mp.weixin.qq.com/s/EWPNyGSkLlugRuvOYcHVdQ"
            })
            add(ItemSimpleCard("这真是 App 开发者的神器！", true).apply {
                web_url = "https://mp.weixin.qq.com/s/0RhWfm0n29jDU8kGeLRxKg"
            })

        }
    }

    override fun doWhenClickItem(item: ItemSimpleCard) {
        openNextUI(NormalWebViewActivity::class.java, item.title, BaseTabActivity.TabType.SystemUI.RECYCLER_VIEW_TYPE, item.web_url)
    }

}