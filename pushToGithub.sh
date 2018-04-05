#! /bin/bash
echo "Start to publish...\n"
# 切换到项目目录
cd E:/workspace/IMG
# 执行git命令
# git pull origin test
git config  user.name "wellRich"
git config  user.email "kitty_gyk@163.com"
git add .
git commit -m "自动提交"
git push;
echo "Success\n";
