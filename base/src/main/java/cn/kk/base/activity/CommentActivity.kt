package cn.kk.base.activity

import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.kk.base.R
import cn.kk.base.adapter.CommentAdapter
import cn.kk.base.bean.CommentModel

class CommentActivity: BaseActivity() {
    override fun getLayout(): Int {
       return R.layout.activity_comment
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()



        val commentList = mutableListOf<CommentModel>()
        commentList.add(CommentModel("name1", "", "comment_1", System.currentTimeMillis(), true))
        commentList.add(CommentModel("name2", "", "comment_2", System.currentTimeMillis(), false))

        val rv_comment_list = findViewById<RecyclerView>(R.id.rv_comment_list)
         rv_comment_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val commentAdapter = CommentAdapter(commentList, this)
        rv_comment_list.adapter = commentAdapter

        // 添加空布局
        commentAdapter.setEmptyView(R.layout.item_list_empty)

        // 添加脚布局
        commentAdapter.addFooterView(TextView(this).apply {
            text = "展开更多评论"
            setOnClickListener {
                // 展开更多点击事件
                commentList.add(CommentModel("name3", "", "comment_3", System.currentTimeMillis(), false))
                commentList.add(CommentModel("name4", "", "comment_4", System.currentTimeMillis(), false))

                commentAdapter.notifyDataSetChanged()
            }
        })


        commentAdapter.setOnItemClickListener { adapter, view, position -> showToast("click: ${position}") }

        commentAdapter.addChildClickViewIds(R.id.tv_nikename, R.id.tv_like)

        commentAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.tv_like -> {
                    commentList[position].like = !commentList[position].like

                    commentAdapter.refreshLikeState(view as TextView, commentList[position])
                }
            }
        }
    }
}