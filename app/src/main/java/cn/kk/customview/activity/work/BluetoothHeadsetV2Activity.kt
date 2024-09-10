package cn.kk.customview.activity.work

import android.graphics.Color
import android.media.AudioDeviceCallback
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import android.util.Log
import android.widget.TextView
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R


class BluetoothHeadsetV2Activity: BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_bluetooth_headset
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        registerBTReceiver()
    }

    private fun registerBTReceiver() {
        val tvInfo = findViewById<TextView>(R.id.tv_info)
        (getSystemService(AUDIO_SERVICE) as AudioManager).run {
            registerAudioDeviceCallback(object : AudioDeviceCallback() {
                override fun onAudioDevicesAdded(addedDevices: Array<out AudioDeviceInfo>?) {
                    tvInfo.text = ""
                    tvInfo.setTextColor(Color.GREEN)
                    // Device Added
                    addedDevices?.forEach {
                        if (it.type == AudioDeviceInfo.TYPE_BLUETOOTH_SCO || it.type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP) {
                            tvInfo.append("Bluetooth device connected: " + it.productName + "\n")
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            Log.d(TAG, "onAudioDevicesAdded: type: " + it.type + ", address: " + it.address + ", name: " + it.productName)
                        }
                    }
                }

                override fun onAudioDevicesRemoved(removedDevices: Array<out AudioDeviceInfo>?) {
                    // Device Removed
                    tvInfo.text = ""
                    tvInfo.setTextColor(Color.RED)
                    removedDevices?.forEach {
                        if (it.type == AudioDeviceInfo.TYPE_BLUETOOTH_SCO || it.type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP) {
                            tvInfo.append("Bluetooth device removed: " + it.productName + "\n")
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            Log.d(TAG, "onAudioDevices removed: type: " + it.type + ", address: " + it.address + ", name: " + it.productName)
                        }
                    }
                }
            }, null)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
    }

}