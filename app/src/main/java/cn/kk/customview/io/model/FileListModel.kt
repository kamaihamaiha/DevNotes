package cn.kk.customview.io.model

import cn.kk.base.utils.DateHelper

class FileListModel {

    var errno = 0
    var guid_info: String? = null
    var list: List<FileInfo>? = null
    var request_id: Long = 0
    var guid = 0
    class FileInfo {
        val FILE_TYPE_DIR = 1
        val FILE_CATEGORY_VIDEO = 1
        val FILE_CATEGORY_AUDIO = 2
        val FILE_CATEGORY_PIC = 3
        val FILE_CATEGORY_DOCUMENT = 4
        val FILE_CATEGORY_APP = 5
        val FILE_CATEGORY_OHTER = 6
        val FILE_CATEGORY_torrent = 7

        var server_filename: String? = null
        var privacy = 0
        var category = 0 // 文件类型，1 视频、2 音频、3 图片、4 文档、5 应用、6 其他、7 种子
        var unlist = 0
        var fs_id: Long = 0
        var dir_empty = 0
        var server_atime = 0
        var server_ctime = 0
        var local_mtime = 0 // 文件在服务器修改时间
        var size: Long = 0
        var isdir = 0 // 是否为目录，0 文件、1 目录
        var share = 0
        var path: String? = null
        var local_ctime = 0
        var server_mtime = 0
        var empty = 0
        var oper_id: Long = 0
        var thumbs: Thumbs? = null
        var md5: String? = null

        class Thumbs {
            var url3: String? = null
            var url2: String? = null
            var url1: String? = null
        }


        fun getServerATime(): String {
            return DateHelper.getTime(server_mtime * 1000L)
        }

        fun isDirTag(): Boolean = isdir == FILE_TYPE_DIR

        fun isVideoType(): Boolean = category == FILE_CATEGORY_VIDEO

        fun isAudioType(): Boolean = category == FILE_CATEGORY_AUDIO

        fun isPicType(): Boolean = category == FILE_CATEGORY_PIC

        fun isDocumentType(): Boolean = category == FILE_CATEGORY_DOCUMENT

        fun isAppType(): Boolean = category == FILE_CATEGORY_APP

        fun isOthterType(): Boolean = category == FILE_CATEGORY_OHTER

        fun supportType(): Boolean {
            return isDirTag() || isVideoType() || isAudioType() || isDocumentType() || isPicType() || isAppType() || isOthterType()
        }
        fun getHumanSize(): String {
            val kb = size / 1024
            val mb = kb / 1024
            val gb = mb / 1024
            val tb = gb / 1024
            return if (tb > 0) {
                String.format("%.2f TB", tb.toFloat())
            } else if (gb > 0) {
                String.format("%.2f GB", gb.toFloat())
            } else if (mb > 0) {
                String.format("%.2f MB", mb.toFloat())
            } else if (kb > 0) {
                String.format("%.2f KB", kb.toFloat())
            } else {
                String.format("%d B", size)
            }
        }
    }
}