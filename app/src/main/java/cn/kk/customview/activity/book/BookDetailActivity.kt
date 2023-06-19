package cn.kk.customview.activity.book

import android.os.Bundle
import androidx.fragment.app.Fragment
import cn.kk.customview.activity.BaseFragmentActivity
import cn.kk.base.bean.BaseItem
import cn.kk.customview.bean.BookModel
import cn.kk.customview.dialog.MoreInfoListBottomDialog
import cn.kk.customview.fragment.BookDetailFragment
import cn.kk.customview.fragment.BookDetailFragmentV2

/**
 * Book 详情页面 - Activity
 */
class BookDetailActivity : BaseFragmentActivity() {

    val bookModel: BookModel by lazy {
        intent.getSerializableExtra(INTENT_MODEL_KEY) as BookModel
    }

    override fun getFragment(): Fragment =
        when (bookModel.itemAction) {
            // 二级列表，ExpandableListView
            BaseItem.ACTION_BOOK_TEMP -> BookDetailFragmentV2()
            else -> {
                // 二级列表，使用两层 RecyclerView 实现
                BookDetailFragment().apply {
                    if (bookModel.moreItemList != null) {
                        showTitleMoreBtnIcon()
                    }
                    arguments = Bundle().apply { putSerializable(INTENT_MODEL_KEY, bookModel) }
                }
            }

        }

    override fun onTitleMoreClick() {
        showMoreInfoDialog()
    }

   private fun showMoreInfoDialog(){
        val dialog = MoreInfoListBottomDialog(this, bookModel.moreItemList!!)
        dialog.show()
    }
}