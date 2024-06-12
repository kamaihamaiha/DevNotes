package cn.kk.customview.ui

import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import com.airbnb.lottie.LottieAnimationView

class LottieAnimActivity: BaseActivity() {

    val ANIM_LIKE = "ting_icon_knowledge_like.json"
    val ANIM_LIKE2 = "icon_like_2.json"
    val ANIM_VOICE = "icon_voice_wave.json"
    var loadProgress = 0f
    override fun getLayout(): Int {
      return  R.layout.activity_lottie_anim
    }

    lateinit var lav: LottieAnimationView

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        lav = findViewById(R.id.lav)

        lav.setAnimation(ANIM_VOICE)
        lav.progress = 0f

        lav.setOnClickListener {
            playAnim()
        }

    }

    private fun playAnim(){

        lav.playAnimation()
    }
}