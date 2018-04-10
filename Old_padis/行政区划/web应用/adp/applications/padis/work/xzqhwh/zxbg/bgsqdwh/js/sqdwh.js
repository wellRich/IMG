function initPage(){
	window.open("promptMessage.html","提示信息","height=150px,width=450px,top=250,left=400,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=no,resizable=no");
}
function queryMxb(){
var flag =false;
	var sqdxh ="";
	var sqdzt_dm="";
	var sbxzqh_dm="";
	var sqdArray=document.getElementsByName("sqdxh_radio");
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			flag=true;
			sqdxh = sqdArray[i].value.split("||")[0];
			sqdzt_dm =sqdArray[i].value.split("||")[1];
			sbxzqh_dm =sqdArray[i].value.split("||")[2];
		}
	 }
	 if(flag)
	 {
	 		var iframe = document.getElementById("iframe1");
			iframe.src="SqdwhService.queryMxb.do?PAGESIZE=10&SQDXH="+sqdxh+"&SQDZT_DM="+sqdzt_dm+"&SBXZQH_DM="+sbxzqh_dm;
	 }else
	 {
	 	showMessage("请选择一条变更对照表记录！",0);
		return false;
	 }

}

function getSelect(obj)
{
	var groupxh = obj.value;
	var str = document.getElementById("check_value").value;
	if(obj.checked == true)
	{
		if(str!=groupxh)
		{
			str = groupxh;
		}
	}else
	{
		str ="";	
	}
	var wdxlArray=document.getElementsByName("mxb_check");
	for(var i=0;i<wdxlArray.length;i++)
 	{
		if(wdxlArray[i].value==str)
		{
			wdxlArray[i].checked =true;
		}else
		{
			wdxlArray[i].checked =false;
		}
	 }
	 document.getElementById("check_value").value=str;
	
}

/*
*检查是否有选中的记录
*/
function checkSelected()
{
 var wdxlArray=document.getElementsByName("mxb_check");
 var cnt=0;
 for(var i=0;i<wdxlArray.length;i++)
 {
	if(wdxlArray[i].checked==true)
	{
		cnt++;
	}
 }
 return cnt;
}

function deleteMxb(sbxzqh_dm){
	
	if(checkSelected()<1){
		showMessage("请选择要删除的记录！",0);
		return false;
	}
	
	var groupxh=document.getElementById("check_value").value;
	var sqdxh=document.getElementById("SQDXH").value;
	var flag = checkScsj(sqdxh);
	if(flag=="true"){
		showMessage("集中上报数据不能删除明细，只能删除整个变更对照表！",0);
		return false;
	}
	if(confirm("将变更组序号为"+groupxh+"的变更对照表都删除吗？"))
	{
		var paramXml = "<ROOT><map>";
		paramXml = paramXml+"<GROUPXH>"+groupxh+"</GROUPXH><SQDXH>"+sqdxh+"</SQDXH><SBXZQH_DM>"+sbxzqh_dm+"</SBXZQH_DM></map></ROOT>";
		var service = new Service("com.padis.business.xzqhwh.zxbg.bgsqdwh.SqdwhService.deleteMxb");
		var resultXml = service.doService(paramXml); //发送请求，获取返回结果
		var code = service.getCode();  //返回结果的状态
		var message = service.getMessage();  //返回的信息
		if( code != 2000 ){
			showBizMsg(code,message);
		}else{
			showMessage("操作执行成功！",0);		
		}
		parent.location.href="index.jsp";
	}
}

function addSqd(mc,count)
{
	if(count!="0"){
		showMessage("不能在同一个时期添加两个变更对照表",0);
		return ;
	}
	var return_value= showModalDialog("controlWindow.jsp?control=add&mc="+mc,window,"dialogWidth=450px;dialogHeight=280px;center:yes;");
	if(return_value == null)
	{
		return ;
	}
	 if(return_value !=null && return_value!=""){
	 	var service = new Service("com.padis.business.xzqhwh.zxbg.bgsqdwh.SqdwhService.querySqdzt");
		var resultXml = service.doService(); //发送请求，获取返回结果
		var doc = loadXml(resultXml);
		if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
			var counts = doc.selectSingleNode("ROOT/Result/MAP/COUNT").text;
			if(Number(counts)>0){
				showMessage("不能在同一个时期添加两个变更对照表",0);
				location.href="index.jsp";
				return ;
			}
		}
		var sqdmc = return_value.substring(0,return_value.indexOf("&"));
		var bz=return_value.substring(return_value.indexOf("&")+1);
		var paramXml = "<ROOT><map>";
		paramXml = paramXml+"<SQDMC>"+sqdmc+"</SQDMC><BZ>"+bz+"</BZ></map></ROOT>";
		var service = new Service("com.padis.business.xzqhwh.zxbg.bgsqdwh.SqdwhService.addSqd");
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
	
}

