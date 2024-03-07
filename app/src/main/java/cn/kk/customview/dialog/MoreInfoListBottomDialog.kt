package cn.kk.customview.dialog

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import cn.kk.base.R
import cn.kk.base.activity.BaseActivity
import cn.kk.base.bean.BaseItem
import cn.kk.base.bean.BaseMoreItem
import cn.kk.base.dialog.BaseListBottomDialog
import cn.kk.customview.activity.NormalWebViewActivity
import cn.kk.customview.utils.SystemUtil
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kk.beatbox.MainActivity

class MoreInfoListBottomDialog(val mActivity: BaseActivity, modelList: MutableList<BaseMoreItem>, val bookType: Int): BaseListBottomDialog<BaseMoreItem>(mActivity, modelList) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tvTitle = findViewById<TextView>(R.id.tv_title)
        tvTitle?.text = "更多内容"
    }

    override fun fillItem(holder: BaseViewHolder, item: BaseMoreItem) {

        holder.setText(R.id.tv_item_home_name, item.title)
        holder.getView<TextView>(R.id.tv_item_home_name).alpha = if (item.finishTag) 1f else 0.4f
//        holder.setTextColor(R.id.tv_item_home_name, ContextCompat.getColor(mActivity, R.color.LightPink))
    }

    override fun onItemClick(model: BaseMoreItem) {
        model.bookType = bookType // 统一给 moreItem 所属的bookType 赋值
        if (!TextUtils.isEmpty(model.appPkgName)) {
            SystemUtil.startAppByPkgName(mActivity, model.appPkgName)
        } else {
            if (model.url.isEmpty()) { // 打开本地页面
                if (model.bookTypeAndroidProgramGuide()) {
                    when (model.item_action) {
                        BaseItem.ACTION_BOOK_ANDROID_PROGRAMMING_APP_BEATBOX -> {
                            mActivity.openNextUI(MainActivity::class.java, model.title)
                        }
                        else -> {}
                    }
                } else {

                }

            } else { // 打开网页
                mActivity.openNextUI(NormalWebViewActivity::class.java, model.title, -1, model.url)
            }
        }

    }


}