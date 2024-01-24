package cn.kk.av.task_list

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.*
import android.os.Build
import android.os.Environment
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.kk.av.task_list.task2.AudioConfig
import cn.kk.av.task_list.task2.PcmToWavUtil
import cn.kk.base.activity.BaseActivity
import cn.kk.base.utils.ThreadHelper
import cn.kk.customview.R
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * 任务列表2：使用 AudioRecord 和 AudioTrack API 完成音频 PCM 数据的采集和播放，并实现读写音频 wav 文件
 *
 */
class Task2AudioRecord: BaseActivity() {

    override fun getLayout(): Int = R.layout.activity_task2_audio_record


    val REQUEST_PERMISSION = 1001
    val AUDIO_FILE_NAME = "foo.pcm"
    val AUDIO_wav_FILE_NAME = "foo.wav"
    private val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val notAllowPermissions = mutableListOf<String>()
    @Volatile
    private  var  isRecording = false
    private var playState = false
    private var mAudioRecord: AudioRecord? = null
    private var mAudioTrack: AudioTrack? = null

    val btn_record_audio by lazy {
        findViewById<Button>(R.id.btn_record_audio)
    }

    val btn_play_pcm_audio by lazy {
        findViewById<Button>(R.id.btn_play_pcm_audio)
    }

    val btn_play_wav_audio by lazy {
        findViewById<Button>(R.id.btn_play_wav_audio)
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        checkPermissions()

        // 录音
        btn_record_audio.setOnClickListener {
            if (isRecording) {
                stopRecord()
            } else {
                startRecord()
            }
        }

        // 播放/暂停
        btn_play_pcm_audio.setOnClickListener {
            if (playState) {
                stopPlayAudio()
            } else {
                startPlayAudio()
            }
        }
        btn_play_wav_audio.setOnClickListener {
            if (playState) {
                stopPlayAudio(false)
            } else {
                startPlayAudio(false)
            }
        }

        // pcm 2 wav
        val btn_pcm2wav = findViewById<Button>(R.id.btn_pcm2wav)
        btn_pcm2wav.setOnClickListener {
            pcm2wav()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    showToast(permissions[i].plus("权限被用户禁止！"))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRecording = true
    }

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
                    REQUEST_PERMISSION
                )
            }
        }
    }

    private fun changeRecordBtnState(){
        if (isRecording) {
            btn_record_audio.setTextColor(Color.RED)
            btn_record_audio.text = getString(R.string.audio_record_stop)
        } else {
            btn_record_audio.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btn_record_audio.text = getString(R.string.audio_record_start)
        }
    }

    private fun changePlayBtnState(pcm: Boolean = true){
        if (playState) {

            if (pcm) {
                btn_play_pcm_audio.setTextColor(Color.RED)
                btn_play_pcm_audio.text = getString(R.string.audio_play_stop)
            } else {
                btn_play_wav_audio.setTextColor(Color.RED)
                btn_play_wav_audio.text = getString(R.string.audio_play_stop)
            }

        } else {
            if (pcm) {
                btn_play_pcm_audio.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                btn_play_pcm_audio.text = getString(R.string.audio_play_pcm_start)
            } else {
                btn_play_wav_audio.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                btn_play_wav_audio.text = getString(R.string.audio_play_wav_start)
            }

        }
    }

    /**
     * 开始录音
     */
    private fun startRecord(){
        val minBufferSize = AudioRecord.getMinBufferSize(AudioConfig.SAMPLE_RATE_WITH_HZ, AudioConfig.CHANNEL_CONFIG, AudioConfig.AUDIO_FORMAT)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mAudioRecord = AudioRecord(AudioConfig.AUDIO_SOURCE_MIC, AudioConfig.SAMPLE_RATE_WITH_HZ, AudioConfig.CHANNEL_CONFIG, AudioConfig.AUDIO_FORMAT, minBufferSize)
        val data = ByteArray(minBufferSize)

        // 输出的音频文件
        val outputAudioFile = File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), AUDIO_FILE_NAME)
        if (!outputAudioFile.mkdir()) {
//            showToast("目录未创建！")
//            return
        }
        showToast("start record: ${outputAudioFile.absolutePath}")
        if (outputAudioFile.exists()) { // 删除旧文件
            outputAudioFile.delete()
        }

        // start recording
        mAudioRecord?.startRecording()
        isRecording = true
        changeRecordBtnState()

        ThreadHelper.runTask {
            val fos = FileOutputStream(outputAudioFile)

            while (isRecording) {
                val read = mAudioRecord?.read(data, 0, minBufferSize)
                // 如果读取数据无误，则写入文件中
                if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                    fos.write(data)
                }
            }

            fos.close()
        }
    }

    /**
     * 停止录音
     */
    private fun stopRecord(){
        isRecording = false
        changeRecordBtnState()
        // 释放资源
        if (mAudioRecord != null) {
            mAudioRecord!!.stop()
            mAudioRecord!!.release()
            mAudioRecord = null
        }
    }

    /**
     * 播放 pcm 音频，使用 stream 模式
     */
    private fun startPlayAudio(pcm: Boolean = true){
        val channelConfig = AudioFormat.CHANNEL_OUT_MONO
        val minBufferSize = AudioTrack.getMinBufferSize(AudioConfig.SAMPLE_RATE_WITH_HZ, channelConfig, AudioConfig.AUDIO_FORMAT)
        mAudioTrack = AudioTrack(
            AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build(),
            AudioFormat.Builder()
                .setSampleRate(AudioConfig.SAMPLE_RATE_WITH_HZ)
                .setEncoding(AudioConfig.AUDIO_FORMAT)
                .setChannelMask(channelConfig)
                .build(),
            minBufferSize,
            AudioTrack.MODE_STREAM,
            AudioManager.AUDIO_SESSION_ID_GENERATE
        )
        mAudioTrack?.play()
        playState = true
        changePlayBtnState(pcm)

        val inputAudioFile = File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), if(pcm) AUDIO_FILE_NAME else AUDIO_wav_FILE_NAME)
        val fis = FileInputStream(inputAudioFile)
        ThreadHelper.runTask {
            val buffer = ByteArray(minBufferSize)
            while (fis.available() > 0) {
                val readCount = fis.read(buffer)
                if (readCount == AudioTrack.ERROR_INVALID_OPERATION || readCount == AudioTrack.ERROR_BAD_VALUE) {
                    continue
                }
                mAudioTrack?.write(buffer, 0, readCount)
            }
            // 播放结束
            playState = false
            ThreadHelper.runOnUIThread { changePlayBtnState(pcm) }
        }
    }

    private fun stopPlayAudio(pcm: Boolean = true){
        mAudioTrack?.stop()
        mAudioTrack?.release()
        playState = false
        changePlayBtnState(pcm)
    }

    private fun pcm2wav(){
        val pcmToWavUtil = PcmToWavUtil(AudioConfig.SAMPLE_RATE_WITH_HZ, AudioConfig.CHANNEL_CONFIG, AudioConfig.AUDIO_FORMAT)
        val pcmFile = File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), AUDIO_FILE_NAME)
        val wavFile = File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), AUDIO_wav_FILE_NAME)
        if (wavFile.exists()) {
            wavFile.delete()
        }
        pcmToWavUtil.pcmToWav(pcmFile.absolutePath, wavFile.absolutePath)
    }
}