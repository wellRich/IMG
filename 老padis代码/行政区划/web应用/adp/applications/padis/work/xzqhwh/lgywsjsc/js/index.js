function init(jc_dm){
	if(jc_dm=="1"){
		document.getElementById("XZQH_SHENG").disabled=true;
		document.getElementById("query_btn").disabled=true;
	}
	if(jc_dm=="2"){
		document.getElementById("XZQH_SHENG").disabled=true;
		document.getElementById("XZQH_SHI").disabled=true;
		document.getElementById("query_btn").disabled=true;
	}
	if(jc_dm=="3"){
		document.getElementById("XZQH_SHENG").disabled=true;
		document.getElementById("XZQH_SHI").disabled=true;
		document.getElementById("XZQH_XIAN").disabled=true;
		document.getElementById("query_btn").disabled=true;
	}
	if(jc_dm=="4"){
		document.getElementById("XZQH_SHENG").disabled=true;
		document.getElementById("XZQH_SHI").disabled=true;
		document.getElementById("XZQH_XIAN").disabled=true;
		document.getElementById("XZQH_XIANG").disabled=true;
	}
	if(jc_dm=="5"){
		document.getElementById("XZQH_SHENG").disabled=true;
		document.getElementById("XZQH_SHI").disabled=true;
		document.getElementById("XZQH_XIAN").disabled=true;
		document.getElementById("XZQH_XIANG").disabled=true;
		document.getElementById("XZQH_CUN").disabled=true;
	}
	if(jc_dm=="6"){
		document.getElementById("XZQH_SHENG").disabled=true;
		document.getElementById("XZQH_SHI").disabled=true;
		document.getElementById("XZQH_XIAN").disabled=true;
		document.getElementById("XZQH_XIANG").disabled=true;
		document.getElementById("XZQH_CUN").disabled=true;
		document.getElementById("XZQH_ZU").disabled=true;
	}
}
 /*
  @company digitalchina
  @author LIJL
  @param temp ����temp�жϸ��Ǹ��ֶθ�ֵ
  @date 2009-06-10
  Ϊ�����������е�ʡ����������������ֵ
*/
function getXjXzqh(obj,temp){
	var fhjd = "";
	var gbjd = obj.id;
	/*������*/
	if(gbjd=="XZQH_SHENG"){
	   fhjd = "XZQH_SHI";
	   document.getElementById("XZQH_XIAN").options.length=0; //�����ǰ��ѡ��
	   document.getElementById("XZQH_XIANG").options.length=0;
	   document.getElementById("XZQH_CUN").options.length=0;
	   document.getElementById("XZQH_ZU").options.length=0;
	   document.getElementById("query_btn").disabled=true;
	}
	if(gbjd=="XZQH_SHI"){
	   fhjd = "XZQH_XIAN";
	   document.getElementById("XZQH_XIANG").options.length=0;
	   document.getElementById("XZQH_CUN").options.length=0;
	   document.getElementById("XZQH_ZU").options.length=0;
	   document.getElementById("query_btn").disabled=true;
	}
	if(gbjd=="XZQH_XIAN"){
	   fhjd = "XZQH_XIANG";
	   document.getElementById("XZQH_CUN").options.length=0;
	   document.getElementById("XZQH_ZU").options.length=0;
	   document.getElementById("query_btn").disabled=true;
	}
	if(gbjd=="XZQH_XIANG"){
	   fhjd = "XZQH_CUN";
	   document.getElementById("XZQH_ZU").options.length=0;
	   document.getElementById("query_btn").disabled=false;
	}
	if(gbjd=="XZQH_CUN"){
	   fhjd = "XZQH_ZU";
	}
	var paramXml = "<ROOT><map>";
	paramXml = paramXml+"<SJXZQH>"+obj.value+"</SJXZQH><FHJD>"+fhjd+"</FHJD></map></ROOT>";
	var service = new Service("com.padis.business.xzqhwh.bgsqb.XzqhbgsqbService.getXjXzqh");
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
function getXzqhMcDm(obj){
	var xzqh_dm = obj.value;
	var xzqh_mc = "";
	for(i=0;i<obj.length;i++){
	   if(obj[i].selected==true){
		   xzqh_mc = obj[i].innerText;
		   var index = xzqh_mc.lastIndexOf(" ");
		   xzqh_mc = xzqh_mc.substring(0,index);
		   document.getElementById("XZQH_MC").value=xzqh_mc;
		   document.getElementById("XZQH_DM").value=xzqh_dm;
	   }
	}
}

/*
  @company digitalchina
  @author LIJL
  @param obj ������ID
  @date 2009-07-31
  ��ȡ�����������ƺʹ���
*/
function deleteXzqh(){
	var xzqh_dm = document.getElementById("XZQH_DM").value;
	var msg="";
	if(xzqh_dm==""){
		showMessage("��ѡ������������",0);
		return false;
	}else{
		if(confirm("��ȷʵҪɾ��������������")){
			window.open("LgywsjscService.deleteXzqh.do?XZQH_DM="+xzqh_dm,"delIframe");	
		}
	}
}

/*
  @company digitalchina
  @author lizhi
  @param obj ��ѯ
  @date 2009-07-31
*/
function subquery(){
	var xzqh_dm = document.getElementById("XZQH_DM").value;
	var msg="";
	if(xzqh_dm==""){
		showMessage("��ѡ������������",0);
		return false;
	}else{
		window.open("LgywsjscService.queryGaxx.do?XZQH_DM="+xzqh_dm,"delIframe");
	}
}


