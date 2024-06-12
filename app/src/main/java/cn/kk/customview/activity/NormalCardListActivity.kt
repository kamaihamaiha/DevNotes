package cn.kk.customview.activity

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import cn.kk.customview.activity.BaseTabActivity
import cn.kk.customview.bean.ItemSimpleCard
import cn.kk.customview.bean.SimpleWikiModel
import cn.kk.customview.ui.work.RecyclerViewDemoActivity
import cn.kk.customview.widget.dialog.NormalWikiBottomDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 通用卡片列表页面
 */
abstract class NormalCardListActivity: BaseActivity() {

    override fun getLayout(): Int {
       return R.layout.activity_normal_list
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        // config adapter
        val rv_list = findViewById<RecyclerView>(R.id.rv_list)
        rv_list.layoutManager = GridLayoutManager(this, getListSpanCount())
        rv_list.adapter = object :  BaseQuickAdapter<ItemSimpleCard, BaseViewHolder>(R.layout.item_card_list, getItemCardList()){
            override fun convert(holder: BaseViewHolder, item: ItemSimpleCard) {
                holder.setText(R.id.tv_name, item.title)
                holder.setVisible(R.id.iv_ok_flag, item.finish)
            }
        }.apply {
            setOnItemClickListener { adapter, view, position ->
                doWhenClickItem(data[position])
                /*val title = data[position].title
                when(data[position].item_action) {
                    ItemSimpleCard.ACTION_MORE_WORK_INTENT_SERIALIZABLE -> {
                        showWikiDialog(SimpleWikiModel(title, getString(R.string.intent_serial_wiki)))
                    }
                    ItemSimpleCard.ACTION_MORE_WORK_REYCYCLER_VIEW -> openNextUI(RecyclerViewDemoActivity::class.java, title, BaseTabActivity.TabType.SystemUI.RECYCLER_VIEW_TYPE)
                }*/
            }
        }
    }

    abstract fun getListSpanCount(): Int

    // todo 抽象 getDataList method
    abstract fun getItemCardList():MutableList<ItemSimpleCard>


    // todo 抽象 item 点击跳转
    abstract fun doWhenClickItem(item: ItemSimpleCard)

    protected fun showWikiDialog(wiki: SimpleWikiModel){
        NormalWikiBottomDialog(mSelf, wiki).show()
    }
}