package cn.kk.customview.activity

import android.graphics.BitmapFactory
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import cn.kk.customview.widget.GradientImageView
import cn.kk.customview.widget.SelectHandleRangeTextView
import cn.kk.customview.widget.TextSelectView
import cn.kk.customview.widget.VocabularyLevelBar
import cn.kk.customview.widget.WordInputViewNew
import cn.kk.customview.widget.drawable.ConcaveCornerDrawable
import cn.kk.customview.widget.work.ChannelTabView
import cn.kk.customview.widget.work.CheckInWeekView
import com.bumptech.glide.Glide
import com.example.hencoder.AvatarView
import com.example.hencoder.CameraView
import com.example.hencoder.MultilineTextView
import com.example.hencoder.draw.SimpleDrawable

/**
 * 用来显示单纯的 View(各种自定义的 View) 页面
 */
class NormalViewActivity: BaseActivity() {

    var channelVisibleWidth = 0

    companion object {
        // 混合模式
        val VIEW_TYPE_XFERMODE_AVATARVIEW = 0
        // 文字测量
        val VIEW_TYPE_TEXT_MEAUSRE = 1
        // 范围裁剪和几何变换
        val VIEW_TYPE_CLIP_AND_TRANS = 2
        // 图片渐变
        val VIEW_TYPE_GRADIENT_IMG = 3
        // 区间拖拽
        val VIEW_TYPE_DRAG_LEVEL = 4
        // 自定义 drawable
        val VIEW_TYPE_DRAWABLE = 5

        // 触摸反馈：原理全解析
        val VIEW_TYPE_TOUCH_FEED_1 = 10
        val VIEW_TYPE_TOUCH_FEED_2 = 11 // 双向滑动的 ScalableImageView

        // about work
        val VIEW_TYPE_CHECKIN_WEEK = 100
        val VIEW_TYPE_TEXTVIEW_HIGHLIGHT = 101 // TextView 高亮特定内容
        val VIEW_TYPE_TEXTVIEW_MARQUEE = 102   // 跑马灯效果
        val VIEW_TYPE_image_view_svg = 103  // svg
        val VIEW_TYPE_EDIT_TEXT_FOCUS_FIXED = 104  // EditText 焦点不跳
        val VIEW_TYPE_DATE_TEXT_VIEW = 105 // TextView 日期+斜线
        val VIEW_TYPE_CHANNEL_TAB_VIEW = 106 // channel tab view
        val VIEW_TYPE_WORD_INPUT_VIEW = 107 // 拼写组件
        val VIEW_TYPE_VERTICAL_SCROLL_IMAGE_VIEW = 108 // 可上下滚动图片
        val VIEW_TYPE_TEXT_SELECT = 109 // 文本选择
        val VIEW_TYPE_CONCAVE_CONOR_DRAWABLE = 110 // 缺角Drawable
    }

    override fun getLayout(): Int {
        return R.layout.activity_normal_view
    }

