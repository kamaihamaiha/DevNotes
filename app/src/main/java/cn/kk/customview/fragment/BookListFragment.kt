package cn.kk.customview.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.kk.base.fragment.BaseFragment
import cn.kk.base.utils.AssetsHelper
import cn.kk.customview.R
import cn.kk.customview.activity.SearchBookActivity
import cn.kk.customview.activity.book.BookDetailActivity
import cn.kk.customview.bean.BookModel
import cn.kk.customview.factory.BookModelFactory
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class BookListFragment: BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_book
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

       val toolbar = rootView.findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_search)
        toolbar.setTitle("")
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        val rvBookList = rootView.findViewById<RecyclerView>(R.id.rv_book)
        // init book data

        val bookListV2 = BookModelFactory.getBooks()

        rvBookList.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = object : BaseQuickAdapter<BookModel, BaseViewHolder>(R.layout.item_book_view, bookListV2){
                override fun convert(holder: BaseViewHolder, item: BookModel) {
                    val tv = holder.getView<TextView>(R.id.tv_name)
                    if (item.bookImgRes == -1) { // background 主题色透明度，按压效果水波纹（颜色为主题色）
                        tv.text = item.title
                        holder.getView<CardView>(R.id.root_view).setCardBackgroundColor(ContextCompat.getColor(context, R.color.primary_10))
                        holder.setBackgroundResource(R.id.tv_name, 0)
                        tv.setBackgroundResource(R.drawable.bg_press_ripple_book)
                        holder.itemView.foreground = null

                    } else { // background 为书封面，按压效果封面上加上黑色遮罩
                        holder.setText(R.id.tv_name, "")
                        tv.setBackgroundResource(item.bookImgRes)
                        holder.itemView.foreground = ContextCompat.getDrawable(context, R.drawable.bg_press_dark_selector)
                    }

                }
            }.apply {
                setOnItemClickListener { adapter, view, position ->
                    startNextUI(BookDetailActivity::class.java, data[position].title, data[position])
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_book_search -> {
                // start search activity
                try {
                    startNextUI(SearchBookActivity::class.java, "")
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}