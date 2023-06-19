package cn.kk.customview.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cn.kk.base.fragment.BaseFragment
import cn.kk.customview.R
import cn.kk.customview.adapter.BaseChapterAdapter
import cn.kk.customview.bean.ItemChapterModel
import cn.kk.customview.bean.ItemSectionModel
import kotlinx.android.synthetic.main.chapter_list_layout.*

/**
 * 以章节列表形式显示(基类)
 */
abstract class BaseChapterListFragment: BaseFragment() {

    override fun getLayoutId(): Int = R.layout.normal_chapter_list_without_title_layout

    private val parts by lazy {
        resources.getStringArray(getPartsStringsId()).toMutableList()
    }

    private val baseItemActionValue by lazy {
        getActionBaseType()
    }

    /**
     * 该模块分成具体的章节，string array 资源表示
     */
    abstract fun getPartsStringsId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rv_chapter_list.layoutManager = LinearLayoutManager(context)
        val chapterModelList = mutableListOf<ItemChapterModel>()

        for (part in parts) { // 添加章节信息
            val size = chapterModelList.size
            chapterModelList.add(ItemChapterModel(part, getSectionDataList(baseItemActionValue + size + 1)))
        }
        rv_chapter_list.adapter = BaseChapterAdapter(chapterModelList).apply {
            mItemSectionClickListener = object : BaseChapterAdapter.OnItemSectionClickListener {
                override fun onSectionClick(bookType: Int, item: ItemSectionModel) {
                    onSectionViewClick(item)
                }

            }
        }
    }

    /**
     * 模块基本类型 type
     */
    abstract fun getActionBaseType(): Int

    /**
     * @param chapterType: 章节类型
     */
    abstract fun getSectionDataList(chapterType: Int): MutableList<ItemSectionModel>

    /**
     * 具体篇被点击了
     */
    abstract fun onSectionViewClick(item: ItemSectionModel)

}