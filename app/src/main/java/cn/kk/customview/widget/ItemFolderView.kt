package cn.kk.customview.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.kk.customview.R
import cn.kk.customview.bean.ItemChapterModel
import cn.kk.customview.bean.ItemSectionModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 目录view
 * 内部含有具体章节
 */
class ItemFolderView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    var sectionClickListener: SectionClickListener?= null

    lateinit var containerView: View
    var titleView: View
    var tvTitle: TextView
    var tvSectionCount: TextView
    var ivArrow: ImageView
    var rvList: RecyclerView

    var expandState = false

    init {
        // 使用自己定义的布局
        containerView =
            LayoutInflater.from(context).inflate(R.layout.item_audio_part_layout, this, true)
        titleView = containerView.findViewById(R.id.ll_title)
        tvTitle = containerView.findViewById(R.id.tv_title)
        tvSectionCount = containerView.findViewById(R.id.tv_section_count)
        ivArrow = containerView.findViewById(R.id.iv_arrow)
        rvList = containerView.findViewById(R.id.rv_section)
    }

    fun setData(itemFolderModel: ItemChapterModel, expand: Boolean = false, highlightPos: Int = -1) {
        tvTitle.text = itemFolderModel.title
        tvSectionCount.text = String.format(context.getString(R.string.section_count), itemFolderModel.sections.size)

        rvList.layoutManager = LinearLayoutManager(context)
        rvList.adapter = object :
            BaseQuickAdapter<ItemSectionModel, BaseViewHolder>(R.layout.item_list_v4, itemFolderModel.sections) {
            override fun convert(holder: BaseViewHolder, item: ItemSectionModel) {
                // hide arrow
                holder.setVisible(R.id.finishTag, item.finishTag)
                holder.getView<TextView>(R.id.tv_item_home_name).apply {
                    textSize = 14f
                    text = item.title
                    isSelected = true
                    setTextColor(ContextCompat.getColor(context, R.color.ForestGreen))
                }
                holder.itemView.foreground = if (expand && highlightPos == holder.layoutPosition) ContextCompat.getDrawable(context ,R.drawable.bg_shape_frame_location) else null
            }

        }.apply {
            setOnItemClickListener { adapter, view, position ->
                // open special ui
                sectionClickListener?.onSectionClick(data[position])
            }
        }

        // expand
        expandState = expand
        changeExpandUI()

        // title view click event
        titleView.setOnClickListener {
            expandState = !expandState
            changeExpandUI()
        }
    }

    /**
     * 改变展开状态
     * rotation: 0 -> 展开；1 -> 收起
     */
   private fun changeExpandUI(){
       val rotateAnim = RotateAnimation(if (expandState) -90f else 0f, if (expandState) 0f else -90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
           duration = 300
           fillAfter = true
       }
        rvList.visibility = if (expandState) View.VISIBLE else View.GONE
        ivArrow.startAnimation(rotateAnim)
    }

    interface SectionClickListener {
        fun onSectionClick(item: ItemSectionModel)
    }
}