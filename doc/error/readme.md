## 错误整理

- [好改的](#好改的)
- [不好改的](#不容易改的)
- [没找到办法的](#没找到办法的)

### 好改的


### 不容易改的

- 听力pdf/图片分享，系统切换主题后，点击按钮分享、保存等操作会闪退
  - 问题：在主题切换后，Activity 和 Fragment 会重新实例化，然后 Activity 中持有的 Fragment 不是重新实例化的 fragment 对象了，所以会报错。
    - 解决办法：不要通过 Activity 中持有的 Fragment 对象来操作 Fragment，而是通过 FragmentManager 来操作 Fragment。但是我用的是 FragmentPagerAdapter 加载的fragment，因此我是在 fragment 中通过 getActivity() 来获取 Activity，然后通过给 Activity 设置监听的方式解决
- 通用拼写组件
- 词典通用拼写组件测试用例
- 听力，热门配音页面加过滤标签。线上版本过滤没有效果，本地编译正常
- 词典首页 「翻译」tab 页面很卡顿
- 词典AI批改页面，错误定位，无法滚动到对应的 item 
- app links，使用 Android Studio 自带的 App Links Assistant 检验服务器地址，一直不通过问题
- 配音秀录音有杂音，声音小的问题
- 单词导出PDF有的设备，卡住一直加载问题；后来可以导出pdf了，但是导出后数据错误打不开问题
- 文件阅读，epub 阅读app无法适配夜间问题
- Android 13适配：打开系统图片浏览器，打开系统视频浏览器 替代使用第三方的选择器
- 词典首页自定义背景，只要求在部分页面（fragment）生效
- 更换自定义背景图，要预览首页的「词典」页效果
- 词典支持键盘快捷键：背单词页面方向键、空格键会自动切换焦点，然后响应按钮（处于焦点）问题；单词解释页面方向键会自动滚动页面问题
- 听力文章播放页面支持快捷键：空格键、方向键会自动处理文章网页部分的页面问题
- 听力资源新版页面，三层资源。页面非常卡顿（RecyclerView + ViewPager 多重嵌套导致不断重新测量，不断加载新的页面）
- 单词列表，批量操作模式下：要实现手指滑动可以选中单词
- 智能客服页面
- AI批改页面，选中 AIWriterWebView 的文本后，要弹出自己定义的菜单
- 加载的网页没有 js 样式
- 听力状态栏沉浸效果
  - 首页
  - 收藏夹详情页面
  - 个人主页页面
- Event Bus 使用卡住
- TextView 在 约束布局中宽度动态变化，卡住很久
- 约束布局，代码修改约束关系。卡住很久
- 个人简介，String 去掉重复的换行符，卡住很久
- TabLayout 高度、宽度充满、indicator 距离文字颜色
- Toolbar 上面的 icon 图片左边距
- menu text color 修改卡住
- [阅读app 小米审核隐私政策没通过](reader_xiaomi_privacy_bug/reader_xiaomi_privacy_bug_report.md)


### 没找到办法的

- BottomSheetDialog 里面有两个 RecyclerView. RecyclerView 列表内容多时，需要滚动。但是如果往下滚动会有问题，就是直接滚动 BottomSheetDialog 了
- android.app.RemoteServiceException
    - 发生率很低，万分之几的概率。但是总是会发生，很多是华为设备
    - [相关分析文章](https://www.jianshu.com/p/5857673e73e3)





