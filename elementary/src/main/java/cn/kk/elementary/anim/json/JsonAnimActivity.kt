package cn.kk.elementary.anim.json

import android.os.Bundle
import cn.kk.base.activity.BaseActivity
import cn.kk.elementary.R
import kotlinx.android.synthetic.main.activity_anim_json.*

class JsonAnimActivity: BaseActivity() {

    var count = 0
    val anims = arrayOf("home.json", "ting.json", "learn.json", "account.json")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_anim_json)

//        lottieAnim.setAnimation("articleLoading.json")
//        lottieAnim.loop(true)

        btn_play.setOnClickListener {

            lottieAnim.setAnimation(anims[count++ % 4])
            
            lottieAnim.playAnimation()

        }
    }
}