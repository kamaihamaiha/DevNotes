## AOSP


-[搭建编译环境: 官方文档](https://source.android.com/docs/setup/initializing?hl=zh-cn)

官方建议的硬件要求:

- 一个 64 位 x86 系统。
- 如果要检出和构建代码，至少需要 400 GB 可用磁盘空间（250 GB 空间用于检出代码 + 150 GB 空间用于构建代码）。
- 至少 64 GB RAM。Google 使用 72 核机器和 64 GB RAM 来构建 Android。采用此硬件配置时，一个完整的 Android build 大约需要 40 分钟；Android 增量 build 大约需要几分钟的时间。相比之下，使用 6 核机器和 64 GB RAM 构建一个完整 build 大约需要 6 个小时。

**我的nuc9**

- [Intel® Core™ i7-9750H Processor](https://www.intel.com/content/www/us/en/products/sku/191045/intel-core-i79750h-processor-12m-cache-up-to-4-50-ghz/specifications.html)
  - 6核心 12线程
- 32G 内存
- 
---


- [AOSP 源码和内核源码下载](./c_6.md)
- [Android 系统的整编和单编](./c_7.md)
- [导入系统源码](./c_8.md)
- [查看系统源码](#使用-android-studio-加载-aosp-源码)

### 使用 Android Studio 加载 AOSP 源码

- [手动创建 gradle 配置方式](./as_tip_1.md)
- [AIDEGen 方式](./as_from_aidegen.md)