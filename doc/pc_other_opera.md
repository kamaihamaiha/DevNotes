### 电脑其他操作

#### Windows + Ubuntu 双系统安装

参考：

- [参考1](https://article.itxueyuan.com/oJ8bmn)
- [参考2](https://www.bilibili.com/read/cv15396206/)

#### Ubuntu 没有 Wi-Fi 图标

- 首先进入 BIOS 把 WLAN 打开

#### Ubuntu Wi-Fi 搜索不到信号

- 查看网卡类型：Broadcom 802.11ac Network Adapter
- 很多 Linux 系统默认不支持这种型号的无线网卡
- 因此要安装驱动
    ```shell
     # 先整根网线
      
     # 更新
     sudo apt-get update
     sudo apt-get --reinstall install bcmwl-kernel-source 
    ```
  
#### Windows 环境基本配置

- chrome
- clash
- git
- jdk
- typora
- vs code
- visual studio
- android studio
- python
- cmake
- notepad++
- WindTerm
- wps
- BaiduNetdisk
- source tree
- ToDesk
- 其他下载的源码
- 其他存储的课程资料

#### Mac 电脑操作

- [见](./mac_wiki.md)