function updateSqd()
{
	var flag =false;
	var sqdxh ="";
	var sqdmc="";
	var bz = "";
	var sqdzt_dm ="";
	var sqdArray=document.getElementsByName("sqdxh_radio");
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			flag=true;
			sqdxh = sqdArray[i].value.split("||")[0];
			sqdzt_dm =sqdArray[i].value.split("||")[1];
			sqdmc =  sqdArray[i].value.split("||")[3];
			bz =  sqdArray[i].value.split("||")[4];
		}
	 }
	 
	 if(flag)
	 {
	 	if(sqdzt_dm != "10" && sqdzt_dm!="21" && sqdzt_dm!="")
		 {
		 	showMessage("只能修改变更对照表状态为未提交或审核未通过的记录！",0);
		 	return false;
		 }
		var return_value= showModalDialog("controlWindow.jsp?control=update&sqdmc="+sqdmc+"&bz="+bz,window,"dialogWidth=450px;dialogHeight=280px;center:yes;");
		if(return_value == null)
		{
		 	return ;
		}
		sqdmc = return_value.substring(0,return_value.indexOf("&"));
		bz=return_value.substring(return_value.indexOf("&")+1);
	 	var paramXml = "<ROOT><map>";
		paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH><SQDMC>"+sqdmc+"</SQDMC><BZ>"+bz+"</BZ></map></ROOT>";
		var service = new Service("com.padis.business.xzqhwh.zxbg.bgsqdwh.SqdwhService.updateSqd");
		var resultXml = service.doService(paramXml); //发送请求，获取返回结果
		var code = service.getCode();  //返回结果的状态
		var message = service.getMessage();  //返回的信息
		if( code != 2000 ){
			showBizMsg(code,message);
			return ;
		}else{
			location.href="index.jsp";
		}
	 }else
	 {
	 	showMessage("请选择一条变更对照表记录！",0);
		return false;
	 }
	
}

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
	 if(flag)
	 {
	 	 if(sqdzt_dm != "10" && sqdzt_dm!="21" && sqdzt_dm!="")
		 {
		 	showMessage("只能删除变更对照表状态为未提交或审核未通过的记录！",0);
		 	return false;
		 }
		if(confirm("确定删除该记录？"))
		{
			var paramXml = "<ROOT><map>";
			paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH></map></ROOT>";
			var service = new Service("com.padis.business.xzqhwh.zxbg.bgsqdwh.SqdwhService.deleteSqd");
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
		var service = new Service("com.padis.business.xzqhwh.zxbg.bgsqdwh.SqdwhService.queryMxb");
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
			var service = new Service("com.padis.business.xzqhwh.zxbg.bgsqdwh.SqdwhService.commitSqd");
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

function expSqd()
{
	var flag =false;
	var sqdxh ="";
	var sqdmc="";
	var sqdArray=document.getElementsByName("sqdxh_radio");
	for(var i=0;i<sqdArray.length;i++)
 	{
		if(sqdArray[i].checked == true)
		{
			flag=true;
			sqdxh = sqdArray[i].value.split("||")[0];
			sqdmc = sqdArray[i].value.split("||")[2];
		}
	 }
	 if(flag)
	 {
	 		form1.action="SqdwhService.expSqd.do?value(SQDXH)="+sqdxh+"&value(SQDMC)="+sqdmc;
	 		form1.submit();
			
	 }else
	 {
	 	showMessage("请选择一条变更对照表记录！",0);
		return false;
	 }
}

function checkScsj(sqdxh){
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.bgsqdwh.SqdwhService.checkScsj");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
	if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
		var flag = doc.selectSingleNode("ROOT/Result/MAP/FLAG").text;
		return flag;
	}
}

