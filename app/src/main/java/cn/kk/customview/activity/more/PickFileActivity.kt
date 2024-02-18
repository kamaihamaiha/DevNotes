package cn.kk.customview.activity.more

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity
import cn.kk.base.utils.SystemHelper
import cn.kk.customview.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * https://developer.android.com/training/data-storage/shared/documents-files?hl=zh-cn
 * SAF 框架
 * get real path from uri: http://www.java2s.com/example/android/android.media/get-real-file-path-from-a-uri-get-the-the-path-for-storage-access-fra.html
 *
 * ---
 * [访问共享存储空间中的媒体文件 -- 官方文档](https://developer.android.com/training/data-storage/shared/media?hl=zh-cn#kotlin)
 * [MediaStore 示例](https://github.com/android/storage-samples/tree/main/MediaStore)
 *
 * VLC android 是可以访问文件的，也没有申请权限，是怎么做到的？ 看看源码：https://github.com/videolan/vlc-android
 * ---
 * [打开系统文件管理器，博客1](https://juejin.cn/post/7015529877717647373)
 * [打开系统文件管理器，博客2](https://blog.51cto.com/u_13259/6538488)
 */
class PickFileActivity: BaseActivity() {

    val PICK_FILE_CODE = 100
    override fun getLayout(): Int {
        return R.layout.activity_pick_file
    }
    lateinit var tvFileInfo: TextView
    lateinit var tvFilePath: TextView
    lateinit var tvFileName: TextView

    private val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

    val myExoPlayer by lazy {
        ExoPlayer.Builder(this).build()
    }
    var mCurMediaUri: Uri? = null


    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        val btnPickVideo = findViewById<Button>(R.id.btn_pick_video)
        val btnPickAudio = findViewById<Button>(R.id.btn_pick_audio)
        val btnPlay = findViewById<Button>(R.id.btn_play)
        tvFileInfo = findViewById(R.id.tv_file_info)
        tvFilePath = findViewById(R.id.tv_file_path)
        tvFileName = findViewById(R.id.tv_file_name)

        btnPickVideo.setOnClickListener {
            chooseFile()
        }
        btnPickAudio.setOnClickListener {
            chooseFile(false)
        }

        btnPlay.setOnClickListener {
            if (mCurMediaUri == null) {
                UIHelper.toast("请先选择文件", this@PickFileActivity)
                return@setOnClickListener
            }
            tvFileName.text = getNameFromURI(mCurMediaUri!!)
            initPlayer()
        }


        tvFileInfo.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(view: View?): Boolean {
                // set text to clipboard
                SystemHelper.setClipboardText(tvFileInfo.text.toString(), this@PickFileActivity)
                return true
            }

        })
    }

    override fun onPause() {
        super.onPause()
        if (myExoPlayer.isPlaying) {
            myExoPlayer.pause()
        }
    }

    override fun onStop() {
        super.onStop()
        if (myExoPlayer.isPlaying) {
            myExoPlayer.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (myExoPlayer.isPlaying) {
            myExoPlayer.stop()
            myExoPlayer.release()
        }
    }
    fun chooseFile(videoType: Boolean = true){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            if (videoType) {
                type = "video/*"
            } else {
                type = "audio/*"
            }
        }
        startActivityForResult(intent, PICK_FILE_CODE)
    }

    private fun initPlayer(){
        val mediaItem = MediaItem.Builder()
            .setUri(mCurMediaUri)
            .build()
        myExoPlayer.apply {
            setMediaItem(mediaItem)
            prepare()
        }
        val playView = findViewById<PlayerView>(cn.kk.base.R.id.player_view)
        playView.player = myExoPlayer
        myExoPlayer.play()
    }



    /**
     * 概念:
     * 内容提供器Uri
     * 文件系统路径Uri
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // parse file url from data
        if (requestCode == PICK_FILE_CODE && resultCode == Activity.RESULT_OK) {

            // get real path from uri
//             val realPath = getRealPathFromURI(data?.data)

            data?.data.also {
                // perform operations on the document using its URI
                mCurMediaUri = it
                tvFileInfo.text = it?.path
                tvFilePath.text = getRealPathFromURI(it!!)
            }

        }

    }

    fun getRealPathFromURI(contentUri: Uri): String? {
        var filePath: String?=null
        val cursor = contentResolver.query(contentUri, null, null, null, null)
        if (cursor == null) {
            filePath = contentUri.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DOCUMENT_ID)
            filePath = cursor.getString(idx)
            cursor.close()
        }
        return filePath
    }

    fun getNameFromURI(contentUri: Uri): String? {
        var fileName: String?=null
        val cursor = contentResolver.query(contentUri, null, null, null, null)
        if (cursor == null) {
            fileName = contentUri.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)
            fileName = cursor.getString(idx)
            cursor.close()
        }
        return fileName
    }

    /**
     * 保留权限
     * 当您的应用打开文件进行读取或写入时，系统会向应用授予对该文件的 URI 的访问权限，该授权在用户重启设备之前一直有效。但是，假设您的应用是图片编辑应用，而且您希望用户能够直接从应用中访问他们最近修改的 5 张图片，那么在用户重启设备后，您就必须让用户返回到系统选择器来查找这些文件。
     * 如需在设备重启后保留对文件的访问权限并提供更出色的用户体验，您的应用可以“获取”系统提供的永久性 URI 访问权限，如以下代码段所示
     */
    fun keepPermission(uri: Uri) {
        // 保留权限
        // 当您的应用打开文件进行读取或写入时，系统会向应用授予对该文件的 URI 的访问权限，该授权在用户重启设备之前一直有效。但是，假设您的应用是图片编辑应用，而且您希望用户能够直接从应用中访问他们最近修改的 5 张图片，那么在用户重启设备后，您就必须让用户返回到系统选择器来查找这些文件。
        // 如需在设备重启后保留对文件的访问权限并提供更出色的用户体验，您的应用可以“获取”系统提供的永久性 URI 访问权限，如以下代码段所示
        // val takeFlags: Int = intent.flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        // contentResolver.takePersistableUriPermission(uri, takeFlags)
        val contentResolver = applicationContext.contentResolver

        val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        // Check for the freshest data.
        contentResolver.takePersistableUriPermission(uri, takeFlags)
    }

    /**
     * 检查文档元数据
     * 获得文档的 URI 后，便可以访问该文档的元数据。以下代码段用于获取 URI 所指定文档的元数据，并将其记入日志：
     */

//    val mContentResolver = applicationContext.contentResolver

    fun dumpImageMetaData(uri: Uri) {

        // The query, because it only applies to a single document, returns only
        // one row. There's no need to filter, sort, or select fields,
        // because we want all fields for one document.
        val cursor: Cursor? = contentResolver.query(
            uri, null, null, null, null, null)

        cursor?.use {
            // moveToFirst() returns false if the cursor has 0 rows. Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (it.moveToFirst()) {

                // Note it's called "Display Name". This is
                // provider-specific, and might not necessarily be the file name.
                val displayName: String =
                    it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                Log.i(TAG, "Display Name: $displayName")

                val sizeIndex: Int = it.getColumnIndex(OpenableColumns.SIZE)
                // If the size is unknown, the value stored is null. But because an
                // int can't be null, the behavior is implementation-specific,
                // and unpredictable. So as
                // a rule, check if it's null before assigning to an int. This will
                // happen often: The storage API allows for remote files, whose
                // size might not be locally known.
                val size: String = if (!it.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
                    it.getString(sizeIndex)
                } else {
                    "Unknown"
                }
                Log.i(TAG, "Size: $size")
            }
        }
    }

    /**
     * 打开文档
     * 1. 位图
     * 2. 输入流
     */


    // 输入流
    @Throws(IOException::class)
    private fun readTextFromUri(uri: Uri): String {
        val stringBuilder = StringBuilder()
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        return stringBuilder.toString()
    }
}