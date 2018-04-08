function btn_sub_query()
{	
	var hzmc = document.getElementById("HZMC").value;
	var hzsjq = document.getElementById("HZSJQ").value;
	var hzsjz = document.getElementById("HZSJZ").value;
	var checkObj = document.getElementsByName("HZTJ");
	var hztj="";
	var count=0;
	for(var i=0;i<checkObj.length;i++){
		if(checkObj[i].checked){
			hztj = checkObj[i].value;
			count++;
		}
	}
	if(count==2){
		hztj = "3";
	}
	if(hzmc==""){
		showMessage("本次汇总名称不能为空,请填写！",0);
		return false;
	}
	if(hzsjq==""&&hzsjz==""){
		showMessage("汇总时间不能为空！",0);
		return false;
	}
	if(hzsjq!=""&&hzsjz!=""){
		if(hzsjq>hzsjz){
			showMessage("汇总时间起不能大于汇总时间止！",0);
			return false;
		}
	}
	if(hztj==""){
		showMessage("请选择汇总条件！",0);
		return false;
	}
	location="QhdmbgqkhztjService.sumQhbgqk.do?HZSJQ="+hzsjq+"&HZSJZ="+hzsjz+"&HZTJ="+hztj+"&HZMC="+hzmc;
}

function btn_sub_reset()
{	
	document.getElementById("HZSJQ").value = "";
	document.getElementById("HZSJZ").value = "";
	document.getElementById("HZMC").value = "";
	document.getElementById("YYHZMC").value = "";
	var checkObj = document.getElementsByName("HZTJ");
	for(var i=0;i<checkObj.length;i++){
		if(checkObj[i].checked){
			checkObj[i].checked=false;
		}
	}
}

function btn_sub_delete()
{	
	var yyhzmc = document.getElementById("YYHZMC").value;
	if(yyhzmc==""){
		showMessage("请选择要删除的汇总名称！",0);
		return false;
	}else{
		if(confirm("此次将会删除您所有汇总名称为“"+yyhzmc+"”的数据，您确定要删除此汇总数据吗？")){
			var paramXml = "<ROOT><map>";
			paramXml = paramXml+"<HZMC>"+yyhzmc+"</HZMC></map></ROOT>";
			var service = new Service("com.padis.business.xzqhwh.qhdmbgqkhztj.QhdmbgqkhztjService.deleteQhbgqk");
			var resultXml = service.doService(paramXml); //发送请求，获取返回结果
			var code = service.getCode();  //返回结果的状态
			var message = service.getMessage();  //返回的信息
			if( code != 2000 ){
				showBizMsg(code,message);
			}else{
				showMessage("删除汇总数据成功！",0);
				location="index.jsp";
			}
		}
	}
}





