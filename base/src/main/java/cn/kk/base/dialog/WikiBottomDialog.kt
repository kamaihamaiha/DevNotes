package cn.kk.base.dialog

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import cn.kk.base.R
import cn.kk.base.activity.BaseActivity
import cn.kk.base.bean.WikiModel

/**
 * 简单的底部 wiki 弹窗 dialog
 */
class WikiBottomDialog(mContext: Context,val model: WikiModel ,theme: Int = R.style.EdgeToEdgeDialogStyle): BaseBottomDialog(mContext, theme) {

    constructor(mContext: Context): this(mContext, WikiModel(),0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_wiki_bottom)

        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        // show info
        findViewById<TextView>(R.id.tv_title)?.text = model.title
        findViewById<TextView>(R.id.tv_wiki)?.text = model.intro

        findViewById<Button>(R.id.btn_exit)?.setOnClickListener { dismiss() }
    }
}