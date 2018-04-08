/*
*获取选中的上传文件记录的序列号
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
*检查是否有选中的记录
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
		showMessage("请至少一条数据！",0);
		return false;
	}
	var ztArray = flag.split(",");
	if(ztArray.length>0){
		for(var i=0;i<ztArray.length;i++){
			if(ztArray[i]!=sjxzqh_dm&&sjxzqh_dm!="00"){
				showMessage("只能删除本省上已传的文件！",0);
				return false;
			}
		}
	}

	if(!getZipzt(zipxh)){
		return false;
	}
	if(confirm("确实要删除选中的数据吗？"))
	{
		self.location="XzqhsjscService.deleteZip.do?ZIPXH="+zipxh;
	}
}
//获取ZIP文件状态
function getZipzt(zipxh){
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<ZIPXH>"+zipxh+"</ZIPXH></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.sjjzbg.xzqhsjsc.XzqhsjscService.getZipzt");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
	if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
		var msg = doc.selectSingleNode("ROOT/Result/MAP/MSG").text;
		if(msg!="可以删除"){
			showMessage(msg,0);
			return false;
		}else{
			return true;
		}
	}
}