package cn.kk.customview.dialog

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import cn.kk.base.R
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.base.bean.BaseItem
import cn.kk.base.bean.BaseMoreItem
import cn.kk.base.dialog.BaseListBottomDialog
import cn.kk.base.utils.CommonUtil
import cn.kk.bean.XhsUserModel
import cn.kk.customview.activity.NormalWebViewActivity
import cn.kk.customview.utils.SystemUtil
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kk.beatbox.MainActivity

/**
 * 打开小红书，通过 deeplink 方式
 * 小红书 deeplink规则文档: https://pages.xiaohongshu.com/activity/deeplink#toc_14
 */
class XhsListBottomDialog(val mActivity: BaseActivity, val modelList: MutableList<XhsUserModel>): BaseListBottomDialog<XhsUserModel>(mActivity, modelList) {

    init {
        buildData()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tvTitle = findViewById<TextView>(R.id.tv_title)
        tvTitle?.text = "小红书用户主页"
    }

    override fun fillItem(holder: BaseViewHolder, item: XhsUserModel) {
        holder.setText(R.id.tv_item_home_name, item.userName)
    }

    override fun onItemClick(model: XhsUserModel) {
        if (CommonUtil.hasInstallXhs(mActivity)) {
            CommonUtil.openSpecialApp(mActivity, model.getDeepLinkUserHome())
        } else {
            CommonUtil.gotoMarketDownload(mActivity, CommonUtil.XHS_PKG_NAME)
        }

    }

    fun buildData() {
        modelList.add(XhsUserModel("5b16a87ee8ac2b2b2bbd55f1", "我的主页"))
        modelList.add(XhsUserModel("5c34870c0000000007008eb2", "厨匠东哥"))
    }

}