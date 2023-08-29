package cn.kk.customview.activity.work

import android.content.ComponentName
import android.content.pm.PackageManager
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import kotlinx.android.synthetic.main.activity_change_launcher_icon.*

/**
 * 改变app icon
 * 参考：https://github.com/running-libo/AppIconChange
 * 相关文章：
 * 1 jianshu.com/p/5852ec1a6037
 * 2
 *
 */
class ChangeLauncherIconActivity: BaseActivity() {
    val defaultComponentName = "cn.kk.customview.activity.HomeTabActivity"
    val vipComponentName = "cn.kk.customview.VipLauncherActivity"
    override fun getLayout(): Int {
        return R.layout.activity_change_launcher_icon
    }

    private var mPackageManager: PackageManager? = null

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        mPackageManager = applicationContext.packageManager
        val fromActivity = intent.getStringExtra("activity")?:""
        updateAppIconGuide(fromActivity == vipComponentName)
        btn_change_app_icon.setOnClickListener {
            if (fromActivity == vipComponentName) {
                enableComponent(ComponentName(baseContext, defaultComponentName))
            } else {
                enableComponent(ComponentName(baseContext, vipComponentName))
            }
            disableComponent(fromActivity)
        }
    }

    //启用组件
    private fun enableComponent(componentName: ComponentName){
        mPackageManager?.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
        )
    }

    //隐藏组件
    private fun disableComponent(componentName: String){
        mPackageManager?.setComponentEnabledSetting(
            ComponentName(baseContext, componentName),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )
    }

    private fun updateAppIconGuide(vip: Boolean){
        iv_app_launcher_cur.setImageDrawable(getDrawable(if (vip) R.drawable.icon_cat_v2 else R.drawable.icon_cat))
        iv_app_launcher_next.setImageDrawable(getDrawable(if (vip) R.drawable.icon_cat else R.drawable.icon_cat_v2))
    }

}