package cn.kk.customview.activity

import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import com.mukesh.MarkdownView

/**
 * 通用显示 Markdown 文件
 */
class NormalMarkDownViewActivity: BaseActivity() {
    override fun getLayout(): Int {
       return R.layout.activity_normal_markdown_view
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        val markDownPath = intent.getStringExtra(INTENT_MARKDOWN_PATH_KEY).toString()
        val local = intent.getBooleanExtra(INTENT_MARKDOWN_LOCAL_KEY, true)

        val markDownView = findViewById<MarkdownView>(R.id.markdown_view)
        if (local) {
            markDownView.loadMarkdownFromAssets(markDownPath)
        } else {
            markDownView.loadUrl(markDownPath)
        }
    }

}