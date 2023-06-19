package cn.kk.customview.activity.more

import cn.kk.customview.activity.BaseMixListActivity
import cn.kk.customview.activity.NormalMarkDownViewActivity
import cn.kk.base.bean.BaseItem
import cn.kk.customview.bean.ItemSimpleCard

/**
 * Git 总结
 * 页面说明：
 *  分上下两部分：按照章节的列表、水平滑动的卡片列表
 */
class GitActivity : BaseMixListActivity() {

    override fun getBookAction(): Int= BaseItem.action_book_git


    /**
     * 底部卡片具体信息集合
     */
    override fun getItemCardList(): MutableList<ItemSimpleCard> {
        return mutableListOf<ItemSimpleCard>().apply {
            add(ItemSimpleCard("常见操作", true).apply {
                item_action = BaseItem.ACTION_MORE_GIT_1
                markdown_url = "https://github.com/kamaihamaiha/StudyForGit/blob/master/README.md"
            })
        }
    }

    /**
     * 底部卡片点击跳转
     */
    override fun doWhenClickItem(item: ItemSimpleCard) {
        when (item.item_action) {
            BaseItem.ACTION_MORE_GIT_1 -> {
                openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, item.title, item.markdown_url, item.markdownLocalFlag)
            }

        }
    }

}