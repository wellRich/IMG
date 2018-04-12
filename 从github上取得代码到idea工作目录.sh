#! /bin/bash
echo "Start to publish...\n"
echo "remove .git...\n"
source=E:/workspace/Server/人口与计生服务管理应用系统/区划代码管理分系统/SERVICE/bapfopm-pfpsmas-zcms-service/src/main/java/com/digital
root=F:/github_work/root
git=F:/github_work/root/.git
filelist=`ls $git`
for file in $filelist
do 
 echo $file
 mv $git/$file F:/gitTemp
done


echo "delete github local Code $root"
rm -rf  $root/bapfopm-pfpsmas-zcms-service/src/main/java/com/digital

echo "从工作目录拷贝代码文件...\n"
cp -rv $source/$codeFile  $root/bapfopm-pfpsmas-zcms-service/src/main/java/com


echo "找回.git中的文件...\n"
filelist=`ls F:/gitTemp`
for file in $filelist
do 
 echo $file
 mv F:/gitTemp/$file $git
done

cd $root
git add .
git commit -m "自动提交"
git push origin master;
echo "Success\n";