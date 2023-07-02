package cn.kk.customview.adapter

import cn.kk.base.bean.NodeModel
import cn.kk.base.bean.SectionDetailModel
import cn.kk.customview.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 简单条款适配器
 */
class SimpleNodeAdapter(chapterData: SectionDetailModel): BaseQuickAdapter<NodeModel, BaseViewHolder>(R.layout.item_list_card, chapterData.nodeModelList) {

    override fun convert(holder: BaseViewHolder, item: NodeModel) {
        holder.setText(R.id.tv_item_home_name, item.title.plus("（${item.index}）"))
    }

}