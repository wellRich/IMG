#! /bin/bash
#提交代码，从idea的工作目录拷贝代码文件，至github本地文件夹，再提交至github

echo "Start to publish...\n"
echo "移除.git中的文件...\n"
filelist=`ls .git`
for file in $filelist
do 
 echo $file
 mv .git/$file F:/gitTemp
done

#准备代码文件
echo "删除github本地文件夹下的旧代码"
rm -rf 人口与计生服务管理应用系统/区划代码管理分系统/SERVICE/bapfopm-pfpsmas-zcms-service/src/main/java/com/digital

echo "从工作目录拷贝代码文件...\n"
cp -rv E:/workspace/Server/人口与计生服务管理应用系统/区划代码管理分系统/SERVICE/bapfopm-pfpsmas-zcms-service/src/main/java/com/$codeFile  人口与计生服务管理应用系统/区划代码管理分系统/SERVICE/bapfopm-pfpsmas-zcms-service/src/main/java/

echo "找回.git中的文件...\n"
filelist=`ls F:/gitTemp`
for file in $filelist
do 
 echo $file
 mv F:/gitTemp/$file .git
done

cd 人口与计生服务管理应用系统/区划代码管理分系统/SERVICE/bapfopm-pfpsmas-zcms-service/src/main/java
#执行git命令
git add .
git commit -m "自动提交"
git push origin master;
echo "Success\n";