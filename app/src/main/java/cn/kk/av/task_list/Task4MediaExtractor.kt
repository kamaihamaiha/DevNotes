package cn.kk.av.task_list

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMuxer
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.kk.base.activity.BaseActivity
import cn.kk.base.utils.SystemHelper
import cn.kk.customview.R
import java.nio.ByteBuffer

/**
 * 任务列表4：
 * Extractor 和 MediaMuxer API，知道如何解析和封装 mp4 文件
 */
class Task4MediaExtractor: BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_task4_meida_extractor

    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    private val notAllowPermissions = mutableListOf<String>()
    private val SDCARD_PATH = Environment.getExternalStorageDirectory().path
    val INPUT_FILE_PATH = SystemHelper.getSdcardPath().plus("/input.mp4")
    val OUTPUT_VIDEO_FILE_PATH = SystemHelper.getSdcardPath().plus("/output_video.mp4")
    val OUTPUT_AUDIO_FILE_PATH = SystemHelper.getSdcardPath().plus("/output_audio.mp3")
    val OUTPUT_MUXER_MEDIA_FILE_PATH = SystemHelper.getSdcardPath().plus("/output_media.mp4")

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        checkPermissions()

        findViewById<Button>(R.id.btn_extra_video).setOnClickListener { extractorVideoOrVideoData(true) }
        findViewById<Button>(R.id.btn_extra_audio).setOnClickListener { extractorVideoOrVideoData(false) }
        findViewById<Button>(R.id.btn_mux_audio_video).setOnClickListener { muxerVideoAndVideo() }
    }

    /**
     * 抽取视频
     */
    private fun extractorVideoOrVideoData(video: Boolean){
        val mediaExtractor = MediaExtractor()
        var mediaMuxer: MediaMuxer?=null
        // 轨道索引
        var videoTrackIndex = -1

        try {// 设置数据源
            mediaExtractor.setDataSource(INPUT_FILE_PATH)
            // 轨道数
            val trackCount = mediaExtractor.trackCount
            for (i in 0 until trackCount) {
                // 视频轨道格式信息
                val trackFormat = mediaExtractor.getTrackFormat(i)
                if (trackFormat.getString(MediaFormat.KEY_MIME)!!.startsWith(if(video) "video/" else "audio/")) {
                    // 该轨道是视频轨道？会不会多次赋值，那是什么情况呢？？？
                    videoTrackIndex = i
                }
            }

            // 切换到想要的轨道
            mediaExtractor.selectTrack(videoTrackIndex)

            // 视频轨道格式信息
            val trackFormat = mediaExtractor.getTrackFormat(videoTrackIndex)

            mediaMuxer = MediaMuxer(if(video) OUTPUT_VIDEO_FILE_PATH else OUTPUT_AUDIO_FILE_PATH, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)

            // 添加媒体通道
            val trackIndex = mediaMuxer.addTrack(trackFormat)

            val byteBuffer = ByteBuffer.allocate(1024 * 500)
            val bufferInfo = MediaCodec.BufferInfo()

            // 开始抽取视频
            mediaMuxer.start()

            // 帧之间到间隔时间
            var videoSampleTime = 0L
            run {
                // 将样本数据存储到缓存区
                mediaExtractor.readSampleData(byteBuffer, 0)

                // 跳转第一个 I frame
                if (mediaExtractor.sampleFlags == MediaExtractor.SAMPLE_FLAG_SYNC) {
                    // 读取下一帧
                    mediaExtractor.advance()
                }
                mediaExtractor.readSampleData(byteBuffer, 0)

                val firstVideoPTS = mediaExtractor.sampleTime

                mediaExtractor.advance()
                mediaExtractor.readSampleData(byteBuffer, 0)
                val secondVideoPTS = mediaExtractor.sampleTime

                videoSampleTime = Math.abs(secondVideoPTS - firstVideoPTS)
            }


            // 什么意思？？？
            mediaExtractor.unselectTrack(videoTrackIndex)
            mediaExtractor.selectTrack(videoTrackIndex)

            var count = 0
            var size = 0L

            while (true) {
                // 将样本数据存储到缓存区
                val readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0)
                if (readSampleSize < 0) {
                    break
                }
                // 读取下一帧
                Log.d(TAG, "extractorVideoData: 读取下一帧")
                mediaExtractor.advance()

                bufferInfo.size = readSampleSize
                bufferInfo.offset = 0
                bufferInfo.flags = mediaExtractor.sampleFlags
                bufferInfo.presentationTimeUs += videoSampleTime

                // 写数据到文件中
                mediaMuxer.writeSampleData(trackIndex, byteBuffer, bufferInfo)
                count++
                size += readSampleSize
            }

            // 释放
            mediaMuxer.stop()
            mediaExtractor.release()
            mediaMuxer.release()

            if (video) {
                Toast.makeText(this, "抽取视频完成: count=${count}, size=${size}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "抽取音频完成: count=${count}, size=${size}", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            if (video) {
                showToast("抽取视频失败：${e.toString()}")
            } else {
                showToast("抽取音频失败：${e.toString()}")
            }

        }
    }

    /**
     * 合成音视频
     */
    private fun muxerVideoAndVideo() {
        try {// step1 找到 output_video.mp4 中的视频轨道
            val videoExtractor = MediaExtractor().apply { setDataSource(OUTPUT_VIDEO_FILE_PATH) }
            var videoTractIndex = -1
            var videoFormat: MediaFormat?=null
            for (index in 0 until videoExtractor.trackCount) {
                if (videoExtractor.getTrackFormat(index).getString(MediaFormat.KEY_MIME)?.startsWith("video/") == true) {
                    videoFormat = videoExtractor.getTrackFormat(index)
                    videoTractIndex = index
                    break
                }
            }

            // step2 找到 output_audio.mp3 中的音频轨道
            val audioExtractor = MediaExtractor().apply { setDataSource(OUTPUT_AUDIO_FILE_PATH) }
            var audioTractIndex = -1
            var audioFormat: MediaFormat?=null
            for (index in 0 until audioExtractor.trackCount) {
                if (audioExtractor.getTrackFormat(index).getString(MediaFormat.KEY_MIME)?.startsWith("audio/") == true) {
                    audioFormat = audioExtractor.getTrackFormat(index)
                    audioTractIndex = index
                    break
                }
            }

            // step3 合成前准备工作
            videoExtractor.selectTrack(videoTractIndex)
            audioExtractor.selectTrack(audioTractIndex)

            val videoBufferInfo = MediaCodec.BufferInfo()
            val audioBufferInfo = MediaCodec.BufferInfo()

            // 合成文件设置
            val mediaMuxer = MediaMuxer(OUTPUT_MUXER_MEDIA_FILE_PATH, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
            val writeVideoTractIndex = mediaMuxer.addTrack(videoFormat!!)   // 添加视频通道
            val writeAudioTractIndex = mediaMuxer.addTrack(audioFormat!!)   // 添加音频通道

            // 开始合成
            mediaMuxer.start()

            val byteBuffer = ByteBuffer.allocate(500 * 1024)
            var sampleTime = 0L

            videoExtractor.readSampleData(byteBuffer, 0)
            if (videoExtractor.sampleFlags == MediaExtractor.SAMPLE_FLAG_SYNC) {
                videoExtractor.advance()
            }

            videoExtractor.readSampleData(byteBuffer, 0)
            val secondTime = videoExtractor.sampleTime
            videoExtractor.advance()

            sampleTime = Math.abs(videoExtractor.sampleTime - secondTime)

            videoExtractor.apply {
                unselectTrack(videoTractIndex)
                selectTrack(videoTractIndex)
            }

            // step4 写视频数据
            while (true) {
                val readSampleSize = videoExtractor.readSampleData(byteBuffer ,0)
                if (readSampleSize < 0) {
                    break
                }

                videoBufferInfo.apply {
                    size = readSampleSize
                    presentationTimeUs += sampleTime
                    offset = 0
                    flags = videoExtractor.sampleFlags
                }
                mediaMuxer.writeSampleData(writeVideoTractIndex, byteBuffer, videoBufferInfo)
                videoExtractor.advance()
            }

            // step5 写音频数据
            while (true) {
                val readAudioSampleSize = audioExtractor.readSampleData(byteBuffer, 0)
                if (readAudioSampleSize < 0) break

                audioBufferInfo.apply {
                    size = readAudioSampleSize
                    presentationTimeUs += sampleTime
                    offset = 0
                    flags = audioExtractor.sampleFlags
                }
                mediaMuxer.writeSampleData(writeAudioTractIndex, byteBuffer, audioBufferInfo)
                audioExtractor.advance()
            }

            // step6 释放资源
            mediaMuxer.stop()
            mediaMuxer.release()
            videoExtractor.release()
            audioExtractor.release()

            showToast("合成完成！")
        } catch (e: Exception) {
            showToast("合成失败：${e.message}")
        }
    }

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE"
    )



    private fun checkPermissions(){
        //6.0 动态权限判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (i in permissions.indices) {
                if (ContextCompat.checkSelfPermission(applicationContext, permissions.get(i))
                    !== PackageManager.PERMISSION_GRANTED
                ) {
                    notAllowPermissions.add(permissions.get(i))
                }
            }
            if (notAllowPermissions.isNotEmpty()) {
                val permissions: Array<String> = notAllowPermissions.toTypedArray<String>()
                ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    REQUEST_EXTERNAL_STORAGE
                )
            }
        }
    }

}