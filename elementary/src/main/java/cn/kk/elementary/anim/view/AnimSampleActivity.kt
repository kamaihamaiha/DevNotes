package cn.kk.elementary.anim.view

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import cn.kk.base.activity.BaseActivity
import cn.kk.elementary.R
import cn.kk.elementary.anim.adapter.AnimFragmentAdapter

/**
 * 动画示例：
 * 1. 镜头由远及近效果
 * 2. 加载动画
 * 3. 水波纹动画
 * 4. 逐帧动画
 */
class AnimSampleActivity: BaseActivity() {

    override fun getLayout(): Int = R.layout.activity_anim_samples

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        findViewById<ViewPager2>(R.id.anim_sample_viewpager)?.adapter = AnimFragmentAdapter(this)
    }
}