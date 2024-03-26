package cn.kk.customview.activity.work

import android.content.Intent
import android.widget.Button
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import cn.kk.customview.activity.more.WorkActivity

class BroadcastDemoActivity: BaseActivity() {

    var config1Change = false
    var config2Change = false
    var config3Change = false
    override fun getLayout(): Int {
        return R.layout.activity_broadcast_demo
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

       val btn1 = findViewById<Button>(R.id.btn_change_1)
       val btn2 = findViewById<Button>(R.id.btn_change_2)
       val btn3 = findViewById<Button>(R.id.btn_change_3)

        btn1.setOnClickListener {
            config1Change = !config1Change
        }
        btn2.setOnClickListener {
            config2Change = !config2Change
        }
        btn3.setOnClickListener {
            config3Change = !config3Change
        }
    }

    override fun finish() {
        super.finish()
        if (config1Change || config2Change || config3Change) {
            // send broadcast
            val intent = Intent(WorkActivity.ACTION_WORK_UPDATE)
            intent.putExtra(WorkActivity.ACTION_CONFIG_1_CHANGE, config1Change)
            intent.putExtra(WorkActivity.ACTION_CONFIG_2_CHANGE, config2Change)
            intent.putExtra(WorkActivity.ACTION_CONFIG_3_CHANGE, config3Change)
             LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
    }
}