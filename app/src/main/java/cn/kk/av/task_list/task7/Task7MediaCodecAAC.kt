package cn.kk.av.task_list.task7

import android.media.*
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import cn.kk.base.activity.BaseActivity
import cn.kk.base.utils.PermissionsHelper
import cn.kk.base.utils.StorageHelper
import cn.kk.base.utils.ThreadHelper
import cn.kk.customview.R
import java.io.*
import java.nio.ByteBuffer

/**
 * MediaCodec 播放 pcm：
 * https://juejin.cn/post/7002158083925360647
 * https://juejin.cn/post/6844903929440911367
 */
class Task7MediaCodecAAC: BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_task7_media_codec_aac
    }

    companion object {
        val fileAACPath: String = StorageHelper.getRecordAudioFilePath("demo.aac")
        val fileAACFromPcmPath: String = StorageHelper.getRecordAudioFilePath("eudic-from-pcm.aac")
        val filePcmPath: String = StorageHelper.getRecordAudioFilePath("demo.pcm")
        val filePcmOriginalPath: String = StorageHelper.getRecordAudioFilePath("eudic_demo.pcm")
    }

    private lateinit var permissionsHelper: PermissionsHelper
    //定位权限,获取app内常用权限
    var permsLocation = arrayOf(
        "android.permission.READ_PHONE_STATE",
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.CAMERA",
        "android.permission.RECORD_AUDIO"
    )

    private var isRecord = false //是否在录制，默认没在录制


    private var mediaCodec: MediaCodec? = null
    private val samples_per_frame = 2048
    private var encodeBufferInfo: MediaCodec.BufferInfo? = null
    private var encodeInputBuffers: Array<ByteBuffer>?= null
    private var encodeOutputBuffers: Array<ByteBuffer>?= null
    private var chunkAudio = ByteArray(0)
    private var out: BufferedOutputStream? = null
    private var audioRecordThread: AudioRecordThread? = null
    private var audioRecordPCMThread: AudioRecordPcmThread? = null
    private lateinit var btnRecordAAC: TextView
    private lateinit var btnRecordPCM: TextView

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        permissionsHelper = PermissionsHelper(mSelf)
        if (permissionsHelper.lacksPermissions(permsLocation)) {
            ActivityCompat.requestPermissions(mSelf, permsLocation, 1)
        }

        btnRecordAAC = findViewById(R.id.btn_record_aac)
        btnRecordPCM = findViewById(R.id.btn_record_pcm)
        btnRecordAAC.setOnClickListener {
            if (isRecord) {
                stopRecordAudio()
            } else {
                startRecordAudio()
            }
        }
        btnRecordPCM.setOnClickListener {
            if (isRecord) {
                stopRecordPCMAudio()
            } else {
                startRecordPcmAudio()
            }
        }

        findViewById<View>(R.id.btn_aac_convert_pcm).setOnClickListener { aac2Pcm() }
        findViewById<View>(R.id.btn_pcm_convert_aac).setOnClickListener { pcm2Aac() }
        findViewById<View>(R.id.btn_play_aac).setOnClickListener { startPlayAudio() }
        findViewById<View>(R.id.btn_play_pcm).setOnClickListener { startPlayAudioOriginal() }

    }

    private fun startRecordAudio() {
        btnRecordAAC.text = "正在录制..."
        isRecord = true
        //1.开启录音线程并准备录音
        audioRecordThread = AudioRecordThread()
        audioRecordThread?.start()
    }

    private fun startRecordPcmAudio(){
        isRecord = true
        audioRecordPCMThread = AudioRecordPcmThread()
        audioRecordPCMThread?.start()
        btnRecordPCM.text = "正在录制..."

    }

    private fun stopRecordAudio() {
        btnRecordAAC.text = "录制 AAC 音频"
        isRecord = false
    }

    private fun stopRecordPCMAudio() {
        btnRecordPCM.text = "录制 pcm 音频"
        isRecord = false
    }

    private fun startPlayAudio(){
        PlayPcmUtils(filePcmPath).playPcm()
    }

    private fun startPlayAudioOriginal(){
        PlayPcmUtils(filePcmOriginalPath).playPcm()
    }

    private fun aac2Pcm(){
        ThreadHelper.run { AACToPCM(fileAACPath, filePcmPath).decode() }
    }
    private fun pcm2Aac() {
        ThreadHelper.run {
            PCMToAAC(fileAACFromPcmPath, filePcmOriginalPath).apply {
                readInputStream(
                    File(filePcmOriginalPath), object : ResultCallback {
                        override fun onSuccess(res: String) {
                            showToast(res)
                        }

                        override fun onError(err: String) {
                            showToast(err)
                        }

                    }
                )
            }
        }
    }

    /**
     * 初始化AAC编码器
     */
    private fun initAACMediaEncode() {
        try {
            //参数对应-> mime type、采样率、声道数
            val encodeFormat =
                MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_AAC, 16000, 1)
            encodeFormat.setInteger(MediaFormat.KEY_BIT_RATE, 64000) //比特率
            encodeFormat.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1)
            encodeFormat.setInteger(MediaFormat.KEY_CHANNEL_MASK, AudioFormat.CHANNEL_IN_MONO)
            encodeFormat.setInteger(
                MediaFormat.KEY_AAC_PROFILE,
                MediaCodecInfo.CodecProfileLevel.AACObjectLC
            )
            encodeFormat.setInteger(
                MediaFormat.KEY_MAX_INPUT_SIZE,
                samples_per_frame
            ) //作用于inputBuffer的大小
            mediaCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_AUDIO_AAC)
            mediaCodec?.configure(encodeFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (mediaCodec == null) {
            Log.e("wqs", "create mediaEncode failed")
            return
        }
        mediaCodec?.start()
        encodeInputBuffers = mediaCodec?.getInputBuffers()
        encodeOutputBuffers = mediaCodec?.getOutputBuffers()
        encodeBufferInfo = MediaCodec.BufferInfo()
    }

   inner class AudioRecordThread internal constructor() : Thread() {
        private val audioRecord: AudioRecord?
        override fun run() {
            super.run()
            var fos: FileOutputStream? = null
            try {
                val file = File(fileAACPath)
                if (!file.exists()) {
                    file.parentFile.mkdirs()
                    file.createNewFile()
                }

                fos = FileOutputStream(fileAACPath)
                audioRecord!!.startRecording()
                while (isRecord) {
                    val byteBuffer = ByteArray(samples_per_frame)
                    val end = audioRecord.read(byteBuffer, 0, byteBuffer.size)
                    if (end == AudioRecord.ERROR_BAD_VALUE || end == AudioRecord.ERROR_INVALID_OPERATION) Log.e(
                        "wqs",
                        "Read error"
                    )
                    if (audioRecord != null && end > 0) {
                        dstAudioFormatFromPCM(byteBuffer)
                    }
                }
            } catch (e: FileNotFoundException) {
                ThreadHelper.runOnUIThread { showToast(e.toString()) }
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        init {
            /**
             * 1.设置缓冲区大小
             * 参数:采样率 16k; 通道数 单通道; 采样位数
             */
            val bufferSize = AudioRecord.getMinBufferSize(
                16000,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT * 1
            )
            /**
             * 2.初始化AudioRecord
             * 参数:录音来源 麦克风; 采样率 16k; 通道数 单通道 ;采样位数/数据格式 pcm; 缓冲区大小
             */
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC, 16000,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize
            )
            try {
                out = BufferedOutputStream(FileOutputStream(File(fileAACPath), false))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            initAACMediaEncode()
        }
    }


    /**
     * 编码PCM数据 得到AAC格式的音频文件
     */
    private fun dstAudioFormatFromPCM(pcmData: ByteArray) {
        val inputIndex: Int
        val inputBuffer: ByteBuffer
        var outputIndex: Int
        var outputBuffer: ByteBuffer
        var outBitSize: Int
        var outPacketSize: Int
        val PCMAudio: ByteArray
        PCMAudio = pcmData
        encodeInputBuffers = mediaCodec!!.inputBuffers
        encodeOutputBuffers = mediaCodec!!.outputBuffers
        encodeBufferInfo = MediaCodec.BufferInfo()
        inputIndex = mediaCodec!!.dequeueInputBuffer(0)
        inputBuffer = encodeInputBuffers!![inputIndex]
        inputBuffer.clear()
        inputBuffer.limit(PCMAudio.size)
        inputBuffer.put(PCMAudio) //PCM数据填充给inputBuffer
        mediaCodec!!.queueInputBuffer(inputIndex, 0, PCMAudio.size, 0, 0) //通知编码器 编码
        outputIndex = mediaCodec!!.dequeueOutputBuffer(encodeBufferInfo!!, 0)
        while (outputIndex > 0) {
            outBitSize = encodeBufferInfo!!.size
            outPacketSize = outBitSize + 7 //7为ADT头部的大小
            outputBuffer = encodeOutputBuffers!![outputIndex] //拿到输出Buffer
            outputBuffer.position(encodeBufferInfo!!.offset)
            outputBuffer.limit(encodeBufferInfo!!.offset + outBitSize)
            chunkAudio = ByteArray(outPacketSize)
            addADTStoPacket(chunkAudio, outPacketSize) //添加ADTS
            outputBuffer[chunkAudio, 7, outBitSize] //将编码得到的AAC数据 取出到byte[]中
            try {
                //录制aac音频文件，保存在手机内存中
                out!!.write(chunkAudio, 0, chunkAudio.size)
                out?.flush()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            outputBuffer.position(encodeBufferInfo!!.offset)
            mediaCodec!!.releaseOutputBuffer(outputIndex, false)
            outputIndex = mediaCodec!!.dequeueOutputBuffer(encodeBufferInfo!!, 0)
        }
    }


    /**
     * 添加ADTS头
     *
     * @param packet
     * @param packetLen
     */
    private fun addADTStoPacket(packet: ByteArray, packetLen: Int) {
        val profile = 2 // AAC LC
        val freqIdx = 8 // 16KHz
        val chanCfg = 1 // CPE

        // fill in ADTS data
        packet[0] = 0xFF.toByte()
        packet[1] = 0xF1.toByte()
        packet[2] = ((profile - 1 shl 6) + (freqIdx shl 2) + (chanCfg shr 2)).toByte()
        packet[3] = ((chanCfg and 3 shl 6) + (packetLen shr 11)).toByte()
        packet[4] = (packetLen and 0x7FF shr 3).toByte()
        packet[5] = ((packetLen and 7 shl 5) + 0x1F).toByte()
        packet[6] = 0xFC.toByte()
    }

    inner class AudioRecordPcmThread internal constructor() : Thread() {
        private val audioRecord: AudioRecord
        private val bufferSize: Int
        override fun run() {
            super.run()
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(filePcmOriginalPath)
                audioRecord.startRecording()
                val byteBuffer = ByteArray(bufferSize)
                while (isRecord) {
                    //3.不断读取录音数据并保存至文件中
                    val end = audioRecord.read(byteBuffer, 0, byteBuffer.size)
                    fos!!.write(byteBuffer, 0, end)
                    fos.flush()
                }
                //4.当执行stop()方法后state != RecordState.RECORDING，终止循环，停止录音
                audioRecord.stop()
            } catch (e: Exception) {
            } finally {
                try {
                    fos?.close()
                } catch (e: IOException) {
                }
            }
        }

        init {
            /**
             * 1.设置缓冲区大小
             * 参数:采样率 16k; 通道数 单通道; 采样位数
             */
            bufferSize = AudioRecord.getMinBufferSize(
                16000,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT * 1
            )
            /**
             * 2.初始化AudioRecord
             * 参数:录音来源 麦克风; 采样率 16k; 通道数 单通道 ;采样位数/数据格式 pcm; 缓冲区大小
             */
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC, 16000,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize
            )
        }
    }

}