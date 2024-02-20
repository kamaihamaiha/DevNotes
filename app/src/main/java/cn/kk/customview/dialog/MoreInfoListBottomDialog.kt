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
import cn.kk.customview.activity.NormalWebViewActivity
import cn.kk.customview.utils.SystemUtil
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kk.beatbox.MainActivity

class MoreInfoListBottomDialog(val mActivity: BaseActivity, modelList: MutableList<BaseMoreItem>): BaseListBottomDialog<BaseMoreItem>(mActivity, modelList) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tvTitle = findViewById<TextView>(R.id.tv_title)
        tvTitle?.text = "更多内容"
    }

    override fun fillItem(holder: BaseViewHolder, item: BaseMoreItem) {

        holder.setText(R.id.tv_item_home_name, item.title)
//        holder.setTextColor(R.id.tv_item_home_name, ContextCompat.getColor(mActivity, R.color.LightPink))
    }

    override fun onItemClick(model: BaseMoreItem) {
        if (!TextUtils.isEmpty(model.appPkgName)) {
            SystemUtil.startAppByPkgName(mActivity, model.appPkgName)
        } else {
            if (model.url.isEmpty()) {
//            mActivity.openNextUI()
                if (model.item_action == BaseItem.ACTION_BOOK_ANDROID_PROGRAMMING_APP_BEATBOX) {
                    mActivity.openNextUI(MainActivity::class.java, model.title)
                } else {
                    UIHelper.toast("todo...", mActivity)
                }
            } else {
                mActivity.openNextUI(NormalWebViewActivity::class.java, model.title, -1, model.url)
            }
        }

    }


}