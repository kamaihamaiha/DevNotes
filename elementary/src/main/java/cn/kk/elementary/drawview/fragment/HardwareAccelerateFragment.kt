package cn.kk.elementary.drawview.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import cn.kk.base.fragment.BaseFragment
import cn.kk.elementary.R

class HardwareAccelerateFragment: BaseFragment() {
    val intros_1 = "GPU 是图形处理器，与 CPU 不同，GPU 是专门为处理图形任务而产生的芯片。\n\t对于 Android 来讲，API 11 之前没有 GPU 概念；" +
            "在 API 11 之后加入了对 GPU 加速对支持；在 API 14 之后，硬件加速功能是默认开启的。"

    val intros_2 = "在 GPU 加速时，实际上是使用 OpenGL 的相关函数来完成绘制。因此硬件加速提高了 Android 系统显示和刷新的速度；但是也有缺点：兼容性问题、内存消耗问题、电量消耗问题"
    override fun getLayoutId(): Int = R.layout.fragment_hardware_accelerate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tv_content).text = intros_1
        view.findViewById<TextView>(R.id.tv_content).text = intros_2
    }
}