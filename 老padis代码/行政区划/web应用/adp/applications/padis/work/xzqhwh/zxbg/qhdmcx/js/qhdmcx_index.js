 /*
  @company digitalchina
  @author LIJL
  @param temp ����temp�жϸ��Ǹ��ֶθ�ֵ
  @date 2009-06-10
  Ϊ�����������е�ʡ����������������ֵ
*/
function getXjXzqh(obj,db){
	var fhjd = "";
	var gbjd = obj.id;
	/*������*/
	if(gbjd=="XZQH_SHENG"){
	   fhjd = "XZQH_SHI";
	   document.getElementById("XZQH_XIAN").options.length=0; //�����ǰ��ѡ��
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
	var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
	var code = service.getCode();  //���ؽ����״̬
	var message = service.getMessage();  //���ص���Ϣ
	if( code != 2000 ){
		showBizMsg(code,message);
		return ;
	}else{
		var doc = loadXml(resultXml); //�����׼��MSXML2.DOMDocument����
		var shjxzqhDOC = doc.getElementsByTagName(fhjd)(0);
		var shjxzqhXML = shjxzqhDOC.xml;
		ControlService.setOptions(fhjd,shjxzqhXML,'-1');
	}
	
}

 /*
  @company digitalchina
  @author LIJL
  @param obj ������ID
  @date 2009-06-15
  ��ȡ�����������ƺʹ���
*/

function queryXzqh(obj,db){
	var xzqh_dm =obj.value;
	if(xzqh_dm==""){
		return;
	}
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<DB>"+db+"</DB><XZQHDM>"+xzqh_dm+"</XZQHDM></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.zxbg.qhdmcx.QhdmcxService.queryXzqh");
	var resultXml = service.doService(paramXml); //�������󣬻�ȡ���ؽ��
	var code = service.getCode();  //���ؽ����״̬
	var message = service.getMessage();  //���ص���Ϣ
	if( code != 2000 ){
		showBizMsg(code,message);
		return ;
	}else{
		var doc = loadXml(resultXml); //�����׼��MSXML2.DOMDocument����
		document.getElementById("XZQH_DM").innerHTML =doc.selectSingleNode("ROOT/Result/MAP/XZQH_DM").text;
		document.getElementById("XZQH_MC").innerHTML= doc.selectSingleNode("ROOT/Result/MAP/XZQH_MC").text;
		document.getElementById("XZQH_QC").innerHTML = doc.selectSingleNode("ROOT/Result/MAP/XZQH_QC").text;
		document.getElementById("SJ_XZQH_MC").innerHTML = doc.selectSingleNode("ROOT/Result/MAP/SJ_XZQH_MC").text;
		document.getElementById("DWLSGX").innerHTML= doc.selectSingleNode("ROOT/Result/MAP/DWLSGX").text;
		document.getElementById("XZQHLX").innerHTML = doc.selectSingleNode("ROOT/Result/MAP/XZQHLX").text;
		document.getElementById("XNJD_BZ").innerHTML = doc.selectSingleNode("ROOT/Result/MAP/XNJD_BZ").text;
		
	}
}