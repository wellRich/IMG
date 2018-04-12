#! /bin/bash
echo "Start to publish...\n"
# 切换到项目目录
#cd E:/workspace/SERVER

#cd 人口与计生服务管理应用系统/区划代码管理分系统/SERVICE/bapfopm-pfpsmas-zcms-service/src/main/java
# 执行git命令
# git pull origin test
#git config  user.name "wellRich"
#git config  user.email "kitty_gyk@163.com"

cd E:/workspace/SERVER
git add .
git commit -m "自动提交"
git push origin master;
echo "Success\n";

