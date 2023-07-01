package cn.kk.customview.adapter

import cn.kk.base.bean.ChapterModel
import cn.kk.customview.R
import cn.kk.customview.bean.ItemChapterModel
import cn.kk.customview.bean.ItemSectionModel
import cn.kk.customview.widget.ItemFolderView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlin.math.exp

/**
 * 简单条款适配器
 */
class SimpleNodeAdapter(chapterData: ChapterModel): BaseQuickAdapter<ChapterModel.NodeModel, BaseViewHolder>(R.layout.item_list_card, chapterData.nodeModelList) {

    override fun convert(holder: BaseViewHolder, item: ChapterModel.NodeModel) {
        holder.setText(R.id.tv_item_home_name, item.title.plus("（${item.index}）"))
    }

}