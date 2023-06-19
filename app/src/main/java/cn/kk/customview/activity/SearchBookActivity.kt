package cn.kk.customview.activity

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import cn.kk.base.activity.BaseActivity
import cn.kk.base.bean.BaseItem
import cn.kk.base.utils.AssetsHelper
import cn.kk.customview.R
import cn.kk.customview.factory.BookModelFactory
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_search_book.*

class SearchBookActivity: BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_search_book
    }

    private val mBookList by lazy {
        BookModelFactory.getBooks(AssetsHelper.getBooksValue(this@SearchBookActivity))
    }

    private val mBookDataKeywords by lazy {
        getBookKeywords()
    }

    private val mSearchKeywordResultList = mutableListOf<BaseItem>()

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        // init search list
        rv_result?.layoutManager = LinearLayoutManager(this@SearchBookActivity)
        rv_result?.adapter = object : BaseQuickAdapter<BaseItem, BaseViewHolder>(R.layout.item_book_search_result, mSearchKeywordResultList) {
            override fun convert(holder: BaseViewHolder, item: BaseItem) {
                holder.setText(R.id.tv_title, item.title)
                holder.setText(
                    R.id.tv_type, when (item.type) {
                        BaseItem.Type.TYPE_BOOK -> {
                            "book"
                        }
                        BaseItem.Type.TYPE_CHAPTER -> {
                            "chapter"
                        }
                        BaseItem.Type.TYPE_SECTION -> {
                            "section"
                        }
                        else -> {
                            "unknown"
                        }
                    }
                )
            }
        }

        btn_cancel.setOnClickListener { finish() }

        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s == null) return

                // search
                mSearchKeywordResultList.clear()
                mBookDataKeywords.forEach {
                    if (!s.toString().isEmpty() && it.title.contains(s)) {
                        // 加入搜索结果
                        mSearchKeywordResultList.add(it)

                    }
                }
                if (TextUtils.isEmpty(s))

                // refresh ui
                (rv_result?.adapter as BaseQuickAdapter<BaseItem, BaseViewHolder>)?.notifyDataSetChanged()
            }
        })


    }


    fun getBookKeywords(): MutableList<BaseItem> {
        val bookKeywords = mutableListOf<BaseItem>()
        mBookList.forEach {
            bookKeywords.add(it.apply { type = BaseItem.Type.TYPE_BOOK })
            it.chapterModelList.forEach { chapter ->
                bookKeywords.add(chapter.apply { type = BaseItem.Type.TYPE_CHAPTER })

                chapter.sections.forEach {
                    bookKeywords.add(it.apply { type = BaseItem.Type.TYPE_SECTION })
                }
            }
        }
        return bookKeywords
    }
}