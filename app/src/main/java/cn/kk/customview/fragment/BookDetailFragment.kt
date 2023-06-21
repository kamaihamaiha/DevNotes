package cn.kk.customview.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.kk.av.task_list.Task1DrawPicture
import cn.kk.av.task_list.Task2AudioRecord
import cn.kk.av.task_list.Task4MediaExtractor
import cn.kk.av.task_list.task15.Task15SimplePlayer
import cn.kk.av.task_list.task3.Task3CameraPreview
import cn.kk.av.task_list.task7.Task7MediaCodecAAC
import cn.kk.av.task_list.task8.Task8MediaCodecH264
import cn.kk.base.fragment.BaseFragment
import cn.kk.base.utils.AssetsHelper
import cn.kk.bean.BookViewModel
import cn.kk.customview.R
import cn.kk.customview.activity.NormalMarkDownViewActivity
import cn.kk.customview.activity.NormalViewActivity
import cn.kk.customview.activity.NormalWebViewActivity
import cn.kk.customview.activity.book.BookDetailActivity
import cn.kk.customview.activity.more.audio.AudioActivity
import cn.kk.customview.activity.more.video.VideoActivity
import cn.kk.customview.adapter.BaseChapterAdapter
import cn.kk.base.bean.BaseItem
import cn.kk.customview.bean.BookModel
import cn.kk.customview.bean.ItemSectionModel
import cn.kk.customview.factory.BookModelFactory
import cn.kk.io.db.Book
import cn.kk.io.db.BookRepository
import com.kk.opengl.OpenGLDemoActivity

/**
 * Book 详情页面 - Fragment
 */
