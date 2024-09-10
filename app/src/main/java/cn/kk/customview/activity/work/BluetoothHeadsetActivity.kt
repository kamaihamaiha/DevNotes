package cn.kk.customview.activity.work

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.core.app.ActivityCompat
import cn.kk.base.activity.BaseActivity
import cn.kk.base.utils.PermissionsHelper
import cn.kk.customview.R


class BluetoothHeadsetActivity: BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_bluetooth_headset
    }

    val REQUEST_BT_CODE = 1
    val mPermissions = arrayOf(
        Manifest.permission.BLUETOOTH_CONNECT
    )

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()



        val permissionsHelper = PermissionsHelper(mSelf)
        if (permissionsHelper.lacksPermissions(mPermissions)) {
            ActivityCompat.requestPermissions(mSelf, mPermissions, REQUEST_BT_CODE)
        }

    }

    private fun registerBTReceiver() {
        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        registerReceiver(mReceiver, filter)
    }

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_ACL_DISCONNECTED == action) {
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                // 这里可以添加判断逻辑，确认断开的是蓝牙耳机
                Log.d("Bluetooth", "Bluetooth device disconnected: " + device!!.name)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_BT_CODE) {
            if (grantResults.isNotEmpty()) {
                var allGranted = true
                for (grantResult in grantResults) {
                    if (grantResult != 0) {
                        allGranted = false
                        break
                    }
                }
                // 用户同意了权限申请
                if (allGranted) {
                    registerBTReceiver()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 别忘了在 Activity 销毁时取消注册
        unregisterReceiver(mReceiver)
    }

}