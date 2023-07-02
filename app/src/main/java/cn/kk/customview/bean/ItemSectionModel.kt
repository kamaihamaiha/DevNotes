package cn.kk.customview.bean

import cn.kk.base.bean.BaseItem
import cn.kk.base.bean.NodeModel
import cn.kk.base.bean.SectionDetailModel
import cn.kk.base.utils.AssetsHelper
import cn.kk.customview.factory.BookModelFactory
import java.io.Serializable

class ItemSectionModel(title: String): BaseItem(title,Type.TYPE_SECTION), Serializable {

    constructor(title: String, finishTag: Boolean): this(title) {
        super.finishTag = finishTag
    }

    constructor(title: String,  chapterAction: Int = 0, sectionAction: Int = 0, finishTag: Boolean = false):this(title) {
        super.chapter_action = chapterAction
        super.section_action = sectionAction
        super.finishTag = finishTag
    }

    fun getMarkdownFileUrl(bookType: Int): String {
       return AssetsHelper.getMarkdownURL(bookType , this)
    }

    fun getDetailModel(): SectionDetailModel {
        return BookModelFactory.getSectionDetailModel(data_source)
    }
}