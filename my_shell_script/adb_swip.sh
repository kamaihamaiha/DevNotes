#!/bin/bash

# 提示用户输入执行次数
echo "请输入执行次数:"
read total_executions

# 验证输入是否为数字
if [[ ! $total_executions =~ ^[0-9]+$ ]]; then
  echo "无效的输入，请输入一个数字。"
  exit 1
fi

# 从下往上滑动
command="adb shell input swipe 500 1000 500 200"

for ((i = 1; i <= total_executions; i++)); do
    # 执行命令
    $command
    echo "执行第 $i 次"

    # 等待4秒
    sleep 5
done
echo "执行完毕"

