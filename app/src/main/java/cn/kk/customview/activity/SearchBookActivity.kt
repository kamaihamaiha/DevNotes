package cn.kk.customview.activity

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.base.bean.BaseItem
import cn.kk.base.utils.AssetsHelper
import cn.kk.customview.R
import cn.kk.customview.activity.book.BookDetailActivity
import cn.kk.customview.factory.BookModelFactory
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_search_book.*

class SearchBookActivity: BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_search_book
    }

    private val mEmptyView by lazy {
        LayoutInflater.from(this@SearchBookActivity).inflate(R.layout.item_list_empty, null)
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
                            "书"
                        }
                        BaseItem.Type.TYPE_CHAPTER -> {
                            "章节"
                        }
                        BaseItem.Type.TYPE_SECTION -> {
                            "小节"
                        }
                        else -> {
                            "unknown"
                        }
                    }
                )
            }
        }.apply {
            setEmptyView(mEmptyView)
            setOnItemClickListener { adapter, view, position ->
                // todo open book
                UIHelper.toast(data[position].title, this@SearchBookActivity)
//                openNextUI(BookDetailActivity::class.java, data[position].title, data[position])
            }
        }

        btn_cancel.setOnClickListener { finish() }

        et_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 在这里执行收起键盘的操作
                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v!!.windowToken, 0)

                    return true
                }
                return false
            }

        })
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

                // refresh ui
                tv_result_count.text = "共${mSearchKeywordResultList.size}条结果"
                (rv_result?.adapter as BaseQuickAdapter<BaseItem, BaseViewHolder>)?.notifyDataSetChanged()
//                changeEmptyViewState()
            }
        })


    }


   private fun getBookKeywords(): MutableList<BaseItem> {
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