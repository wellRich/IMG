 /*
  @company digitalchina
  @author LIJL
  @param temp 根据temp判断给那个字段赋值
  @date 2009-06-10
  为联动下拉框中的省级以下行政区划赋值
*/
function getXjXzqh(obj,db){
	var fhjd = "";
	var gbjd = obj.id;
	/*户籍地*/
	if(gbjd=="XZQH_SHENG"){
	   fhjd = "XZQH_SHI";
	   document.getElementById("XZQH_XIAN").options.length=0; //清空以前的选项
	   document.getElementById("XZQH_XIANG").options.length=0;
	   document.getElementById("XZQH_CUN").options.length=0;
	   document.getElementById("XZQH_ZU").options.length=0;
	}
	if(gbjd=="XZQH_SHI"){
	   fhjd = "XZQH_XIAN";
	   document.getElementById("XZQH_XIANG").options.length=0;
	   document.getElementById("XZQH_CUN").options.length=0;
	   document.getElementById("XZQH_ZU").options.length=0;
	}
	if(gbjd=="XZQH_XIAN"){
	   fhjd = "XZQH_XIANG";
	   document.getElementById("XZQH_CUN").options.length=0;
	   document.getElementById("XZQH_ZU").options.length=0;
	}
	if(gbjd=="XZQH_XIANG"){
	   fhjd = "XZQH_CUN";
	   document.getElementById("XZQH_ZU").options.length=0;
	}
	if(gbjd=="XZQH_CUN"){
	   fhjd = "XZQH_ZU";
	}
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<DB>"+db+"</DB><SJXZQH>"+obj.value+"</SJXZQH><FHJD>"+fhjd+"</FHJD></map></ROOT>";
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
		ControlService.setOptions(fhjd,shjxzqhXML,'-1');
	}
	
}

 /*
  @company digitalchina
  @author LIJL
  @param obj 下拉框ID
  @date 2009-06-15
  获取行政区划名称和代码
*/

function queryXzqh(obj,db){
	var xzqh_dm =obj.value;
	if(xzqh_dm==""){
		return;
	}
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<DB>"+db+"</DB><XZQHDM>"+xzqh_dm+"</XZQHDM></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.qhdmcx.QhdmcxService.queryXzqh");
	var resultXml = service.doService(paramXml); //发送请求，获取返回结果
	var code = service.getCode();  //返回结果的状态
	var message = service.getMessage();  //返回的信息
	if( code != 2000 ){
		showBizMsg(code,message);
		return ;
	}else{
		var doc = loadXml(resultXml); //构造标准的MSXML2.DOMDocument对象
		document.getElementById("XZQH_DM").innerHTML =doc.selectSingleNode("ROOT/Result/MAP/XZQH_DM").text;
		document.getElementById("XZQH_MC").innerHTML= doc.selectSingleNode("ROOT/Result/MAP/XZQH_MC").text;
		document.getElementById("XZQH_QC").innerHTML = doc.selectSingleNode("ROOT/Result/MAP/XZQH_QC").text;
		document.getElementById("SJ_XZQH_MC").innerHTML = doc.selectSingleNode("ROOT/Result/MAP/SJ_XZQH_MC").text;
		document.getElementById("DWLSGX").innerHTML= doc.selectSingleNode("ROOT/Result/MAP/DWLSGX").text;
		document.getElementById("XZQHLX").innerHTML = doc.selectSingleNode("ROOT/Result/MAP/XZQHLX").text;
		document.getElementById("XNJD_BZ").innerHTML = doc.selectSingleNode("ROOT/Result/MAP/XNJD_BZ").text;
		
	}
}