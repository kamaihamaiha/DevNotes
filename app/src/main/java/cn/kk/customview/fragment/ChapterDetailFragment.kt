package cn.kk.customview.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.kk.base.adapter.ListAdapter
import cn.kk.base.bean.WikiModel
import cn.kk.base.dialog.WikiBottomDialog
import cn.kk.base.fragment.BaseFragment
import cn.kk.customview.R
import cn.kk.customview.adapter.SimpleNodeAdapter
import cn.kk.customview.factory.BookModelFactory

class ChapterDetailFragment: BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_chapter_detail_layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvNote = rootView.findViewById<RecyclerView>(R.id.rv_chapter_list)
        rvNote.layoutManager = LinearLayoutManager(context)
        arguments?.getString(INTENT_MODEL_DATA_SOURCE_KEY)?.let {
            val chapterModel = BookModelFactory.getChapterModel(it)
            rvNote.adapter = SimpleNodeAdapter(chapterModel).apply {
                setOnItemClickListener { adapter, view, position ->
                    // show bottom dialog
                    try {
                        WikiBottomDialog(
                            requireContext(),
                            WikiModel(data[position].title, data[position].content)
                        ).show()
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

}