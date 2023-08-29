package cn.kk.customview.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.kk.base.R
import cn.kk.base.activity.BaseActivity
import cn.kk.base.adapter.ListAdapter
import cn.kk.base.bean.ListItemAction
import cn.kk.base.fragment.BaseFragment
import cn.kk.base.utils.SystemHelper
import cn.kk.customview.activity.BaseTabActivity
import cn.kk.customview.activity.NormalListActivity
import cn.kk.customview.activity.NormalTabActivity
import cn.kk.customview.chapter.c1.DrawBasicActivity
import cn.kk.customview.config.UIConfig
import cn.kk.customview.ui.LottieAnimActivity
import cn.kk.customview.activity.NormalViewActivity
import cn.kk.customview.activity.work.*
import cn.kk.customview.chapter.AnimInterpolatorActivity
import cn.kk.customview.chapter.ViewAnimIntrosActivity
import cn.kk.customview.ui.cool300.chapter1.*
import cn.kk.customview.ui.cool300.chapter3.Simple_063
import cn.kk.customview.ui.cool300.chapter3.Simple_071
import cn.kk.customview.ui.cool300.chapter3.Simple_072
import cn.kk.customview.ui.cool300.chapter5.Simple_143
import cn.kk.customview.ui.hencoder.GraphicLocationAndSizeMeasureActivity
import cn.kk.customview.ui.system.*
import cn.kk.customview.ui.system.drawable.DrawableSample
import cn.kk.customview.ui.system.material.MaterialActivity
import cn.kk.customview.ui.work.FlickerActivity
import cn.kk.customview.ui.work.RecyclerViewDemoActivity
import cn.kk.customview.ui.work.TimeProgressActivity
import cn.kk.elementary.anim.property.`object`.AnimatorSetActivity
import cn.kk.elementary.anim.property.`object`.ObjectAnimActivity
import cn.kk.elementary.anim.property.value.ValueAnimationActivity
import cn.kk.elementary.anim.property.value.evaluator.EvaluatorActivity
import cn.kk.elementary.anim.property.value.interpolation.InterpolationActivity
import cn.kk.elementary.anim.view.AnimSampleActivity

/**
 * 普通列表 Fragment
 */
class NormalListFragment: BaseFragment(), ListAdapter.ItemClickListener {

    private val itemList = mutableListOf<String>()
    private val itemActionList = mutableListOf<ListItemAction>()
    var partType = -1

    override fun getLayoutId(): Int {
        return R.layout.fragment_normal_list
    }

    fun addItem(item: String, finishTag: Boolean = false): NormalListFragment {
        itemList.add(item)
        itemActionList.add(ListItemAction(item, finishTag))
        return this
    }

