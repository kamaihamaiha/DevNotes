## 8月计划



- 购买保险(我和李敏): 意外险、百万医疗险、重疾险
- git
- C/C++
- 英语
- 数据结构和算法
- python
- Android 体系
- 百度网盘 + media3 播放功能

### git
玩转Git三剑客(极客时间): 总共62讲
- 28 如何指定不需要git管理的文件: ``.gitignore``
- 29 如何将git仓库备份到本地
- 30 

#### git 29 如何将git仓库备份到本地

常用的传输协议:

- 本地协议1: ``/path/to/repo.git``  哑协议: 传输进度不可见 
- 本地协议2: ``file:///path/to/repo.git`` 智能协议
- HTTP(S) 协议: ``http://git-server.com:port/path/to/repo.git``
- SSH 协议: ``user@git-server.com:paht/to/repo.git``

备份仓库:

```shell
# 本地协议；--bare 选项表示克隆的是裸仓库，只有 Git 的元数据
git clone --bare /Users/kk/Codes/android/custom-view/.git custom_ya
克隆到纯仓库 'custom_ya'...
完成。


# 智能协议
git clone --bare file:///Users/kk/Codes/android/custom-view/.git custom_smart.git

克隆到纯仓库 'custom_smart.git'...
remote: 枚举对象中: 14472, 完成.
remote: 对象计数中: 100% (14472/14472), 完成.
remote: 压缩对象中: 100% (6461/6461), 完成.
remote: 总共 14472（差异 7570），复用 10965（差异 5512），包复用 0（来自  0 个包）
接收对象中: 100% (14472/14472), 122.20 MiB | 36.26 MiB/s, 完成.
处理 delta 中: 100% (7570/7570), 完成.

# 从裸仓库中恢复目录和文件
git clone custom_ya ya
正克隆到 'ya'...
完成。
正在更新文件: 100% (2062/2062), 完成.
```
