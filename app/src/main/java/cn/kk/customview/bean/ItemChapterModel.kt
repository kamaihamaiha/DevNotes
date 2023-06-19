package cn.kk.customview.bean

import cn.kk.base.bean.BaseItem
import java.io.Serializable

class ItemChapterModel(title: String, val sections: MutableList<ItemSectionModel>,val bookType: Int = 0):BaseItem(title, Type.TYPE_CHAPTER) , Serializable{


}