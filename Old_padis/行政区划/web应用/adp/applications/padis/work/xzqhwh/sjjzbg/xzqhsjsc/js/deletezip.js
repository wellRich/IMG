/*
*��ȡѡ�е��ϴ��ļ���¼�����к�
*/
var flag="";
function getSelect()
{
 var xlArray=document.getElementsByName("zipxh_check");
 var zipxhs="";
 var cnt=0;
 for(var i=0;i<xlArray.length;i++)
 {
	if(xlArray[i].checked==true)
	{
		if(cnt==0)
		{
			zipxhs=xlArray[i].value.split(",")[0];
			flag = xlArray[i].value.split(",")[1];
			cnt=1;
		}
		else
		{
			zipxhs=zipxhs+","+xlArray[i].value.split(",")[0];
			flag=flag+","+xlArray[i].value.split(",")[1];
		}
	}
 }
 return zipxhs;
}

/*
*����Ƿ���ѡ�еļ�¼
*/
function checkSelected()
{
 var xlArray=document.getElementsByName("zipxh_check");
 var cnt=0;
 for(var i=0;i<xlArray.length;i++)
 {
	if(xlArray[i].checked==true)
	{
		cnt++;
	}
 }
 return cnt;
}


function deleteZip(sjxzqh_dm){
	var zipxh=getSelect();
	if(checkSelected()<1){
		showMessage("������һ�����ݣ�",0);
		return false;
	}
	var ztArray = flag.split(",");
	if(ztArray.length>0){
		for(var i=0;i<ztArray.length;i++){
			if(ztArray[i]!=sjxzqh_dm&&sjxzqh_dm!="00"){
				showMessage("ֻ��ɾ����ʡ���Ѵ����ļ���",0);
				return false;
			}
		}
	}

	if(!getZipzt(zipxh)){
		return false;
	}
	if(confirm("ȷʵҪɾ��ѡ�е�������"))
	{
		self.location="XzqhsjscService.deleteZip.do?ZIPXH="+zipxh;
	}
}
//��ȡZIP�ļ�״̬
function getZipzt(zipxh){
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<ZIPXH>"+zipxh+"</ZIPXH></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.sjjzbg.xzqhsjsc.XzqhsjscService.getZipzt");
	var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
	var doc = loadXml(resultXml); //�����׼��MSXML2.DOMDocument����
	if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
		var msg = doc.selectSingleNode("ROOT/Result/MAP/MSG").text;
		if(msg!="����ɾ��"){
			showMessage(msg,0);
			return false;
		}else{
			return true;
		}
	}
}