    val view_container by lazy {
        findViewById<FrameLayout>(R.id.view_container)
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        when (intent.getIntExtra(INTENT_TYPE_KEY, -1)) {
            VIEW_TYPE_XFERMODE_AVATARVIEW -> view_container.addView(AvatarView(this))
            VIEW_TYPE_TEXT_MEAUSRE -> view_container.addView(MultilineTextView(this))
            VIEW_TYPE_CLIP_AND_TRANS -> view_container.addView(CameraView(this))
            VIEW_TYPE_GRADIENT_IMG -> view_container.addView(GradientImageView(this))
            VIEW_TYPE_DRAG_LEVEL -> view_container.addView(VocabularyLevelBar(this).apply {
                layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT).apply {
                    height = UIHelper.dip2px(mSelf, 35.0)
                    setPadding(0, UIHelper.dp2px(10.0f).toInt(), 0, UIHelper.dp2px(10.0f).toInt())
                    setMargins(UIHelper.dp2px(20f).toInt(), 0, UIHelper.dp2px(20.0f).toInt(), 0)
                    gravity = Gravity.CENTER
                }
            })
            VIEW_TYPE_DRAWABLE -> view_container.addView(SimpleDrawable(this))
            VIEW_TYPE_TOUCH_FEED_1 -> view_container.addView(getTouchView().apply {
                /*findViewById<TouchView>(R.id.touchView).setOnClickListener {
                    showToast("click..")
                }*/
            })
            VIEW_TYPE_TOUCH_FEED_2 -> view_container.addView(getScalableImageView())

            // region checkin week
            VIEW_TYPE_CHECKIN_WEEK -> {
                view_container.addView(getCheckinWeekView())
                view_container.postDelayed({
                    val checkInView = ((view_container.getChildAt(0) as ViewGroup).getChildAt(0) as CheckInWeekView)
                    // 显示打卡动画
                    val ivTemp = ((view_container.getChildAt(0) as ViewGroup).getChildAt(1) as ImageView)
                    checkInView.playAnim(ivTemp)

                }, 1000)
            }
            // endregion
            VIEW_TYPE_TEXTVIEW_HIGHLIGHT -> {
                val highLightContainer = getTextViewWithHighLightView()
                view_container.addView(highLightContainer)

               val tvInfo = highLightContainer.findViewById<TextView>(R.id.tv_info)
                tvInfo.text = "#时间 锄禾日当午，\n#辛苦 汗滴禾下土。\n#吃饭 谁知盘中餐？\n#总结 粒粒皆辛苦。";
                UIHelper.highlightTag(tvInfo, ContextCompat.getColor(this, R.color.colorPrimary))
            }
            // region TextView highlight
            VIEW_TYPE_TEXTVIEW_MARQUEE -> view_container.addView(getMarqueeTextView())
            VIEW_TYPE_image_view_svg -> view_container.addView(getImageViewForSVG())
            VIEW_TYPE_EDIT_TEXT_FOCUS_FIXED -> view_container.addView(getEditTextFixFocus())
            VIEW_TYPE_DATE_TEXT_VIEW -> view_container.addView(getTextViewWithDate())
            VIEW_TYPE_CHANNEL_TAB_VIEW -> {
                view_container.addView(getChannelTabView())
                val channel_tab_view = findViewById<ChannelTabView>(R.id.channel_tab_view)
                channel_tab_view.createTabViews()
                channel_tab_view.mTabItemSelectedChangeListener = object : ChannelTabView.TabItemSelectedChangeListener {
                    override fun onSelectedChange(selectedIndex: Int) {
                        showToast("tab item ${selectedIndex} 选中了!")
                    }

                }
                channelVisibleWidth = UIHelper.getScreenWidth(this@NormalViewActivity)
                val tv_width = findViewById<TextView>(R.id.tv_width)
                val btn_change_size_add = findViewById<Button>(R.id.btn_change_size_add)
                tv_width.text = String.format("width: %d", channelVisibleWidth)
                btn_change_size_add.setOnClickListener {
                    channelVisibleWidth += UIHelper.dp2px(20f).toInt()
                    channel_tab_view.changeWidth(channelVisibleWidth)
                    tv_width.text = String.format("channel visible width: %d", channelVisibleWidth)
                }

                val btn_change_size_minus = findViewById<Button>(R.id.btn_change_size_minus)
                btn_change_size_minus.setOnClickListener {
                    channelVisibleWidth -= UIHelper.dp2px(20f).toInt()
                    channel_tab_view.changeWidth(channelVisibleWidth)
                    tv_width.text = String.format("channel visible width: %d", channelVisibleWidth)
                }
            }
            VIEW_TYPE_WORD_INPUT_VIEW -> {
                view_container.addView(getInputWordView())
                val word_input_view = findViewById<WordInputViewNew>(R.id.word_input_view)
                val sentence = "That these United Colonies are, and of right ought to be, free and <span class=\"key\">independent</span> States, that they are absolved from all allegiance to the British Crown, and that all political connection between them and the State of Great Britain is, and ought to be, totally dissolved"
                word_input_view.inflateSentence(sentence)
            }
            VIEW_TYPE_VERTICAL_SCROLL_IMAGE_VIEW-> {
                view_container.addView(getVerticalScrollImageView())
                val imgRes = intent.getIntExtra(INTENT_IMG_RES_KEY, -1)
                val bitmap = BitmapFactory.decodeResource(resources, imgRes)
                val ivPicHeight = UIHelper.getScreenWidth(this) * (bitmap.height * 1.0f / bitmap.width)
                val iv_pc = findViewById<ImageView>(R.id.iv_pic)
                iv_pc.layoutParams.apply { // 动态改变 ImageView 高度
                    height = ivPicHeight.toInt()
                }
                Glide.with(this@NormalViewActivity).load(ContextCompat.getDrawable(this, imgRes)).into(iv_pc)
            }
            VIEW_TYPE_TEXT_SELECT -> {
                val sentence = "That these United Colonies are, and of right ought to be, free and <span class=\"key\">independent</span> States, that they are absolved from all allegiance to the British Crown, and that all political connection between them and the State of Great Britain is, and ought to be, totally dissolved"
                view_container.addView(getTextSelectView())
                val textSelectView = findViewById<SelectHandleRangeTextView>(R.id.textSelectView)
                textSelectView.text = sentence
                textSelectView.setSelectContentCallback(object : SelectHandleRangeTextView.SelectContentCallback {
                    override fun onSelectText(selectedText: String) {
                        showToast(selectedText)
                    }

                })
            }
            VIEW_TYPE_CONCAVE_CONOR_DRAWABLE -> {
                view_container.addView(getConcaveCornerDrawableView())
                val tv = findViewById<TextView>(R.id.tv)
                tv.background = ConcaveCornerDrawable()
            }

            // endregion
            else -> {
            }
        }
    }

    /**
     * 获取 TouchView
     * 以填充布局文件的方式
     */
   private fun getTouchView(): View {
       return layoutInflater.inflate(R.layout.view_at_touchview_layout, null)
    }

    private fun getScalableImageView(): View {
        return layoutInflater.inflate(R.layout.view_at_scalable_image_layout, null)
    }

    private fun getCheckinWeekView(): View {
        return layoutInflater.inflate(R.layout.view_at_checkin_week_view_layout, null)
    }

    private fun getTextViewWithHighLightView(): View {
        return layoutInflater.inflate(R.layout.view_at_textview_highlight_layout, null)
    }

    private fun getMarqueeTextView(): View {
        return layoutInflater.inflate(R.layout.view_at_marquee_textview_layout, null)
    }

    private fun getImageViewForSVG(): View {
        return layoutInflater.inflate(R.layout.view_at_image_view_svg_layout, null)
    }

    private fun getEditTextFixFocus(): View {
        return layoutInflater.inflate(R.layout.view_at_fix_focus_edit_text_layout, null)
    }

    private fun getTextViewWithDate(): View {
        return layoutInflater.inflate(R.layout.view_at_text_view_date, null)
    }

    private fun getChannelTabView(): View {
        return layoutInflater.inflate(R.layout.view_at_channel_tab_view, null)
    }

    private fun getInputWordView(): View {
        return layoutInflater.inflate(R.layout.view_at_input_word_view, null)
    }

    private fun getVerticalScrollImageView(): View {
        return layoutInflater.inflate(R.layout.view_at_vertical_scroll_image_view, null)
    }
    private fun getTextSelectView(): View {
        return layoutInflater.inflate(R.layout.view_at_text_select_view, null)
    }

    private fun getConcaveCornerDrawableView(): View {
        return layoutInflater.inflate(R.layout.view_at_concave_corner, null)
    }
}