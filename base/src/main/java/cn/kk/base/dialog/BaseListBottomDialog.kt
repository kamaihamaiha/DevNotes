package cn.kk.base.dialog

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.kk.base.R
import cn.kk.base.activity.BaseActivity
import cn.kk.base.adapter.ListV3Adapter
import cn.kk.base.bean.HtmlWikiModel
import cn.kk.customview.bean.BaseItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

abstract  class  BaseListBottomDialog<B: BaseItem> (mActivity: BaseActivity, val models: MutableList<B>, theme: Int = R.style.EdgeToEdgeDialogStyle): BaseBottomDialog(mActivity, theme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_base_list)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        findViewById<View>(R.id.btn_exit)?.setOnClickListener { dismiss() }

        val rvList = findViewById<RecyclerView>(R.id.rv_dialog_list)
        rvList?.layoutManager = LinearLayoutManager(context)
        rvList?.adapter = object : BaseQuickAdapter<B, BaseViewHolder>(R.layout.item_list_v4, models) {
            override fun convert(holder: BaseViewHolder, item: B) {
                fillItem(holder, item)
            }
        }.apply {
            setOnItemClickListener { adapter, view, position ->
                onItemClick(data[position])
            }
        }
    }

    abstract fun  fillItem(holder: BaseViewHolder, item: B)

    abstract fun onItemClick(model: B)
}