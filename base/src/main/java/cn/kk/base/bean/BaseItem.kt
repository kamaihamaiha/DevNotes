package cn.kk.base.bean

import java.io.Serializable

abstract  class BaseItem(var title: String = "", var type: Type = Type.UNKNOWN): Serializable {
    var item_action = 0
    var chapter_action = 0
    var section_action = 0
    var webUrl = ""
    var finishTag = false
    var htmlTag = false
    var appPkgName = ""

    // 定位chapter和book 使用
    var bookType = 0
    var item_book_type = 0 // 跳转book使用
    var chapterPos = 0
    var sectionPos = 0
    var nodePos = 0
    var data_source: String ?= null // 数据来源：本地book json 文件或者在线链接地址

    enum class Type {
        TYPE_BOOK, TYPE_CHAPTER, TYPE_SECTION, TYPE_NODE ,UNKNOWN
    }

    companion object {

        // region book:
        val ACTION_BOOK_ANDROID_DEV_GUIDE = 200 // android 开发指南（官网）
        val action_book_android_dev_art = 201
        val action_book_linux = 202
        val action_book_android_custom_view_elementary = 203
        val action_book_android_advance = 204
        val action_book_android_programming = 205 // Android 编程权威指南
        val action_book_git = 206
        val action_book_android_arch = 207
        val action_book__android_plugin = 208
        val action_book_android_hot_fix = 209
        val action_book_ffmpeg = 210
        val action_book_data_arch = 211
        val action_book_python = 212
        val action_book_http = 213
        val action_book_java_easy_coding = 214 // 码出高效 Java 开发手册
        val action_book_c_plus = 215
        val action_book_c = 216
        val action_book_kotlin = 217
        val action_book_android_dev_performance = 218 // Android 开发高手课
        val ACTION_BOOK_IDE = 219 // IDE
        val ACTION_BOOK_AV_DEV = 220        // 音视频开发
        val ACTION_BOOK_NDK = 221        // NDK 开发
        val ACTION_BOOK_INTERVIEW = 222        // 面试整理
        val ACTION_BOOK_TEMP = 223        // temp
        val ACTION_BOOK_COOL_300 = 224        // COOL 300
        val ACTION_10_works_method = 225        // 10X 工作法
        val ACTION_BOOK_ALGORITHMS_IN_C = 226       // 算法：C语言实现
        val ACTION_BOOK_HEALTH = 227      // 健康
        val ACTION_BOOK_SOMEONE_SHARE = 228      // 大佬分享
        val ACTION_BOOK_SOME_ARTICLES = 229      // 技术文章大杂烩
        val ACTION_BOOK_GRADLE = 230      // Gradle
        val ACTION_BOOK_FINANCIAL = 231      // 理财知识
        val ACTION_BOOK_CIVIL_CODE = 232      // 民法典
        val ACTION_BOOK_PENAL_CODE = 233      // 刑法
        val ACTION_BOOK_DIGITAL_PRODUCTS = 234      // 数码产品
        val ACTION_BOOK_ANDROID_TEST = 235      // 在Android平台上测试应用
        // endregion

        // region normal chapter and section
        val action_chapter_1 = 1
        val action_chapter_2 = 2
        val action_chapter_3 = 3
        val action_chapter_4 = 4
        val action_chapter_5 = 5
        val action_chapter_6 = 6
        val action_chapter_7 = 7
        val action_chapter_8 = 8
        val action_chapter_9 = 9
        val action_chapter_10 = 10
        val action_chapter_11 = 11
        val action_chapter_12 = 12
        val action_chapter_13 = 13
        val action_chapter_14 = 14
        val action_chapter_15 = 15
        val action_chapter_16 = 16
        val action_chapter_17 = 17
        val action_chapter_18 = 18
        val action_chapter_19 = 19
        val action_chapter_20 = 20
        val action_chapter_21 = 21
        val action_chapter_22 = 22
        val action_chapter_23 = 23
        val action_chapter_24 = 24
        val action_chapter_205 = 25

        val action_section_1 = 1
        val action_section_2 = 2
        val action_section_3 = 3
        val action_section_4 = 4
        val action_section_5 = 5
        val action_section_6 = 6
        val action_section_7 = 7
        val action_section_8 = 8
        val action_section_9 = 9
        val action_section_10 = 10
        // endregion

        // 更多：工作中总结
        val ACTION_MORE_WORK = 100
        val ACTION_MORE_MULTI_THREAD = 102 // 多线程
        val ACTION_MORE_NET = 103
        val ACTION_MORE_BLOGS = 104 // 技术博客
        val ACTION_MORE_DESIGN_PATTERN = 105 // 设计模式
        val ACTION_MORE_DATA_ARCH = 106 // 数据结构和算法
        val ACTION_MORE_ACTIVITY_LIFECYCLE_LAUNCH_MODE = 107 // Activity 生命周期和启动模式
        val ACTION_MORE_ANDROID_HANDLER_MESSAGE = 108 // android 消息机制
        val ACTION_MORE_IPC = 109 // IPC 机制
        val ACTION_MORE_AUDIO = 117 // 音频
        val ACTION_MORE_VIDEO = 118 // 视频
        val ACTION_MORE_HOT_FIX = 120 // 热修复
        val ACTION_MORE_PLUGIN = 122 // android 插件化开发
        val ACTION_MORE_NDK = 124 // android 插件化开发
        val ACTION_MORE_ZONGHE = 127 // JNI / NDK
        val ACTION_MORE_Git = 129
        val ACTION_MORE_Linux = 130
        val ACTION_MORE_Python = 131
        val ACTION_MORE_MIX_DEV = 132 // 混合开发
        val ACTION_MORE_REGEX = 133 // 正则表达式
        val ACTION_MORE_android_debug = 134 // Android Debug
        val ACTION_MORE_BAIDU_PAN = 135 // 百度网盘


        // 更多：工作中总结，细分
        val ACTION_MORE_WORK_INTENT_SERIALIZABLE = 1001
        val ACTION_MORE_WORK_REYCYCLER_VIEW = 1002
        val ACTION_MORE_WORK_MENU = 1003
        val ACTION_MORE_WORK_TEXTVIEW_HIGHLIGHT = 1004
        val ACTION_MORE_WORK_TEXTVIEW_HTML = 1005
        val ACTION_MORE_WORK_EXPAND_VIEW_CLICK = 1006
        val ACTION_MORE_WORK_ADAPTER_LIST = 1007
        val ACTION_MORE_WORK_TEXTVIEW_MARQUEE = 1008
        val ACTION_MORE_WORK_IMAGE_VIEW_SVG = 1009
        val ACTION_MORE_WORK_PICK_FILE = 1010

        // 更多：综合技术，细分
        val ACTION_MORE_ZONGHE_NESTEDSCROLLVIEW = 1271
        val ACTION_MORE_ZONGHE_chazhuang = 1272
        val ACTION_MORE_ZONGHE_mock = 1273

        // region 更多 音频 笔记
        val ACTION_MORE_AUDIO_BASE = 1170
        val ACTION_MORE_AUDIO_BASIC = 1171
        val ACTION_MORE_AUDIO_DENOISE = 1172
        val ACTION_MORE_AUDIO_REMOVE_ECHO = 1173
        val ACTION_MORE_AUDIO_NET_TRANS = 1174
        val ACTION_MORE_AUDIO_SPACE = 1175
        val ACTION_MORE_AUDIO_SPECIAL_EFFECT = 1176
        val ACTION_MORE_AUDIO_END = 1177


        val ACTION_MORE_AUDIO_BASIC_1 = 11711
        val ACTION_MORE_AUDIO_BASIC_2 = 11712
        val ACTION_MORE_AUDIO_BASIC_3 = 11713
        val ACTION_MORE_AUDIO_BASIC_4 = 11714

        val ACTION_MORE_AUDIO_DENOISE_1 = 11721
        val ACTION_MORE_AUDIO_DENOISE_2 = 11722

        val ACTION_MORE_AUDIO_REMOVE_ECHO_1 = 11731
        val ACTION_MORE_AUDIO_REMOVE_ECHO_2 = 11732

        val ACTION_MORE_AUDIO_NET_TRANS_1 = 11742
        val ACTION_MORE_AUDIO_NET_TRANS_2 = 11742
        val ACTION_MORE_AUDIO_NET_TRANS_3 = 11743

        val ACTION_MORE_AUDIO_SPACE_1 = 11751
        val ACTION_MORE_AUDIO_SPACE_2 = 11752

        val ACTION_MORE_AUDIO_SPECIAL_EFFECT_1 = 11761
        val ACTION_MORE_AUDIO_SPECIAL_EFFECT_2 = 11762
        val ACTION_MORE_AUDIO_SPECIAL_EFFECT_3 = 11763

        val ACTION_MORE_AUDIO_END_1 = 11771
        val ACTION_MORE_AUDIO_END_2 = 11772
        // endregion

        // region 更多 视频 笔记
        val ACTION_MORE_VIDEO_BASE = 1180
        val ACTION_MORE_VIDEO_start = 1181
        val ACTION_MORE_VIDEO_basic = 1182
        val ACTION_MORE_VIDEO_decode = 1183
        val ACTION_MORE_VIDEO_trans_and_net = 1184
        val ACTION_MORE_VIDEO_mix_and_play = 1185
        val ACTION_MORE_VIDEO_end = 1186

        val ACTION_MORE_VIDEO_start_1 = ACTION_MORE_VIDEO_start * 10 + 1
        val ACTION_MORE_VIDEO_basic_1 = ACTION_MORE_VIDEO_basic * 10 + 1
        val ACTION_MORE_VIDEO_basic_2 = ACTION_MORE_VIDEO_basic * 10 + 2
        val ACTION_MORE_VIDEO_basic_3 = ACTION_MORE_VIDEO_basic * 10 + 3
        val ACTION_MORE_VIDEO_decode_1 = ACTION_MORE_VIDEO_decode * 10 + 1
        val ACTION_MORE_VIDEO_decode_2 = ACTION_MORE_VIDEO_decode * 10 + 2
        val ACTION_MORE_VIDEO_decode_3 = ACTION_MORE_VIDEO_decode * 10 + 3
        val ACTION_MORE_VIDEO_decode_4 = ACTION_MORE_VIDEO_decode * 10 + 4
        val ACTION_MORE_VIDEO_decode_5 = ACTION_MORE_VIDEO_decode * 10 + 5
        val ACTION_MORE_VIDEO_trans_and_net_1 = ACTION_MORE_VIDEO_trans_and_net * 10 + 1
        val ACTION_MORE_VIDEO_trans_and_net_2 = ACTION_MORE_VIDEO_trans_and_net * 10 + 2
        val ACTION_MORE_VIDEO_trans_and_net_3 = ACTION_MORE_VIDEO_trans_and_net * 10 + 3
        val ACTION_MORE_VIDEO_trans_and_net_4 = ACTION_MORE_VIDEO_trans_and_net * 10 + 4
        val ACTION_MORE_VIDEO_trans_and_net_5 = ACTION_MORE_VIDEO_trans_and_net * 10 + 5
        val ACTION_MORE_VIDEO_mix_and_play_1 = ACTION_MORE_VIDEO_mix_and_play * 10 + 1
        val ACTION_MORE_VIDEO_mix_and_play_2 = ACTION_MORE_VIDEO_mix_and_play * 10 + 2
        val ACTION_MORE_VIDEO_mix_and_play_3 = ACTION_MORE_VIDEO_mix_and_play * 10 + 3
        val ACTION_MORE_VIDEO_end_1 = ACTION_MORE_VIDEO_end * 10 + 1
        // endregion

        // 更多 Git 笔记
        val ACTION_MORE_GIT_1 = 1291
        // endregion

        // 更多 Linux 笔记
        val ACTION_MORE_LINUX_BETTER_CAT = 1301
        val ACTION_MORE_LINUX_MAC_TRANS_LINUX = 1302

        // 更多混合开发笔记
        val ACTION_MORE_MIX_DEV_WEBVIEW = 1321

        // region Android 编程权威指南
        val ACTION_BOOK_ANDROID_PROGRAMMING_APP_GeoQuiz = 2052
        val ACTION_BOOK_ANDROID_PROGRAMMING_APP_BEATBOX = 2053

        // region cool-300
        val ACTION_COOL300 = 90
        val ACTION_COOL300_common_ui = 91
        val ACTION_COOL300_notification = 92
        val ACTION_COOL300_menu = 93
        val ACTION_COOL300_graphic_picture = 94
        val ACTION_COOL300_anim = 95
        val ACTION_COOL300_file_data = 96
        val ACTION_COOL300_sys_device = 97
        val ACTION_COOL300_intent = 98
        val ACTION_COOL300_third_part_sdk = 99

        val ACTION_COOL300_common_ui_001 = 91001
        val ACTION_COOL300_common_ui_002 = 91002
        val ACTION_COOL300_common_ui_003 = 91003
        val ACTION_COOL300_common_ui_004 = 91004
        val ACTION_COOL300_common_ui_005 = 91005
        val ACTION_COOL300_common_ui_006 = 91006
        val ACTION_COOL300_common_ui_007 = 91007
        val ACTION_COOL300_common_ui_008 = 91008
        val ACTION_COOL300_common_ui_009 = 91009
        val ACTION_COOL300_common_ui_010 = 91010
        val ACTION_COOL300_common_ui_011 = 91011
        val ACTION_COOL300_common_ui_012 = 91012
        val ACTION_COOL300_common_ui_013 = 91013
        val ACTION_COOL300_common_ui_014 = 91014
        val ACTION_COOL300_common_ui_015 = 91015
        val ACTION_COOL300_common_ui_016 = 91016
        val ACTION_COOL300_common_ui_017 = 91017
        val ACTION_COOL300_common_ui_018 = 91018
        val ACTION_COOL300_common_ui_019 = 91019
        val ACTION_COOL300_common_ui_020 = 91020
        val ACTION_COOL300_common_ui_021 = 91021
        val ACTION_COOL300_common_ui_022 = 91022

        val ACTION_COOL300_notification_030 = 92030
        val ACTION_COOL300_notification_031 = 92031
        val ACTION_COOL300_notification_032 = 92032
        val ACTION_COOL300_notification_033 = 92033
        val ACTION_COOL300_notification_034 = 92034
        val ACTION_COOL300_notification_035 = 92035
        val ACTION_COOL300_notification_036 = 92036
        val ACTION_COOL300_notification_037 = 92037
        val ACTION_COOL300_notification_038 = 92038

        val ACTION_COOL300_menu_063 = 93063
        val ACTION_COOL300_menu_071 = 93071
        val ACTION_COOL300_menu_072 = 93072

        val ACTION_COOL300_graphic_picture_073 = 94073
        val ACTION_COOL300_graphic_picture_074 = 94074
        val ACTION_COOL300_graphic_picture_075 = 94075
        val ACTION_COOL300_graphic_picture_076 = 94076
        val ACTION_COOL300_graphic_picture_077 = 94077
        val ACTION_COOL300_graphic_picture_078 = 94078
        val ACTION_COOL300_graphic_picture_079 = 94079
        val ACTION_COOL300_graphic_picture_080 = 94080
        val ACTION_COOL300_graphic_picture_081 = 94081
        val ACTION_COOL300_graphic_picture_082 = 94082
        val ACTION_COOL300_graphic_picture_083 = 94083
        val ACTION_COOL300_graphic_picture_106 = 94106

        val ACTION_COOL300_anim_107 = 95107
        val ACTION_COOL300_anim_108 = 95108
        val ACTION_COOL300_anim_109 = 95109
        val ACTION_COOL300_anim_110 = 95110
        val ACTION_COOL300_anim_111 = 95111
        val ACTION_COOL300_anim_112 = 95112
        val ACTION_COOL300_anim_113 = 95113
        val ACTION_COOL300_anim_114 = 95114
        val ACTION_COOL300_anim_115 = 95115
        val ACTION_COOL300_anim_116 = 95116
        val ACTION_COOL300_anim_117 = 95117
        val ACTION_COOL300_anim_143 = 95143
        val ACTION_COOL300_anim_145 = 95145

        val ACTION_COOL300_file_data_146 = 96146
        val ACTION_COOL300_file_data_147 = 96147
        val ACTION_COOL300_file_data_148 = 96148
        val ACTION_COOL300_file_data_149 = 96149
        val ACTION_COOL300_file_data_150 = 96150
        val ACTION_COOL300_file_data_151 = 96151
        val ACTION_COOL300_file_data_152 = 96152
        val ACTION_COOL300_file_data_153 = 96153
        val ACTION_COOL300_file_data_154 = 96154
        val ACTION_COOL300_file_data_155 = 96155
        val ACTION_COOL300_file_data_156 = 96156
        val ACTION_COOL300_file_data_157 = 96157
        val ACTION_COOL300_file_data_175 = 96175

        val ACTION_COOL300_sys_device_176 = 97176
        val ACTION_COOL300_sys_device_177 = 97177
        val ACTION_COOL300_sys_device_178 = 97178
        val ACTION_COOL300_sys_device_179 = 97179
        val ACTION_COOL300_sys_device_180 = 97180
        val ACTION_COOL300_sys_device_181 = 97181
        val ACTION_COOL300_sys_device_182 = 97182
        val ACTION_COOL300_sys_device_183 = 97183
        val ACTION_COOL300_sys_device_184 = 97184
        val ACTION_COOL300_sys_device_207 = 97207

        val ACTION_COOL300_intent_208 = 98208
        val ACTION_COOL300_intent_209 = 98209
        val ACTION_COOL300_intent_210 = 98210
        val ACTION_COOL300_intent_211 = 98211
        val ACTION_COOL300_intent_212 = 98212
        val ACTION_COOL300_intent_213 = 98213
        val ACTION_COOL300_intent_214 = 98214
        val ACTION_COOL300_intent_223 = 98223
        val ACTION_COOL300_intent_244 = 98244
        val ACTION_COOL300_intent_252 = 98252

        val ACTION_COOL300_third_part_sdk_253 = 99253
        val ACTION_COOL300_third_part_sdk_254 = 99254
        val ACTION_COOL300_third_part_sdk_255 = 99255
        val ACTION_COOL300_third_part_sdk_256 = 99256
        val ACTION_COOL300_third_part_sdk_257 = 99257
        val ACTION_COOL300_third_part_sdk_258 = 99258
        val ACTION_COOL300_third_part_sdk_300 = 99300

        // endregion
    }
}