class BookDetailFragment: BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_book_detail_layout

    private val bookListViewModel by lazy {
        ViewModelProviders.of(this).get(BookViewModel::class.java)
    }

    var scrollOrientationListener: ScrollOrientationListener ?= null
    var scrollUp = false
    protected var bottomRvHideState = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookModel = arguments?.getSerializable(INTENT_MODEL_KEY) as BookModel

        // region 添加书籍信息到本地数据库
        bookListViewModel.getBook(bookModel.itemAction).observe(viewLifecycleOwner, Observer { book ->
            if (book == null) {
//                showToast("add book: ${bookModel.title}")
                BookRepository.getInstance().addBook(Book(bookModel.itemAction, bookModel.title, bookModel.bookImgRes))
            } else {
//                showToast(bookModel.title.plus(" 已经添加过了"))
            }
         })
        // endregion

        val rvChapter = rootView.findViewById<RecyclerView>(R.id.rv_chapter_list)

        // 滚动方向监听
        val scrollView = rootView.findViewById<NestedScrollView>(R.id.nested_scroll_view)
        scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // 向上滑动
            if (oldScrollY < scrollY) { //向上滑动
                if (!scrollUp) { // 方向改变了
                    scrollUp = oldScrollY < scrollY
                    scrollOrientationListener?.onScroll(scrollUp)
                }
            } else {
                if (scrollUp) { // 方向改变了
                    scrollUp = oldScrollY < scrollY
                    scrollOrientationListener?.onScroll(scrollUp)
                }
            }
        }

        rvChapter.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = BaseChapterAdapter(bookModel.chapterModelList).apply {
                expandIndex = bookModel.expandChapterIndex
                if (bookModel.locationSection) {
                    highLightSectionPos = bookModel.sectionPos
                }
                mItemSectionClickListener = object : BaseChapterAdapter.OnItemSectionClickListener {
                    override fun onSectionClick(bookType: Int, item: ItemSectionModel) {
                        when(bookType) {
                            // 大佬分享,理财知识,技术文章大杂烩,Gradle
                            BaseItem.ACTION_BOOK_SOMEONE_SHARE, BaseItem.ACTION_BOOK_FINANCIAL, BaseItem.ACTION_BOOK_SOME_ARTICLES, BaseItem.ACTION_BOOK_GRADLE -> {
                                if (item.webUrl.isNullOrEmpty()) return
                                openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, item.title, item.webUrl, false)
                            }
                            // endregion
                            // region 面试
                            BaseItem.ACTION_BOOK_INTERVIEW -> {
                                val url = AssetsHelper.getMarkdownURL(bookType, item)
                                openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, item.title, url, false)
                            }
                            // endregion

                            // region Android 开发高手课
                            BaseItem.action_book_android_dev_performance -> {
                                if (!item.finishTag) {
                                    showToast("未完成。。。")
                                    return
                                }
                                val url = AssetsHelper.getMarkdownURL(bookType, item)
                                openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, item.title, url, false)
                            }
                            // endregion
                            // region audio video 音视频开发
                            BaseItem.ACTION_BOOK_AV_DEV -> {
                                when(item.chapter_action) {
                                    1 -> {  // 第一章 知识树
                                        when(item.section_action) {
                                            1 -> startNextUI(AudioActivity::class.java, item.title)
                                            2 -> startNextUI(VideoActivity::class.java, item.title)
                                            3 -> startNextUI(BookDetailActivity::class.java, item.title, BookModelFactory.getBookByAction(BaseItem.action_book_c))
                                            4 -> startNextUI(BookDetailActivity::class.java, item.title, BookModelFactory.getBookByAction(BaseItem.action_book_c_plus))
                                            5 -> startNextUI(BookDetailActivity::class.java, item.title, BookModelFactory.getBookByAction(BaseItem.action_book_ffmpeg))
                                            6 -> startNextUI(BookDetailActivity::class.java, item.title, BookModelFactory.getBookByAction(BaseItem.action_book_linux))
                                            9 -> startNextUI(BookDetailActivity::class.java, item.title, BookModelFactory.getBookByAction(BaseItem.ACTION_BOOK_NDK))
                                            10 -> startNextUI(OpenGLDemoActivity::class.java, item.title)
                                        }
                                    }
                                    2 -> { // 课程或书本
                                        when(item.section_action) {
                                            1 -> startNextUI(AudioActivity::class.java, item.title)
                                            2 -> startNextUI(VideoActivity::class.java, item.title)
                                            8 -> startNextUI(BookDetailActivity::class.java, item.title, BookModelFactory.getBookByAction(BaseItem.action_book_ffmpeg))
                                        }

                                    }
                                    3 -> { // 第三章：任务列表
                                        when(item.section_action) {
                                            // 三种方式绘制 Bitmap
                                            1 -> startNextUI(Task1DrawPicture::class.java, item.title)
                                            2 -> startNextUI(Task2AudioRecord::class.java, item.title)
                                            3 -> startNextUI(Task3CameraPreview::class.java, item.title)
                                            4 -> startNextUI(Task4MediaExtractor::class.java, item.title)
                                            7 -> startNextUI(Task7MediaCodecAAC::class.java, item.title)
                                            8 -> startNextUI(Task8MediaCodecH264::class.java, item.title)
                                            15 -> startNextUI(Task15SimplePlayer::class.java, item.title)
                                        }
                                    }
                                    5 -> { // 行业大佬
                                        when(item.section_action) {
                                            2 -> startNextUI(NormalWebViewActivity::class.java, item.title, -1, item.webUrl)
                                            // DevYK
                                            5 -> startNextUI(NormalWebViewActivity::class.java, item.title, -1, "https://github.com/yangkun19921001")
                                        }
                                    }
                                    7 -> { // 其他
                                        if (item.finishTag) {
                                            startNextUI(NormalWebViewActivity::class.java, item.title, -1, item.webUrl)
                                        }
                                    }
                                }
                            }
                            // endregion

                            // region Android NDK
                            BaseItem.ACTION_BOOK_NDK -> {
                                val url = AssetsHelper.getMarkdownURL(bookType, item)
                                openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, item.title, url, false)
                            }
                            // endregion

                            // region Android 开发艺术探索
                            BaseItem.action_book_android_dev_art -> {
                                val url = AssetsHelper.getMarkdownURL(bookType, item)
                                openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, item.title, url, false)
                            }
                            // endregion

                            // region Android 进阶之光
                            BaseItem.action_book_android_advance -> {
                                val url = AssetsHelper.getMarkdownURL(bookType, item)
                                openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, item.title, url, false)
                            }
                            // endregion

                            // region cpp
                            BaseItem.action_book_c_plus -> {
                                when(item.chapter_action) {
                                    1, 2, 3, 4 -> { //chapter1 chapter2 & chapter3
                                        // open markdown from online
                                        val markdownURL = AssetsHelper.getMarkdownURL(bookType, item)
                                        openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, item.title, markdownURL, false)
                                    }
                                }
                            }
                            // endregion

                            // region FFMpeg
                            BaseItem.action_book_ffmpeg -> {
                                openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, item.title, AssetsHelper.getMarkdownURL(bookType, item), false)
                            }
                            // endregion

                            // region IDE & Tools
                            BaseItem.ACTION_BOOK_IDE -> {
                                openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, item.title, item.getMarkdownFileUrl(bookType), false)
                            }
                            // endregion

                            // region 10x 程序员工作法
                            BaseItem.ACTION_10_works_method -> {
                                // https://github.com/kamaihamaiha/DevNotes/tree/master/doc/geek_time/effective_work_method/c_2/section_5.md
                                val markdownFileUrl = item.getMarkdownFileUrl(bookType)
                                openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, item.title, markdownFileUrl, false)
                            }
                            // endregion

                            BaseItem.ACTION_BOOK_HEALTH -> {
                                when (item.chapter_action) {
                                    0 -> { // 新冠
                                        when (item.section_action) {
                                            1 -> { // 小区情况
                                                startNextUI(NormalViewActivity::class.java, item.title, NormalViewActivity.VIEW_TYPE_VERTICAL_SCROLL_IMAGE_VIEW, R.drawable.covid_img_1)
                                            }
                                            2 -> { // 症状

                                            }
                                            3 -> { // 专家家交流会
                                                startNextUI(NormalViewActivity::class.java, item.title, NormalViewActivity.VIEW_TYPE_VERTICAL_SCROLL_IMAGE_VIEW, R.drawable.covid_img_3)
                                            }
                                            else -> {}
                                        }
                                    }
                                    else -> {}
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    interface ScrollOrientationListener {

       fun onScroll(up: Boolean)
    }
}