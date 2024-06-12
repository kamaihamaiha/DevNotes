package cn.kk.customview.activity.more.audio

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import cn.kk.base.fragment.BaseFragment
import cn.kk.customview.R
import cn.kk.customview.widget.CountIndicatorView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 如何量化分析语音喜好
 */
class AudioBasic2Fragment: BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_audio_basic2

    private val imgModelList: MutableList<ImgModel> by lazy {
        mutableListOf<ImgModel>().apply {
            add(ImgModel(R.drawable.bg_audio_human))
            add(ImgModel(R.drawable.bg_audio_1))
            add(ImgModel(R.drawable.bg_audio_2))
            add(ImgModel(R.drawable.bg_audio_3))
            add(ImgModel(R.drawable.bg_audio_4))
            add(ImgModel(R.drawable.bg_audio_5))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val view_pager_imgs = view.findViewById<ViewPager2>(R.id.view_pager_imgs)
        val indicator_view = view.findViewById<CountIndicatorView>(R.id.indicator_view)

        view_pager_imgs.adapter = object : BaseQuickAdapter<ImgModel, BaseViewHolder>(R.layout.item_img, imgModelList){
            override fun convert(holder: BaseViewHolder, item: ImgModel) {
                Glide.with(context).load(item.imgResId).into(holder.getView(R.id.image_view))
            }
        }
        view_pager_imgs.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator_view.numberSelected = position
            }
        })
    }

    class ImgModel(val imgResId: Int)
}