## AIDEGen 方式通过 Android Studio 导入 AOSP 源码

[相关文章](https://juejin.cn/post/7166061140298956836)


### 实际遇到了问题(Mac 上)

- 首先 aidegen 脚本里面有 python3 的版本检查，注意下
- 然后 aidegen 脚本最后一个命令执行的是 /Users/kk/Documents/android_source_code/android-11.0.0_r1/prebuilts/asuite/aidegen/linux-x86/aidegen 
  - 这里面用到了 python（应该是 python 2.7）
  - Mac 下需要安装 python 2.7 ，且安装后需要在终端中执行 python 时，找的就是 python 2.7
    - [安装 python 2.7](#安装python 2.7) 使用 pyenv
  - 再次进入到 AOSP 跟目录，执行 aidegen 对应的命令
      


#### 安装python 2.7

```shell
# 安装 pyenv
brew install pyenv

# 安装 Python 2
pyenv install 2.7.18

# 将 Python 2 设置为全局版本，这样当你使用 python 命令时，它将使用 Python 2
pyenv global 2.7.18

# 安装后，如果 python --version 没有，那么需要 添加 pyenv 初始化到 shell 配置文件
# 可能是因为 pyenv 未正确地添加到 shell 配置文件中。确保在你的 ~/.zshrc 文件中添加了以下行，以初始化 pyenv：
export PYENV_ROOT="$HOME/.pyenv"
export PATH="$PYENV_ROOT/bin:$PATH"
eval "$(pyenv init --path)"

# 添加完毕后，保存文件并执行以下命令来重新加载 shell 配置
source ~/.zshrc

```
