## Mac 使用笔记

- [终端使用](https://github.com/kamaihamaiha/basic_dev_tools/blob/main/Mac_Note/terminal.md)
- [配置 ssh](./mac/setup_ssh.md)
- [配置 .zshrc](./mac/setup_zsh.md)
- [计算文件的 SHA1哈希值](#计算文件的SHA1哈希值)
- [Parallels Desktop 上的 Ubuntu 密码忘记了，重置密码方法](mac/reset_pwd_ubuntu_on_parallels-desktop.md)



### 计算文件的SHA1哈希值

#### shasum 命令
``shasum -a 1 path/to/file``

#### opensl 命令
``openssl sha1 path/to/file``