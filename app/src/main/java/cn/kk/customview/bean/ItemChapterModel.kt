package cn.kk.customview.bean

import cn.kk.base.bean.BaseItem
import java.io.Serializable

class ItemChapterModel(title: String, val sections: MutableList<ItemSectionModel>):BaseItem(title, Type.TYPE_CHAPTER) , Serializable{


}