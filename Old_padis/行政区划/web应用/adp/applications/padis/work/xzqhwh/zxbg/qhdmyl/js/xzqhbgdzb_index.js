 /*
  @company digitalchina
  @author lijl
  @param obj 下拉框对象
  @param values 行政区划代码
  @param flag 点击标志
  @date 2008-04-10
  为联动下拉框中的省级以下行政区划赋值
*/
function getBgqXjXzqh(obj,values,flag){
	var fhjd = "";
	var gbjd = obj.id;
	/*户籍地*/
	if(gbjd=="QXZQH_SHENG"){
	   fhjd = "XZQH_SHI";
	   document.getElementById("QXZQH_XIAN").options.length=0; //清空以前的选项
	   document.getElementById("QXZQH_XIANG").options.length=0;
	   document.getElementById("QXZQH_CUN").options.length=0;
	   document.getElementById("QXZQH_ZU").options.length=0;
	}
	if(gbjd=="QXZQH_SHI"){
	   fhjd = "XZQH_XIAN";
	   document.getElementById("QXZQH_XIANG").options.length=0;
	   document.getElementById("QXZQH_CUN").options.length=0;
	   document.getElementById("QXZQH_ZU").options.length=0;
	}
	if(gbjd=="QXZQH_XIAN"){
	   fhjd = "XZQH_XIANG";
	   document.getElementById("QXZQH_CUN").options.length=0;
	   document.getElementById("QXZQH_ZU").options.length=0;
	}
	if(gbjd=="QXZQH_XIANG"){
	   fhjd = "XZQH_CUN";
	   document.getElementById("QXZQH_ZU").options.length=0;
	}
	if(gbjd=="QXZQH_CUN"){
	   fhjd = "XZQH_ZU";
	}
	var xzqh_dm = obj.value;
	if(values!=""){
		xzqh_dm = values;
	}
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<SJXZQH>"+xzqh_dm+"</SJXZQH><FHJD>"+fhjd+"</FHJD><DB>V_DM_XZQH</DB></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.common.XzqhService.getXjXzqh");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var code = service.getCode();  //返回结果的状态
	var message = service.getMessage();  //返回的信息
	if( code != 2000 ){
		showBizMsg(code,message);
		return ;
	}else{
		var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
		var shjxzqhDOC = doc.getElementsByTagName(fhjd)(0);
		var shjxzqhXML = shjxzqhDOC.xml;
		ControlService.setOptions("Q"+fhjd,shjxzqhXML,'-1');
	}

	document.getElementById("qxzqh_dm").innerText=obj.value;
	if(obj.length>0){
		for(i=0;i<obj.length;i++){
		   if(obj[i].selected==true){
			   var xzqh_mc = obj[i].innerText;
			   var index = xzqh_mc.lastIndexOf(" ");
			   if(index>-1){
					xzqh_mc = xzqh_mc.substring(0,index);
			   }
			   document.getElementById("qxzqh_mc").innerText=xzqh_mc;
		   }
		}
	}
	/*
	if(flag=="1"){
		var bghObj;
		if(gbjd=="QXZQH_SHENG"){
		   bghObj = document.getElementById("HXZQH_SHENG");
		   getBghXjXzqh(bghObj,obj.value,"");
		   changeSelected(bghObj,obj.value);

		}
		else if(gbjd=="QXZQH_SHI"){
			bghObj = document.getElementById("HXZQH_SHI");
		   getBghXjXzqh(bghObj,obj.value,"");
		   changeSelected(bghObj,obj.value);
		}
		else if(gbjd=="QXZQH_XIAN"){
			bghObj = document.getElementById("HXZQH_XIAN");
		   getBghXjXzqh(bghObj,obj.value,"");
		   changeSelected(bghObj,obj.value);
		}
		else if(gbjd=="QXZQH_XIANG"){
			bghObj = document.getElementById("HXZQH_XIANG");
		   getBghXjXzqh(bghObj,obj.value,"");
		   changeSelected(bghObj,obj.value);
		}
		else if(gbjd=="QXZQH_CUN"){
			bghObj = document.getElementById("HXZQH_CUN");
		   getBghXjXzqh(bghObj,obj.value,"");
		   changeSelected(bghObj,obj.value);
		}
		getXzqhDmMc(obj,obj.value,bghObj,bghObj.value);
	}
	*/
}

 /*
  @company digitalchina
  @author lijl
  @param obj 下拉框对象
  @param values 行政区划代码
  @param flag 点击标志
  @date 2008-04-10
  为联动下拉框中的省级以下行政区划赋值
*/
function getBghXjXzqh(obj,values,flag){
	var fhjd = "";
	var gbjd = obj.id;
	/*户籍地*/
	if(gbjd=="HXZQH_SHENG"){
	   fhjd = "XZQH_SHI";	   
	   document.getElementById("HXZQH_XIAN").options.length=0; //清空以前的选项
	   document.getElementById("HXZQH_XIANG").options.length=0;
	   document.getElementById("HXZQH_CUN").options.length=0;
	   document.getElementById("HXZQH_ZU").options.length=0;
	}
	if(gbjd=="HXZQH_SHI"){
	   fhjd = "XZQH_XIAN";
	   document.getElementById("HXZQH_XIANG").options.length=0;
	   document.getElementById("HXZQH_CUN").options.length=0;
	   document.getElementById("HXZQH_ZU").options.length=0;
	}
	if(gbjd=="HXZQH_XIAN"){
	   fhjd = "XZQH_XIANG";
	   document.getElementById("HXZQH_CUN").options.length=0;
	   document.getElementById("HXZQH_ZU").options.length=0;
	}
	if(gbjd=="HXZQH_XIANG"){
	   fhjd = "XZQH_CUN";
	   document.getElementById("HXZQH_ZU").options.length=0;
	}
	if(gbjd=="HXZQH_CUN"){
	   fhjd = "XZQH_ZU";
	}
	var xzqh_dm = obj.value;
	if(values!=""){
		xzqh_dm = values;
	}
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<SJXZQH>"+xzqh_dm+"</SJXZQH><FHJD>"+fhjd+"</FHJD><DB>V_DM_XZQH_YLSJ</DB></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.common.XzqhService.getXjXzqh");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var code = service.getCode();  //返回结果的状态
	var message = service.getMessage();  //返回的信息
	if( code != 2000 ){
		showBizMsg(code,message);
		return ;
	}else{
		var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
		var shjxzqhDOC = doc.getElementsByTagName(fhjd)(0);
		var shjxzqhXML = shjxzqhDOC.xml;
		ControlService.setOptions("H"+fhjd,shjxzqhXML,'-1');
	}
	
	document.getElementById("hxzqh_dm").innerText=obj.value;
	if(obj.length>0){
		for(i=0;i<obj.length;i++){
		   if(obj[i].selected==true){
			   var xzqh_mc = obj[i].innerText;
			   var index = xzqh_mc.lastIndexOf(" ");
			   if(index>-1){
					xzqh_mc = xzqh_mc.substring(0,index);
			   }
			   document.getElementById("hxzqh_mc").innerText=xzqh_mc;
		   }
		}
	}

	/*if(flag=="1"){
		var bgqObj;
		if(gbjd=="HXZQH_SHENG"){
			bgqObj = document.getElementById("QXZQH_SHENG");
		   getBgqXjXzqh(bgqObj,obj.value,"");
		   changeSelected(bgqObj,obj.value);
		}
		else if(gbjd=="HXZQH_SHI"){
			bgqObj = document.getElementById("QXZQH_SHI");
		   getBgqXjXzqh(bgqObj,obj.value,"");
		   changeSelected(bgqObj,obj.value);
		}
		else if(gbjd=="HXZQH_XIAN"){
			bgqObj = document.getElementById("QXZQH_XIAN");
		   getBgqXjXzqh(bgqObj,obj.value,"");
		   changeSelected(bgqObj,obj.value);
		}
		else if(gbjd=="HXZQH_XIANG"){
			bgqObj = document.getElementById("QXZQH_XIANG");
		   getBgqXjXzqh(bgqObj,obj.value,"");
		   changeSelected(bgqObj,obj.value);
		}
		else if(gbjd=="HXZQH_CUN"){
			bgqObj = document.getElementById("QXZQH_CUN");
		   getBgqXjXzqh(bgqObj,obj.value,"");
		   changeSelected(bgqObj,obj.value);
		}
		getXzqhDmMc(bgqObj,bgqObj.value,obj,obj.value);
	}
	*/
}

 /*
  @company digitalchina
  @author LIJL
  @param obj 下拉框对象
  @param xzqh_dm 行政区划代码
  @date 2009-06-15
  改变光标位置
*/
function changeSelected(obj,xzqh_dm){
	var count=0;
	for(i=0;i<obj.length;i++){
	   if(obj[i].value==xzqh_dm){
		   obj[i].selected=true;
	   }else{
		   count++;
		   obj[i].selected=false;
	   }
	}
	if(count==obj.length){
		obj.selectedIndex=-1;
	}
}

