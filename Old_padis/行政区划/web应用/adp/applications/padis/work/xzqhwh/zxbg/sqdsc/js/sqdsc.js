function deleteSqd()
{
	var flag =false;
	var sqdxh ="";
	var sqdzt_dm="";
	var sqdArray=document.getElementsByName("sqdxh_radio");
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			flag=true;
			sqdxh = sqdArray[i].value.split("||")[0];
			sqdzt_dm =sqdArray[i].value.split("||")[1];
		}
	 }
	 if(!getCount(sqdxh)){
		showMessage("此变更对照表的明细数据太多，请逐条删除明细或打电话联系运维人员，谢谢合作！",0);
		return false;
	 }
	 if(flag)
	 {
	 	 if(sqdzt_dm != "10" && sqdzt_dm!="21" && sqdzt_dm!="")
		 {
		 	showMessage("只能删除变更对照表状态为未提交或审核未通过的记录！",0);
		 	return false;
		 }
		if(confirm("确定删除该记录？此删除操作是删除整个变更对照表而不是删除明细!"))
		{
			var paramXml = "<ROOT><map>";
			paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH></map></ROOT>";
			var service = new Service("com.padis.business.xzqhwh.zxbg.sqdsc.SqdscService.deleteSqd");
			var resultXml = service.doService(paramXml); //发送请求，获取返回结果
			var code = service.getCode();  //返回结果的状态
			var message = service.getMessage();  //返回的信息
			if( code != 2000 ){
				showBizMsg(code,message);
			}else{
				showMessage("操作执行成功！",0);			
			}
			location.href="index.jsp";
		}
	 }else
	 {
	 	showMessage("请选择一条变更对照表记录！",0);
		return false;
	 }
}

function getCount(sqdxh){
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.sqdsc.SqdscService.getCount");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
	if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
		var count = doc.selectSingleNode("ROOT/Result/MAP/COUNT").text;
		if(count>30){
			return false;
		}else {
			return true;
		}
	}
}
