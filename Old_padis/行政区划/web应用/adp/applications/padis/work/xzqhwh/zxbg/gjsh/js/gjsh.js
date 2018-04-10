var checkboxselect = false;
function selectAll(){	
	var sqdxh_check=document.getElementsByName("sqdxh_check");  	
    if(checkboxselect){		 
         checkboxselect = false;
	}else{					
         checkboxselect = true;
	}
     for(var i=0;i<sqdxh_check.length;i++)   
     {               
         sqdxh_check[i].checked   =   checkboxselect;     
     } 
}
function querySqd(xzqh_dm)
{
	navigate("../work/xzqhwh/zxbg/gjsh/sqd.jsp?XZQH_DM="+xzqh_dm, "变更对照表信息", false,"行政区划维护" ) ;
}
function init()
{
	var paramXml = "<ROOT><map></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.gjsh.GjshService.initSqdzt");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var code = service.getCode();  //返回结果的状态
	var message = service.getMessage();  //返回的信息
	if( code != 2000 ){
		showBizMsg(code,message);
		return ;
	}else{
		var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
		var sqdztDOC = doc.getElementsByTagName("SQDZT_DM")(0);
 		var sqdztXML = sqdztDOC.xml;
 		var obj = document.getElementById("SQDZT_DM");
		ControlService.setOptions("SQDZT_DM",sqdztXML,"");
	}
}
function gjsh()
{

	var wtj = parent.window.frames["iframe2"].document.getElementById("wtj").value;
	if(wtj !="0")
	{
		showMessage("存在未提交变更对照表的区划",0);
		return ;
	}
	var paramXml = "<ROOT><map></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.gjsh.GjshService.querySqdzt");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var code = service.getCode();  //返回结果的状态
	var message = service.getMessage();  //返回的信息
	if( code != 2000 ){
		showBizMsg(code,message);
		return ;
	}else{
		var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
		var msg =doc.selectSingleNode("ROOT/Result/MAP/COUNT").text;
		if(msg!="通过")
		{
			showMessage(msg,0);
			return;
		}
	}
	if(confirm("所有变更对照表都审核通过？"))
	{
		form1.action="GjshService.gjsh.do";
		form1.submit();
	}

}

function checkComplete(){
	var sqdxhArray=document.getElementsByName("xzqh_check");
	var counts = 0;
	var xzqhdms ="";
	for(var i=0;i<sqdxhArray.length;i++)
	{
		if(sqdxhArray[i].checked==true)
		{	
			if(counts>0){
				xzqhdms = xzqhdms+",";
			}
			xzqhdms =xzqhdms+sqdxhArray[i].value;
			counts++;
		}
	}
	if(counts==0){
		showMessage("请至少选择一条记录！",0);
		return;
	}
	var paramXml = "<ROOT><map><XZQH_DM>"+xzqhdms+"</XZQH_DM></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.gjsh.GjshService.querySqdzt");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var code = service.getCode();  //返回结果的状态
	var message = service.getMessage();  //返回的信息
	if( code != 2000 ){
		showBizMsg(code,message);
		return ;
	}else{
		var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
		var msg =doc.selectSingleNode("ROOT/Result/MAP/COUNT").text;
		if(msg!="通过")
		{
			showMessage(msg,0);
			return;
		}
	}
	if(confirm("选中的变更对照表都审核通过吗？"))
	{
		form1.action="GjshService.gjsh.do?XZQH_DM="+xzqhdms;
		form1.submit();
	}
}

