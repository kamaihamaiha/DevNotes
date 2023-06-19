package cn.kk.customview.ui.cool300

import cn.kk.customview.R
import cn.kk.base.bean.BaseItem
import cn.kk.customview.bean.ItemSectionModel
import cn.kk.customview.ui.cool300.chapter1.Simple_005
import cn.kk.customview.ui.cool300.chapter1.Simple_009
import cn.kk.customview.ui.cool300.chapter1.Simple_010
import cn.kk.customview.ui.cool300.chapter1.Simple_022
import cn.kk.customview.ui.cool300.chapter3.Simple_063
import cn.kk.customview.ui.cool300.chapter3.Simple_071
import cn.kk.customview.ui.cool300.chapter3.Simple_072
import cn.kk.customview.ui.cool300.chapter5.Simple_143
import cn.kk.customview.ui.cool300.chapter8.SimpleIntentActivity
import cn.kk.customview.ui.fragment.BaseChapterListFragment

/**
 * 炫酷应用300例子：以章节列表形式显示
 */
class Cool300HomeFragment: BaseChapterListFragment() {

    override fun getPartsStringsId(): Int {
        return R.array.cool_300
    }


    override fun getActionBaseType(): Int {
        return BaseItem.ACTION_COOL300
    }

    override fun getSectionDataList(chapterType: Int): MutableList<ItemSectionModel> {
        val sectionModelList = mutableListOf<ItemSectionModel>()
        when(chapterType) {
            BaseItem.ACTION_COOL300_common_ui -> {
                sectionModelList.add(ItemSectionModel("001. 以折叠方式实现隐藏或显示 TextView").apply { item_action = BaseItem.ACTION_COOL300_common_ui_001 })
                sectionModelList.add(ItemSectionModel("002. 使用可拉伸 9patch 图设置 TextView 背景").apply { item_action = BaseItem.ACTION_COOL300_common_ui_002 })
                sectionModelList.add(ItemSectionModel("003. 使用 TextSwitcher 平滑切换多个标题").apply { item_action = BaseItem.ACTION_COOL300_common_ui_003 })
                sectionModelList.add(ItemSectionModel("004. 在 EditText 中弹出输入电话号码的键盘").apply { item_action = BaseItem.ACTION_COOL300_common_ui_004 })
                sectionModelList.add(ItemSectionModel("005. 自定义 CheckBox 风格", true).apply { item_action = BaseItem.ACTION_COOL300_common_ui_005 })
                sectionModelList.add(ItemSectionModel("009. Drawable shape 作为 btn 背景", true).apply { item_action = BaseItem.ACTION_COOL300_common_ui_009 })
                sectionModelList.add(ItemSectionModel("010. Drawable shape 渐变圆角按钮", true).apply { item_action = BaseItem.ACTION_COOL300_common_ui_010 })
                sectionModelList.add(ItemSectionModel("022. 自定义 selector 以透明前景切换控件", true).apply { item_action = BaseItem.ACTION_COOL300_common_ui_022 })
            }

            BaseItem.ACTION_COOL300_notification -> {
                sectionModelList.add(ItemSectionModel("030. 动态设置当前应用的标题栏文本").apply { item_action = BaseItem.ACTION_COOL300_notification_030 })
                sectionModelList.add(ItemSectionModel("031. 动态设置当前应用的标题栏背景").apply { item_action = BaseItem.ACTION_COOL300_notification_031 })
                sectionModelList.add(ItemSectionModel("032. 动态隐藏或显示当前应用的标题栏").apply { item_action = BaseItem.ACTION_COOL300_notification_032 })
                sectionModelList.add(ItemSectionModel("033. 自定义 TextView 创建渐变标题栏").apply { item_action = BaseItem.ACTION_COOL300_notification_033 })
                sectionModelList.add(ItemSectionModel("034. 使用自定义布局创建个性化标题栏").apply { item_action = BaseItem.ACTION_COOL300_notification_034 })
                sectionModelList.add(ItemSectionModel("035. 在标题栏左侧添加默认的后退按钮").apply { item_action = BaseItem.ACTION_COOL300_notification_035 })
                sectionModelList.add(ItemSectionModel("036. 在标题栏右侧添加分享按钮分享文本").apply { item_action = BaseItem.ACTION_COOL300_notification_036 })
                sectionModelList.add(ItemSectionModel("037. 使用 SearchView 在标题栏添加搜索框").apply { item_action = BaseItem.ACTION_COOL300_notification_037 })
                sectionModelList.add(ItemSectionModel("038. 使用 SearchManager 实现标题栏添加搜索框").apply { item_action = BaseItem.ACTION_COOL300_notification_038 })
            }
            BaseItem.ACTION_COOL300_menu -> {
                sectionModelList.add(ItemSectionModel("063. 使用 Toolbar 在工具栏上添加菜单", true).apply { item_action = BaseItem.ACTION_COOL300_menu_063 })
                sectionModelList.add(ItemSectionModel("071. 在弹出底部菜单时，主窗口立刻变暗", true).apply { item_action = BaseItem.ACTION_COOL300_menu_071 })
                sectionModelList.add(ItemSectionModel("072. 长按 view 弹出上下文", true).apply { item_action = BaseItem.ACTION_COOL300_menu_072 })
            }
            BaseItem.ACTION_COOL300_graphic_picture -> {
                sectionModelList.add(ItemSectionModel("073. 通过像素操作在图像上添加马赛克特效", ).apply { item_action = BaseItem.ACTION_COOL300_graphic_picture_073 })
                sectionModelList.add(ItemSectionModel("074. 通过像素操作实现为图像添加冰冻效果", ).apply { item_action = BaseItem.ACTION_COOL300_graphic_picture_074 })
                sectionModelList.add(ItemSectionModel("075. 通过像素操作将彩色图像改编为怀旧风格", ).apply { item_action = BaseItem.ACTION_COOL300_graphic_picture_075 })
                sectionModelList.add(ItemSectionModel("076. 使用 PorterDuffXfermode 裁剪六边形", ).apply { item_action = BaseItem.ACTION_COOL300_graphic_picture_076 })
                sectionModelList.add(ItemSectionModel("077. 使用 PorterDuffXfermode 扣取异形图像", ).apply { item_action = BaseItem.ACTION_COOL300_graphic_picture_077 })
                sectionModelList.add(ItemSectionModel("078. 使用 ColorMatrix 增强图像颜色对比度", ).apply { item_action = BaseItem.ACTION_COOL300_graphic_picture_078 })
                sectionModelList.add(ItemSectionModel("079. 使用 ColorMatrix 为图像添加亮效果", ).apply { item_action = BaseItem.ACTION_COOL300_graphic_picture_079 })
                sectionModelList.add(ItemSectionModel("080. 使用 ColorMatrix 调整图像的红色色调", ).apply { item_action = BaseItem.ACTION_COOL300_graphic_picture_080 })
                sectionModelList.add(ItemSectionModel("081. 使用 ColorMatrix 旋转图像的颜色色相", ).apply { item_action = BaseItem.ACTION_COOL300_graphic_picture_081 })
                sectionModelList.add(ItemSectionModel("082. 自定义 ColorMatrix 改变图像对比对", ).apply { item_action = BaseItem.ACTION_COOL300_graphic_picture_082 })
                sectionModelList.add(ItemSectionModel("083. 使用 Matrix 实现按照指定角度旋转图像", ).apply { item_action = BaseItem.ACTION_COOL300_graphic_picture_083 })
                sectionModelList.add(ItemSectionModel("...", ).apply { item_action = BaseItem.ACTION_COOL300_graphic_picture })
                sectionModelList.add(ItemSectionModel("106. 使用 BitmapRegionDecoder 加载大图", ).apply { item_action = BaseItem.ACTION_COOL300_graphic_picture_106 })

            }
            BaseItem.ACTION_COOL300_anim -> {
                sectionModelList.add(ItemSectionModel("107. 使用 ObjectAnimator 创建坐标平移动画", true).apply { item_action = BaseItem.ACTION_COOL300_anim_107 })
                sectionModelList.add(ItemSectionModel("108. 使用 ObjectAnimator 创建波纹扩散动画", true).apply { item_action = BaseItem.ACTION_COOL300_anim_108 })
                sectionModelList.add(ItemSectionModel("109. 使用 ValueAnimator 动态绘制桃心图形", true).apply { item_action = BaseItem.ACTION_COOL300_anim_109 })
                sectionModelList.add(ItemSectionModel("110. 使用 AnimationSet 组合多个不同的动画", true).apply { item_action = BaseItem.ACTION_COOL300_anim_110 })
                sectionModelList.add(ItemSectionModel("...").apply { item_action = BaseItem.ACTION_COOL300_anim })
                sectionModelList.add(ItemSectionModel("143. 使用 RippleDrawable 创建波纹扩散动画", true).apply { item_action = BaseItem.ACTION_COOL300_anim_143 })
            }
            BaseItem.ACTION_COOL300_file_data -> {
                sectionModelList.add(ItemSectionModel("146. 采用 DOM 方式解析 Xml 文件的内容").apply { item_action = BaseItem.ACTION_COOL300_file_data_146 })
                sectionModelList.add(ItemSectionModel("147. 采用 Pull 方式解析 Xml 文件的内容").apply { item_action = BaseItem.ACTION_COOL300_file_data_147 })
                sectionModelList.add(ItemSectionModel("148. 使用 JSONArray 解析 JSON 串的多个对象").apply { item_action = BaseItem.ACTION_COOL300_file_data_148})
                sectionModelList.add(ItemSectionModel("...").apply { item_action = BaseItem.ACTION_COOL300_file_data})
                sectionModelList.add(ItemSectionModel("175. 使用 MultiSelectListPreference 实现多选").apply { item_action = BaseItem.ACTION_COOL300_file_data_175})

            }
            BaseItem.ACTION_COOL300_sys_device -> {
                sectionModelList.add(ItemSectionModel("176. 使用 ContentResolver 获取手机短信信息").apply { item_action = BaseItem.ACTION_COOL300_sys_device_176 })
                sectionModelList.add(ItemSectionModel("177. 使用 ContentResolver 获取所有联系人信息").apply { item_action = BaseItem.ACTION_COOL300_sys_device_177 })
                sectionModelList.add(ItemSectionModel("178. 使用 ContentResolver 查询联系人电话号码").apply { item_action = BaseItem.ACTION_COOL300_sys_device_178 })
                sectionModelList.add(ItemSectionModel("179. 使用 ContentResolver 动态新增联系人信息").apply { item_action = BaseItem.ACTION_COOL300_sys_device_179 })
                sectionModelList.add(ItemSectionModel("180. 使用 ContentResolver 动态修改联系人信息").apply { item_action = BaseItem.ACTION_COOL300_sys_device_180 })
                sectionModelList.add(ItemSectionModel("181. 使用 ContentResolver 动态删除联系人信息").apply { item_action = BaseItem.ACTION_COOL300_sys_device_181 })
                sectionModelList.add(ItemSectionModel("...").apply { item_action = BaseItem.ACTION_COOL300_sys_device })
                sectionModelList.add(ItemSectionModel("207. 在当前应用中实现关机和重启功能", true).apply { item_action = BaseItem.ACTION_COOL300_sys_device_207 })

            }
            BaseItem.ACTION_COOL300_intent -> {
                sectionModelList.add(ItemSectionModel("208. 启动百度地图进行骑行导航", ).apply { item_action = BaseItem.ACTION_COOL300_intent_208 })
                sectionModelList.add(ItemSectionModel("209. 启动百度地图查询公交路线", ).apply { item_action = BaseItem.ACTION_COOL300_intent_209 })
                sectionModelList.add(ItemSectionModel("210. 启动百度地图查询步行路线", ).apply { item_action = BaseItem.ACTION_COOL300_intent_210 })
                sectionModelList.add(ItemSectionModel("...").apply { item_action = BaseItem.ACTION_COOL300_anim_143 })
                sectionModelList.add(ItemSectionModel("223 使用 Intent 将文本内容仅分享到微信").apply { item_action = BaseItem.ACTION_COOL300_intent_223; finishTag = true})
                sectionModelList.add(ItemSectionModel("244 使用 Intent 指定应用打开 PDF 文件").apply { item_action = BaseItem.ACTION_COOL300_intent_244; finishTag = true})
                sectionModelList.add(ItemSectionModel("252. 跳转到系统无障碍设置页面").apply { item_action = BaseItem.ACTION_COOL300_intent_252 })

            }
            BaseItem.ACTION_COOL300_third_part_sdk -> {
                sectionModelList.add(ItemSectionModel("253. 使用腾讯 SDK 将指定图像分享给 QQ 好友").apply { item_action = BaseItem.ACTION_COOL300_third_part_sdk_253 })
                sectionModelList.add(ItemSectionModel("...").apply { item_action = BaseItem.ACTION_COOL300_third_part_sdk })
                sectionModelList.add(ItemSectionModel("300. 使用百度 SDK 实现自定义地图缩放按钮的位置").apply { item_action = BaseItem.ACTION_COOL300_third_part_sdk_300 })

            }

            else -> { }
        }

        return sectionModelList
    }

