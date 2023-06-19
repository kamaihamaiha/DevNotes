package cn.kk.customview.activity.more.video

import cn.kk.base.utils.AssetsHelper
import cn.kk.customview.R
import cn.kk.customview.activity.NormalMarkDownViewActivity
import cn.kk.customview.activity.NormalWebViewActivity
import cn.kk.base.bean.BaseItem
import cn.kk.customview.bean.ItemSectionModel
import cn.kk.customview.ui.fragment.BaseChapterListFragment

/**
 * 视频总结首页
 */
class VideoHomeFragment: BaseChapterListFragment() {
    override fun getPartsStringsId(): Int = R.array.video_parts

    override fun getActionBaseType(): Int = BaseItem.ACTION_MORE_VIDEO_BASE

    override fun getSectionDataList(chapterType: Int): MutableList<ItemSectionModel> {
        val sectionModelList = mutableListOf<ItemSectionModel>()
        when (chapterType) {
            BaseItem.ACTION_MORE_VIDEO_start -> {
                val sectionsBasicName = resources.getStringArray(R.array.video_start).toMutableList()
                sectionsBasicName.forEach { sectionModelList.add(ItemSectionModel(it, true).apply { item_action =  chapterType * 10 + (sectionModelList.size + 1)}) }
            }
            BaseItem.ACTION_MORE_VIDEO_basic -> {
                val sectionsName = resources.getStringArray(R.array.video_basic).toMutableList()
                // finish
                sectionsName.forEach { sectionModelList.add(ItemSectionModel(it, true).apply { item_action =  chapterType * 10 + (sectionModelList.size + 1)}) }
            }
            BaseItem.ACTION_MORE_VIDEO_decode -> {
                val sectionsName = resources.getStringArray(R.array.video_decode).toMutableList()
                sectionsName.forEach { sectionModelList.add(ItemSectionModel(it, sectionModelList.size == 0).apply { item_action =  chapterType * 10 + (sectionModelList.size + 1)}) }
            }
            BaseItem.ACTION_MORE_VIDEO_trans_and_net -> {
                val sectionsName = resources.getStringArray(R.array.video_trans_and_net).toMutableList()
                sectionsName.forEach { sectionModelList.add(ItemSectionModel(it, false).apply { item_action =  chapterType * 10 + (sectionModelList.size + 1)}) }
            }
            BaseItem.ACTION_MORE_VIDEO_mix_and_play -> {
                val sectionsName = resources.getStringArray(R.array.video_mix_and_play).toMutableList()
                sectionsName.forEach { sectionModelList.add(ItemSectionModel(it, false).apply { item_action =  chapterType * 10 + (sectionModelList.size + 1)}) }
            }
            BaseItem.ACTION_MORE_VIDEO_end -> {
                val sectionsName = resources.getStringArray(R.array.video_end).toMutableList()
                sectionsName.forEach { sectionModelList.add(ItemSectionModel(it, false).apply { item_action =  chapterType * 10 + (sectionModelList.size + 1)}) }
            }
        }

        return sectionModelList
    }

    override fun onSectionViewClick(item: ItemSectionModel) {
        when(item.item_action) {
            BaseItem.ACTION_MORE_VIDEO_start_1 -> startNextUI(NormalWebViewActivity::class.java, item.title, -1, AssetsHelper.getHtmlFilePath("video_part_1.html"))
            BaseItem.ACTION_MORE_VIDEO_basic_1 -> startNextUI(NormalWebViewActivity::class.java, item.title, -1, AssetsHelper.getHtmlFilePath("video_part_2_1.html"))
            BaseItem.ACTION_MORE_VIDEO_basic_2 -> openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, item.title,  AssetsHelper.getMarkdownFilePath("section_2.md"))
            BaseItem.ACTION_MORE_VIDEO_basic_3 -> openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, item.title,  AssetsHelper.getMarkdownFilePath("section_3.md"))
            BaseItem.ACTION_MORE_VIDEO_decode_1 -> openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, item.title,  AssetsHelper.getMarkdownFilePath("section_4.md"))
        }

    }
}