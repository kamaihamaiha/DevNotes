## 阅读app 小米审核隐私政策没通过

- 突破点: 
  - getprop读取全部属性(包括serialno)
  - com.qiniu.android.dns.local 里面读取了全部属性
  - 调用链路开端: PlaybackInfoBar
    - 这个怎么都没有想通，会从这里开始触发

### debug 后得知：

调用链路: PlaybackInfoBar.java
            -> onAttachedToWindow() -> initData() -> TingArticleModel article = EudicTingStorage.getLastListenArticle(getContext().getContentResolver())
            -> EudicTingStorage.java
                -> getLastListenArticle()
                    -> 查询语句
                        -> TingContentProvider.java
                            -> SQLiteDatabase db = getDataBase().getReadableDatabase();
                                -> getDataBase() 方法中调用了 JniApi.getAppContext()
                                    -> JniApi.java init()
                                        -> LocalStorage.sharedInstance()
                                            -> LocalStorage() 构造方法
                                                -> setMainLocalLibraryPath()
                                                    -> extractDicRes()
                                                        -> EuIOUtil.clearCache(JniApi.getAppContext())
                                                            -> EuIOUtil.java clearCache()
                                                                -> EuDictHttpApi.clearCache()
                                                                    -> Cache cache = mOkHttpClient.cache() 是用了静态变量 mOkHttpClient
                                                                        -> mOkHttpClient 需要 静态变量 sharedHttpDns
                                                                            -> HttpDns3 构造方法
                                                                                -> AndroidDnsServer.defaultResolver(BaseApplication.applicationContext)
                                                                                    -> 一直跟踪进去 AndroidDnsServer.java getByCommand() 方法如下：
                                                                                        ```java
                                                                                        Process process = Runtime.getRuntime().exec("getprop");
                                                                                        ```

#### 分析

为什么在同意隐私之前，还能执行 PlaybackInfoBar？

因为 TabActivityV3 是阅读首页，是通过这个页面打开的隐私页面。打开隐私页面之前就已经 setContentView(layout) 了， layout 里面声明了 PlaybackInfoBar

在 privacy.start 回调成功后再 setContentView(layout) ，就可以解决这个问题了