    override fun onSectionViewClick(item: ItemSectionModel) {
        when(item.item_action) {
            BaseItem.ACTION_COOL300_common_ui_005 -> startNextUI(Simple_005::class.java, item.title)
            BaseItem.ACTION_COOL300_common_ui_009 -> startNextUI(Simple_009::class.java, item.title)
            BaseItem.ACTION_COOL300_common_ui_010 -> startNextUI(Simple_010::class.java, item.title)
            BaseItem.ACTION_COOL300_common_ui_022 -> startNextUI(Simple_022::class.java, item.title)

            BaseItem.ACTION_COOL300_menu_063 -> startNextUI(Simple_063::class.java, item.title)
            BaseItem.ACTION_COOL300_menu_071 -> startNextUI(Simple_071::class.java, item.title)
            BaseItem.ACTION_COOL300_menu_072 -> startNextUI(Simple_072::class.java, item.title)

            BaseItem.ACTION_COOL300_anim_143-> startNextUI(Simple_143::class.java, item.title)

            BaseItem.ACTION_COOL300_intent_223-> startNextUI(SimpleIntentActivity::class.java, item.title)
            BaseItem.ACTION_COOL300_intent_244-> startNextUI(SimpleIntentActivity::class.java, item.title, SimpleIntentActivity.TYPE_OPEN_PDF)
        }
    }

}