function getXzqhDmMc(obj,xzqh_dm,obj1,xzqh_dm1,xzqhjb){
	document.getElementById("qxzqh_dm").innerText=xzqh_dm;
	document.getElementById("hxzqh_dm").innerText=xzqh_dm1;
	var count1=0;
	var count2=0;
	if(obj.length>0){
		for(i=0;i<obj.length;i++){
		   if(obj[i].selected==true){				
			   var xzqh_mc = obj[i].innerText;			   
			   var index = xzqh_mc.lastIndexOf(" ");
			   if(index>-1){
					xzqh_mc = xzqh_mc.substring(0,index);
			   }
			   document.getElementById("qxzqh_mc").innerText=xzqh_mc;
		   }else{
				count1++;
		   }
		}
	}else{
		document.getElementById("qxzqh_mc").innerText="";
	}
	if(count1==obj.length){		
		document.getElementById("qxzqh_dm").innerText="";
		document.getElementById("qxzqh_mc").innerText="";	
	}

	if(obj1.length>0){
		for(i=0;i<obj1.length;i++){
		   if(obj1[i].selected==true){
			   var xzqh_mc = obj1[i].innerText;
			   var index = xzqh_mc.lastIndexOf(" ");
			   if(index>-1){
					xzqh_mc = xzqh_mc.substring(0,index);
			   }
			   document.getElementById("hxzqh_mc").innerText=xzqh_mc;
		   }else{
				count2++;
		   }
		}
	}else{
		document.getElementById("hxzqh_mc").innerText="";
	}

	if(count2==obj1.length){
		document.getElementById("hxzqh_dm").innerText="";
		document.getElementById("hxzqh_mc").innerText="";
	}
}
