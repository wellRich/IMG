function btn_sub_query()
{	
	var dcsjq = document.getElementById("DCSJQ").value;
	var dcsjz = document.getElementById("DCSJZ").value;
	if(dcsjq==""&&dcsjz=="")
	{
		showMessage("��ѯʱ�䲻��Ϊ�գ�",0);
		return false;
	}
	if(dcsjq!=""&&dcsjz!=""){
		if(dcsjq>dcsjz){
			showMessage("���ʱ�����ܴ��ڱ��ʱ��ֹ��",0);
			return false;
		}
	}
	window.open("QhdmxzService.queryXzqhfb.do?DCSJQ="+dcsjq+"&DCSJZ="+dcsjz+"&PAGESIZE=20","operationArea1");
}

function btn_sub_reset()
{	
	document.getElementById("DCSJQ").value = "";
	document.getElementById("DCSJZ").value = "";
}


function exportFile(){
	var service = new Service("com.padis.business.xzqhwh.zxbg.qhdmxz.QhdmxzService.exportFile");
	var resultXml = service.doService();
	//var doc = loadXml(resultXml);
	var code = service.getCode();  //���ؽ����״̬
	var message = service.getMessage();  //���ص���Ϣ
	if( code != 2000 ){
		showBizMsg(code,message);
		return ;
	}else{
		showBizMsg(code,message);
	}
}