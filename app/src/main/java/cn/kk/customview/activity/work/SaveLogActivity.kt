package cn.kk.customview.activity.work

import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import cn.kk.base.activity.BaseActivity
import cn.kk.base.utils.IOUtils
import cn.kk.base.utils.TimeHelper
import cn.kk.customview.R

class SaveLogActivity: BaseActivity() {

    val filePath by lazy {
        application.filesDir.absolutePath.plus("/logs").plus("/").plus("log_1.txt")
    }

    override fun getLayout(): Int {
        return R.layout.activity_save_log
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        val tv_log = findViewById<TextView>(R.id.tv_log)
        val btn_save_log = findViewById<Button>(R.id.btn_save_log)
        val btn_read_log = findViewById<Button>(R.id.btn_read_log)
        tv_log.movementMethod = ScrollingMovementMethod()

        btn_save_log.setOnClickListener {
           IOUtils.saveData2File("this is a log".plus(" @ ").plus(TimeHelper.getTimeSecond()), filePath)
        }

        btn_read_log.setOnClickListener {
           val log = IOUtils.readFromFile(filePath)
            tv_log.text = log
        }
    }
}