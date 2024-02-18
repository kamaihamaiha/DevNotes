package cn.kk.customview.activity.more

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Button
import android.widget.TextView
import cn.kk.base.activity.BaseActivity
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

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        val btnPick = findViewById<Button>(R.id.btn_pick)
        tvFileInfo = findViewById(R.id.tv_file_info)

        btnPick.setOnClickListener {
            chooseFile()
        }
    }

    fun chooseFile(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        startActivityForResult(intent, PICK_FILE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // parse file url from data
        if (requestCode == PICK_FILE_CODE && resultCode == Activity.RESULT_OK) {

            // get real path from uri
//             val realPath = getRealPathFromURI(data?.data)

            data?.data.also {
                // perform operations on the document using its URI
                tvFileInfo.text = it?.path
            }

        }

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