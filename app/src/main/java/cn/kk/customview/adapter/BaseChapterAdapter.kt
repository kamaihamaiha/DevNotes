package cn.kk.customview.adapter

import cn.kk.customview.R
import cn.kk.customview.bean.ItemChapterModel
import cn.kk.customview.bean.ItemSectionModel
import cn.kk.customview.widget.ItemFolderView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlin.math.exp

/**
 * 通用章节适配器
 */
class BaseChapterAdapter(chapterData: MutableList<ItemChapterModel>): BaseQuickAdapter<ItemChapterModel, BaseViewHolder>(R.layout.item_chapter_view, chapterData) {
    var mItemSectionClickListener: OnItemSectionClickListener ?= null
    var expandIndex = 0 // 默认展开第一个

    override fun convert(holder: BaseViewHolder, item: ItemChapterModel) {
        val chapterView = holder.getView<ItemFolderView>(R.id.folder_view)
        if (holder.layoutPosition == expandIndex) {
            chapterView.setData(item, true)
        } else {
            chapterView.setData(item)
        }

        chapterView.sectionClickListener = object : ItemFolderView.SectionClickListener {
            override fun onSectionClick(itemSection: ItemSectionModel) {
                mItemSectionClickListener?.onSectionClick(item.bookType ,itemSection)
            }

        }
    }

    interface OnItemSectionClickListener {

        fun onSectionClick(bookType: Int,item: ItemSectionModel)
    }
}