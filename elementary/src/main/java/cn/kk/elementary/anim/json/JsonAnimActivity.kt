package cn.kk.elementary.anim.json

import android.widget.Button
import cn.kk.base.activity.BaseActivity
import cn.kk.base.bean.ListItemAction
import cn.kk.elementary.R
import com.airbnb.lottie.LottieAnimationView

/**
 * 动画
 * 使用 LottieAnimationView 播放 Json 格式的 After Effects 动画
 */
class JsonAnimActivity: BaseActivity() {

    var count = 0
    val anims = arrayOf("home.json", "ting.json", "learn.json", "account.json")

    override fun getLayout(): Int = R.layout.activity_anim_json

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        val lottieAnim = findViewById<LottieAnimationView>(R.id.lottieAnim)
        findViewById<Button>(R.id.btn_play).setOnClickListener {
            lottieAnim.setAnimation(itemList[count++ % 4].title)
            lottieAnim.playAnimation()

        }
    }

    override fun getItemNameList(): MutableList<ListItemAction> {
        val list = mutableListOf<ListItemAction>()
        anims.toMutableList().forEach {
            list.add(ListItemAction(it, true))
        }
       return list
    }
}