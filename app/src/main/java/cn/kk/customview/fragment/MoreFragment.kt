package cn.kk.customview.fragment

import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cn.kk.base.UIHelper
import cn.kk.base.fragment.BaseFragment
import cn.kk.customview.R
import cn.kk.customview.activity.BaseTabActivity
import cn.kk.customview.activity.NormalMarkDownViewActivity
import cn.kk.customview.activity.NormalWebViewActivity
import cn.kk.customview.activity.book.BookDetailActivity
import cn.kk.customview.activity.more.*
import cn.kk.customview.activity.more.audio.AudioActivity
import cn.kk.customview.activity.more.video.VideoActivity
import cn.kk.customview.activity.ndk.NdkHomeActivity
import cn.kk.customview.bean.BaseItem
import cn.kk.customview.bean.ItemSimpleCard
import cn.kk.customview.factory.BookModelFactory
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class MoreFragment: BaseFragment() {
    override fun getLayoutId(): Int {
       return R.layout.fragment_more
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val rvList = rootView.findViewById<RecyclerView>(R.id.rv_list_more)
        rvList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvList.adapter = object : BaseQuickAdapter<ItemSimpleCard, BaseViewHolder>(R.layout.item_card_list, getItems()){
            override fun convert(holder: BaseViewHolder, item: ItemSimpleCard) {
                holder.setText(R.id.tv_name, item.title)
                holder.setText(R.id.tv_index, (holder.layoutPosition + 1).toString())
                holder.setVisible(R.id.iv_ok_flag, item.finish)
                holder.getView<CardView>(R.id.rootView).setCardBackgroundColor(UIHelper.generaRandomColor())
            }
        }.apply {
            setOnItemClickListener { adapter, view, position ->
                val title = data[position].title
                when (data[position].item_action) {
                    BaseItem.ACTION_MORE_WORK -> startNextUI(WorkActivity::class.java, title)
                    BaseItem.ACTION_MORE_MULTI_THREAD -> {
                        val bookModel = BookModelFactory.createBook(BaseItem.action_book_android_advance).apply { expandChapterIndex = 3 }
                        startNextUI(BookDetailActivity::class.java, bookModel.title, bookModel)
                    }
                    BaseItem.ACTION_MORE_NET -> startNextUI(NetActivity::class.java, title)
                    BaseItem.ACTION_MORE_BLOGS -> startNextUI(BlogsActivity::class.java, title)
                    BaseItem.ACTION_MORE_DESIGN_PATTERN -> {
                        val bookModel = BookModelFactory.createBook(BaseItem.action_book_android_advance).apply { expandChapterIndex = 5 }
                        startNextUI(BookDetailActivity::class.java, bookModel.title, bookModel)
                    }
                    BaseItem.ACTION_MORE_DATA_ARCH -> {
                        val bookModel = BookModelFactory.createBook(BaseItem.action_book_data_arch)
                        startNextUI(BookDetailActivity::class.java, bookModel.title, bookModel)
                    }
                    BaseItem.ACTION_MORE_ACTIVITY_LIFECYCLE_LAUNCH_MODE -> startNextUI(BookDetailActivity::class.java, title, BookModelFactory.createBook(BaseItem.action_book_android_dev_art))
                    BaseItem.ACTION_MORE_ANDROID_HANDLER_MESSAGE -> {
                        val bookModel = BookModelFactory.createBook(BaseItem.action_book_android_dev_art).apply { expandChapterIndex = 9 }
                        startNextUI(BookDetailActivity::class.java, bookModel.title, bookModel)
                    }
                    BaseItem.ACTION_MORE_IPC -> {
                        val bookModel = BookModelFactory.createBook(BaseItem.action_book_android_dev_art).apply { expandChapterIndex = 1 }
                        startNextUI(BookDetailActivity::class.java, bookModel.title, bookModel)
                    } 
                    BaseItem.ACTION_MORE_AUDIO -> startNextUI(AudioActivity::class.java, title)
                    BaseItem.ACTION_MORE_VIDEO -> startNextUI(VideoActivity::class.java, title)
                    BaseItem.ACTION_MORE_HOT_FIX -> startNextUI(BookDetailActivity::class.java, title, BookModelFactory.createBook(BaseItem.action_book_android_hot_fix))
                    BaseItem.ACTION_MORE_PLUGIN -> startNextUI(BookDetailActivity::class.java, title, BookModelFactory.createBook(BaseItem.action_book__android_plugin))
                    BaseItem.ACTION_MORE_NDK -> startNextUI(NdkHomeActivity::class.java, title)
                    BaseItem.ACTION_MORE_ZONGHE -> startNextUI(ZongHeActivity::class.java, title)
                    BaseItem.ACTION_MORE_Git -> startNextUI(GitActivity::class.java, title)
                    BaseItem.ACTION_MORE_Linux -> startNextUI(LinuxActivity::class.java, title)
                    BaseItem.ACTION_MORE_Python -> startNextUI(BookDetailActivity::class.java, title, BookModelFactory.createBook(BaseItem.action_book_c))
                    BaseItem.ACTION_MORE_MIX_DEV -> startNextUI(MixDevActivity::class.java, title)
                    BaseItem.ACTION_MORE_REGEX -> startNextUI(NormalWebViewActivity::class.java, title, BaseTabActivity.TabType.SystemUI.RECYCLER_VIEW_TYPE, data[position].web_url)
                    BaseItem.ACTION_MORE_android_debug -> openNextUIWithMarkdown(NormalMarkDownViewActivity::class.java, title, data[position].markdown_url, data[position].markdownLocalFlag)
                    else -> {
                    }
                }
            }
        }
    }

    private fun getItems(): MutableList<ItemSimpleCard>{
        return  mutableListOf<ItemSimpleCard>().apply {
            add(ItemSimpleCard("???????????????", true).apply { item_action = BaseItem.ACTION_MORE_WORK })
            add(ItemSimpleCard("?????????", true).apply { item_action =  BaseItem.ACTION_MORE_MULTI_THREAD})
            add(ItemSimpleCard("??????", true).apply { item_action = BaseItem.ACTION_MORE_NET })
            add(ItemSimpleCard("????????????", true).apply { item_action = BaseItem.ACTION_MORE_BLOGS })
            add(ItemSimpleCard("????????????", true).apply { item_action = BaseItem.ACTION_MORE_DESIGN_PATTERN })
            add(ItemSimpleCard("?????????????????????", true).apply { item_action = BaseItem.ACTION_MORE_DATA_ARCH })
            add(ItemSimpleCard("Activity ??????????????????????????????", true).apply { item_action = BaseItem.ACTION_MORE_ACTIVITY_LIFECYCLE_LAUNCH_MODE })
            add(ItemSimpleCard("Android ????????????", true).apply { item_action = BaseItem.ACTION_MORE_ANDROID_HANDLER_MESSAGE })
            add(ItemSimpleCard("IPC??????", true).apply { item_action = BaseItem.ACTION_MORE_IPC })
            add(ItemSimpleCard("?????? Window ??? WindowManager"))
            add(ItemSimpleCard("????????????????????????"))
            add(ItemSimpleCard("Bitmap ???????????? Cache"))
            add(ItemSimpleCard("????????????"))
            add(ItemSimpleCard("?????????????????????"))
            add(ItemSimpleCard("???????????????????????????"))
            add(ItemSimpleCard("Flutter"))
            add(ItemSimpleCard("??????", true).apply { item_action = BaseItem.ACTION_MORE_AUDIO })
            add(ItemSimpleCard("??????", true).apply { item_action = BaseItem.ACTION_MORE_VIDEO })
            add(ItemSimpleCard("????????????"))
            add(ItemSimpleCard("?????????", true).apply { item_action = BaseItem.ACTION_MORE_HOT_FIX })
            add(ItemSimpleCard("?????????????????????"))
            add(ItemSimpleCard("Android ?????????????????????", true).apply { item_action = BaseItem.ACTION_MORE_PLUGIN })
            add(ItemSimpleCard("Gradle"))
            add(ItemSimpleCard("JNI ??? NDK", true).apply { item_action = BaseItem.ACTION_MORE_NDK })
            add(ItemSimpleCard("C/C++"))
            add(ItemSimpleCard("OpenGL"))
            add(ItemSimpleCard("????????????", true).apply { item_action = BaseItem.ACTION_MORE_ZONGHE })
            add(ItemSimpleCard("????????????"))
            add(ItemSimpleCard("Git", true).apply { item_action = BaseItem.ACTION_MORE_Git })
            add(ItemSimpleCard("Linux", true).apply { item_action = BaseItem.ACTION_MORE_Linux })
            add(ItemSimpleCard("Python", true).apply { item_action = BaseItem.ACTION_MORE_Python })
            add(ItemSimpleCard("????????????", true).apply { item_action = BaseItem.ACTION_MORE_MIX_DEV })
            add(ItemSimpleCard("???????????????", true).apply {
                item_action = BaseItem.ACTION_MORE_REGEX
                web_url = "https://www.runoob.com/java/java-regular-expressions.html"
            })
            add(ItemSimpleCard("Android ??????", true).apply {
                item_action = BaseItem.ACTION_MORE_android_debug
                markdown_url = "https://gitee.com/kamaihamaiha/Android_Debug/blob/main/README.md"
            })
        }
    }

}