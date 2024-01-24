package cn.kk.base.adapter

import android.view.View
import android.widget.TextView
import androidx.appcompat.view.menu.ListMenuItemView
import cn.kk.base.R
import cn.kk.base.bean.ListItemAction
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 使用了开源库：BaseQuickAdapter
 */
class ListV2Adapter( data: MutableList<ListItemAction>): BaseQuickAdapter<ListItemAction, BaseViewHolder>(R.layout.item_list, data) {

    private var curPos = -1

    override fun convert(holder: BaseViewHolder, item: ListItemAction) {
        holder.getView<TextView>(R.id.tv_item_home_name).text = item.title
        holder.setVisible(R.id.finishTag, item.finish)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                curPos = holder.adapterPosition
                return false
            }

        })
    }


    fun getSelectItem(): ListItemAction {
        if (curPos < 0) return ListItemAction("")
        return data[curPos]
    }

}