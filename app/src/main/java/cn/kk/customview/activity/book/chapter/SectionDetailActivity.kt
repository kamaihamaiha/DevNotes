package cn.kk.customview.activity.book.chapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import cn.kk.customview.activity.BaseFragmentActivity
import cn.kk.customview.fragment.SectionDetailFragment

class SectionDetailActivity: BaseFragmentActivity() {
    override fun getFragment(): Fragment {
        return SectionDetailFragment().apply { arguments = Bundle().apply {
            putString(INTENT_MODEL_DATA_SOURCE_KEY, intent.getStringExtra(INTENT_MODEL_DATA_SOURCE_KEY))
        } }
    }
}