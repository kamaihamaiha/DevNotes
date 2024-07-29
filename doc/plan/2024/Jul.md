## 2024-7月


- 购买保险(我和李敏): 意外险、百万医疗险、重疾险
- git
- 英语
- 数据结构和算法
- python
- Android 体系
- 百度网盘 + media3 播放功能

### git
玩转Git三剑客(极客时间): 总共62讲
- 14 删除不需要的分支 // ✅
- 15 修改最新的commit的message // ✅
- 16 修改老旧的commit的message // ✅
- 17 把连续的多个commit整理成1个 // ✅
- 18 把间隔的几个commit整理成1个 // ✅
- 19 比较暂存区和HEAD所含文件的差异 // ✅
- 20 比较工作区和暂存区所含文件的差异 // ✅
- 21 如何让暂存区恢复成和HEAD的一样 // ✅
- 22 如何让工作区的文件恢复和暂存区一样 // ✅
- 23 怎样取消暂存取部分文件的更改 // ✅
- 24 消除最近的几次提交 // ✅
- 25 看看不同提交的指定文件差异 // ✅
- 26 正确删除文件的方法 // ✅
- 27 开发中临时加塞了紧急任务怎么处理 // ✅
- 28 如何指定不需要git管理的文件
- 29 如何将git仓库备份到本地
- 30 

---

### 7-1 -7-5

#### 7-1

- git: 14 删除不需要的分支

#### 7-2

- git: 15 修改最新的commit的message
- git: 16 修改老旧的commit的message
  - git rebase -i [要修改的commit 的 parent commit id]; 然后进入编辑界面
    - 操作后，当前分支的 head 指向的 commit 也会改变
    - 注意: 这个操作是基于自己的分支，或者本地的分支；其他项目成员没有参与这个分支的情况
    
#### 7-3
  
- git: 17 把连续的多个commit整理成1个
    - git rebase -i [要修改的commit 的 parent commit id]; 然后进入编辑界面
        - 注意最新的 commit 前面的 pick 保持不变；后面的都改为 squash
- python: sorted
- Android 训练营，学习 Gradle 模块

#### 7-4

- Gradle 基础知识 

#### 7-10 周三

- Gradle 基础知识之 Groovy语法完成
- git: 18 把间隔的几个commit整理成1个
  - 找到最早的要整理的 commit 的parent commit id
    - git rebase -i [parent commit id]; 然后进入编辑界面
    - 把间隔的commit连续排列，从上往下第一个 commit 的pick保持不变；后面的都改为 squash


### 7-23(周二) - 7-26(五)

#### 7-23
  - 19 比较暂存区和HEAD所含文件的差异
    - ``git diff --cached``
  - 20 比较工作区和暂存区所含文件的差异
    - ``git diff``
      - 默认是对仓库中所有的文件比较
      - 如果想比较某个文件，可以指定文件名：``git diff -- fileName``
        - 多个文件: ``git diff -- fileName1 fileName2`` 
#### 7-24
  - 21 如何让暂存区恢复成和HEAD的一样
    - ``git reset HEAD`` 或者 ``git restore --staged fileName``
#### 7-25
  - 22 如何让工作区的文件恢复和暂存区一样
    - ``git checkout -- fileName``
#### 7-26
  - 23 怎样取消暂存取部分文件的更改
    - ``git reset HEAD -- file1 file2``
  - 24 消除最近的几次提交
    - ``git reset hard commitID``
      - commitID: 要回退到的 commit 的 id


### 7-29(周一) - 7-31(三)
    - git 
        - 25 看看不同提交的指定文件差异
            - 不同分支的差异: ``git diff branch1 branch2 -- fileName``
                - 不加文件，则为两个分支所有文件的差异
                - 也可以把 branch1 和 branch2 换成分支对应的 commitID
        - 26 正确删除文件的方法  
            - ``git rm fileName``
        - 27 开发中临时加塞了紧急任务怎么处理
            - ``git stash`` 
            -  ``git stash apply`` 或者 ``git stash pop``
                - apply 会保留 git stash list 的记录，pop 则不会