
function commitSqd(){
	var flag =false;
	var sqdxh ="";
	var sqdzt_dm="";
	var sqdArray=document.getElementsByName("sqdxh_radio");
	if(confirm("请确保在“录入变更明细”模块中已完成本县所有区划代码变更明细录入工作，且纸质文件已报相关负责同志审定。是否继续？",0)==true){
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			flag=true;
			sqdxh = sqdArray[i].value.split("||")[0];
			sqdzt_dm =sqdArray[i].value.split("||")[1];
		}
	 }
	 if(flag)
	 {
	 	 if(sqdzt_dm != "10" && sqdzt_dm!="21" && sqdzt_dm!="")
		 {
		 	showMessage("只能提交变更对照表状态为未提交或审核未通过的记录！",0);
		 	return false;
		 }
		var paramXml = "<ROOT><map>";
		paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH><SQDZT_DM>"+sqdzt_dm+"</SQDZT_DM></map></ROOT>";
		var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.tjbgdzb.TjbgdzbService.queryMxb");
		var resultXml = service.doService(paramXml); //发送请求，获取返回结果
		var doc = loadXml(resultXml);
		var msg = "确定提交该记录？";
		if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
			var flag = doc.selectSingleNode("ROOT/Result/MAP/SFLTJ").text;
			if(flag=="true"){
				msg = "是否要零上报？";
			}
		}
		if(confirm(msg))
		{
			var paramXml = "<ROOT><map>";
			paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH><SQDZT_DM>"+sqdzt_dm+"</SQDZT_DM></map></ROOT>";
			var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.tjbgdzb.TjbgdzbService.commitSqd");
			var resultXml = service.doService(paramXml); //发送请求，获取返回结果
			var code = service.getCode();  //返回结果的状态
			var message = service.getMessage();  //返回的信息
			if( code != 2000 ){
				showBizMsg(code,message);
				return ;
			}else{
				showMessage("操作执行成功！",0);
				location.href="index.jsp";
			}
		}
	 	
	 }else
	 {
	 	showMessage("请选择一条变更对照表记录！",0);
		return false;
	 }
	}
}


