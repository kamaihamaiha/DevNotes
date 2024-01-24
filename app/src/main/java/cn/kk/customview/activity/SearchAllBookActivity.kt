package cn.kk.customview.activity

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.base.bean.BaseItem
import cn.kk.base.bean.NodeModel
import cn.kk.customview.R
import cn.kk.customview.activity.book.BookDetailActivity
import cn.kk.customview.activity.book.chapter.SectionDetailActivity
import cn.kk.customview.bean.BookModel
import cn.kk.customview.bean.ItemChapterModel
import cn.kk.customview.factory.BookModelFactory
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 搜索所有课本信息，根据关键字搜索
 */
class SearchAllBookActivity: BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_search_book
    }

    private val mEmptyView by lazy {
        LayoutInflater.from(this@SearchAllBookActivity).inflate(R.layout.item_list_empty, null)
    }

    private val mBookList by lazy {
        BookModelFactory.getBooks()
    }

    // 可以进行关键字搜索的所有 model 集合
    private val mBookDataKeywords by lazy {
        getBookKeywords()
    }

    private val mSearchKeywordResultList = mutableListOf<BaseItem>()

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        // init search list
        val rv_result = findViewById<RecyclerView>(R.id.rv_result)
        rv_result?.layoutManager = LinearLayoutManager(this@SearchAllBookActivity)
        rv_result?.adapter = object : BaseQuickAdapter<BaseItem, BaseViewHolder>(R.layout.item_book_search_result, mSearchKeywordResultList) {
            override fun convert(holder: BaseViewHolder, item: BaseItem) {
                holder.setText(R.id.tv_title, item.title)
                holder.setImageResource(R.id.iv_type, when(item.type) {
                    BaseItem.Type.TYPE_BOOK -> R.drawable.icon_list_book
                    BaseItem.Type.TYPE_CHAPTER -> R.drawable.icon_chapter
                    BaseItem.Type.TYPE_SECTION -> R.drawable.icon_section
                    BaseItem.Type.TYPE_NODE -> R.drawable.icon_node
                    else -> R.drawable.icon_unknown
                })
            }
        }.apply {
            setEmptyView(mEmptyView)
            setOnItemClickListener { adapter, view, position ->
                //  open book
                val curModel = data[position]
//                UIHelper.toast(curModel.title, this@SearchBookActivity)
                if (curModel.type == BaseItem.Type.TYPE_BOOK) {
                    openNextUI(BookDetailActivity::class.java, curModel.title, curModel)
                } else if (curModel.type == BaseItem.Type.TYPE_CHAPTER) {
                    // 先查找对应的 bookModel
                    var targetBookModel: BookModel?=null
                    for (bookModel in mBookList) {
                        if (bookModel.bookType == (curModel as ItemChapterModel).bookType) {
                            // 计算 chapter index
                            targetBookModel = bookModel
                            targetBookModel.expandChapterIndex = curModel.chapterPos
                            break
                        }
                    }
                    if (targetBookModel != null) {
                        openNextUI(BookDetailActivity::class.java, targetBookModel.title, targetBookModel)
                    } else {
                        UIHelper.toast("没有找到", this@SearchAllBookActivity)
                    }

                } else if (curModel.type == BaseItem.Type.TYPE_SECTION) {
                    var targetBookModel: BookModel?=null
                    for (bookModel in mBookList) {
                        if (bookModel.bookType == curModel.bookType) {
                            // 计算 chapter index
                            targetBookModel = bookModel
                            targetBookModel.expandChapterIndex = curModel.chapterPos
                            targetBookModel.sectionPos = curModel.sectionPos
                            break
                        }
                    }
                    if (targetBookModel != null) {
                        openNextUI(BookDetailActivity::class.java, targetBookModel.title, targetBookModel.apply { locationSection = true })
                    }
                } else if (curModel.type == BaseItem.Type.TYPE_NODE) {

                    //  找到对应的 json 文件名即可
                    curModel.data_source?.let {
                        openNextUI(SectionDetailActivity::class.java, curModel.title, curModel.data_source!!)
                    }
                }
            }
        }
        val btn_cancel = findViewById<Button>(R.id.btn_cancel)
        val et_search = findViewById<EditText>(R.id.et_search)
        val tv_result_count = findViewById<TextView>(R.id.tv_result_count)
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
                val input = s.toString()
                mSearchKeywordResultList.clear()
                mBookDataKeywords.forEach {
                    if (input.isNotEmpty()) {
                        if (it.title.lowercase().contains(input.lowercase())) {
                            // 加入搜索结果
                            mSearchKeywordResultList.add(it)
                        }
                        if (it.type == BaseItem.Type.TYPE_NODE && (it as NodeModel).index.contains(input.lowercase())) {
                            // 加入搜索结果
                            mSearchKeywordResultList.add(it)
                        }

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
            val bookType = it.bookType
            bookKeywords.add(it.apply { type = BaseItem.Type.TYPE_BOOK })

            var chapterPos = 0
            it.chapterModelList.forEach { chapter ->
                bookKeywords.add(chapter.apply {
                    type = BaseItem.Type.TYPE_CHAPTER
                    this.bookType = bookType
                    this.chapterPos = chapterPos
                })

                var sectionPos = 0
                chapter.sections.forEach {
                    bookKeywords.add(it.apply {
                        type = BaseItem.Type.TYPE_SECTION
                        this.bookType = bookType
                        this.chapterPos = chapterPos
                        this.sectionPos = sectionPos
                    })

                    var nodePos = 0
                    val curDataSource = it.data_source
                    it.getDetailModel().nodeModelList?.forEach {
                        bookKeywords.add(it.apply {
                            type = BaseItem.Type.TYPE_NODE
                            this.bookType = bookType
                            this.chapterPos = chapterPos
                            this.sectionPos = sectionPos
                            this.nodePos = nodePos
                            this.data_source = curDataSource // 保存数据源文件名（搜索出结果，点击跳转时用到）
                        })
                        nodePos++
                    }
                    sectionPos++
                }
                chapterPos++
            }
        }

       // 按照 type 排序
       bookKeywords.sortBy { item ->
           when(item.type) {
               BaseItem.Type.TYPE_BOOK -> 0
               BaseItem.Type.TYPE_CHAPTER -> 1
               BaseItem.Type.TYPE_SECTION -> 2
               BaseItem.Type.TYPE_NODE -> 3
               else -> 4

           }
       }
        return bookKeywords
    }
}