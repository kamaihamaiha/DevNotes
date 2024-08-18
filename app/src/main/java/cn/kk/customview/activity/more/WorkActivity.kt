package cn.kk.customview.activity.more

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.kk.customview.R
import cn.kk.customview.activity.BaseTabActivity
import cn.kk.customview.activity.NormalCardListActivity
import cn.kk.customview.activity.NormalViewActivity
import cn.kk.customview.activity.NormalWebViewActivity
import cn.kk.base.bean.BaseItem
import cn.kk.base.utils.CommonUtil
import cn.kk.customview.activity.work.BottomSheetDialogActivity
import cn.kk.customview.activity.work.BroadcastDemoActivity
import cn.kk.customview.bean.ItemSimpleCard
import cn.kk.customview.bean.SimpleWikiModel
import cn.kk.customview.dialog.XhsListBottomDialog
import cn.kk.customview.ui.system.HtmlText
import cn.kk.customview.ui.work.ExpandViewTouchDemo
import cn.kk.customview.ui.work.MenuDemoActivity
import cn.kk.customview.ui.work.RecyclerViewDemoActivity
import java.util.Locale

/**
 * 工作中总结
 */
class WorkActivity: NormalCardListActivity() {
    companion object {
        val ACTION_WORK_UPDATE = "action_work_update"
        val ACTION_CONFIG_1_CHANGE = "action_config_1_change"
        val ACTION_CONFIG_2_CHANGE = "action_config_2_change"
        val ACTION_CONFIG_3_CHANGE = "action_config_3_change"
    }
    override fun getLayout() = R.layout.activity_normal_list

    override fun getListSpanCount() = 2

   override fun getItemCardList(): MutableList<ItemSimpleCard>{
        return mutableListOf<ItemSimpleCard>().apply {
            add(ItemSimpleCard("Intent 序列化传值", true).apply { item_action = BaseItem.ACTION_MORE_WORK_INTENT_SERIALIZABLE })
            add(ItemSimpleCard("RecyclerView 使用总结", true).apply { item_action = BaseItem.ACTION_MORE_WORK_REYCYCLER_VIEW })
            add(ItemSimpleCard("菜单 使用总结", true).apply { item_action = BaseItem.ACTION_MORE_WORK_MENU })
            add(ItemSimpleCard("EditText/TextView 高亮文本", true).apply { item_action = BaseItem.ACTION_MORE_WORK_TEXTVIEW_HIGHLIGHT })
            add(ItemSimpleCard("TextView 显示 html 格式", true).apply { item_action = BaseItem.ACTION_MORE_WORK_TEXTVIEW_HTML })
            add(ItemSimpleCard("扩大 View 点击范围", true).apply { item_action = BaseItem.ACTION_MORE_WORK_EXPAND_VIEW_CLICK })
            add(ItemSimpleCard("Adapter 数据源改变，无法刷新", true).apply {
                item_action = BaseItem.ACTION_MORE_WORK_ADAPTER_LIST
                web_url = "https://www.jianshu.com/p/aa23911c6681"
            })
            add(ItemSimpleCard("TextView 跑马灯效果", true).apply { item_action = BaseItem.ACTION_MORE_WORK_TEXTVIEW_MARQUEE })
            add(ItemSimpleCard("ImageView svg 锯齿", true).apply { item_action = BaseItem.ACTION_MORE_WORK_IMAGE_VIEW_SVG })
            add(ItemSimpleCard("文件选择器", true).apply { item_action = BaseItem.ACTION_MORE_WORK_PICK_FILE })
            add(ItemSimpleCard("测试广播", true).apply { item_action = BaseItem.ACTION_MORE_WORK_BROADCAST })
            add(ItemSimpleCard("文本选择", true).apply { item_action = BaseItem.ACTION_MORE_WORK_TEXT_SELECT })
            add(ItemSimpleCard("BottomSheetDialog Style Activity", true).apply { item_action = BaseItem.ACTION_MORE_WORK_BOTTOM_SHEET_DIALOG_STYLE_ACTIVITY })
            add(ItemSimpleCard("打开小红书", true).apply { item_action = BaseItem.ACTION_MORE_WORK_OPEN_XHS })
        }
    }

    override fun doWhenClickItem(item: ItemSimpleCard) {
        when(item.item_action) {
            BaseItem.ACTION_MORE_WORK_INTENT_SERIALIZABLE -> showWikiDialog(SimpleWikiModel(item.title, getString(R.string.intent_serial_wiki)))
            BaseItem.ACTION_MORE_WORK_REYCYCLER_VIEW -> openNextUI(RecyclerViewDemoActivity::class.java, item.title, BaseTabActivity.TabType.SystemUI.RECYCLER_VIEW_TYPE)
            BaseItem.ACTION_MORE_WORK_MENU -> openNextUI(MenuDemoActivity::class.java, item.title, BaseTabActivity.TabType.SystemUI.MENU_TYPE)
            BaseItem.ACTION_MORE_WORK_TEXTVIEW_HIGHLIGHT -> openNextUI(NormalViewActivity::class.java, item.title, NormalViewActivity.VIEW_TYPE_TEXTVIEW_HIGHLIGHT)
            BaseItem.ACTION_MORE_WORK_TEXTVIEW_HTML -> openNextUI(HtmlText::class.java, item.title)
            BaseItem.ACTION_MORE_WORK_EXPAND_VIEW_CLICK -> openNextUI(ExpandViewTouchDemo::class.java, item.title)
            BaseItem.ACTION_MORE_WORK_ADAPTER_LIST -> openNextUI(NormalWebViewActivity::class.java, item.title, -1, item.web_url)
            BaseItem.ACTION_MORE_WORK_TEXTVIEW_MARQUEE -> openNextUI(NormalViewActivity::class.java, item.title, NormalViewActivity.VIEW_TYPE_TEXTVIEW_MARQUEE)
            BaseItem.ACTION_MORE_WORK_IMAGE_VIEW_SVG -> openNextUI(NormalViewActivity::class.java, item.title, NormalViewActivity.VIEW_TYPE_image_view_svg)
            BaseItem.ACTION_MORE_WORK_PICK_FILE -> openNextUI(PickFileActivity::class.java, item.title)
            BaseItem.ACTION_MORE_WORK_BROADCAST -> openNextUI(BroadcastDemoActivity::class.java, item.title)
            BaseItem.ACTION_MORE_WORK_TEXT_SELECT -> openNextUI(NormalViewActivity::class.java, item.title, NormalViewActivity.VIEW_TYPE_TEXT_SELECT)
            BaseItem.ACTION_MORE_WORK_BOTTOM_SHEET_DIALOG_STYLE_ACTIVITY -> openNextUI(BottomSheetDialogActivity::class.java, item.title, true)
            BaseItem.ACTION_MORE_WORK_OPEN_XHS -> {
                XhsListBottomDialog(this, mutableListOf()).show()
            }
        }
    }

    private val mReceiver by lazy {
        object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let {
                    intent.action?.let {
                        if (it == ACTION_WORK_UPDATE) {
                            // update data
                            val info = "config1: ${intent.getBooleanExtra(ACTION_CONFIG_1_CHANGE, false)}, config2: ${intent.getBooleanExtra(ACTION_CONFIG_2_CHANGE, false)}, config3: ${intent.getBooleanExtra(ACTION_CONFIG_3_CHANGE, false)}"
                            showToast("update data: $info")
                        }
                    }
                }
            }

        }
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        // register receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, IntentFilter(ACTION_WORK_UPDATE))

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }
}