    fun addItems(items: MutableList<String>) {
        for (item in items) {
            addItem(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvList = view.findViewById<RecyclerView>(R.id.rv_list_in_fragment)

        rvList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ListAdapter(itemActionList).apply {
                itemClickListener = this@NormalListFragment
            }
        }
    }

    // region 点击条目
    override fun onItemClick(position: Int) {
        // 跳转页面
        val title = itemList[position]
        when(partType){
            UIConfig.PART_SYSTEM_STUDY -> {
                when(position){
                    0 -> startNextUI(DrawBasicActivity::class.java, title)
                    1 -> startNextUI(NormalTabActivity::class.java, title, BaseTabActivity.TabType.ANIM_TYPE)
                    2 -> startNextUI(NormalTabActivity::class.java, title, BaseTabActivity.TabType.DRAW_TYPE)
                    3 -> startNextUI(NormalTabActivity::class.java, title, BaseTabActivity.TabType.VIEW_TYPE)
//                    3 -> startNextUI(ViewHomeActivity::class.java, title)
                }
            }
            // region system UI
            UIConfig.PART_SYSTEM_UI -> {
                when(position){
                    0 -> startNextUI(ImageViewActivity::class.java, title)
                    1 -> startNextUI(DialogActivity::class.java, title)
                    2 -> startNextUI(ImmersiveModeActivity::class.java, title)
                    3 -> startNextUI(CoordinatorLayoutActivity::class.java, title)
                    4 -> startNextUI(CoordinatorLayoutAndCollapsingToolbarLayoutActivity::class.java, title)
                    5 -> startNextUI(CustomBehaviorActivity::class.java, title)
                    6 -> startNextUI(StatusBarActivity::class.java, title)
                    7 -> startNextUI(LineHeightActivity::class.java, title)
                    8 -> startNextUI(MaterialActivity::class.java, title)
                    9 -> startNextUI(TextViewWidthEnableChangeActivity::class.java, title)
                    10 ->startNextUI(TextViewWithDrawableActivity::class.java, title)
                    11 ->startNextUI(IgnoreSystemBigFont::class.java, title)
                    12 ->startNextUI(HtmlText::class.java, title)
                    13 ->startNextUI(AppWidgetSample::class.java, title)
                    14 ->startNextUI(DrawableSample::class.java, title)
                    15 ->startNextUI(RecyclerViewDemoActivity::class.java, title, BaseTabActivity.TabType.SystemUI.RECYCLER_VIEW_TYPE)
                    16 ->startNextUI(EditTextSample::class.java, title)
                }
            }
            // endregion

            // region cool 300
            UIConfig.SubConfig.COMMON_VIEW -> {
                when (position) {
                    0 -> startNextUI(Simple_005::class.java, itemList[position])
                    1 -> startNextUI(Simple_009::class.java, itemList[position])
                    2 -> startNextUI(Simple_010::class.java, itemList[position])
                    3 -> startNextUI(Simple_022::class.java, itemList[position])
                }
            }
            UIConfig.SubConfig.MENU_VIEW -> {
                when(position){
                    0 -> startNextUI(Simple_063::class.java, itemList[position])
                    1 -> startNextUI(Simple_071::class.java, itemList[position])
                    2 -> startNextUI(Simple_072::class.java, itemList[position])
                }
            }
            UIConfig.SubConfig.ANIM_VIEW -> {
                when(position){
                    0 -> startNextUI(Simple_143::class.java, itemList[position])
                }
            }
            // endregion
            UIConfig.PART_THIRD -> {
                when(position){
                    0 -> startNextUI(LottieAnimActivity::class.java,title)
                }
            }
            // region Hencoder
            UIConfig.PART_HENCODER -> {
                when(position){
                    0 -> startNextUI(GraphicLocationAndSizeMeasureActivity::class.java, title)
                    1 -> startNextUI(NormalViewActivity::class.java, title, NormalViewActivity.VIEW_TYPE_XFERMODE_AVATARVIEW)
                    2 -> startNextUI(NormalViewActivity::class.java, title, NormalViewActivity.VIEW_TYPE_TEXT_MEAUSRE)
                    3 -> startNextUI(NormalViewActivity::class.java, title, NormalViewActivity.VIEW_TYPE_CLIP_AND_TRANS)
                    5 -> startNextUI(NormalViewActivity::class.java, title, NormalViewActivity.VIEW_TYPE_DRAWABLE)
                    11 -> startNextUI(NormalListActivity::class.java, title)
                }
            }
            // endregion

            // region work
            UIConfig.PART_WORK -> {
                when(position){
                    0 -> startNextUI(TimeProgressActivity::class.java, title)
                    1 -> startNextUI(FlickerActivity::class.java, title)
                    2 -> startNextUI(NormalViewActivity::class.java, title, NormalViewActivity.VIEW_TYPE_GRADIENT_IMG)
                    3 -> startNextUI(NormalViewActivity::class.java, title, NormalViewActivity.VIEW_TYPE_DRAG_LEVEL)
                    4 -> startNextUI(NormalViewActivity::class.java, title, NormalViewActivity.VIEW_TYPE_WORD_INPUT_VIEW)
                    5 -> startNextUI(NormalViewActivity::class.java, title, NormalViewActivity.VIEW_TYPE_CHECKIN_WEEK)
                    6 -> startNextUI(RecyclerViewDemoActivity::class.java, title, BaseTabActivity.TabType.SystemUI.RECYCLER_VIEW_TYPE)
                    7 -> startNextUI(NormalViewActivity::class.java, title, NormalViewActivity.VIEW_TYPE_TEXTVIEW_HIGHLIGHT)
                    8 -> startNextUI(NormalViewActivity::class.java, title, NormalViewActivity.VIEW_TYPE_TEXTVIEW_MARQUEE)
                    9 -> startNextUI(NormalViewActivity::class.java, title, NormalViewActivity.VIEW_TYPE_EDIT_TEXT_FOCUS_FIXED)
                    10 -> startNextUI(NormalViewActivity::class.java, title, NormalViewActivity.VIEW_TYPE_DATE_TEXT_VIEW)
                    11 -> startNextUI(NormalViewActivity::class.java, title, NormalViewActivity.VIEW_TYPE_CHANNEL_TAB_VIEW)
                    12 -> startNextUI(WebViewOperaLogsToClientActivity::class.java, title, NormalViewActivity.VIEW_TYPE_CHANNEL_TAB_VIEW)
                    13 -> startNextUI(SaveLogActivity::class.java, title)
                    14 -> startNextUI(WebViewScrollCapActivity::class.java, title)
                    15 -> SystemHelper.openSysNotificationSetting(requireContext()) // 打开系统通知设置
                    16 -> startNextUI(KeyboardStateActivity::class.java, title) // 蓝牙键盘
//                    17 -> startNextUI(ChangeLauncherIconActivity::class.java, title)
                    17 -> startActivity(Intent(requireContext(), ChangeLauncherIconActivity::class.java).apply {
                        putExtra("title", title)
                        putExtra("activity", activity?.componentName?.className?:"")
                    })
                }
            }
            // endregion

            // region sub ui

            // anim- 视图动画
            UIConfig.SubConfigSystem.Anim.ANIM_VIEW -> {
                when (position) {
                    0 -> startNextUI(ViewAnimIntrosActivity::class.java, itemList[position])
                    1 -> startNextUI(AnimSampleActivity::class.java, itemList[position])
                    2 -> startNextUI(AnimInterpolatorActivity::class.java, itemList[position])
                }
            }
            // endregion

            // anim- 属性动画
            UIConfig.SubConfigSystem.Anim.ANIM_PROPERTY -> {
                val itemName = itemList[position]
                when (position) {
                    0 -> startNextUI(ValueAnimationActivity::class.java, itemName)
                    1 -> startNextUI(InterpolationActivity::class.java, itemName)
                    2 -> startNextUI(EvaluatorActivity::class.java, itemName)
                    3 -> startNextUI(ObjectAnimActivity::class.java, itemName)
                    4 -> startNextUI(AnimatorSetActivity::class.java, itemName)
                }
            }
            // endregion

            // endregion

        }
    }
    // endregion

}