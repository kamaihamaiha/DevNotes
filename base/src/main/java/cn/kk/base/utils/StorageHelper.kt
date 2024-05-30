package cn.kk.base.utils

import cn.kk.base.BaseApp

object StorageHelper {

    val RECORD_AUDIO_FILE_PATH: String
    init {
        RECORD_AUDIO_FILE_PATH = BaseApp.application.filesDir.absolutePath + "/record"
    }

    fun getRecordAudioFilePath(fileName: String): String {
        return "$RECORD_AUDIO_FILE_PATH/$fileName"
    }
}