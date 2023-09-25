### 性能优化方法

- 布局优化
- 绘制优化
- 内存泄漏优化
- 响应速度优化
- Bitmap 优化
- 线程优化
- [性能优化建议](#性能优化建议)

---

#### 布局优化

- 减少布局文件的层级
- 复杂的界面使用约束布局
- 使用性能较低的 ViewGroup，能用 LinearLayout/FrameLayout，就不用 RelativeLayout。
- 使用：`<include>` 标签、`<merge>` 标签和 ViewStub
    - `<include>` 主要用于布局重用
    - `<merge>` 标签一般和 `<include>` 配合使用，可以降低布局的层级
    - ViewStub 提供按需加载功能，当需要时才会将 ViewStub 中的布局加载到内存


#### 绘制优化

View 的 onDraw 方法避免执行大量的操作。

- 第一：不要创建新的局部对象，因为 onDraw 方法可能会被频繁调用，就会一瞬间产生大量临时对象，占用内存，导致频繁 gc
- 第二：不要执行耗时任务，也不能执行很多次的循环操作。绘制时间不要超过 16ms，保证 View 绘制帧率 60帧。

#### 内存泄漏优化

- 场景1：静态变量导致的内存泄漏
- 场景2：单例模式导致的内存泄漏
- 场景3：属性动画导致的内存泄漏

##### 静态变量导致的内存泄漏

静态变量持有了 Activity，如下：
```java
public class DemoActivity extends AppCompatActivity {
    
    protected static Context sContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext = this;
    }
}
```
或者：
```java
public class DemoActivity extends AppCompatActivity {
    
    private static View view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new View(this);
    }
}

```

##### 单例模式导致的内存泄漏

单例模式中有注册监听方法，Activity 调用注册监听，但是 onDestroy() 时，没有解注册监听。因此单例模式的类就持有了销毁的 Activity 对象。   
单例模式的类的声明周期和 Application 保持一致，因此导致 Activity 对象无法及时释放。

##### 属性动画导致的内存泄漏

属性动画开启了无限动画，因此会一直持有执行动画的 view，而 view 又会持有 Activity，因此如果不在 onDestroy() 中停止动画，   
即使 Activity 销毁了，也无法释放。导致内存泄漏

#### 响应速度优化

核心思想是避免在主线程中做耗时操作。

##### ANR
- Activity 5s 无法响应屏幕触摸事件或者键盘输入事件就会触发 ANR
- BroadcastReceiver 10s 内未执行完操作也会出现 ANR

**ANR 定位：**

发生 ANR 后，系统会在 ``/data/anr`` 目录下创建一个文件 **traces.txt**，通过分析这个文件定位。

#### Bitmap 优化

BitmapFactory.Options 根据需要对图片进行采样

#### 线程优化

采用线程池，避免程序存在大量的 Thread


#### 性能优化建议

- 避免创建过多的对象
- 不要过多使用枚举，枚举占用的内存空间要比整型大
- 常量使用 static final 来修饰
- 使用一些 Android 特有的数据结构，比如 SparseArray 和 Pair 等，他们都具有更好的性能
- 适当使用软引用和弱引用
- 采用内存缓存和磁盘缓存
- 尽量采用静态内部类，这样可以避免潜在的由于内部类而代之的内存泄漏

#### 性能优化分析工具

- [使用profile 和 MAT 工具进行内存泄漏检测](https://juejin.cn/post/6844904080691691533)