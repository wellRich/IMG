function revokeSqd(){
	var flag =false;
	var sqdxh ="";
	var sqdArray=document.getElementsByName("sqdxh_radio");
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			flag=true;
			sqdxh = sqdArray[i].value.split("||")[0];
		}
	 }
	 if(flag)
	 {	var sqdzt_dm = querySqdzt(sqdxh);
		if(sqdzt_dm != "11")
		{
		 	showMessage("只能撤消变更对照表状态为已提交的记录！",0);
		 	location.href="index.jsp";
			return;
		}
		var paramXml = "<ROOT><map>";
		paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH><SQDZT_DM>"+sqdzt_dm+"</SQDZT_DM></map></ROOT>";
		var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.cxbgdzb.CxbgdzbService.revokeSqd");
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
	 }else
	 {
	 	showMessage("请选择一条变更对照表记录！",0);
		return false;
	 }
}

function querySqdzt(sqdxh){
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.cxbgdzb.CxbgdzbService.querySqdzt");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
	if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
		var flag = doc.selectSingleNode("ROOT/Result/MAP/SQDZT_DM").text;
		return flag;
	}
}