function back()
{
	var sqdxh="";
	var sqdztdm="";
	var isPass = true;
	var counts=0;
	var sqdxhArray=document.getElementsByName("sqdxh_check");
	var flag = false;
	 for(var i=0;i<sqdxhArray.length;i++)
	 {

		if(sqdxhArray[i].checked==true)
		{	
			
			flag = true;
			if(counts>0){
				sqdxh = sqdxh+",";
			}
			sqdxh =sqdxh+sqdxhArray[i].value.split("||")[0];
			sqdztdm = sqdxhArray[i].value.split("||")[1];			
			if(sqdztdm!="30"){
				isPass = false;
			}
			counts++;
		}
	 }
	if(flag)
	{
		if(!isPass)
		{
			showMessage("只能驳回已确认的记录!",0);
			return ;
		}
		if(confirm("确认驳回所选中的变更对照表？"))
		{
			var return_value="";
			return_value= showModalDialog("controlWindow.jsp",window,"dialogWidth=450px;dialogHeight=280px;center:yes;");
	
			if(return_value == null)
			{
				return ;
			}
			if(sqdxh != null && sqdxh != "")
			{
				var paramXml = "<ROOT><map>";
				paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH><SPYJ>"+return_value+"</SPYJ></map></ROOT>";
				var service = new Service("com.padis.business.xzqhwh.zxbg.gjsh.GjshService.back");
				var resultXml = service.doService(paramXml); //发送请求，获取返回结果
				var code = service.getCode();  //返回结果的状态
				var message = service.getMessage();  //返回的信息
				if( code != 2000 ){
					showBizMsg(code,message);
					return ;
				}else{
					showMessage("操作执行成功！",0);
					var xzqh_dm=parent.window.document.getElementById("xzqh_dm").value;
					parent.location.href="sqd.jsp?XZQH_DM="+xzqh_dm;
				}
			}
		}

	}else
	{
		showMessage("请选择一条记录!",0);
	}
}

function bgsqqr()
{
	var sqdxh="";
	var sqdztdm="";
	var isPass = true;
	var counts=0;
	var sqdxhArray=document.getElementsByName("sqdxh_check");
	var flag = false;
	 for(var i=0;i<sqdxhArray.length;i++)
	 {

		if(sqdxhArray[i].checked==true)
		{	
			
			flag = true;
			if(counts>0){
				sqdxh = sqdxh+",";
			}
			sqdxh =sqdxh+sqdxhArray[i].value.split("||")[0];
			sqdztdm = sqdxhArray[i].value.split("||")[1];			
			if(Number(sqdztdm)>=30||Number(sqdztdm)<=10){
				isPass = false;
			}
			counts++;
		}
	 }
	if(flag)
	{
		if(!isPass)
		{
			showMessage("只能确认已提交或者被驳回的记录!",0);
			return ;
		}
		if(confirm("确认所选中的变更对照表？"))
		{
			if(sqdxh != null && sqdxh != "")
			{
				var paramXml = "<ROOT><map>";
				paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH></map></ROOT>";
				var service = new Service("com.padis.business.xzqhwh.zxbg.gjsh.GjshService.bgsqqr");
				var resultXml = service.doService(paramXml); //发送请求，获取返回结果
				var code = service.getCode();  //返回结果的状态
				var message = service.getMessage();  //返回的信息
				if( code != 2000 ){
					showBizMsg(code,message);
					return ;
				}else{
					showMessage("操作执行成功！",0);
					var xzqh_dm=parent.window.document.getElementById("xzqh_dm").value;
					parent.location.href="sqd.jsp?XZQH_DM="+xzqh_dm;
				}
			}
		}

	}else
	{
		showMessage("请选择一条记录!",0);
	}
}

function btn_sub_query(xzqh_dm)
{	
	
	var sqdmc = document.getElementById("SQDMC").value;
	var sqdzt_dm = document.getElementById("SQDZT_DM").value;
	var iframe = document.getElementById("iframe_sqd");
	iframe.src="GjshService.querySqd.do?XZQH_DM="+xzqh_dm+"&SQDMC="+sqdmc+"&SQDZT_DM="+sqdzt_dm;
}

function btn_sub_reset()
{	
	document.getElementById("SQDMC").value = "";
	document.getElementById("SQDZT_DM").value = "";
}





