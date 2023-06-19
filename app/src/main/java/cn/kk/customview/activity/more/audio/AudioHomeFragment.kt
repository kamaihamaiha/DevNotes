package cn.kk.customview.activity.more.audio

import cn.kk.base.utils.AssetsHelper
import cn.kk.customview.R
import cn.kk.customview.activity.NormalWebViewActivity
import cn.kk.base.bean.BaseItem
import cn.kk.customview.bean.ItemSectionModel
import cn.kk.customview.ui.fragment.BaseChapterListFragment

/**
 * 音频总结首页
 */
class AudioHomeFragment: BaseChapterListFragment() {
    override fun getPartsStringsId(): Int = R.array.audio_parts

    override fun getActionBaseType(): Int = BaseItem.ACTION_MORE_AUDIO_BASE

    override fun getSectionDataList(chapterType: Int): MutableList<ItemSectionModel> {
        val sectionModelList = mutableListOf<ItemSectionModel>()
        when (chapterType) {
            BaseItem.ACTION_MORE_AUDIO_BASIC -> {
                val sectionsBasicName = resources.getStringArray(R.array.audio_basic_types).toMutableList()
                sectionsBasicName.forEach { sectionModelList.add(ItemSectionModel(it, true).apply { item_action =  chapterType * 10 + (sectionModelList.size + 1)}) }
            }
            BaseItem.ACTION_MORE_AUDIO_DENOISE -> {
                val sectionsName = resources.getStringArray(R.array.audio_denoise).toMutableList()
                sectionsName.forEach { sectionModelList.add(ItemSectionModel(it, false).apply { item_action =  chapterType * 10 + (sectionModelList.size + 1)}) }
            }
            BaseItem.ACTION_MORE_AUDIO_REMOVE_ECHO -> {
                val sectionsName = resources.getStringArray(R.array.audio_remove_echo).toMutableList()
                sectionsName.forEach { sectionModelList.add(ItemSectionModel(it, false).apply { item_action =  chapterType * 10 + (sectionModelList.size + 1)}) }
            }
            BaseItem.ACTION_MORE_AUDIO_NET_TRANS -> {
                val sectionsName = resources.getStringArray(R.array.audio_net_trans).toMutableList()
                sectionsName.forEach { sectionModelList.add(ItemSectionModel(it, false).apply { item_action =  chapterType * 10 + (sectionModelList.size + 1)}) }
            }
            BaseItem.ACTION_MORE_AUDIO_SPACE -> {
                val sectionsName = resources.getStringArray(R.array.audio_space).toMutableList()
                sectionsName.forEach { sectionModelList.add(ItemSectionModel(it, false).apply { item_action =  chapterType * 10 + (sectionModelList.size + 1)}) }
            }
            BaseItem.ACTION_MORE_AUDIO_SPECIAL_EFFECT -> {
                val sectionsName = resources.getStringArray(R.array.audio_special_effect).toMutableList()
                sectionsName.forEach { sectionModelList.add(ItemSectionModel(it, false).apply { item_action =  chapterType * 10 + (sectionModelList.size + 1)}) }
            }
            BaseItem.ACTION_MORE_AUDIO_END -> {
                val sectionsName = resources.getStringArray(R.array.audio_end).toMutableList()
                sectionsName.forEach { sectionModelList.add(ItemSectionModel(it, false).apply { item_action =  chapterType * 10 + (sectionModelList.size + 1)}) }
            }
        }

        return sectionModelList
    }

    override fun onSectionViewClick(item: ItemSectionModel) {
        when(item.item_action) {
            BaseItem.ACTION_MORE_AUDIO_BASIC_1 -> startNextUI(AudioBasicActivity::class.java, item.title, AudioBasicActivity.SECTION_1)
            BaseItem.ACTION_MORE_AUDIO_BASIC_2 -> startNextUI(AudioBasicActivity::class.java, item.title, AudioBasicActivity.SECTION_2)
            BaseItem.ACTION_MORE_AUDIO_BASIC_3 -> startNextUI(AudioBasicActivity::class.java, item.title, AudioBasicActivity.SECTION_3)
            BaseItem.ACTION_MORE_AUDIO_BASIC_4 -> startNextUI(NormalWebViewActivity::class.java, item.title, -1, AssetsHelper.getHtmlFilePath("section_4.html"))
        }
    }
}