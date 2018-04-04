#! /bin/bash
echo "Start to publish...\n"
# 切换到项目目录
cd E:/workspace/Server
# 执行git命令
# git pull origin test
git add .
git commit -m "自动提交"
git push -u https://github.com/wellRich/IMG.git
echo "Success\n";
