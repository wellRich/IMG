#! /bin/bash
echo "Start to publish...\n"
echo "remove .git...\n"
source=E:/workspace/Server/�˿�������������Ӧ��ϵͳ/������������ϵͳ/SERVICE/bapfopm-pfpsmas-zcms-service/src/main/java/com/digital
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

echo "�ӹ���Ŀ¼���������ļ�...\n"
cp -rv $source/$codeFile  $root/bapfopm-pfpsmas-zcms-service/src/main/java/com


echo "�һ�.git�е��ļ�...\n"
filelist=`ls F:/gitTemp`
for file in $filelist
do 
 echo $file
 mv F:/gitTemp/$file $git
done

cd $root
git add .
git commit -m "�Զ��ύ"
git push origin master;
echo "Success\n";