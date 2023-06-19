package cn.kk.customview.bean

import cn.kk.base.bean.BaseItem

/**
 * 卡片 item
 */
class ItemSimpleCard(title: String, val finish: Boolean = false): BaseItem(title) {

    var web_url = ""
    var markdown_url = ""
    var markdownLocalFlag = false
}