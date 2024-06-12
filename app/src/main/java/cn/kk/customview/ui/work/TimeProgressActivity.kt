package cn.kk.customview.ui.work

import android.view.View
import android.widget.Button
import android.widget.ImageView
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import cn.kk.elementary.chapter1.TimeProgressbar

class TimeProgressActivity: BaseActivity() {
    override fun getLayout(): Int {
      return  R.layout.activity_time_progressbar
    }

    lateinit var scoreDrawable: TextDrawable

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        val timeProgress = findViewById<TimeProgressbar>(R.id.timeProgress)
        val btn_rest = findViewById<Button>(R.id.btn_rest)
        val btn_voice = findViewById<ImageView>(R.id.btn_voice)

        timeProgress.duration = 3000
        timeProgress.hideWhenFinished = false

        timeProgress.start()

        scoreDrawable = TextDrawable(resources, "33", UIHelper.dip2px(this, 60.0))

        btn_rest.visibility = View.INVISIBLE
        timeProgress.progressCallback = object : TimeProgressbar.ProgressCallback{
            override fun onProgress(progress: Long) {
            }

            override fun onFinish() {
                btn_rest.visibility = View.VISIBLE
            }


        }
        btn_rest.setOnClickListener {
            timeProgress.reset()
            timeProgress.start()
            btn_rest.visibility = View.INVISIBLE
        }

        btn_voice.setOnClickListener {
            btn_voice.setImageDrawable(scoreDrawable)
        }
    }
}