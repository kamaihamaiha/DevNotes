## Android 构建

- [从命令行构建应用](https://developer.android.com/studio/build/building-cmdline?hl=zh-cn)
- [Gradle 常见命令](#gradle-常见命令)
- 混淆


### Gradle 常见命令

[参考文章](https://juejin.cn/post/7171493698243395597)

- 编译命令
- 清除命令
- 卸载命令
- 调试命令
- 任务相关
- 查看依赖
- 性能相关
- 动态传参

#### 编译命令
```shell
# 检查依赖并编译打包
./gradlew build

# 编译并打出Debug包
./gradlew assembleDebug

# 编译打出Debug包并安装
./gradlew installDebug

# 编译并打出Release包
./gradlew assembleRelease

# 编译打出Release包并安装
./gradlew installRelease

# Debug/Release编译并打印日志
./gradlew assembleDebug --info
# or
./gradlew assembleRelease --info

```

#### 清除命令
```shell
# 清除构建目录下的所有文件, 等同于Build->Clean Project
./gradlew clean
```

#### 卸载命令
```shell
# 卸载Debug/Release安装包
./gradlew uninstallDebug
# or
./gradlew uninstallRelease

```

#### 调试命令
```shell
# 编译并打印堆栈日志
./gradlew assembleDebug --stacktrace
# or
./gradlew assembleDebug -s

```

#### 日志级别
```text
-q，--quiet
仅记录错误。

-w，--warn
将日志级别设置为警告。

-i，--info
将日志级别设置为信息。

-d，--debug
调试模式（包括正常的stacktrace）。
```

##### 示例
```shell
./gradlew assembleDebug -w

```

#### 任务相关
```shell
# 查看主要Task
./gradlew tasks

# 查看所有Task
./gradlew tasks --all

# 执行Task
./gradlew taskName
# or
./gradlew :moduleName:taskName

```

#### 查看依赖
```shell
# 查看项目根目录下的依赖
./gradlew dependencies

# 查看app模块下的依赖
./gradlew app:dependencies

# 查看依赖输出到文件
./gradlew app:dependencies > dependencies.txt

```

#### 性能相关
```shell
# 离线编译
./gradlew assembleDebug --offline

# 构建缓存
./gradlew assembleDebug --build-cache // 开启
# or
./gradlew assembleDebug --no-build-cache // 不开启

# 配置缓存
./gradlew assembleDebug --configuration-cache // 开启
# or
./gradlew assembleDebug --no-configuration-cache // 不开启

# 并行构建
./gradlew assembleDebug --parallel // 开启
# or
./gradlew assembleDebug --no-parallel // 不开启

# 编译并输出性能报告
./gradlew assembleDebug --profile

# 编译并输出更详细性能报告
./gradlew assembleDebug --scan

```

#### 动态传参
```shell
# 获取参数
 ./gradlew assembleDebug -PisTest=true 

# 
```


### 混淆

[反混淆堆栈日志：](https://stackoverflow.com/questions/56006933/how-to-deobfuscate-an-android-stacktrace-using-mapping-file)
```shell
# 我的 mac 下工具命令路径: ~/Library/Android/sdk/tools/proguard/bin/retrace.sh
retrace.bat|retrace.sh [-verbose] mapping.txt [<stacktrace_file>]
```