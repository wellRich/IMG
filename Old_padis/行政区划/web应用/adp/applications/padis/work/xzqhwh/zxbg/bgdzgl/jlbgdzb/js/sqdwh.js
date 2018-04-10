function initPage(){
	window.open("promptMessage.html","提示信息","height=170px,width=450px,top=250,left=400,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=no,resizable=no");
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
	 	var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.jlbgdzb.JlbgdzbService.querySqdzt");
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
		var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.jlbgdzb.JlbgdzbService.addSqd");
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

function checkScsj(sqdxh){
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<SQDXH>"+sqdxh+"</SQDXH></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.bgdzgl.jlbgdzb.JlbgdzbService.checkScsj");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
	if(doc.selectSingleNode("ROOT/Result/MAP").text != "null"){
		var flag = doc.selectSingleNode("ROOT/Result/MAP/FLAG").text;
		return flag;
	}
}

