## 6月下旬

- 购买保险(我和李敏): 意外险、百万医疗险、重疾险
- git 
- 英语
- 数据结构和算法
- python
- Android 体系
- 百度网盘 + media3 播放功能

### git 
玩转Git三剑客(极客时间): 总共62讲
- 09 探秘.git目录 ✅ 
- 10 commit,tree,blob 三个对象之间的关系 ✅
- 11 小练习: 数一数tree的个数 ✅
- 12 分离头指针情况下的注意事项 ✅
- 13 进一步理解HEAD和branch ✅
- 14 删除不需要的分支
- 15 修改最新的commit的message
- 16 修改老旧的commit的message
- 17 把连续的多个commit整理成1个
- 18 把间隔的几个commit整理成1个
- 19 比较暂存区和HEAD所含文件的差异
- 20 比较工作区和暂存区所含文件的差异
- 21 如何让暂存区恢复成和HEAD的一样
- 22 如何让工作区的文件恢复和暂存区一样
- 23 怎样取消暂存取部分文件的更改

### 英语

- 许岑看电影学英语

### 数据结构和算法

- 线性表

### python

- 函数式编程
- 模块
- 面向对象编程

### Android体系

- 看书


### 每天

- [6-19 周三](#6-19周三)
- [6-18 周二](#6-18周二)
- [6-17 周一](#6-17周一)
- [6-16 周日](#6-16周日)

#### 6-19_周三

- git 学习
  - 进一步理解HEAD和branch  
  - 学习了新命令: 
    - `git checkout -b bugfix master`
      - 在分支 master 的基础上，创建出一个新的分支: bugfix
    - 比较差异:
      - `git diff HEAD HEAD^`: 当前最新的提交和parent提交差异
      - `git diff HEAD HEAD^^`: 当前最新的提交和parent提交的parent条差异
      - `git diff HEAD HEAD~1`: 当前最新的提交和parent提交差异
      - `git diff HEAD HEAD~2`: 当前最新的提交和parent提交的parent条差异

#### 6-18_周二

- git 学习:
  - tree: 每个目录都会
  - 分离头指针情况下的注意事项

#### 6-17_周一

- git 学习:
  - tree

#### 6-16_周日

- git 学习: 晚上
  - .git 目录
    - HEAD 文件
    - config 文件
    - refs 目录
      - heads 目录
      - tags 目录
    - 新学的命令
      - `git branch -v` 查看分支，并且有 commit 信息
      - `git cat-file -t [commit_id]` 查看git object 的类型
      - `git cat-file -p [commit_id]` 查看git object 的内容
  - commit,tree,blob 的关系