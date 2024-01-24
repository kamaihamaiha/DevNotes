package cn.kk.av.task_list.task15

import android.widget.Button
import android.widget.TextView
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import com.kk.ffmpegdemo.NativeFFmpegDemo
import com.kk.ffmpegdemo.widget.FFVideoView

class Task15SimplePlayer: BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_task15_simple_player
    }

    val videoPath = "/sdcard/av/videos/out.mp4"

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        val nativeFFmpegDemo = NativeFFmpegDemo()
        findViewById<TextView>(R.id.tv_av_format_info).text = nativeFFmpegDemo.fFmpegAvFormatVersion

        findViewById<Button>(R.id.btn_play_video).setOnClickListener {
            findViewById<FFVideoView>(R.id.ff_video).playVideo(videoPath)
        }
    }
}