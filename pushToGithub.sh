#! /bin/bash
echo "Start to publish...\n"
# 切换到项目目录
cd E:/workspace/IMG
# 执行git命令
# git pull origin test
git add .
git commit -m "自动提交"
git push https://github.com/wellRich/IMG.git
echo "Success\n";
