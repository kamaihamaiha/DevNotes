package cn.kk.customview.ui.system

import android.text.Html
import android.widget.TextView
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R

/**
 * Html 显示在 TextView 上
 */
class HtmlText: BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_html_text
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        val tv_html = findViewById<TextView>(R.id.tv_html)
        val tv_html_ting = findViewById<TextView>(R.id.tv_html_ting)
        val tv_recite = findViewById<TextView>(R.id.tv_recite)
        tv_html.setText(Html.fromHtml(getString(R.string.html_demo)))
        tv_html_ting.setText(Html.fromHtml(getString(R.string.html_ting)))

//        tv_recite.text = Html.fromHtml(String.format(getString(R.string.recite_learning_progress_new_word), 3, 9, 2))

        tv_recite.text = String.format("%d%s", 13, "%")
    }
}